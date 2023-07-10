package com.erhan.rentacar.controller.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetCarResponse {
    private long id;
    private String brand;
    private String model;
    private int seats;
    private double pricePerDay;
}
