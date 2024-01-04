package com.test.service;
import com.test.entity.Kapal;
import com.test.exception.CustomIllegalArgumentException;
import com.test.repository.KapalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KapalService {

@Autowired
    private final KapalRepository kapalRepository;

    public KapalService(KapalRepository repository) {
        this.kapalRepository = repository;
    }

    public Kapal saveKapal(Kapal kapal) {
        return kapalRepository.save(kapal);
    }

    public Page<Kapal> getKapal(int page, int size) {
        return kapalRepository.findAll(PageRequest.of(page, size));
    }
    public Kapal getkapalByid(int idkapal) {
        Optional<Kapal> optionalKapal = Optional.ofNullable(kapalRepository.findById(idkapal));
        return optionalKapal.orElse(null);
    }

    public Page<Kapal> sortAndSearchKapal(int page, int size, Sort.Order order, String search) {
        return kapalRepository.findByNamakapalContainingIgnoreCase(search, PageRequest.of(page, size, Sort.by(order)));
    }

    public Page<Kapal> sortKapal(int page, int size, Sort.Order order) {
        return kapalRepository.findAll(PageRequest.of(page, size, Sort.by(order)));
    }

    public List<Kapal> searchKapal(String namakapal) {
        return kapalRepository.findByNamakapal(namakapal);
    }


    public String deleteKapal(int idkapal) {
        // Cari kapal berdasarkan ID
        Optional<Kapal> optionalKapal = Optional.ofNullable(kapalRepository.findById(idkapal));

        // Jika kapal tidak ditemukan, lempar CustomIllegalArgumentException
        if (optionalKapal.isEmpty()) {
            throw new CustomIllegalArgumentException("Kapal with ID " + idkapal + " not found");
        }

        kapalRepository.deleteById(idkapal);
        return "Data with ID " + idkapal + " deleted successfully";
    }

    public Kapal updateKapal(Kapal kapal) throws CustomIllegalArgumentException {
        Optional<Kapal> optionalExistingKapal = Optional.ofNullable(kapalRepository.findById(kapal.getIdkapal()));

        // Jika kapal tidak ditemukan, lempar CustomIllegalArgumentException
        Kapal existingKapal = optionalExistingKapal.orElseThrow(() -> new CustomIllegalArgumentException("Kapal with ID " + kapal.getIdkapal() + " not found"));

        existingKapal.setNamakapal(kapal.getNamakapal());
        return kapalRepository.save(existingKapal);
    }









}




