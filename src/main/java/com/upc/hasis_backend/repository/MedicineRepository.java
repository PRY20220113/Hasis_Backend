package com.upc.hasis_backend.repository;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {


}