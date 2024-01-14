package com.test.repository;


import com.test.entity.Biodata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface BiodataRepository extends JpaRepository<Biodata, Integer> {

    Biodata findByNpm(int npm);

    List<Biodata> findBydivisipenempatan(String divisipenempatan);

    long countBydivisipenempatan(String divisipenempatan);

    long countByjenismagang(String jenismagang);



    // Tambahan method jika diperlukan




}
