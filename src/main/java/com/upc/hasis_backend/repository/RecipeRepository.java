package com.upc.hasis_backend.repository;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select r from Recipe r where r.doctor.doctorId = ?1 and r.patient.patientId = ?2 and r.status = 1")
    public Recipe findActiveRecipeByDoctorAndPatient(Long doctor_doctorId, Long patient_patientId);


    @Query("select r from Recipe r where r.patient.patientId = ?1 and r.status = 1")
    public Recipe findActiveRecipeByPatient(Long patientId);

}