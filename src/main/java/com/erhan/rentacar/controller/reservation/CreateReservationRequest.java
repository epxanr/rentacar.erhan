package com.erhan.rentacar.controller.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateReservationRequest {
    private long userId;
    private long carId;
    private String startDate;
    private String endDate;
}
