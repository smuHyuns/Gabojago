package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.trip.tripId = :tripId")
    List<Transaction> findAllByTripId(@Param("tripId") Long tripId);

    @Query("select sum (expenseAmount) from Transaction where trip.tripId = :tripId group by trip.tripId")
    Long findSumByTripId(@Param("tripId") Long tripId);

    @Query("select sum (expenseAmount) from Transaction where trip.tripId = :tripId and expenseDate = :expenseDate group by trip.tripId")
    Long findSumByTripIdAndExpenseDate(@Param("tripId") Long tripId, @Param("expenseDate") LocalDate expenseDate);

    List<Transaction> findAllByTripTripIdAndExpenseDate(@Param("tripId") Long tripId , @Param("expenseDate") LocalDate expenseDate);
}
