package com.upc.hasis_backend.controller;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Patient;
import com.upc.hasis_backend.model.Recipe;
import com.upc.hasis_backend.model.Speciality;
import com.upc.hasis_backend.model.dto.request.CreateRecipeRequestDTO;
import com.upc.hasis_backend.model.dto.response.ResponseDTO;
import com.upc.hasis_backend.service.DoctorService;
import com.upc.hasis_backend.service.PatientService;
import com.upc.hasis_backend.service.RecipeService;
import com.upc.hasis_backend.service.SpecialityService;
import com.upc.hasis_backend.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;


    @Autowired
    SpecialityService specialityService;
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
    public ResponseEntity<ResponseDTO<List<Recipe>>> getActiveRecipesByPatient(@RequestParam Long patientId){

        ResponseDTO<List<Recipe>> responseDTO = new ResponseDTO<>();
        try {
            List<Recipe> recipes = recipeService.findActiveRecipeByPatient(patientId);
            if (recipes.isEmpty()){
                responseDTO.setErrorMessage("El paciente no tiene recetas activas.");
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            responseDTO.setData(recipes);
            List<Long> specialities = new ArrayList<>();
            for (Recipe recipe: recipes){
                specialities.add(recipe.getDoctor().getSpeciality().getSpecialityId());
            }
            HttpHeaders responseHeaders = new HttpHeaders();
            String specialitiesString = specialities.toString();
            responseHeaders.set("Specialities", specialitiesString.substring(1,specialitiesString.length()-1).replace(", ", "-"));

            return new ResponseEntity<>(responseDTO,responseHeaders, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/patient/speciality")
    public ResponseEntity<ResponseDTO<Recipe>> getActiveRecipeByPatientAndSpeciality(@RequestParam Long patientId, @RequestParam Long specialityId){

        ResponseDTO<Recipe> responseDTO = new ResponseDTO<>();
        try {
            Recipe recipe = recipeService.findActiveRecipeByPatientAndSpeciality(patientId,specialityId);
            if (recipe == null){
                responseDTO.setErrorMessage("El paciente no tiene una receta activa con la especialidad indicada.");
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



    @GetMapping("/patient/record")
    public ResponseEntity<ResponseDTO<List<Recipe>>> getRecordOfRecipesByPatient(@RequestParam Long patientId){

        ResponseDTO<List<Recipe>> responseDTO = new ResponseDTO<>();
        try {
            Patient patient = patientService.findPatientById(patientId);
            if (patient == null){
                responseDTO.setErrorMessage("El paciente no existe.");
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            responseDTO.setData(recipeService.findRecordOfRecipesByPatient(patientId));
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

            Speciality speciality = specialityService.findSpecialityById(doctor.getSpeciality().getSpecialityId());
            if (speciality == null){
                responseDTO.setErrorMessage("La especialidad no existe.");
                responseDTO.setErrorCode(3);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

            responseDTO.setHttpCode(HttpStatus.CREATED.value());
            responseDTO.setData(recipeService.createRecipe(recipeRequestDTO, doctor, patient));
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(4);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
