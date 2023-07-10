package com.erhan.rentacar.controller.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCarRequest {
    private String brand;
    private String model;
    private Integer seats;
    private Double pricePerDay;
}
