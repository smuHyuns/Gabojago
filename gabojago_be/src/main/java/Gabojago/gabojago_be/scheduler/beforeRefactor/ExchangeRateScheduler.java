package Gabojago.gabojago_be.scheduler.beforeRefactor;

import Gabojago.gabojago_be.exchangeRate.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateScheduler {
    private final ExchangeRateService exchangeRateService;

    @Scheduled(cron = "0 5 9 * * ?", zone = "Asia/Seoul")
    public void scheduleUpdate() {
        log.info("ExchangeRate Update 시작");
        exchangeRateService.updateExchangeRate();
    }
}
