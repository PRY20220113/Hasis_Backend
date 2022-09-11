package com.upc.hasis_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long userId;


    @Column(length = 8)
    private String dni = "";

    @Column(length = 120)
    private String firstName;

    @Column(length = 120)
    private String lastName;

    @Column(length = 100)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private LocalDate birthDate;

    private Integer age;

    private String phone;

    //(F)emenino-(M)asculino
    @Column(length = 1, nullable = false)
    private String sex;

    @JsonIgnore
    private LocalDate registerDate;

    //D-P
    @Column(length = 1, nullable = false)
    private String rol;


}
