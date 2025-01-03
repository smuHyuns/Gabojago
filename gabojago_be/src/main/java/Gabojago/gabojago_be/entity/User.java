package Gabojago.gabojago_be.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_profile_img")
    private String userProfileImg;

    @Column(name = "user_login_id")
    private String userLoginId;

    @Column(name = "user_username")
    private String userUsername;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_gender")
    private Integer userGender;

    @Column(name = "user_birth")
    private LocalDate userBirth;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
}
