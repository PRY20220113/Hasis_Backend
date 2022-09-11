package com.upc.hasis_backend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientDTO {

    private String dni = "";
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;
    private String phone;
    private String sex;
    private String bloodT;
    private String chronicD;
    private String allergy;
}
