package com.upc.hasis_backend.service;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Patient;
import com.upc.hasis_backend.model.Speciality;
import com.upc.hasis_backend.model.User;
import com.upc.hasis_backend.model.dto.request.RegisterDoctorDTO;
import com.upc.hasis_backend.model.dto.request.RegisterPatientDTO;
import com.upc.hasis_backend.repository.DoctorRepository;
import com.upc.hasis_backend.repository.PatientRepository;
import com.upc.hasis_backend.repository.UserRepository;
import com.upc.hasis_backend.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    SpecialityService specialityService;

    public Doctor registerDoctor(RegisterDoctorDTO registerDoctorDTO){

        User user = new User();
        user.setDni(registerDoctorDTO.getDni());
        user.setEmail(registerDoctorDTO.getEmail());
        user.setFirstName(registerDoctorDTO.getFirstName());
        user.setLastName(registerDoctorDTO.getLastName());
        user.setPhone(registerDoctorDTO.getPhone());
        user.setSex(registerDoctorDTO.getSex());
        user.setRegisterDate(UtilService.getNowDate());
        user.setBirthDate(registerDoctorDTO.getBirthDate());
        user.setAge(UtilService.getActualAge(registerDoctorDTO.getBirthDate()));
        user.setPassword(UtilService.encriptarContrasena(registerDoctorDTO.getPassword()));
        user.setRol("D");

        Doctor doctor = new Doctor();
        doctor.setLicense(registerDoctorDTO.getLicense());



        try {
            Speciality speciality = specialityService.findSpecialityById(registerDoctorDTO.getSpecialityId());
            doctor.setSpeciality(speciality);
            user = userRepository.save(user);
            doctor.setUser(user);
            doctor = doctorRepository.save(doctor);
            return  doctor;
        } catch ( Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Patient registerPatient(RegisterPatientDTO registerPatientDTO){

        User user = new User();
        user.setDni(registerPatientDTO.getDni());
        user.setEmail(registerPatientDTO.getEmail());
        user.setFirstName(registerPatientDTO.getFirstName());
        user.setLastName(registerPatientDTO.getLastName());
        user.setPhone(registerPatientDTO.getPhone());
        user.setSex(registerPatientDTO.getSex());
        user.setBirthDate(registerPatientDTO.getBirthDate());
        user.setRegisterDate(UtilService.getNowDate());
        user.setAge(UtilService.getActualAge(registerPatientDTO.getBirthDate()));
        user.setPassword(UtilService.encriptarContrasena(registerPatientDTO.getPassword()));
        user.setRol("P");


        Patient patient = new Patient();
        patient.setAllergy(registerPatientDTO.getAllergy());
        patient.setBloodT(registerPatientDTO.getBloodT());
        patient.setChronicD(registerPatientDTO.getChronicD());

        try {
            user = userRepository.save(user);
            patient.setUser(user);
            patient = patientRepository.save(patient);
            return  patient;
        } catch ( Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public User findUserByDni(String dni){
        return userRepository.findByDni(dni);
    }
}
