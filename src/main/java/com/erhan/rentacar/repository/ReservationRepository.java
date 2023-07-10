package com.erhan.rentacar.repository;

import com.erhan.rentacar.entity.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    Iterable<Reservation> findByCarId(Long carId);
    Iterable<Reservation> findByCarIdAndStartDateGreaterThan(Long carId, Date startDate);
    Iterable<Reservation> findByUserId(Long id);

    @Query("SELECT r FROM reservations r WHERE r.startDate BETWEEN ?1 AND ?2 AND r.endDate BETWEEN ?1 AND ?2")
    Iterable<Reservation> findBetweenDates(Date startDate, Date endDate);
}
