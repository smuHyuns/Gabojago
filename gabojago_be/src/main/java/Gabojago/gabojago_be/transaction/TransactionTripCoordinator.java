package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.dto.request.RequestTransactionDeleteDto;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.exception.TripNotFoundException;
import Gabojago.gabojago_be.trip.TripRepository;
import Gabojago.gabojago_be.trip.TripService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionTripCoordinator {
    private final TripRepository tripRepository;

    @Transactional
    public void updateBudget(long tripId, Integer tripBudget, Integer exchangeTripBudget, String transactionType) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId));

        Integer curBudget = trip.getTripBudget();
        Integer exchangedCurBudget = trip.getTripExchangeBudget();

        if ("지출".equals(transactionType)) {
            curBudget -= tripBudget;
            exchangedCurBudget -= exchangeTripBudget;
        } else if ("추가".equals(transactionType)) {
            curBudget += tripBudget;
            exchangedCurBudget += exchangeTripBudget;
        } else {
            throw new IllegalArgumentException("유효하지 않은 트랜잭션 타입입니다: " + transactionType);
        }

        trip.setTripBudget(curBudget);
        trip.setTripExchangeBudget(exchangedCurBudget);

        tripRepository.save(trip);
    }
}
