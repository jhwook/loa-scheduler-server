package com.example.loascheduler.user.service;

import com.example.loascheduler.common.exception.AuthException;
import com.example.loascheduler.common.exception.InvalidRequestException;
import com.example.loascheduler.config.security.JwtUtil;
import com.example.loascheduler.user.dto.request.SigninRequest;
import com.example.loascheduler.user.dto.request.SignupRequest;
import com.example.loascheduler.user.dto.response.SigninResponse;
import com.example.loascheduler.user.dto.response.SignupResponse;
import com.example.loascheduler.user.entity.User;
import com.example.loascheduler.user.repository.UserRepository;
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
        // request => username, password
        //중복 username 체크
        validateUsername(request.getUsername());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User(request.getUsername(), encodedPassword);

        User savedUser = userRepository.save(newUser);
        String token = jwtUtil.createToken(savedUser.getId(), savedUser.getUsername());

        return SignupResponse.of(token);
    }

    public SigninResponse signin(SigninRequest request) {
        User user = findUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("잘못된 비밀번호입니다.");
        }

        String token = jwtUtil.createToken(user.getId(), user.getUsername());

        return SigninResponse.of(token);
    }

//    @Transactional
//    public void resetPassword(@Valid ResetPasswordRequest request) {
//        User user = findUserByUsername(request.getUsername());
//
//        userService.validateNewPassword(request.getPassword());
//
//        String encodedPassword = passwordEncoder.encode(request.getPassword());
//        user.changePassword(encodedPassword);
//    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidRequestException("가입되지 않은 사용자입니다."));
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new InvalidRequestException("이미 사용 중인 아이디입니다.");
        }
    }
}