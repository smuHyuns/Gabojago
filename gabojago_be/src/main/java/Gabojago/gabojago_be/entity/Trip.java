package Gabojago.gabojago_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Date startPeriod;

    @Column(nullable = false)
    private Date endPeriod;

    @Column(nullable = false)
    private Integer tripPurpose;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer headCount;

    @Column(nullable = false)
    private Integer tripBudget;

    @Column(nullable = false)
    private String tripCountry;

    @Column
    private Integer tripExchangeBudget; // 환율 반영 값

    @Column(nullable = false)
    private Integer tripStatus; // 0: 다가오는 여행, 1: 여행 중, 2: 완료

    @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER)
    private List<Transaction> transactions;
}