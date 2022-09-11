package com.upc.hasis_backend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecipeRequestDTO {

    private Long patientId;
    private Long doctorId;
    private List<CreateMedicineRequestDTO> medicines;


}
