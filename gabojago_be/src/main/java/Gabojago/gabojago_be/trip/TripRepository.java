package Gabojago.gabojago_be.trip;


import Gabojago.gabojago_be.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserId(Long userId);
    List<Trip> findByTripStatusAndUserId(Integer tripStatus, Long userId);
}
