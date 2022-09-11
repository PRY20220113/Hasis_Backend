package com.upc.hasis_backend.controller;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Patient;
import com.upc.hasis_backend.model.Recipe;
import com.upc.hasis_backend.model.dto.request.CreateRecipeRequestDTO;
import com.upc.hasis_backend.model.dto.response.ResponseDTO;
import com.upc.hasis_backend.service.DoctorService;
import com.upc.hasis_backend.service.PatientService;
import com.upc.hasis_backend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;

    @GetMapping()
    public ResponseEntity<ResponseDTO<Recipe>> getRecipeById(@RequestParam Long recipeId){

        ResponseDTO<Recipe> responseDTO = new ResponseDTO<>();
        try {
            Recipe recipe = recipeService.findRecipeById(recipeId);
            if (recipe == null){
                responseDTO.setErrorMessage("La receta no existe.");
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            responseDTO.setData(recipe);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patient")
    public ResponseEntity<ResponseDTO<Recipe>> getActiveRecipeByPatient(@RequestParam Long patientId){

        ResponseDTO<Recipe> responseDTO = new ResponseDTO<>();
        try {
            Recipe recipe = recipeService.findActiveRecipeByPatient(patientId);
            if (recipe == null){
                responseDTO.setErrorMessage("El paciente no tiene una receta activa.");
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            responseDTO.setData(recipe);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping()
    public ResponseEntity<ResponseDTO<Recipe>> createRecipe(@RequestBody CreateRecipeRequestDTO recipeRequestDTO){

        ResponseDTO<Recipe> responseDTO = new ResponseDTO<>();
        try {
            Doctor doctor = doctorService.findDoctorById(recipeRequestDTO.getDoctorId());
            if (doctor == null){
                responseDTO.setErrorMessage("El doctor no existe.");
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            Patient patient = patientService.findPatientById(recipeRequestDTO.getPatientId());
            if (patient == null){
                responseDTO.setErrorMessage("El paciente no existe.");
                responseDTO.setErrorCode(2);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setData(recipeService.createRecipe(recipeRequestDTO, doctor, patient));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(3);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
