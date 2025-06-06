package com.example.loascheduler.user.service;

import com.example.loascheduler.common.dto.AuthUser;
import com.example.loascheduler.common.exception.InvalidRequestException;
import com.example.loascheduler.user.dto.request.UserChangePasswordRequest;
import com.example.loascheduler.user.dto.request.UserWithdrawRequest;
import com.example.loascheduler.user.dto.response.UserResponse;
import com.example.loascheduler.user.entity.User;
import com.example.loascheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user;
    }

    public UserResponse getUser(long userId) {
        User user = findValidUser(userId);
        return UserResponse.entityToDto(user);
    }

    @Transactional
    public void withdrawUser(AuthUser authUser, UserWithdrawRequest request) {
        User user = findValidUser(authUser.getId());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidRequestException("비밀번호가 틀렸습니다.");
        }

        user.withdrawUser(); // 유저 탈퇴 처리
    }

    @Transactional
    public void changePassword(AuthUser authUser, UserChangePasswordRequest request) {
        User user = findValidUser(authUser.getId());

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidRequestException("잘못된 비밀번호입니다.");
        }

        validateNewPassword(request.getNewPassword());
        user.changePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    private User findValidUser(long userId) {
        return userRepository.findById(userId)
                .filter(User::isUserStatus)
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 사용자입니다."));
    }

    public static void validateNewPassword(String password) {
        if (password == null || password.length() < 8 ||
                !password.matches(".*\\d.*") ||
                !password.matches(".*[A-Z].*")) {
            throw new InvalidRequestException("새 비밀번호는 8자 이상이어야 하며, 숫자와 대문자를 포함해야 합니다.");
        }
    }

}
