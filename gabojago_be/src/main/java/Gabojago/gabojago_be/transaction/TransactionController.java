package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.dto.request.RequestTransactionAddDto;
import Gabojago.gabojago_be.dto.request.RequestTransactionDeleteDto;
import Gabojago.gabojago_be.dto.request.RequestTransactionDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailDayDto;
import Gabojago.gabojago_be.entity.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/transaction")
@Tag(name = "거래내역(트랜잭션)", description = "거래내역 추가, 날짜별 거래내역 확인, 거래내역 삭제")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/save-from-trip")
    @Operation(summary = "거래내역 추가", description = "날짜에 해당하는 거래내역을 추가합니다.")
    public ResponseEntity<Transaction> addTransactionFromTrip(@RequestHeader("Authorization") String token, @RequestBody RequestTransactionAddDto request) {
        try {
            Transaction transaction = transactionService.saveTransactionFromTrip(token, request);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            log.info("에러발생 : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/detail-day-transaction")
    @Operation(summary = "날짜별 거래내역 확인", description = "특정 날짜에 해당하는 거래내역의 요약정보를 가져옵니다.")
    public ResponseEntity<List<Transaction>> detailDayTransaction(
            @RequestHeader("Authorization") String token, @RequestParam("tripId") Long tripId,
            @RequestParam("tripDate") LocalDate tripDate) {
        try {
            return ResponseEntity.ok(transactionService.getTripDetailTransaction(token, tripId, tripDate));
        } catch (Exception e) {
            log.info("날짜별 거래 가져오기 에러 발생 : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    //거래내역 삭제
    @DeleteMapping("/delete")
    @Operation(summary = "거래내역 삭제", description = "DTO 해당 날짜의 거래내역을 삭제합니다.")
    public ResponseEntity deleteTransaction(@RequestHeader("Authorization") String token,
                                            @RequestBody RequestTransactionDeleteDto request) {
        try {
            transactionService.deleteTransaction(token, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("거래 삭제 에러 발생 : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    //현재 미사용 컨트롤러
//    @PostMapping("/save")
//    public ResponseEntity<Transaction> addTransaction(@RequestBody RequestTransactionDto request) {
//        try {
//            Transaction transaction = transactionService.saveTransaction(request);
//            return ResponseEntity.ok(transaction);
//        } catch (Exception e) {
//            log.info("에러발생 : {}", e.getMessage());
//            return ResponseEntity.badRequest().build();
//        }
//    }

//    @GetMapping("/sum/{tripId}")
//    public ResponseEntity<Long> sum(@PathVariable("tripId") Long tripId) {
//        Long number = transactionService.getSum(tripId);
//        return ResponseEntity.ok(number);
//    }


//    @Operation(summary = "거래내역 요약", description = "특정 날짜에 해당하는 거래내역의 요약정보를 가져옵니다.")
//    @GetMapping("/detail-day")
//    public ResponseEntity<ResponseTripDetailDayDto> detailDay(@RequestParam("tripId") Long tripId,
//                                                              @RequestParam("tripDate") LocalDate tripDate) {
//        try {
//            return ResponseEntity.ok(transactionService.getExpenseByDate(tripId, tripDate));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}
