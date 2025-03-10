package com.example.loascheduler.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SigninResponse {

    private final String bearerToken;

    public static SigninResponse of(String bearerToken) {
        return new SigninResponse(bearerToken);
    }
}
