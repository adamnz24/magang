package com.test.entity;

import jakarta.persistence.*;
import lombok.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kapal")
public class Kapal {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int idkapal;
    public String namakapal;


}
