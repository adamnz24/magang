package com.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "biodata")
public class Biodata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private int npm;

    @Column(nullable = false)
    private String namalengkap;

    @Column(unique = true, nullable = false)
    private int NIK;

    @Column(nullable = false)
    private String TTL;

    @Column(nullable = false)
    private String jeniskelamin;

    @Column(nullable = false)
    private String namainstansi;

    @Column(nullable = false)
    private String jenjangpendidikan;

    @Column(nullable = false)
    private String jurusan;

    @Column(unique = true, nullable = false)
    private int notelp;

    @Column(nullable = false)
    private String jenismagang;

    @Column(nullable = false)
    private String programmagang;

    @Column(nullable = false)
    private String bulanplaksanaan;

    @Column(nullable = false)
    private String durasimagang;

    @Column(nullable = false)
    private String alamat;

    @Column(nullable = false)
    private String namaortu;

    @Column(nullable = false)
    private String pekerjaanortu;

    @Column(nullable = false)
    private String divisipenempatan;

    // Tambahkan getter dan setter jika belum ada

    @OneToMany(mappedBy = "biodata")
    private Collection<Absensi> absensi;



    public void setAbsensi(Collection<Absensi> absensi) {
        this.absensi = absensi;
    }
}
