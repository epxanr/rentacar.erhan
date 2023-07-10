package com.erhan.rentacar.controller.car;

import com.erhan.rentacar.controller.user.GetUserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetCarReservationResponse {
    private long id;
    private GetUserResponse user;
    private String startDate;
    private String endDate;
    private long days;
    private double totalPrice;
}
