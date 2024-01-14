package com.test.entity;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("ALL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "absensi")
public class Absensi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "npm", referencedColumnName = "npm")
    private Biodata biodata;

    @Column(nullable = false)
    private String status; // "checkin" atau "checkout"

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String location; // Memisahkan latitude dan longitude mungkin lebih baik

    private String name;

    private String typemagang;

    private String kapal;

    private String divisi;


    private LocalDateTime waktuMasuk;


    private LocalDateTime waktuKeluar;

    private int radius;

    // Tambahkan getter dan setter jika belum ada
    public int getNpm() {
        return (biodata != null) ? biodata.getNpm() : 0;
    }

    public void setNpm(int npm) {

        if (biodata != null) {
            biodata.setNpm(npm);
        }
    }
}
