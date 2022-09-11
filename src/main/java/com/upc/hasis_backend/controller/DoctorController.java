package com.upc.hasis_backend.controller;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Patient;
import com.upc.hasis_backend.model.dto.response.ResponseDTO;
import com.upc.hasis_backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping()
    public ResponseEntity<ResponseDTO<Doctor>> getDoctorById(@RequestParam Long doctorId){

        ResponseDTO<Doctor> responseDTO = new ResponseDTO<>();
        try {
            Doctor doctor = doctorService.findDoctorById(doctorId);
            if (doctor == null){
                responseDTO.setErrorMessage("El doctor no existe.");
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            responseDTO.setData(doctor);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
