package com.test.controller;

import com.test.entity.Absensi;
import com.test.entity.Biodata;
import com.test.service.AbsensiService;
import com.test.service.BiodataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
public class AbsensiController {
    @Autowired
    private AbsensiService absensiService;

    @Autowired
    private BiodataService biodataService;

    @PostMapping("/checkin")
    public ResponseEntity<String> checkin(@RequestHeader("npm") int npm,
                                          @RequestHeader("divisi") String divisi,
                                          @RequestHeader("kapal") String kapal,
                                          @RequestHeader("location") String location,
                                          @RequestHeader("typemagang") String typemagang) {
        try {
            // Validasi NPM
            if (npm <= 0) {
                return ResponseEntity.badRequest().body("Invalid NPM");
            }

            Biodata biodata = biodataService.getBiodataByNpm(npm);
            if (biodata == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Biodata not found");
            }

            Absensi checkinData = new Absensi();
            checkinData.setBiodata(biodata);
            checkinData.setStatus("checkin");
            checkinData.setTimestamp(LocalDateTime.now());
            checkinData.setWaktuMasuk(LocalDateTime.now());
            checkinData.setDivisi(divisi);
            checkinData.setKapal(kapal);
            checkinData.setLocation(location);
            checkinData.setTypemagang(typemagang);

            // Set name from biodata
            checkinData.setName(biodata.getName());

            absensiService.saveAbsensi(checkinData);

            return ResponseEntity.ok("Checkin With NPM " + npm + " Successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
