package com.upc.hasis_backend.service;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Medicine;
import com.upc.hasis_backend.model.Patient;
import com.upc.hasis_backend.model.Recipe;
import com.upc.hasis_backend.model.dto.request.CreateMedicineRequestDTO;
import com.upc.hasis_backend.model.dto.request.CreateRecipeRequestDTO;
import com.upc.hasis_backend.repository.MedicineRepository;
import com.upc.hasis_backend.repository.RecipeRepository;
import com.upc.hasis_backend.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    MedicineRepository medicineRepository;


    public Recipe findRecipeById(Long id){
        return recipeRepository.findById(id).orElse(null);
    }

    public Recipe findActiveRecipeByPatient(Long patientId){
        return recipeRepository.findActiveRecipeByPatient(patientId);
    }

    public List<Recipe> findRecordOfRecipesByPatient(Long patientId){
        return recipeRepository.findRecordOfRecipesByPatient(patientId);
    }

    public Recipe createRecipe(CreateRecipeRequestDTO createRecipeRequestDTO, Doctor doctor, Patient patient){



        Recipe lastRecipe = recipeRepository.findActiveRecipeByDoctorAndPatient(doctor.getDoctorId(), patient.getPatientId());
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
