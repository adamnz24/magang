package com.test.repository;


import com.test.entity.Kapal;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Configuration
@EnableJpaRepositories
public interface KapalRepository extends JpaRepository<Kapal,Integer> {



    Kapal findById(int idkapal);



    Page<Kapal> findByNamakapalContainingIgnoreCase(String search, PageRequest of);

    List<Kapal> findByNamakapal(String namakapal);
}
