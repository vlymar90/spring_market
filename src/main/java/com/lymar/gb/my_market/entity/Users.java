package com.lymar.gb.my_market.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String last_name;
    private String second_name;
    private String phone;
    private String login;
    private String password;
    private String email;
    private String role;

    public String getFIO() {
        return String.format("%s %s %s",
                getLast_name() != null ? getLast_name() : "",
                getName() != null ? getName() : "",
                getSecond_name() != null ? getSecond_name() : "");
    }
}
