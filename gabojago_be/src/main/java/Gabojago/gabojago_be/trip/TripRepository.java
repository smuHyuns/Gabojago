package Gabojago.gabojago_be.trip;


import Gabojago.gabojago_be.dto.response.ResponseTripDetailDayDto;
import Gabojago.gabojago_be.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserIdOrderByStartPeriodAscEndPeriodAsc(Long userId);

    List<Trip> findByTripStatusAndUserIdOrderByStartPeriodAscEndPeriodAsc(Integer tripStatus, Long userId);

    @Query(value = "SELECT t.description, t.start_period, t.end_period, SUM(tr.expense_amount) AS expense, " +
            "t.trip_budget AS balance " +
            "FROM trip t " +
            "JOIN transaction tr ON t.trip_id = tr.trip_id " +
            "WHERE t.trip_id = :tripId " +
            "GROUP BY t.description, t.start_period, t.end_period, t.trip_budget",
            nativeQuery = true)
    Object[] getTripDetailDayNative(@Param("tripId") Long tripId);

    Trip findByTripId(@Param("tripId")Long tripId);

    @Query("SELECT SUM(t.expenseAmount) FROM Transaction t WHERE t.trip.tripId = :tripId")
    Integer findTotalExpenseByTripId(@Param("tripId") Long tripId);

}
