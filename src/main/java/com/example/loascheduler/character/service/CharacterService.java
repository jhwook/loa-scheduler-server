package com.example.loascheduler.character.service;

import com.example.loascheduler.character.dto.response.CharacterInfoResponse;
import com.example.loascheduler.character.dto.response.CharacterListResponse;
import com.example.loascheduler.character.entity.Characters;
import com.example.loascheduler.character.repository.CharacterRepository;
import com.example.loascheduler.classType.repository.ClassTypeRepository;
import com.example.loascheduler.client.CharacterInfoService;
import com.example.loascheduler.common.dto.AuthUser;
import com.example.loascheduler.common.exception.CharacterAlreadyExistsException;
import com.example.loascheduler.user.entity.User;
import com.example.loascheduler.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CharacterService {

    private final CharacterInfoService characterInfoService;
    private final CharacterRepository characterRepository;
    private final ClassTypeRepository classTypeRepository;
    private final UserService userService;

    public CharacterInfoResponse saveCharacter(String characterName) {
        Optional<Characters> existingCharacter = Optional.ofNullable(characterRepository.findByNickName(characterName));
        if (existingCharacter.isPresent()) {
            throw new CharacterAlreadyExistsException("Character already exists.");
        }
        CharacterInfoResponse response = characterInfoService.getMyCharacter(characterName);
        Characters characters = new Characters(
                response.getClassName(),
                response.getNickName(),
                response.getLevel()
        );
        characterRepository.save(characters);

        Characters character = characterRepository.findByNickName(characterName);

        return new CharacterInfoResponse(
                character.getClassName(),
                character.getLevel(),
                character.getNickName()
        );
    }

    public String searchMyCharacters(AuthUser authUser, String characterName, String serverName) {
        JSONArray characterList = characterInfoService.getAllCharacter(characterName);

        List<Characters> myCharacters = new ArrayList<>();

        User user = userService.getUserById(authUser.getId());

        for (int i = 0; i < characterList.length(); i++) {
            JSONObject character = characterList.getJSONObject(i);
            String serverName1 = character.getString("ServerName");

            if (!serverName1.equals(serverName)) {
                continue;
            }

            Characters characterInfo = new Characters(
                    character.getString("CharacterName"),
                    character.getString("CharacterClassName"),
                    character.getString("ItemAvgLevel"),
                    character.getInt("CharacterLevel"),
                    serverName,
                    user
            );

            myCharacters.add(characterInfo);
        }

        characterRepository.saveAll(myCharacters);

        return "ok";
    }

    public List<CharacterListResponse> getMyCharacters(AuthUser authUser) {
        User user = userService.getUserById(authUser.getId());

        List<Characters> charactersList = user.getCharactersList();

        List<CharacterListResponse> response = new ArrayList<>();

        for(Characters character : charactersList) {
            CharacterListResponse characterDto = new CharacterListResponse(
                    character.getServerName(),
                    character.getNickName(),
                    character.getExp_level(),
                    character.getClassName(),
                    character.getLevel()
            );

            response.add(characterDto);
        }

        return response;
    }

    public CharacterInfoResponse getCharacter(String characterName) {
        Characters characters = characterRepository.findByNickName(characterName);

        return new CharacterInfoResponse(
                characters.getClassName(),
                characters.getNickName(),
                characters.getLevel()
        );
    }

    public List<CharacterInfoResponse> getAllCharacters() {
        List<Characters> characters = characterRepository.findAll();

        return characters.stream().map(character -> {
            CharacterInfoResponse response = new CharacterInfoResponse(
                    character.getClassName(),
                    character.getLevel(),
                    character.getNickName()
            );

            return response;
        }).collect(Collectors.toList());
    }

    public void deleteCharacters(String nickName) {
        Characters character = characterRepository.findByNickName(nickName);
        characterRepository.delete(character);
    }
}
