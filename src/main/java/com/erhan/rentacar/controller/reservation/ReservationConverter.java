package com.erhan.rentacar.controller.reservation;

import com.erhan.rentacar.controller.Mapper;
import com.erhan.rentacar.controller.car.GetCarResponse;
import com.erhan.rentacar.controller.user.GetUserResponse;
import com.erhan.rentacar.entity.Reservation;

import java.text.SimpleDateFormat;

public class ReservationConverter {

    private ReservationConverter() { }
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static GetReservationResponse toResponse(Reservation reservation)
    {
        return new GetReservationResponse(
                reservation.getId(),
                Mapper.map(reservation.getCar(), GetCarResponse.class),
                Mapper.map(reservation.getUser(), GetUserResponse.class),
                dateFormat.format(reservation.getStartDate()),
                dateFormat.format(reservation.getEndDate()),
                reservation.getDays(),
                reservation.getTotalPrice());
    }
}
