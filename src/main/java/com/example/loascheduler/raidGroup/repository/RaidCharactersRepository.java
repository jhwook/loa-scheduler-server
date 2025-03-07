package com.example.loascheduler.raidGroup.repository;

import com.example.loascheduler.raidGroup.entity.RaidCharacters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaidCharactersRepository extends JpaRepository<RaidCharacters, Long> {
    RaidCharacters findByRaidGroupIdAndCharacterId(Long raidGroupId, Long characterId);
}
