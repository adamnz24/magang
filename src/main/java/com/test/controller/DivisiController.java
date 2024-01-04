package com.test.controller;
import com.test.entity.Divisi;
import com.test.exception.CustomIllegalArgumentException;
import com.test.service.DivisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RequestMapping()
@RestController
public class DivisiController {
    private final DivisiService divisiService;
    @Autowired
    public DivisiController(DivisiService divisiService) {
        this.divisiService = divisiService;
    }
@Secured("ROLE_ADMIN")
    @PostMapping("/a")
    public ResponseEntity<String> addDivisi(@Validated @RequestBody Divisi divisi) {
        Divisi savedDivisi = divisiService.saveDivisi(divisi);
        return ResponseEntity.ok("Data Divisi dengan ID : " + savedDivisi.getIddivisi() +
                " || dengan nama : " + savedDivisi.getNamadivisi() + " berhasil ditambahkan");
    }
@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showdivisi")
    public ResponseEntity<Page<Divisi>> findAllDivisi(
            @RequestHeader(defaultValue = "0") int page,
            @RequestHeader(defaultValue = "10") int size) {
        Page<Divisi> divisiPage = divisiService.getDivisi(page, size);
        return ResponseEntity.ok(divisiPage);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/show_divisi_Byid/{iddivisi}")
    public ResponseEntity<Divisi> findByiddivisi(@PathVariable int iddivisi) {
        Divisi divisi = divisiService.getdivisiByid(iddivisi);
        if (divisi != null) {
            return ResponseEntity.ok(divisi);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Secured("ROLE_ADMIN")
    @PutMapping("/update_divisi")
    public ResponseEntity<String> updateDivisi(@Validated @RequestBody Divisi divisi) {
        try {
            Divisi updateDivisi = divisiService.updateDivisi(divisi);
            if (updateDivisi != null) {
                return ResponseEntity.ok("Data Kapal dengan ID: " + updateDivisi.getIddivisi() + " berhasil diperbarui");
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
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete_divisiById/{iddivisi}")
    public ResponseEntity<String> deleteDivisi(@PathVariable int iddivisi) {
        try {
            String result = divisiService.deleteDivisi(iddivisi);
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