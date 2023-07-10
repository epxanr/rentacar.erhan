package com.erhan.rentacar.controller.user;

import com.erhan.rentacar.controller.Mapper;
import com.erhan.rentacar.controller.car.GetCarResponse;
import com.erhan.rentacar.controller.reservation.GetReservationResponse;
import com.erhan.rentacar.entity.User;
import com.erhan.rentacar.service.ReservationService;
import com.erhan.rentacar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<GetUserResponse[]> getAll()
    {
        var array = Mapper.map(userService.getAll(), GetUserResponse[].class);
        return ResponseEntity.ok(array);
    }

    @GetMapping(path = "/{idOrEmail}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable String idOrEmail) {
        Optional<User> userOpt;
        try {
            var id = Long.parseLong(idOrEmail);
            userOpt = userService.getById(id);
        } catch (NumberFormatException e) {
            userOpt = userService.getByEmail(idOrEmail);
        }

        if(userOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(Mapper.map(userOpt.get(), GetUserResponse.class));
    }
    
    @GetMapping("/{id}/reservations")
    public ResponseEntity<GetUserReservationResponse[]> getUserReservations(@PathVariable long id)
    {
        var userOpt = userService.getById(id);
        if(userOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var reservations = reservationService.getReservationsByUserId(id);

        var array = Mapper.map(reservations, GetUserReservationResponse[].class);

        return ResponseEntity.ok(array);
    }

    // TODO: Get by email
    /*@GetMapping
    public ResponseEntity<GetUserResponse> getUserByEmail(@RequestParam String email) {
        var user = userService.getByEmail(email);
        if(user.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(Mapper.map(user.get(), GetUserResponse.class));
    }*/

    @PostMapping
    public ResponseEntity<GetUserResponse> registerUser(@RequestBody UserRegisterRequest request) {
        var userOpt = userService.create(request.getEmail(), request.getPassword());

        if(userOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        var response = Mapper.map(userOpt.get(), GetUserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserResponse> updateUser(@PathVariable long id, @RequestBody UpdateUserRequest request)
    {
        var userOpt = userService.update(
                id,
                request.getEmail(),
                request.getPassword());

        if(userOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var response = Mapper.map(userOpt.get(), GetUserResponse.class);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteUser(@PathVariable Long id){
        if(userService.deleteById(id))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }
}
