package Gabojago.gabojago_be.exchangeRate;

import Gabojago.gabojago_be.entity.ExchangeRateTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateTempRepository extends JpaRepository<ExchangeRateTemp, Long> {
    ExchangeRateTemp findByCountryAndCurrency(String country, String currency);
}
