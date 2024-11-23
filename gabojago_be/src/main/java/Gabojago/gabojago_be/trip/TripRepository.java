package Gabojago.gabojago_be.trip;


import Gabojago.gabojago_be.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserIdOrderByStartPeriodAscEndPeriodAsc(Long userId);
    List<Trip> findByTripStatusAndUserIdOrderByStartPeriodAscEndPeriodAsc(Integer tripStatus, Long userId);
}
