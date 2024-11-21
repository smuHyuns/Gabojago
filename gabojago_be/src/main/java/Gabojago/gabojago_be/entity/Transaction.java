package Gabojago.gabojago_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    private long tripId;
    private long userId;
    private int expenseType;
    private Date expenseDate;
    private int expenseAmount;
    private int exchangeAmount;
}
