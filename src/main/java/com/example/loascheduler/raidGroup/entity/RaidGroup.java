package com.example.loascheduler.raidGroup.entity;

import com.example.loascheduler.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SequenceGenerator(name = "character_seq", sequenceName = "character_sequence", allocationSize = 1)
public class RaidGroup extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_seq")
    private Long id;

    private String day;

    private String raidTime;

    private String raidName;

    @OneToMany(mappedBy = "raidGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RaidCharacters> characters = new ArrayList<>();

    public RaidGroup(String day) {
        this.day = day;
    }

    public void updateRaidName(String raidName) {
        this.raidName = raidName;
    }

    public void updateRaidTime(String raidTime) {
        this.raidTime = raidTime;
    }
}
