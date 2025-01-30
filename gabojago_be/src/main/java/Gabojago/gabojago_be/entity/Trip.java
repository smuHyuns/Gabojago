package Gabojago.gabojago_be.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long tripId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_period", nullable = false)
    private LocalDate startPeriod;

    @Column(name = "end_period", nullable = false)
    private LocalDate endPeriod;

//    @Column(name = "trip_purpose", nullable = false)
//    private Integer tripPurpose;

    @Column(name = "description")
    private String description;

    @Column(name = "trip_budget", nullable = false)
    private Integer tripBudget;

    @Column(name = "trip_country")
    private String tripCountry;

    @Column(name = "trip_exchange_budget")
    private Integer tripExchangeBudget;

    @Column(name = "headcount", nullable = false)
    private Integer headcount;

    @Column(name = "trip_status")
    private Integer tripStatus;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<Transaction> transactions;
}
