package com.shop.pj.domain.jpa.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userid;

    private String socialId;

    private String nickname;

    private String email;

    private String password; // 암호화된 비밀번호 저장

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    // 비밀번호 일치 여부 확인
    public boolean isPasswordValid(String rawPassword) {
        return Objects.equals(this.password, rawPassword);
    }

    // 비밀번호 암호화 후 생성
    public static User user(String nickname, String email, String password) {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .profileImage(null)
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .build();
    }
}
