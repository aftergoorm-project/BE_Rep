package com.shop.pj.domain.jpa.user.controller;

import com.shop.pj.domain.jpa.user.dto.request.UserSignUpRequest;
import com.shop.pj.domain.jpa.user.dto.response.UserResponse;
import com.shop.pj.domain.jpa.user.service.UserService;
import com.shop.pj.domain.jpa.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 엔드포인트
     * @param request 회원가입 요청 데이터 (nickname, email, password, confirmPassword)
     * @return 생성된 User 엔티티와 함께 HTTP 201 상태 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody UserSignUpRequest request) {
        User user = userService.signUp(request);

        // UserResponse로 변환하여 응답
        UserResponse response = new UserResponse(
                user.getUserid(),
                user.getNickname(),
                user.getEmail(),
                user.getProfileImage(),
                user.getRole().name(),
                user.getStatus().name()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 회원 수정 엔드포인트
     * @param userId 수정할 회원의 ID
     * @param nickname 새로운 닉네임
     * @param profileImage 새로운 프로필 이미지
     * @return 수정된 User 엔티티와 함께 HTTP 200 상태 반환
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId,
                                                      @RequestParam String nickname,
                                                      @RequestParam String profileImage) {
        User user = userService.updateUser(userId, nickname, profileImage);

        // UserResponse로 변환하여 응답
        UserResponse response = new UserResponse(
                user.getUserid(),
                user.getNickname(),
                user.getEmail(),
                user.getProfileImage(),
                user.getRole().name(),
                user.getStatus().name()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 회원 삭제 엔드포인트
     * @param userId 삭제할 회원의 ID
     * @return HTTP 204 상태 반환
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 회원 조회 엔드포인트
     * @param userId 조회할 회원의 ID
     * @return UserResponseDTO와 함께 HTTP 200 상태 반환
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse response = userService.getUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
