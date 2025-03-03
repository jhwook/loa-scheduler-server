package com.example.loascheduler.user.service;

import com.example.loascheduler.common.exception.InvalidRequestException;
import com.example.loascheduler.config.security.JwtUtil;
import com.example.loascheduler.user.dto.request.ResetPasswordRequest;
import com.example.loascheduler.user.dto.request.SigninRequest;
import com.example.loascheduler.user.dto.request.SignupRequest;
import com.example.loascheduler.user.dto.response.SigninResponse;
import com.example.loascheduler.user.dto.response.SignupResponse;
import com.example.loascheduler.user.entity.User;
import com.example.loascheduler.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    
    @Transactional
    public SignupResponse signup(SignupRequest request) {
        //중복 이메일 체크
        validateEmail(request.getEmail());
        //비밀번호 조건 체크
        userService.validateNewPassword(request.getPassword());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = User.of(request.getEmail(), encodedPassword, request.getName());

        User savedUser = userRepository.save(newUser);
        String token = jwtUtil.createToken(savedUser.getId(), savedUser.getEmail());

        return SignupResponse.of(token);
    }

    public SigninResponse signin(SigninRequest request) {
        User user = findUserByEmail(request.getEmail());

//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new AuthException("잘못된 비밀번호입니다.");
//        }

        String token = jwtUtil.createToken(user.getId(), user.getEmail());

        return SigninResponse.of(token);
    }

    @Transactional
    public void resetPassword(@Valid ResetPasswordRequest request) {
        User user = findUserByEmail(request.getEmail());

        userService.validateNewPassword(request.getPassword());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.changePassword(encodedPassword);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidRequestException("가입되지 않은 사용자입니다."));
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new InvalidRequestException("이미 사용 중인 이메일입니다.");
        }
    }
}