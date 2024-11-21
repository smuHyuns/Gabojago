package Gabojago.gabojago_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Entity
@Getter
@Setter
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tripId;
    private Long userId;
    private Date startPeriod;
    private Date endPeriod;
    private int tripPurpose;
    private String describe;
    private int headCount;
    private int tripBudget;
    private String tripCountry;
    private int tripExchangeBudget; // 환율 반영한 후 소수점 제거하고 값 저장할 예정
}
