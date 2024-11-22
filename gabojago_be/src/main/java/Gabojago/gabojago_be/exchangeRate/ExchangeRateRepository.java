package Gabojago.gabojago_be.exchangeRate;


import Gabojago.gabojago_be.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
}
