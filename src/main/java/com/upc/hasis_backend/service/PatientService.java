package com.upc.hasis_backend.service;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Patient;
import com.upc.hasis_backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;



    public Patient findPatientByDni(String dni){
        return patientRepository.findByDni(dni);
    }


    public Patient findPatientById(Long id){
        return patientRepository.findById(id).orElse(null);
    }

}
