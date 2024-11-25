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
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip; // Trip 엔터티와 연관 관계 설정

    private Long userId;
    private Integer expenseType;
    private Date expenseDate;
    private Integer expenseAmount;
    private Integer exchangeAmount;
}
