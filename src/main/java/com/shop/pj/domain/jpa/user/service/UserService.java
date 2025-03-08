package com.shop.pj.domain.jpa.user.service;

import com.shop.pj.domain.jpa.user.entity.User;
import com.shop.pj.domain.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입: 비밀번호 암호화 후 저장
     */
    public User registerUser(String nickname, String email, String rawPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화 후 User 객체 생성
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = User.builder()
                .nickname(nickname)
                .email(email)
                .password(encodedPassword)
                .build();

        return userRepository.save(user);
    }

    /**
     * 사용자 조회 (이메일 기준)
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 비밀번호 검증
     */
    public boolean validatePassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}