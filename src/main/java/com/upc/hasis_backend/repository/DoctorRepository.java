package com.upc.hasis_backend.repository;

import com.upc.hasis_backend.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    @Query("select d from Doctor d where d.user.dni = ?1")
    public Doctor findByDni(String user_dni);




}