package Gabojago.gabojago_be.exchangeRate;


import Gabojago.gabojago_be.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findExchangeRateByCountry(String country);
}
