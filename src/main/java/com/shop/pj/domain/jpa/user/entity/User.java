package com.shop.pj.domain.jpa.user.entity;

import jakarta.persistence.*;

@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long userid;

    private String socialId;

    private String nickname;

    private String email;

    private String password;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;


    // ----- 메서드 ----- //
    public static User user(String nickname, String email, String password) {
        User user = new User();
        user.nickname = nickname;
        user.email = email;
        user.password = password;
        user.profileImage = null;
        user.role = Role.ADMIN;
        user.status = Status.ACTIVE;
        return user;
    }
}
