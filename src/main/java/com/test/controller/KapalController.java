package com.test.controller;


import com.test.entity.Kapal;
import com.test.exception.CustomIllegalArgumentException;
import com.test.service.KapalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RequestMapping("/kapal")
@RestController
@Validated
public class KapalController {

    private final KapalService kapalService;

    @Autowired
    public KapalController(KapalService kapalService) {
        this.kapalService = kapalService;
    }

    @PostMapping
    public ResponseEntity<String> addKapal(@Validated @RequestBody Kapal kapal) {
        Kapal savedKapal = kapalService.saveKapal(kapal);
        if (savedKapal != null) {
            return ResponseEntity.ok("Data Kapal dengan ID : " + savedKapal.getIdkapal() +
                    " || dengan nama : " + savedKapal.getNamakapal() + " berhasil ditambahkan");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menambahkan data Kapal");
        }
    }

    @GetMapping
    public ResponseEntity<Page<Kapal>> findAllBiodata(
            @RequestHeader(defaultValue = "0") int page,
            @RequestHeader(defaultValue = "10") int size) {
        Page<Kapal> kapalPage = kapalService.getKapal(page, size);
        return ResponseEntity.ok(kapalPage);
    }

    @PutMapping
    public ResponseEntity<String> updateKapal(@Validated @RequestBody Kapal kapal) {
        try {
            Kapal updatedKapal = kapalService.updateKapal(kapal);
            if (updatedKapal != null) {
                return ResponseEntity.ok("Data Kapal dengan ID: " + updatedKapal.getIdkapal() + " berhasil diperbarui");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memperbarui data Kapal");
            }
        } catch (CustomIllegalArgumentException e) {
            String errorMessage = "Invalid request: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing the request: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteKapal(@PathVariable int idkapal) {
        try {
            String result = kapalService.deleteKapal(idkapal);
            return ResponseEntity.ok(result);
        } catch (CustomIllegalArgumentException e) {
            String errorMessage = "Invalid request: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing the request: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}
