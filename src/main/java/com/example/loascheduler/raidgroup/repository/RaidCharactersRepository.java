package com.example.loascheduler.raidgroup.repository;

import com.example.loascheduler.raidgroup.entity.RaidCharacters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaidCharactersRepository extends JpaRepository<RaidCharacters, Long> {
    RaidCharacters findByRaidGroupIdAndCharacterId(Long raidGroupId, Long characterId);
}
