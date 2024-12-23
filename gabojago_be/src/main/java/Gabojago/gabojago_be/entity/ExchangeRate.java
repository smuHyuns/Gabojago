package Gabojago.gabojago_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeRateId;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Double rate;
}
