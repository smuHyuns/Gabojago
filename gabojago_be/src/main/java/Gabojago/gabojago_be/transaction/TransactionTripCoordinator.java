package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.trip.TripRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionTripCoordinator {
    private final TripRepository tripRepository;

    @Transactional
    public void updateBudget(long tripId, Integer tripBudget, Integer exchangeTripBudget, String transactionType) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new GabojagoException(ErrorCode.TRIP_NOT_FOUND));

        Integer curBudget = trip.getTripBudget();
        Integer exchangedCurBudget = trip.getTripExchangeBudget();

        if ("지출".equals(transactionType)) {
            curBudget -= tripBudget;
            exchangedCurBudget -= exchangeTripBudget;
        } else if ("추가".equals(transactionType)) {
            curBudget += tripBudget;
            exchangedCurBudget += exchangeTripBudget;
        } else {
            log.info("타입 : {}", transactionType);
            throw new GabojagoException(ErrorCode.TRANSACTION_INVALID_TYPE);
        }

        trip.setTripBudget(curBudget);
        trip.setTripExchangeBudget(exchangedCurBudget);

        tripRepository.save(trip);
    }
}
