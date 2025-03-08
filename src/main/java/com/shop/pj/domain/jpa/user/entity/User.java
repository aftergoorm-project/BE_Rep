package com.shop.pj.domain.jpa.user.entity;

import com.shop.pj.global.entity.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users") // "user"는 예약어이므로 변경
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String socialId;
    private String nickname;
    private String email;
    private String password; // 암호화된 비밀번호 저장
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * 비밀번호 암호화 후 저장하는 메서드
     */
    public void setPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(rawPassword);
    }

    /**
     * 비밀번호 검증 메서드 (암호화 적용)
     */
    public boolean isPasswordValid(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.password);
    }

    /**
     * 새로운 User 객체 생성 (비밀번호 암호화 적용)
     */
    public static User createUser(String nickname, String email, String rawPassword, PasswordEncoder passwordEncoder) {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(passwordEncoder.encode(rawPassword)) // 비밀번호 암호화 적용
                .profileImage(null)
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .build();
    }
}