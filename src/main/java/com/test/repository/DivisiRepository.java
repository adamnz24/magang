package com.test.repository;

import com.test.entity.Divisi;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
@EnableJpaRepositories
public interface DivisiRepository extends JpaRepository<Divisi,Integer> {

    Divisi findByiddivisi(int iddivisi);

    Page<Divisi> findByNamadivisi(String keyword, PageRequest of);
}
