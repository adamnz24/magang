package com.test.service;

import com.test.entity.Cabang;
import com.test.exception.CustomIllegalArgumentException;
import com.test.repository.CabangRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CabangService {
    private static CabangRepository cabangRepository ;

    @Autowired

    public CabangService(CabangRepository repository) {
        CabangService.cabangRepository = repository;
    }

    public static Cabang saveCabang(Cabang cabang) {
        return cabangRepository.save(cabang);
    }


    public Page<Cabang> getCabang(int page, int size) {
        Pageable pageable;
        pageable = PageRequest.of(page, size);
        return cabangRepository.findAll(pageable);
    }

    public Cabang getCabangByid(int idcabang) {
        Optional<Cabang> optionalCabang = Optional.ofNullable(cabangRepository.findByIdcabang(idcabang));
        return optionalCabang.orElse(null);
    }

    public String deleteCabang(int idcabang) {
        // Cari kapal berdasarkan ID
        Optional<Cabang> optionalCabang = Optional.ofNullable(cabangRepository.findByIdcabang(idcabang));

        // Jika kapal tidak ditemukan, lempar CustomIllegalArgumentException
        if (optionalCabang.isEmpty()) {
            throw new CustomIllegalArgumentException("Kapal with ID " + idcabang + " not found");
        }

        cabangRepository.deleteById(idcabang);
        return "Data with ID " + idcabang + " deleted successfully";
    }

    public Cabang updateCabang(Cabang cabang) {
        Optional<Cabang> optionalExistingCabang = Optional.ofNullable(cabangRepository.findByIdcabang(cabang.getIdcabang()));

        // Jika kapal tidak ditemukan, lempar CustomIllegalArgumentException
        Cabang existingCabang = optionalExistingCabang.orElseThrow(() -> new CustomIllegalArgumentException("Kapal with ID " + cabang.getIdcabang() + " not found"));

        existingCabang.setName(cabang.getName());
        return cabangRepository.save(existingCabang);
    }
}

