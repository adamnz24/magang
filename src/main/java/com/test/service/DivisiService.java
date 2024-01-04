package com.test.service;
import com.test.entity.Divisi;
import com.test.exception.CustomIllegalArgumentException;
import com.test.repository.DivisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class DivisiService {
    private final DivisiRepository divisiRepository ;

    // Inside some method or constructor
    


    @Autowired
    public DivisiService(DivisiRepository repository) {
        this.divisiRepository = repository;
    }

    public Divisi saveDivisi(Divisi divisi) {
        return divisiRepository.save(divisi);
    }


    public Page<Divisi> getDivisi(int page, int size) {
        Pageable pageable;
        pageable = PageRequest.of(page, size);
        return divisiRepository.findAll(pageable);
    }

    public Divisi getdivisiByid(int iddivisi) {
        Optional<Divisi> optionalDivisi = Optional.ofNullable(divisiRepository.findByiddivisi(iddivisi));
        return optionalDivisi.orElse(null);
    }
    public Page<Divisi> sortAndSearchDivisi(int page, int size, Sort.Order order, String search) {
        return divisiRepository.findByNamadivisi(search, PageRequest.of(page, size, Sort.by(order)));
    }

    public Page<Divisi> sortDivisi(int page, int size, Sort.Order order) {
        return divisiRepository.findAll(PageRequest.of(page, size, Sort.by(order)));
    }

    public List<Divisi> searchDivisi(String namadivisi) {
        return divisiRepository.findByNamadivisi(namadivisi);
    }

    public String deleteDivisi(int iddivisi) {
        // Cari kapal berdasarkan ID
        Optional<Divisi> optionalDivisi = Optional.ofNullable(divisiRepository.findByiddivisi(iddivisi));

        // Jika kapal tidak ditemukan, lempar CustomIllegalArgumentException
        if (optionalDivisi.isEmpty()) {
            throw new CustomIllegalArgumentException("Kapal with ID " + iddivisi + " not found");
        }

        divisiRepository.deleteById(iddivisi);
        return "Data with ID " + iddivisi + " deleted successfully";
    }

    public Divisi updateDivisi(Divisi divisi) {
        Optional<Divisi> optionalExistingDivisi = Optional.ofNullable(divisiRepository.findByiddivisi(divisi.getIddivisi()));

        // Jika kapal tidak ditemukan, lempar CustomIllegalArgumentException
        Divisi existingDivisi = optionalExistingDivisi.orElseThrow(() -> new CustomIllegalArgumentException("Kapal with ID " + divisi.getIddivisi() + " not found"));

        existingDivisi.setNamadivisi(divisi.getNamadivisi());
        return divisiRepository.save(existingDivisi);
    }
}