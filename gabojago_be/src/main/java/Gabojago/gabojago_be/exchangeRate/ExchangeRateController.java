package Gabojago.gabojago_be.exchangeRate;

import Gabojago.gabojago_be.entity.ExchangeRate;
import Gabojago.gabojago_be.entity.ExchangeRateTemp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange-rate")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @PostMapping("/update")
    public void updateExchangeRate() {
        exchangeRateService.updateExchangeRate();
    }
}
