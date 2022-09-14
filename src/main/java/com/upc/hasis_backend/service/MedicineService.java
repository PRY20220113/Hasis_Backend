package com.upc.hasis_backend.service;

import com.upc.hasis_backend.model.Medicine;
import com.upc.hasis_backend.model.Recipe;
import com.upc.hasis_backend.model.dto.request.CreateMedicineRequestDTO;
import com.upc.hasis_backend.model.dto.request.UpdateMedicineRequestDTO;
import com.upc.hasis_backend.model.dto.response.ResponseDTO;
import com.upc.hasis_backend.repository.MedicineRepository;
import com.upc.hasis_backend.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MedicineService {

    @Autowired
    MedicineRepository medicineRepository;


    public Medicine findMedicineById(Long id){
        return medicineRepository.findById(id).orElse(null);
    }

    public Medicine updateMedicine(Medicine medicine, UpdateMedicineRequestDTO createMedicineRequestDTO){
        medicine.setWeight(createMedicineRequestDTO.getWeight());
        medicine.setEachHour(createMedicineRequestDTO.getEachHour());
        medicine.setQuantity(createMedicineRequestDTO.getQuantity());
        medicine.setEndDate(UtilService.getEndDay(medicine.getPrescribedDays()));
        return  medicineRepository.save(medicine);
    }

}
