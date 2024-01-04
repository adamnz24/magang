package com.test.repository;


import com.test.entity.Biodata;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BiodataRepository extends JpaRepository<Biodata, Integer> {

    Biodata findByNpm(int npm);

    // Tambahan method jika diperlukan




}
