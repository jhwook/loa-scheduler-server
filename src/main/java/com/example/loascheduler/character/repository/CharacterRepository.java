package com.example.loascheduler.character.repository;

import com.example.loascheduler.character.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Characters, Long> {
    Characters findByNickName(String characterName);
}
