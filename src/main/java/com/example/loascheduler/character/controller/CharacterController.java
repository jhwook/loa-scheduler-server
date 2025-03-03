package com.example.loascheduler.character.controller;

import com.example.loascheduler.character.dto.response.CharacterInfoResponse;
import com.example.loascheduler.character.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CharacterInfoResponse>> getAllCharacters() {
        return ResponseEntity.ok(characterService.getAllCharacters());
    }

    @DeleteMapping("/characters/{nickName}")
    public void deleteCharacters(@PathVariable String nickName) {
        characterService.deleteCharacters(nickName);
    }

    @GetMapping("/characters/all")
    public void searchMyCharacters(@RequestParam String characterName) {
        this.characterService.searchMyCharacters(characterName);
    }
}
