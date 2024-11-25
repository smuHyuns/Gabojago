package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
