package com.shop.pj.domain.jpa.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSignUpRequest {

    private String nickname;
    private String email;
    private String password;
    private String confirmPassword;  // 비밀번호 확인 필드 추가
}
