package com.test.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "biodata")
public class Biodata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id ;
    @Column(unique = true)
    public int npm;

    public String namalengkap;
    @Column(unique = true)
    public int NIK;
    public String TTL;
    public String jeniskelamin;
    public String namainstansi;
    public String jenjangpendidikan;
    public String jurusan;
    @Column(unique = true)
    public int notelp;
    public String jenismagang;
    public String programmagang;
    public Date bulanplaksanaan;
    public String durasimagang;
    public String alamat;
    public String namaortu;
    public String pekerjaanortu;
    public String divisipenempatan;

    public String getName() {
        return namalengkap;
    }
}
