package com.erhan.rentacar.controller.user;

import com.erhan.rentacar.controller.car.GetCarResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetUserReservationResponse {
    private long id;
    private GetCarResponse car;
    private String startDate;
    private String endDate;
    private long days;
    private double totalPrice;
}
