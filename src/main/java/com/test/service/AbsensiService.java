package com.test.service;

import com.test.entity.Absensi;
import com.test.entity.Biodata;
import com.test.repository.AbsensiRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public void saveAbsensi(Absensi absensi) {
        absensiRepository.save(absensi);
    }
    @Transactional
    public Absensi getExistingCheckin(int npm) {
        return absensiRepository.findByBiodata_NpmAndStatus(npm, "checkin");
    }
public List<Object> barChart() {
    return absensiRepository.barChart();
}
    public List<Absensi> getAllAbsensi() {
        return null;
    }



}