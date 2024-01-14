package com.test.controller;

import com.test.entity.Absensi;
import com.test.entity.Biodata;
import com.test.service.AbsensiService;
import com.test.service.BiodataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/absensi")

public class AbsensiController {
    private final double latitudeDefault;
    private final double longitudeDefault;
    private final double ALLOWED_LATITUDE;
    private final double ALLOWED_LONGITUDE;
    private static final double ALLOWED_RADIUS = 0.07;
    private static final int EARTH_RADIUS = 6371;

    private final AbsensiService absensiService;

    private final BiodataService biodataService;

    public AbsensiController(@Value("${latitudeDefault}") double latitudeDefault,
                             @Value("${longitudeDefault}") double longitudeDefault,
            AbsensiService absensiService, BiodataService biodataService) {
        this.latitudeDefault = latitudeDefault;
        this.longitudeDefault = longitudeDefault;
        this.ALLOWED_LATITUDE = latitudeDefault;
        this.ALLOWED_LONGITUDE = longitudeDefault;
        this.absensiService = absensiService;
        this.biodataService = biodataService;
    }


    @PostMapping("/checkin")
    public ResponseEntity<String> checkin(@RequestHeader("npm") int npm,
                                          @RequestHeader("divisi") String divisi,
                                          @RequestHeader("kapal") String kapal,
                                          @RequestHeader(value = "latitude", defaultValue = "0.0") double latitude,
                                          @RequestHeader(value = "longitude", defaultValue = "0.0") double longitude,
                                          @RequestHeader("typemagang") String typemagang) {
        try {
            // Validasi NPM
            if (npm <= 0) {
                return ResponseEntity.badRequest().body("NPM tidak valid");
            }

            Biodata biodata = biodataService.getBiodataByNpm(npm);
            if (biodata == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Biodata tidak ditemukan");
            }

            // Validasi lokasi geografis dengan koordinat yang diizinkan dan jarak 100 meter
            if (!isValidLocation(latitude, longitude)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Anda harus berada di radius 100 meter dari lokasi yang diizinkan");
            }

            Absensi existingCheckin = absensiService.getExistingCheckin(npm);
            if (existingCheckin != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Anda sudah melakukan check-in sebelumnya");
            }

            Absensi checkinData = new Absensi();
            checkinData.setBiodata(biodata);
            checkinData.setNpm(biodata.getNpm()); // Pastikan npm diset dari biodata
            checkinData.setStatus("Hadir");
            checkinData.setTimestamp(LocalDateTime.now());
            checkinData.setWaktuMasuk(LocalDateTime.now());
            checkinData.setDivisi(divisi);
            checkinData.setKapal(kapal);
            checkinData.setLocation(String.format("(%f, %f)", latitude, longitude));
            checkinData.setTypemagang(typemagang);
            checkinData.setName(biodata.getNamalengkap());

            absensiService.saveAbsensi(checkinData);

            return ResponseEntity.ok("Checkin dengan NPM " + npm + " berhasil");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during checkin: " + e.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestHeader("npm") int npm,
                                           @RequestHeader("divisi") String divisi,
                                           @RequestHeader("kapal") String kapal,
                                           @RequestHeader(value = "latitude", defaultValue = "0.0") double latitude,
                                           @RequestHeader(value = "longitude", defaultValue = "0.0") double longitude,
                                           @RequestHeader("typemagang") String typemagang) {
        try {
            // Validasi NPM
            if (npm <= 0) {
                return ResponseEntity.badRequest().body("NPM tidak valid");
            }

            Biodata biodata = biodataService.getBiodataByNpm(npm);
            if (biodata == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Biodata tidak ditemukan");
            }

            // Validasi lokasi geografis dengan koordinat yang diizinkan dan jarak 100 meter
            if (!isValidLocation(latitude, longitude)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Anda harus berada di koordinat yang diizinkan (kurang dari 100 meter) untuk melakukan checkout");
            }

            Absensi existingCheckin = absensiService.getExistingCheckin(npm);
            if (existingCheckin == null) {
                return ResponseEntity.notFound().build();
            }

            // Update existing checkin with checkout data
            existingCheckin.setNpm(biodata.getNpm()); // Pastikan npm diset dari biodata
            existingCheckin.setStatus("Hadir");
            existingCheckin.setTimestamp(LocalDateTime.now());
            existingCheckin.setWaktuKeluar(LocalDateTime.now());
            existingCheckin.setDivisi(divisi);
            existingCheckin.setKapal(kapal);
            existingCheckin.setLocation(String.format("(%f, %f)", latitude, longitude));
            existingCheckin.setTypemagang(typemagang);

            absensiService.saveAbsensi(existingCheckin);

            return ResponseEntity.ok("Checkout With NPM " + npm + " Successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during checkout: " + e.getMessage());
        }
    }

    private boolean isValidLocation(double latitude, double longitude) {
        double distance = haversine(latitude, longitude, ALLOWED_LATITUDE, ALLOWED_LONGITUDE, EARTH_RADIUS);
        System.out.println("Distance: " + distance); // Tambahkan ini untuk debugging
        System.out.println(latitude + ", " + longitude);
        return distance <= (double) 100;
    }


    private double haversine(double lat1, double lon1, double lat2, double lon2, double allowedRadius) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c * 1000; // Dikonversi ke meter
    }




}
