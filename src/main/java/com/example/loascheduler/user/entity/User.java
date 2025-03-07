package com.example.loascheduler.user.entity;

import com.example.loascheduler.character.entity.Characters;
import com.example.loascheduler.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
@SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "user_status")
    private boolean userStatus = true; // 유저 상태 (true: 활성, false: 탈퇴)

    // 대표 캐릭터
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_character_id")
    private Characters mainCharacter;

    // 보유 캐릭터 list
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Characters> charactersList = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long userId) {
        this.id = userId;
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User of(String email, String password, String name) {
        return new User(email, password, name);
    }


    // 비밀번호 변경 기능
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // 유저 탈퇴 처리
    public void withdrawUser() {
        this.userStatus = false;
    }
}
