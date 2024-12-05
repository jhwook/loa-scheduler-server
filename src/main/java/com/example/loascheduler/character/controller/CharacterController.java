package com.example.loascheduler.character.controller;

import com.example.loascheduler.character.dto.response.CharacterInfoResponse;
import com.example.loascheduler.character.entity.Characters;
import com.example.loascheduler.character.service.CharacterService;
import com.example.loascheduler.user.dto.response.CharacterInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/save/character")
    public void saveCharacter(@RequestParam String characterName) {
        characterService.saveCharacter(characterName);
    }

    @GetMapping("/character")
    public ResponseEntity<CharacterInfoResponse> getCharacter(@RequestParam String characterName) {
        return ResponseEntity.ok(characterService.saveCharacter(characterName));
    }

    @GetMapping("/all/characters")
    public ResponseEntity<List<Characters>> getAllCharacters() {
        return ResponseEntity.ok(characterService.getAllCharacters());
    }

}
