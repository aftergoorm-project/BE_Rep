package com.shop.pj.domain.jpa.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long userId;
    private String nickname;
    private String email;
    private String profileImage;
    private String role;
    private String status;

    public UserResponse(Long userId, String nickname, String email, String profileImage, String role, String status) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
        this.status = status;
    }
}
