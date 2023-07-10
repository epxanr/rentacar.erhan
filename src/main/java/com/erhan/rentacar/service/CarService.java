package com.erhan.rentacar.service;

import com.erhan.rentacar.entity.Car;
import com.erhan.rentacar.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository repository;

    public Iterable<Car> getAll()
    {
        return repository.findAll();
    }

    public Optional<Car> getById(Long id)
    {
        return repository.findById(id);
    }

    public Optional<Car> create(String brand, String model, int seats, double pricePerDay)
    {
        if(brand == null || model == null) return Optional.empty();

        var car = new Car(brand, model, seats, pricePerDay);
        return Optional.of(repository.save(car));
    }

    public Optional<Car> update(long id, String brand, String model, Integer seats, Double pricePerDay)
    {
        var carOpt = repository.findById(id);
        if(carOpt.isEmpty()) return Optional.empty();
        var car = carOpt.get();

        if(brand != null)
            car.setBrand(brand);
        if(model != null)
            car.setModel(model);
        if(seats != null)
            car.setSeats(seats);
        if(pricePerDay != null)
            car.setPricePerDay(pricePerDay);

        return Optional.of(repository.save(car));
    }

    public boolean deleteById(Long id)
    {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
