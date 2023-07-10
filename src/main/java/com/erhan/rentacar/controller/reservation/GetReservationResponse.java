package com.erhan.rentacar.controller.reservation;


import com.erhan.rentacar.controller.car.GetCarResponse;
import com.erhan.rentacar.controller.user.GetUserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetReservationResponse {
    private long id;
    private GetCarResponse car;
    private GetUserResponse user;
    private String startDate;
    private String endDate;
    private long days;
    private double totalPrice;
}
