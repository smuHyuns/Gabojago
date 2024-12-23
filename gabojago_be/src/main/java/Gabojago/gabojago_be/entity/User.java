package Gabojago.gabojago_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;
    @Column
    private String userNickname;
    @Column
    private String userPassword;
    @Column
    private String userProfileImg;
    @Column
    private String userLoginId;
}
