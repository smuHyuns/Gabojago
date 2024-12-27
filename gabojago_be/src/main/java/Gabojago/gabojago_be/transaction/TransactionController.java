package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.dto.request.RequestTransactionAddDto;
import Gabojago.gabojago_be.dto.request.RequestTransactionDeleteDto;
import Gabojago.gabojago_be.entity.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
@Tag(name = "거래내역(트랜잭션)", description = "거래내역 추가, 날짜별 거래내역 확인, 거래내역 삭제")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/save-from-trip")
    @Operation(summary = "거래내역 추가", description = "날짜에 해당하는 거래내역을 추가합니다.")
    public ResponseEntity<Transaction> addTransactionFromTrip(@RequestHeader("Authorization") String token,
                                                              @RequestBody RequestTransactionAddDto request) {
        Transaction transaction = transactionService.saveTransactionFromTrip(token, request);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/detail-day-transaction")
    @Operation(summary = "날짜별 거래내역 확인", description = "특정 날짜에 해당하는 거래내역의 요약정보를 가져옵니다.")
    public ResponseEntity<List<Transaction>> detailDayTransaction(
            @RequestHeader("Authorization") String token,
            @RequestParam("tripId") Long tripId,
            @RequestParam("tripDate") LocalDate tripDate) {
        List<Transaction> transactions = transactionService.getTripDetailTransaction(token, tripId, tripDate);
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "거래내역 삭제", description = "DTO 해당 날짜의 거래내역을 삭제합니다.")
    public ResponseEntity<Void> deleteTransaction(@RequestHeader("Authorization") String token,
                                                  @RequestBody RequestTransactionDeleteDto request) {
        transactionService.deleteTransaction(token, request);
        return ResponseEntity.ok().build();
    }
}
