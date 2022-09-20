package com.upc.hasis_backend.controller;

import com.upc.hasis_backend.model.Recipe;
import com.upc.hasis_backend.model.Speciality;
import com.upc.hasis_backend.model.dto.response.ResponseDTO;
import com.upc.hasis_backend.service.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/speciality")
public class SpecialityController {


    @Autowired
    SpecialityService specialityService;

    @GetMapping("/all")
    private ResponseEntity<ResponseDTO<List<Speciality>>> getAllSpecialities(){
        ResponseDTO<List<Speciality>> responseDTO = new ResponseDTO<>();
        try {
            responseDTO.setData(specialityService.getAllSpecialities());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(1);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
