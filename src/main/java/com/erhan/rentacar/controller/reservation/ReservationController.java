package com.erhan.rentacar.controller.reservation;

import com.erhan.rentacar.controller.Mapper;
import com.erhan.rentacar.controller.car.GetCarResponse;
import com.erhan.rentacar.controller.user.GetUserResponse;
import com.erhan.rentacar.entity.Reservation;
import com.erhan.rentacar.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/reservation")
@Slf4j
public class ReservationController {
    private final ReservationService service;

    @GetMapping
    public ResponseEntity<GetReservationResponse[]> getAll(@RequestParam(required = false) String startDate,
                                                           @RequestParam(required = false) String endDate)
    {
        Iterable<Reservation> reservations;

        if(startDate == null || endDate == null)
            reservations = service.getAll();
        else
        {
            try{
                reservations = service.getAll(startDate, endDate);
            } catch (ParseException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        var array = Mapper.map(reservations, GetReservationResponse[].class);
        return ResponseEntity.ok(array);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<GetReservationResponse> getReservation(@PathVariable Long id) {
        var reservationOpt = service.getById(id);
        if(reservationOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var response = ReservationConverter.toResponse(reservationOpt.get());

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<GetReservationResponse> createReservation(@RequestBody CreateReservationRequest request) {
        Optional<Reservation> reservationOpt;

        try {
            reservationOpt = service.create(
                    request.getUserId(),
                    request.getCarId(),
                    request.getStartDate(),
                    request.getEndDate());
        } catch (Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        if(reservationOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var response = ReservationConverter.toResponse(reservationOpt.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // TODO: Update reservation



    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteReservation(@PathVariable Long id){
        if(service.deleteById(id))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }
}
