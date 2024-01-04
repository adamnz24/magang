package com.test.entity;

import jakarta.persistence.*;
import lombok.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "cabang")
public class Cabang {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int idcabang;
    public String name;
    public void setName(String name) {
        this.name = name;
    }
    public void setIdcabang(int idcabang) {
        this.idcabang = idcabang;
    }


}

