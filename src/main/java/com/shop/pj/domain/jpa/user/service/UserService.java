package com.shop.pj.domain.jpa.user.service;

import com.shop.pj.domain.jpa.user.entity.User;
import com.shop.pj.domain.jpa.user.repository.UserRepository;
import com.shop.pj.domain.jpa.user.dto.request.UserSignUpRequest;
import com.shop.pj.domain.jpa.user.dto.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입 로직
     * @param signUpRequest 회원가입 요청 데이터
     * @return 등록된 User 엔티티
     * @throws IllegalArgumentException 이미 등록된 이메일일 경우 예외 발생
     * @throws IllegalArgumentException 비밀번호와 비밀번호 확인이 일치하지 않을 경우 예외 발생
     */
    @Transactional
    public User signUp(UserSignUpRequest signUpRequest) {
        // 이메일 중복 검사
        Optional<User> existingUser = userRepository.findByEmail(signUpRequest.getEmail());
        if(existingUser.isPresent()){
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 비밀번호와 비밀번호 확인이 일치하는지 검사
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        // User 엔티티 생성 (기본 role: ADMIN, status: ACTIVE)
        User newUser = User.user(signUpRequest.getNickname(), signUpRequest.getEmail(), encodedPassword);

        // DB에 저장
        return userRepository.save(newUser);
    }

    /**
     * 회원 수정
     * @param userId 수정할 회원의 ID
     * @param nickname 수정할 닉네임
     * @param profileImage 수정할 프로필 이미지
     * @return 수정된 User 엔티티
     * @throws IllegalArgumentException 유효하지 않은 사용자 ID일 경우 예외 발생
     */
    @Transactional
    public User updateUser(Long userId, String nickname, String profileImage) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        user.setNickname(nickname);
        user.setProfileImage(profileImage);

        return userRepository.save(user);
    }

    /**
     * 회원 삭제
     * @param userId 삭제할 회원의 ID
     * @throws IllegalArgumentException 유효하지 않은 사용자 ID일 경우 예외 발생
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        userRepository.delete(user);
    }

    /**
     * 회원 조회
     *
     * @param userId 조회할 회원의 ID
     * @return 조회된 User 엔티티
     * @throws IllegalArgumentException 유효하지 않은 사용자 ID일 경우 예외 발생
     */
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        // UserResponseDTO로 변환하여 반환
        return new UserResponse(
                user.getUserid(),
                user.getNickname(),
                user.getEmail(),
                user.getProfileImage(),
                user.getRole().name(),
                user.getStatus().name()
        );
    }
}
