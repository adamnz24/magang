package com.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "absensi")

public class Absensi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "npm", referencedColumnName = "npm")
    public Biodata biodata;


    public String status; // "checkin" atau "checkout"


    public LocalDateTime timestamp;


    public String location;

    // Tambahkan kolom checkoutStatus

    // Tambahkan kolom-kolom tambahan

    public String name;


    public String typemagang;


    public String kapal;


    public String divisi;


    public LocalDateTime waktuMasuk;


    public LocalDateTime waktuKeluar;
}


