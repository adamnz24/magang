package com.test.repository;


import com.test.entity.Cabang;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
@EnableJpaRepositories
public interface CabangRepository extends JpaRepository<Cabang, Integer> {
    Cabang findByIdcabang(int idcabang);
}
