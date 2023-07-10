package com.erhan.rentacar.service;

import com.erhan.rentacar.entity.Reservation;
import com.erhan.rentacar.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
    private final CarService carService;
    private final UserService userService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public Optional<Reservation> getById(Long id) {
        return repository.findById(id);
    }

    public Iterable<Reservation> getAll() {
        return repository.findAll();
    }

    public Iterable<Reservation> getAll(String startDateStr, String endDateStr) throws ParseException {
        var dateRange = new DateRange(startDateStr, endDateStr);
        return repository.findBetweenDates(dateRange.getStartDate(), dateRange.getEndDate());
    }

    public Optional<Reservation> create(long userId, long carId, String startDateStr, String endDateStr)
            throws ParseException
    {
        if(startDateStr == null || endDateStr == null) return Optional.empty();

        var car = carService.getById(carId);
        var user = userService.getById(userId);

        if(car.isEmpty() || user.isEmpty()) return Optional.empty();

        var dateRange = new DateRange(startDateStr, endDateStr);

        if(!reservationIsPossible(carId, dateRange))
            throw new IllegalArgumentException("Reservation not possible, due to schedule overlap.");

        var reservation = new Reservation(user.get(), car.get(), dateRange.getStartDate(), dateRange.getEndDate());
        return Optional.of(repository.save(reservation));
    }

    /*public Optional<Reservation> updateReservation(long reservationId, Long carId, String startDateStr, String endDateStr)
            throws IllegalArgumentException, ParseException {
        var reservationOpt = repository.findById(reservationId);
        if(reservationOpt.isEmpty()) return Optional.empty();

        var reservation = reservationOpt.get();

        var dateRange = new DateRange(startDateStr, endDateStr);

        if(!reservationIsPossible(carId, dateRange))
            throw new IllegalArgumentException("Reservation not possible, due to schedule overlap.");

        if(carId != reservation.getCar().getId())
        {
            var carOpt = carService.getById(carId);
            if(carOpt.isEmpty()) return Optional.empty();
            reservation.setCar(carOpt.get());
        }

        return Optional.of(repository.save(reservation));
    }*/

    private boolean reservationIsPossible(long carId, DateRange dateRange)
    {
        var reservations = repository.findByCarIdAndStartDateGreaterThan(carId, new Date());

        for (var reservation : reservations) {
            if(!(reservation.getStartDate().before(dateRange.getStartDate()) &&
                reservation.getEndDate().before(dateRange.getStartDate()) ||
                reservation.getStartDate().after(dateRange.getEndDate()) &&
                reservation.getEndDate().after(dateRange.getEndDate())))
                return false;
        }
        return true;
    }

    public boolean deleteById(Long id) {
        if(!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public Iterable<Reservation> getReservationsByCarId(Long carId) {
        return repository.findByCarId(carId);
    }

    public Iterable<Reservation> getReservationsByUserId(long id) {
        return repository.findByUserId(id);
    }

    public Iterable<Reservation> getReservationsBetweenDates(DateRange dateRange)
    {
        return repository.findBetweenDates(dateRange.getStartDate(), dateRange.getEndDate());
    }

    @Getter
    private class DateRange
    {
        public DateRange(String startDateStr, String endDateStr) throws ParseException {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);

            if(startDate.before(new Date()))
                throw new IllegalArgumentException("Start date cannot be before today");

            if(endDate.before(startDate))
                throw new IllegalArgumentException("End date cannot be before start date");
        }

        private Date startDate;
        private Date endDate;
    }
}
