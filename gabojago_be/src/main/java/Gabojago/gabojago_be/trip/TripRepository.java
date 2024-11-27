package Gabojago.gabojago_be.trip;


import Gabojago.gabojago_be.dto.response.ResponseTripDetailEntireDto;
import Gabojago.gabojago_be.entity.Transaction;
import Gabojago.gabojago_be.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface TripRepository extends JpaRepository<Trip, Long> {
    //List<Trip> findByUserIdOrderByStartPeriodAscEndPeriodAsc(Long userId);
    List<Trip> findByUserUserIdOrderByStartPeriodAscEndPeriodAsc(Long userId);

    //List<Trip> findByTripStatusAndUserIdOrderByStartPeriodAscEndPeriodAsc(Integer tripStatus, Long userId);
    List<Trip> findByTripStatusAndUserUserIdOrderByStartPeriodAscEndPeriodAsc(Integer tripStatus, Long userId);

    Trip findByTripId(@Param("tripId") Long tripId);

//    @Query("SELECT COALESCE(SUM(t.expenseAmount), 0) FROM Transaction t WHERE t.trip.tripId = :tripId")
//    Long findTotalExpenseByTripId(@Param("tripId") Long tripId);


}
