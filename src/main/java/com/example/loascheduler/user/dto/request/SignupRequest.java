package com.example.loascheduler.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    private String username;

    @Pattern(
            regexp = "^.{4,15}$",
            message = "비밀번호는 4자 이상 15자 이하"
    )
    @NotBlank
    private String password;
}
