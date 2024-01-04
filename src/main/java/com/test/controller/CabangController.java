package com.test.controller;

import com.test.entity.Cabang;
import com.test.exception.CustomIllegalArgumentException;
import com.test.service.CabangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/test")
@RestController
public class CabangController {
    private final CabangService cabangService;
    @Autowired
    public CabangController(CabangService cabangService) {
        this.cabangService = cabangService;
    }
@PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<String> addCabang(@Validated @RequestBody Cabang cabang) {
        Cabang savedCabang = CabangService.saveCabang(cabang);
        return ResponseEntity.ok("Data Kapal dengan ID : " + savedCabang.getIdcabang() +
                " || dengan nama : " + savedCabang.getName() + " berhasil ditambahkan");
    }
//@Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<Page<Cabang>> findAllCabang(
            @RequestHeader(defaultValue = "0") int page,
            @RequestHeader(defaultValue = "10") int size) {
        Page<Cabang> cabangPage = cabangService.getCabang(page, size);
        return ResponseEntity.ok(cabangPage);
    }
    // Sort Users by Name in Ascending Order
@PreAuthorize("hasRole('ADMIN')")
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
@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<String> deleteCabang(@RequestHeader("idcabang") int idcabang) {
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
