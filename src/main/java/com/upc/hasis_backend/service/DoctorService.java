package com.upc.hasis_backend.service;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Patient;
import com.upc.hasis_backend.model.User;
import com.upc.hasis_backend.model.dto.request.RegisterDoctorDTO;
import com.upc.hasis_backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {


    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    UserService userService;

    public Doctor findDoctorByDni(String dni){
        return doctorRepository.findByDni(dni);
    }

    public Doctor findDoctorById(Long id){
        return doctorRepository.findById(id).orElse(null);
    }

    public List<Doctor> registerInitialDoctors(List<RegisterDoctorDTO> registerDoctorDTOS){
        List<Doctor> doctors = new ArrayList<>();
        for (RegisterDoctorDTO doctorDTO: registerDoctorDTOS){
            doctors.add(userService.registerDoctor(doctorDTO));
        }
        return doctors;
    }
}
