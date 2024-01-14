package com.test.service;

import com.test.entity.Biodata;
import com.test.exception.CustomIllegalArgumentException;
import com.test.repository.BiodataRepository;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class BiodataService {

    private final BiodataRepository biodataRepository;

    public BiodataService(BiodataRepository biodataRepository) {
        this.biodataRepository = biodataRepository;
    }

    public Biodata getBiodataByNpm(int npm) {
        return biodataRepository.findByNpm(npm);
    }

    public Biodata saveBiodata(@Validated Biodata biodata) {
        Biodata existingBiodata = getBiodataByNpm(biodata.getNpm());

        if (existingBiodata != null) {
            throw new CustomIllegalArgumentException("Peserta dengan NPM " + biodata.getNpm() + " sudah terdaftar");
        }

        return biodataRepository.save(biodata);
    }

    public Page<Biodata> getBiodata(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return biodataRepository.findAll(pageable);
    }

    public Biodata updateBiodata(@Validated Biodata biodata) {
        // Implement the update logic here
        // ...

        return biodataRepository.save(biodata);
    }

    // Sort Users by Name in Ascending Order
    public Page<Biodata> sortUsersByName(int page, int size, Sort.Order order) {
        try {
            System.out.println("Sorting Users by Name in " + (order.isAscending() ? "Ascending" : "Descending") + " Order");

            Sort sort = Sort.by(order);
            return biodataRepository.findAll(PageRequest.of(page, size, sort));
        } catch (Exception e) {
            throw new RuntimeException("Error sorting users: " + e.getMessage(), e);
        }
    }

    // Search User by Part of Name
    public List<Biodata> searchUserByName(String namalengkap) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withMatcher("namalengkap", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id", "npm", "NIK", "TTL", "jeniskelamin", "namainstansi", "jenjangpendidikan", "jurusan","notelp","jenismagang","programmagang","bulanplaksanaan","durasimagang","alamat","namaortu","pekerjaanortu","divisipenempatan");

        Biodata biodata = new Biodata();
        biodata.setNamalengkap(namalengkap);

        Example<Biodata> example = Example.of(biodata, matcher);

        return biodataRepository.findAll(example);
    }

    // Search User by NPM


    // Sort and Search Users by Name
    public Page<Biodata> sortAndSearchUsersByName(int page, int size, Sort.Order order, String namalengkap) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withMatcher("namalengkap", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id", "npm", "NIK", "TTL", "jeniskelamin", "namainstansi", "jenjangpendidikan", "jurusan","notelp","jenismagang","programmagang","bulanplaksanaan","durasimagang","alamat","namaortu","pekerjaanortu","divisipenempatan");

        Biodata biodata = new Biodata();
        biodata.setNamalengkap(namalengkap);

        Example<Biodata> example = Example.of(biodata, matcher);
        Sort sort = Sort.by(order);

        return biodataRepository.findAll(example, PageRequest.of(page, size, sort));
    }

    // Sort and Search Users by NPM
    public Page<Biodata> sortAndSearchUsersByNPM(int page, int size, Sort.Order order, String npm) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withMatcher("npm", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id", "namalengkap", "NIK", "TTL", "jeniskelamin", "namainstansi", "jenjangpendidikan", "jurusan","notelp","jenismagang","programmagang","bulanplaksanaan","durasimagang","alamat","namaortu","pekerjaanortu","divisipenempatan");

        Biodata biodata = new Biodata();
        biodata.setNpm(Integer.parseInt(npm));

        Example<Biodata> example = Example.of(biodata, matcher);
        Sort sort = Sort.by(order);

        return biodataRepository.findAll(example, PageRequest.of(page, size, sort));
    }
    public List<Biodata> getBiodataByDivision(String divisipenempatan) {
        return biodataRepository.findBydivisipenempatan(divisipenempatan);
    }
    public long countBiodataByDivision(String divisipenempatan) {
        return biodataRepository.countBydivisipenempatan(divisipenempatan);
    }
    public long countAllBiodata() {
        return biodataRepository.count();
    }
    public long countBiodataByType(String jenismagang) {
        return biodataRepository.countByjenismagang(jenismagang);
    }
    public String deleteBiodata(int id) {
        Optional<Biodata> biodataOptional = biodataRepository.findById(id);

        if (biodataOptional.isPresent()) {
            biodataRepository.deleteById(id);
            return "Biodata dengan ID " + id + " berhasil dihapus";
        } else {
            throw new CustomIllegalArgumentException("Biodata dengan ID " + id + " tidak ditemukan");
        }
    }


    public Biodata getUserByNpm(int npm) {
        return biodataRepository.findByNpm(npm);
    }


}