package com.upc.hasis_backend.service;

import com.upc.hasis_backend.model.Speciality;
import com.upc.hasis_backend.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityService {

    @Autowired
    SpecialityRepository specialityRepository;


    public List<Speciality> getAllSpecialities(){
        return specialityRepository.findAll();
    }

    public Speciality findSpecialityById(Long specialityId){
        return specialityRepository.findById(specialityId).orElse(null);
    }

}
