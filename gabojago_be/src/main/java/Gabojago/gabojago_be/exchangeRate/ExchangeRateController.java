package Gabojago.gabojago_be.exchangeRate;

import Gabojago.gabojago_be.entity.ExchangeRate;
import Gabojago.gabojago_be.entity.ExchangeRateTemp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange-rate")
@Tag(name = "환율", description = "환율 최신화")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Operation(summary = "환율 업데이트", description = "v6.exchangerate-api에 요청하여 요청시각 기준 환율을 DB에 반영합니다.")
    @PostMapping("/update")
    public void updateExchangeRate() {
        exchangeRateService.updateExchangeRate();
    }

}
