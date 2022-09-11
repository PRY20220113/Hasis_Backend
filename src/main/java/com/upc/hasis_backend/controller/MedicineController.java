package com.upc.hasis_backend.controller;

import com.upc.hasis_backend.model.Medicine;
import com.upc.hasis_backend.model.Recipe;
import com.upc.hasis_backend.model.dto.request.CreateMedicineRequestDTO;
import com.upc.hasis_backend.model.dto.request.UpdateMedicineRequestDTO;
import com.upc.hasis_backend.model.dto.response.ResponseDTO;
import com.upc.hasis_backend.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    MedicineService medicineService;

    @GetMapping()
    public ResponseEntity<ResponseDTO<Medicine>> getMedicineById(@RequestParam Long medicineId){

        ResponseDTO<Medicine> responseDTO = new ResponseDTO<>();
        try {
            Medicine medicine = medicineService.findMedicineById(medicineId);
            if (medicine == null){
                responseDTO.setErrorMessage("La receta no existe.");
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            responseDTO.setData(medicine);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch (Exception e){
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setErrorCode(2);
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<ResponseDTO<Medicine>> updateMedicine(@RequestParam Long medicineId, @RequestBody UpdateMedicineRequestDTO updateMedicineRequestDTO){

        ResponseDTO<Medicine> responseDTO = new ResponseDTO<>();
        try {
            Medicine medicine = medicineService.findMedicineById(medicineId);
            if (medicine == null){
                responseDTO.setErrorMessage("El medicamento no existe.");
                responseDTO.setErrorCode(1);
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            responseDTO.setData(medicineService.updateMedicine(medicine, updateMedicineRequestDTO));
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
