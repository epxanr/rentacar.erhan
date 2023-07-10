package com.erhan.rentacar.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cars")
public class Car {

    public Car(String brand, String model, int seats, double pricePerDay)
    {
        this.brand = brand;
        this.model = model;
        this.seats = seats;
        this.pricePerDay = pricePerDay;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String brand;
    private String model;
    private int seats;
    private double pricePerDay;
}
