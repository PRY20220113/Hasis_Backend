package com.upc.hasis_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "speciality")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long specialityId;

    private String name;

    private String description;

}
