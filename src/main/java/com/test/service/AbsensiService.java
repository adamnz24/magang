package com.test.service;

import com.test.entity.Absensi;
import com.test.repository.AbsensiRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class AbsensiService {
    private final AbsensiRepository absensiRepository;


    public BiodataService biodataService;

    public AbsensiService(AbsensiRepository absensiRepository, BiodataService biodataService) {
        this.absensiRepository = absensiRepository;
        this.biodataService = biodataService;
    }



//    public List<Absensi> getAbsensiByNpm(int npm) {
//        return absensiRepository.findByUser_NpmOrderByTimestampDesc(npm);
//    }

    public Long getTotalAbsensi() {
        return absensiRepository.count();
    }
//    public void checkout(int npm, String location, String name, String typemagang, String kapal, String divisi) {
//        User user = biodataService.getUserByNpm(npm);
//        if (user != null) {
//            Absensi existingCheckin = absensiRepository.findByUserAndStatus(user, "checkin");
//
//            if (existingCheckin != null) {
//                // Update existing checkin with checkout data
//                existingCheckin.setStatus("checkout");
//                existingCheckin.setTimestamp(LocalDateTime.now());
//                existingCheckin.setWaktuKeluar(LocalDateTime.now());
//                existingCheckin.setLocation(location);
//                existingCheckin.setName(name);
//                existingCheckin.setTypemagang(typemagang);
//                existingCheckin.setKapal(kapal);
//                existingCheckin.setDivisi(divisi);
//
//                absensiRepository.save(existingCheckin);
//            }
//        }
//    }
    public void saveAbsensi(Absensi absensi) {
        absensiRepository.save(absensi);
    }
//    @Transactional
//    public Absensi getExistingCheckin(int npm) {
//        return absensiRepository.findByUser_NpmAndStatus(npm, "checkin");
//    }

    public List<Absensi> getAllAbsensi() {
        return null;
    }
}