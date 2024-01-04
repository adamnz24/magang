package com.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "divisi")

public class Divisi {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int iddivisi;
    public String namadivisi;
}
