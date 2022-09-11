package com.upc.hasis_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "medicine")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long medicineId;

    private String name;

    private Integer weight;

    private Integer quantity;

    private Integer eachHour;

    private Integer prescribedDays;

    private LocalDate startDate;

    private LocalDate endDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recipeId",nullable = false)
    private Recipe recipe;

}
