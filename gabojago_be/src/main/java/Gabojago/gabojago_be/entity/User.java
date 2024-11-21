package Gabojago.gabojago_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column
    private String userNickname;
    @Column
    private String userPassword;
    @Column
    private String userProfileImg;
    @Column
    private String userLoginId;
    @Column
    private String userUsername;
    @Column
    private String userEmail;
    @Column
    private int userGender;  //0은 여성 1은 남성
    @Column
    private Date userBirth;
}
