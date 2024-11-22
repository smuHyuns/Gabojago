package Gabojago.gabojago_be.scheduler;

import Gabojago.gabojago_be.exchangeRate.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateScheduler {
    private final ExchangeRateService exchangeRateService;

    @Scheduled(cron = "0 5 9 * * ?", zone = "Asia/Seoul")
    public void scheduleUpdate() {
        exchangeRateService.updateExchangeRate();
    }
}
