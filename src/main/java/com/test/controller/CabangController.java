package com.test.controller;

import com.test.entity.Cabang;
import com.test.exception.CustomIllegalArgumentException;
import com.test.service.CabangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cabang")
@RestController
public class CabangController {
    private final CabangService cabangService;
    @Autowired
    public CabangController(CabangService cabangService) {
        this.cabangService = cabangService;
    }

    @PostMapping
    public ResponseEntity<String> addCabang(Cabang cabang,
            @RequestHeader("name") String name) {
        cabang.setName(name);
        Cabang savedCabang = CabangService.saveCabang(cabang);
        return ResponseEntity.ok("Data Kapal dengan ID : " + savedCabang.getIdcabang() +
                " || dengan nama : " + savedCabang.getName() + " berhasil ditambahkan");
    }

    @GetMapping
    public ResponseEntity<Page<Cabang>> findAllCabang(
            @RequestHeader(value = "page", defaultValue = "0") int page,
            @RequestHeader(value = "size", defaultValue = "10") int size) {
        Page<Cabang> cabangPage = cabangService.getCabang(page, size);
        return ResponseEntity.ok(cabangPage);
    }


@PutMapping
public ResponseEntity<String> updateCabang(
        @RequestHeader("idcabang") int idcabang,
        @RequestHeader("name") String name,
        @Validated Cabang cabang) {
    try {
        cabang.setIdcabang(idcabang);
        cabang.setName(name); // Set nilai name dari header ke objek Cabang
        Cabang updateCabang = cabangService.updateCabang(cabang);
        if (updateCabang != null) {
            return ResponseEntity.ok("Data Kapal dengan ID: " + updateCabang.getIdcabang() + " berhasil diperbarui");
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
    public ResponseEntity<String> deleteCabang(@RequestHeader(value = "idcabang", defaultValue = "0") int idcabang) {
        try {
            String result = cabangService.deleteCabang(idcabang);
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
