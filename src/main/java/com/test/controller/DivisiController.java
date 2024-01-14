package com.test.controller;

import com.test.entity.Divisi;
import com.test.exception.CustomIllegalArgumentException;
import com.test.service.DivisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RequestMapping("/divisi")
@RestController
public class DivisiController {
    private final DivisiService divisiService;
    @Autowired
    public DivisiController(DivisiService divisiService) {
        this.divisiService = divisiService;
    }

    @PostMapping
    public ResponseEntity<String> addDivisi(
            @RequestHeader(value = "namadivisi", defaultValue = "") String namadivisi) {

        ValidationStatus validationStatus = validateInput(namadivisi);

        switch (validationStatus) {
            case SUCCESS:
                // Lakukan operasi tambahan jika diperlukan
                return ResponseEntity.ok("Divisi : "+namadivisi+" berhasil ditambahkan");
            case EMPTY_NAME:
                return ResponseEntity.badRequest().body("Nama divisi tidak boleh kosong");
            default:
                return ResponseEntity.badRequest().body("Validasi gagal");
        }
    }

    private ValidationStatus validateInput(String namadivisi) {
        return (namadivisi == null || namadivisi.isEmpty() || divisiService == null)
                ? ValidationStatus.EMPTY_NAME
                : ValidationStatus.SUCCESS;
    }

    enum ValidationStatus {
        SUCCESS,
        EMPTY_NAME,

    }

    @GetMapping
    public ResponseEntity<Page<Divisi>> findAllDivisi(
            @RequestHeader(name = "keyword", defaultValue = "") String keyword,
            @RequestHeader(name = "page", defaultValue = "0") int page,
            @RequestHeader(name = "size", defaultValue = "10") int size) {
        Page<Divisi> divisiPage;

        if (keyword.isEmpty()) {
            divisiPage = divisiService.getDivisi(page, size);
        } else {
            divisiPage = divisiService.searchDivisi(keyword, page, size);
        }

        if (divisiPage.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(divisiPage);
        }
    }



    @PutMapping
    public ResponseEntity<String> updateDivisi(
            @RequestHeader(value = "iddivisi", defaultValue = "0") int iddivisi,
            @RequestHeader(value = "namadivisi", defaultValue = "") String namadivisi) {
        try {
            Divisi divisi = new Divisi();
            divisi.setIddivisi(iddivisi);
            divisi.setNamadivisi(namadivisi);

            Divisi updateDivisi = divisiService.updateDivisi(divisi);
            if (updateDivisi != null) {
                return ResponseEntity.ok("Data Divisi dengan ID: " + updateDivisi.getIddivisi() + " berhasil diperbarui");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal memperbarui data Divisi");
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
    public ResponseEntity<String> deleteDivisi(@RequestHeader("iddivisi") int iddivisi) {
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