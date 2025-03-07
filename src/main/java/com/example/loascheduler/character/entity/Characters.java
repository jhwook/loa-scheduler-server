package com.example.loascheduler.character.entity;

import com.example.loascheduler.common.entity.Timestamped;
import com.example.loascheduler.raidGroup.entity.RaidCharacters;
import com.example.loascheduler.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SequenceGenerator(name = "character_seq", sequenceName = "character_sequence", allocationSize = 1)
public class Characters extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_seq")
    private Long id;

    private String nickName;

    private String className;

    private String level;

    private Integer exp_level;

    private String serverName;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RaidCharacters> raidCharacters = new ArrayList<>();

//    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserCharacters> userCharacterList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Characters(String characterClassName, String characterName, String itemLevel) {
        this.nickName = characterName;
        this.className = characterClassName;
        this.level = itemLevel;
    }

    public Characters(
            String characterClassName,
            String characterName,
            String itemLevel,
            int expLevel,
            String serverName,
            User user
    ) {
        this.nickName = characterName;
        this.className = characterClassName;
        this.level = itemLevel;
        this.exp_level = expLevel;
        this.serverName = serverName;
        this.user = user;
    }
}
