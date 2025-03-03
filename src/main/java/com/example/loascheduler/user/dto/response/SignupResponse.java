package com.example.loascheduler.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupResponse {

    private final String bearerToken;

    public static SignupResponse of(String bearerToken) {
        return new SignupResponse(bearerToken);
    }
}
