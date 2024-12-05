package com.example.loascheduler.character.service;

import com.example.loascheduler.character.dto.response.CharacterInfoResponse;
import com.example.loascheduler.character.entity.Characters;
import com.example.loascheduler.character.repository.CharacterRepository;
import com.example.loascheduler.classType.repository.ClassTypeRepository;
import com.example.loascheduler.client.CharacterInfoService;
import com.example.loascheduler.common.exception.CharacterAlreadyExistsException;
import com.example.loascheduler.user.dto.response.CharacterInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CharacterService {

    private final CharacterInfoService characterInfoService;
    private final CharacterRepository characterRepository;
    private final ClassTypeRepository classTypeRepository;

    public CharacterInfoResponse saveCharacter(String characterName) {
        Optional<Characters> existingCharacter = Optional.ofNullable(characterRepository.findByNickName(characterName));
        if (existingCharacter.isPresent()) {
            throw new CharacterAlreadyExistsException("Character already exists.");
        }
        CharacterInfoResponseDto response = characterInfoService.getMyCharacter(characterName);
        Characters characters = new Characters(
                response.getCharacterClassName(),
                response.getCharacterName(),
                response.getItemLevel()
        );
        characterRepository.save(characters);

        Characters character = characterRepository.findByNickName(characterName);

        return new CharacterInfoResponse(
                character.getClassName(),
                character.getNickName(),
                character.getLevel()
        );
    }

    public CharacterInfoResponse getCharacter(String characterName) {
        Characters characters = characterRepository.findByNickName(characterName);

        return new CharacterInfoResponse(
                characters.getClassName(),
                characters.getNickName(),
                characters.getLevel()
        );
    }

    public List<Characters> getAllCharacters() {
        return characterRepository.findAll();
    }
}
