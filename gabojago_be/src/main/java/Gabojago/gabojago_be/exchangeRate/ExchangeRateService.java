package Gabojago.gabojago_be.exchangeRate;

import Gabojago.gabojago_be.entity.ExchangeRate;
import Gabojago.gabojago_be.entity.ExchangeRateTemp;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateTempRepository exchangeRateTempRepository;

    @Value("${exchangeRate.api.url}")
    private String apiUrl;

    @Value("${exchangeRate.api.key}")
    private String apiKey;

    private static final String BASE_CURRENCY = "KRW";

    /**
     * 환율 데이터를 API로부터 가져와 업데이트
     */

    @Transactional
    public void clear() {
        exchangeRateTempRepository.deleteAll();
        exchangeRateRepository.deleteAll();
    }


    @Transactional
    public void updateExchangeRate() {
        Map<String, Object> rates = getAllExchangeRates();

        List<ExchangeRateTemp> tempRates = mapToExchangeRateTempEntities(rates);

        for (ExchangeRateTemp tempRate : tempRates) {
            ExchangeRateTemp existingRate = exchangeRateTempRepository.findByCountryAndCurrency(
                    tempRate.getCountry(), tempRate.getCurrency()
            );
            if (existingRate != null) {
                tempRate.setExchangeRateId(existingRate.getExchangeRateId());
            }
        }

        // 업데이트 또는 삽입
        exchangeRateTempRepository.saveAll(tempRates);

        switchTables();
    }


    @Transactional
    public void switchTables() {
        exchangeRateRepository.deleteAll();

        List<ExchangeRate> newRates = exchangeRateTempRepository.findAll().stream()
                .map(temp -> {
                    ExchangeRate rate = new ExchangeRate();
                    rate.setCountry(temp.getCountry());
                    rate.setCurrency(temp.getCurrency());
                    rate.setRate(temp.getRate());
                    return rate;
                })
                .collect(Collectors.toList());

        exchangeRateRepository.saveAll(newRates);
    }

    private List<ExchangeRateTemp> mapToExchangeRateTempEntities(Map<String, Object> rates) {
        return rates.entrySet().stream()
                .map(entry -> {
                    ExchangeRateTemp temp = new ExchangeRateTemp();
                    temp.setCurrency(entry.getKey());

                    Object value = entry.getValue();
                    if (value instanceof Number) {
                        temp.setRate(((Number) value).doubleValue());
                    } else {
                        throw new GabojagoException(ErrorCode.EXCHANGE_RATE_INVALID_FORMAT);
                    }

                    temp.setCountry(CurrencyCountryMapping.getMapping().getOrDefault(entry.getKey(), "Unknown"));
                    return temp;
                })
                .collect(Collectors.toList());
    }


    public Map<String, Object> getAllExchangeRates() {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .pathSegment(apiKey, "latest", BASE_CURRENCY)
                .toUriString();

        System.out.println("Request URL: " + url);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        System.out.println("API Response: " + response);

        if (response != null && "success".equals(response.get("result"))) {
            return (Map<String, Object>) response.get("conversion_rates");
        }

        // 실패 시 예외 발생
        log.info("response : {}", response);
        throw new GabojagoException(ErrorCode.EXCHANGE_RATE_API_CALL_FAILED);
    }


    public Optional<ExchangeRate> getExchangeRateByCountry(String country) {
        return exchangeRateRepository.findExchangeRateByCountry(country);
    }
}
