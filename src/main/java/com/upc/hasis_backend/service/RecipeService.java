package com.upc.hasis_backend.service;

import com.upc.hasis_backend.model.*;
import com.upc.hasis_backend.model.dto.request.CreateMedicineRequestDTO;
import com.upc.hasis_backend.model.dto.request.CreateRecipeRequestDTO;
import com.upc.hasis_backend.repository.MedicineRepository;
import com.upc.hasis_backend.repository.RecipeRepository;
import com.upc.hasis_backend.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    MedicineRepository medicineRepository;


    public Recipe findRecipeById(Long id){
        return recipeRepository.findById(id).orElse(null);
    }

    public List<Recipe> findActiveRecipeByPatient(Long patientId){
        return recipeRepository.findActiveRecipesByPatient(patientId);
    }

    public Recipe findActiveRecipeByPatientAndSpeciality(Long patientId, Long specialityId){
        return recipeRepository.findActiveRecipeByPatientAndSpeciality(patientId, specialityId);
    }

    public List<Speciality> findActivySpecialityByPatient(Long patientId){
        List<Speciality> specialities = new ArrayList<>(recipeRepository.findActiveSpecialityByPatient(patientId));
        return new ArrayList<>(specialities);
    }

    public List<Recipe> findRecordOfRecipesByPatient(Long patientId){
        return recipeRepository.findRecordOfRecipesByPatient(patientId);
    }

    public Recipe closeRecipe(Recipe recipe){
        recipe.setStatus(0);
        return recipeRepository.save(recipe);
    }

    public Recipe createRecipe(CreateRecipeRequestDTO createRecipeRequestDTO, Doctor doctor, Patient patient){



        Recipe lastRecipe = recipeRepository.findActiveRecipeByPatientAndSpeciality(patient.getPatientId(), doctor.getSpeciality().getSpecialityId());
        if (lastRecipe != null){
            lastRecipe.setStatus(0);
            lastRecipe = recipeRepository.save(lastRecipe);
        }

        Recipe recipe = new Recipe();
        recipe.setDoctor(doctor);
        recipe.setPatient(patient);
        recipe.setStatus(1);
        recipe.setDescription(createRecipeRequestDTO.getDescription());

        try {
            recipe = recipeRepository.save(recipe);

            List<Medicine> medicines = new ArrayList<>();
            Medicine medicine;
            for (CreateMedicineRequestDTO request: createRecipeRequestDTO.getMedicines()) {
                medicine = new Medicine();
                medicine.setName(request.getName());
                medicine.setEachHour(request.getEachHour());
                medicine.setQuantity(request.getQuantity());
                medicine.setPrescribedDays(request.getPrescribedDays());
                medicine.setWeight(request.getWeight());
                medicine.setRecipe(recipe);
                medicine.setStartDate(UtilService.getNextDay());
                medicine.setEndDate(UtilService.getEndDay(medicine.getPrescribedDays()));
                medicineRepository.save(medicine);
                medicines.add(medicine);
            }
            recipe.setMedicines(medicines);
            return recipe;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
