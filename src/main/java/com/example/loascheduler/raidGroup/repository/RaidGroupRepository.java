package com.example.loascheduler.raidGroup.repository;

import com.example.loascheduler.raidGroup.entity.RaidGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RaidGroupRepository extends JpaRepository<RaidGroup, Long> {

    @Query("SELECT rg FROM RaidGroup rg LEFT JOIN FETCH rg.characters rc LEFT JOIN FETCH rc.character")
    List<RaidGroup> findAllWithCharacters();

    @Query("SELECT rg FROM RaidGroup rg LEFT JOIN FETCH rg.characters rc LEFT JOIN FETCH rc.character WHERE rg.raidName = :raidName")
    List<RaidGroup> findAllRaidNameWithCharacters(@Param("raidName") String raidName);

    @Query("SELECT rg FROM RaidGroup rg LEFT JOIN FETCH rg.characters rc LEFT JOIN FETCH rc.character WHERE rg.day = :day")
    List<RaidGroup> findAllByDayWithCharacters(@Param("day") String day);

    @Query("SELECT rg FROM RaidGroup rg LEFT JOIN FETCH rg.characters rc LEFT JOIN FETCH rc.character WHERE rg.day = :day AND rg.raidName = :raidName")
    List<RaidGroup> findAllByDayAndRaidNameWithCharacters(@Param("day") String day, @Param("raidName") String raidName);
}
