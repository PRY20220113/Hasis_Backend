package com.upc.hasis_backend.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMedicineRequestDTO {
    private Integer weight;
    private Integer quantity;
    private Integer eachHour;
}
