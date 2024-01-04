package com.test.repository;
import com.test.entity.Absensi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AbsensiRepository extends JpaRepository<Absensi, Long> {



}