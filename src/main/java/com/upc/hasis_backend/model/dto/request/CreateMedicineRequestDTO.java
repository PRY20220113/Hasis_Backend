package com.upc.hasis_backend.model.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upc.hasis_backend.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMedicineRequestDTO {
    private String name;
    private Integer weight;
    private Integer quantity;
    private Integer eachHour;
    private Integer prescribedDays;
}
