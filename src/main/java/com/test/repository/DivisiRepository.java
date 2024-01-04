package com.test.repository;

import com.test.entity.Divisi;
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
public interface DivisiRepository extends JpaRepository<Divisi,Integer> {

    Divisi findByiddivisi(int iddivisi);
    List<Divisi> findByNamadivisi(String namadivisi);

    Page<Divisi> findByNamadivisi(String search, PageRequest of);
}
