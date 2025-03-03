package com.example.loascheduler.character.entity;

import com.example.loascheduler.common.entity.Timestamped;
import com.example.loascheduler.raidgroup.entity.RaidCharacters;
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

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RaidCharacters> raidCharacters = new ArrayList<>();

    public Characters(String characterClassName, String characterName, String itemLevel) {
        this.nickName = characterName;
        this.className = characterClassName;
        this.level = itemLevel;
    }
}
