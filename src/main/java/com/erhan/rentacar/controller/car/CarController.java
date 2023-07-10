package com.erhan.rentacar.controller.car;

import com.erhan.rentacar.controller.Mapper;
import com.erhan.rentacar.service.CarService;
import com.erhan.rentacar.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/car")
public class CarController {
    private final CarService carService;
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<GetCarResponse[]> getAll()
    {
        var array = Mapper.map(carService.getAll(), GetCarResponse[].class);
        return ResponseEntity.ok(array);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<GetCarResponse> getCar(@PathVariable Long id) {
        var car = carService.getById(id);
        if(car.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(Mapper.map(car.get(), GetCarResponse.class));
    }

    @GetMapping(path = "/{id}/reservations")
    public ResponseEntity<GetCarReservationResponse[]> getCarReservations(@PathVariable long id)
    {
        var carOpt = carService.getById(id);
        if(carOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var reservations = reservationService.getReservationsByCarId(id);

        var array = Mapper.map(reservations, GetCarReservationResponse[].class);

        return ResponseEntity.ok(array);
    }

    @PostMapping
    public ResponseEntity<GetCarResponse> createCar(@RequestBody CreateCarRequest request) {
        var carOpt = carService.create(
                request.getBrand(),
                request.getModel(),
                request.getSeats(),
                request.getPricePerDay());

        var response = Mapper.map(carOpt.get(), GetCarResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetCarResponse> updateCar(@PathVariable long id, @RequestBody UpdateCarRequest request)
    {
        var carOpt = carService.update(
                id,
                request.getBrand(),
                request.getModel(),
                request.getSeats(),
                request.getPricePerDay());

        if(carOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var response = Mapper.map(carOpt.get(), GetCarResponse.class);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteCar(@PathVariable Long id){
        if(carService.deleteById(id))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }
}
