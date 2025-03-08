package com.shop.pj.domain.jpa.user.controller;

import com.shop.pj.domain.jpa.user.entity.User;
import com.shop.pj.domain.jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 API
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String nickname,
                                          @RequestParam String email,
                                          @RequestParam String password) {
        try {
            User user = userService.registerUser(nickname, email, password);
            return ResponseEntity.ok("회원가입 성공! 사용자 ID: " + user.getUserId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 사용자 정보 조회 API
     */
    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}