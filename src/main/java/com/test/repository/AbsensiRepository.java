package com.test.repository;

import com.test.entity.Absensi;
import com.test.entity.Biodata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface AbsensiRepository extends JpaRepository<Absensi, Long> {

    List<Object> barChart();

    Absensi findByBiodata_NpmAndStatus(int npm, String checkin);

    Absensi findByBiodataAndStatus(Biodata biodata, String checkin);


    }