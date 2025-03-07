package com.example.loascheduler.raidGroup.entity;

import com.example.loascheduler.character.entity.Characters;
import com.example.loascheduler.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@SequenceGenerator(name = "character_seq", sequenceName = "character_sequence", allocationSize = 1)
public class RaidCharacters extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "raid_group_id", nullable = false)
    private RaidGroup raidGroup;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private Characters character;

    public RaidCharacters(RaidGroup raidGroup, Characters character) {
        this.raidGroup = raidGroup;
        this.character = character;
    }
}
