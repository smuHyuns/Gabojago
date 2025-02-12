package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.request.RequestTripSaveDto;
import Gabojago.gabojago_be.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/trip")
@RequiredArgsConstructor
@Tag(name = "여행", description = "유저 전체확인, 유저 여행상태, 여행 요약, 여행 화폐단위/환율, 여행 세부, 여행 저장")
public class TripController {

    private final TripService tripService;

    @GetMapping("/all")
    @Operation(summary = "유저 전체여행 불러오기", description = "유저의 전체 여행 내역을 불러옵니다.")
    public ResponseEntity<List<ResponseTripDto>> getUserTrips(@RequestHeader("Authorization") String token) {
        List<ResponseTripDto> response = tripService.getTrips(token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    @Operation(summary = "유저의 여행상태 불러오기", description = "유저의 여행상태(종료/진행/미래)를 불러옵니다.")
    public ResponseEntity<List<ResponseTripDto>> getUserTripsByStatus(@RequestHeader("Authorization") String token,
                                                                      @RequestParam("status") Integer status) {
        List<ResponseTripDto> response = tripService.getTripsByStatus(token, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail-all/{tripId}")
    @Operation(summary = "여행 요약 불러오기", description = "전달된 여행번호의 여행요약을 불러옵니다.")
    public ResponseEntity<ResponseTripDetailEntireDto> getTripDetailEntire(@RequestHeader("Authorization") String token,
                                                                           @PathVariable("tripId") Long tripId) {
        ResponseTripDetailEntireDto response = tripService.getEntireTripDetail(token, tripId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-country")
    @Operation(summary = "여행나라 화폐단위/환율 불러오기", description = "전달한 나라에 해당하는 화폐단위와 환율을 불러옵니다.")
    public ResponseEntity<ResponseExchangeRateDto> getCountry(@RequestHeader("Authorization") String token,
                                                              @RequestParam("tripId") Long tripId) {
        ResponseExchangeRateDto response = tripService.getExchange(token, tripId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail/{tripId}")
    @Operation(summary = "여행세부내역 불러오기", description = "전달한 여행번호에 해당하는 요약정보를 제공합니다.")
    public ResponseEntity<ResponseTripDetailEntireDto> getTripDetail(@PathVariable("tripId") Long tripId) {
        ResponseTripDetailEntireDto response = tripService.getDetail(tripId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    @Operation(summary = "여행 저장하기", description = "전달한 정보에 해당하는 여행을 저장합니다.")
    public ResponseEntity<ResponseTripSaveDto> saveTrip(@RequestHeader("Authorization") String token,
                                                        @RequestBody RequestTripSaveDto request) {
        ResponseTripSaveDto response = tripService.saveTrip(token, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test/{tripId}")
    public void test(@PathVariable("tripId") Long tripId) {
        tripService.test(tripId);
    }
}
