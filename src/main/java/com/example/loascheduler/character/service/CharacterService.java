package com.example.loascheduler.character.service;

import com.example.loascheduler.character.dto.response.CharacterInfoResponse;
import com.example.loascheduler.character.dto.response.CharacterListResponse;
import com.example.loascheduler.character.entity.Characters;
import com.example.loascheduler.character.repository.CharacterRepository;
import com.example.loascheduler.classType.repository.ClassTypeRepository;
import com.example.loascheduler.client.CharacterInfoService;
import com.example.loascheduler.common.exception.CharacterAlreadyExistsException;
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

//    public List<JSONObject> searchMyCharacters(String characterName, String serverName) {
//        JSONArray characterList = characterInfoService.getAllCharacter(characterName);
//        List<JSONObject> filteredCharacters = new ArrayList<>();
//
//        for (int i = 0; i < characterList.length(); i++) {
//            JSONObject character = characterList.getJSONObject(i);
//            String serverName1 = character.getString("ServerName");
//
//            // 서버 이름이 일치하지 않으면 continue
//            if (!serverName1.equals(serverName)) {
//                continue;
//            }
//
//            // 필요한 데이터 추출
//            JSONObject filteredCharacter = new JSONObject();
//            filteredCharacter.put("ServerName", character.getString("ServerName"));
//            filteredCharacter.put("CharacterName", character.getString("CharacterName"));
//            filteredCharacter.put("CharacterLevel", character.getInt("CharacterLevel"));
//            filteredCharacter.put("CharacterClassName", character.getString("CharacterClassName"));
//            filteredCharacter.put("ItemAvgLevel", character.getString("ItemAvgLevel"));
//            filteredCharacter.put("ItemMaxLevel", character.getString("ItemMaxLevel"));
//
//            System.out.println("🔹 캐릭터 정보:");
//            System.out.println("  - 서버: " + serverName);
//            System.out.println("  - 캐릭터명: " + characterName);
//            System.out.println("  - 레벨: " + filteredCharacter.get("CharacterLevel"));
//            System.out.println("  - 클래스: " + filteredCharacter.get("CharacterClassName"));
//            System.out.println("  - 평균 아이템 레벨: " + filteredCharacter.get("ItemAvgLevel"));
//            System.out.println("  - 최대 아이템 레벨: " + filteredCharacter.get("ItemMaxLevel"));
//            System.out.println("------------------------------------");
//
//            // 필터링된 캐릭터 추가
//            filteredCharacters.add(filteredCharacter);
//        }
//
//        return filteredCharacters;
//    }

    public List<CharacterListResponse> searchMyCharacters(String characterName, String serverName) {
        JSONArray characterList = characterInfoService.getAllCharacter(characterName);
        List<CharacterListResponse> filteredCharacters = new ArrayList<>();

        for (int i = 0; i < characterList.length(); i++) {
            JSONObject character = characterList.getJSONObject(i);
            String serverName1 = character.getString("ServerName");

            if (!serverName1.equals(serverName)) {
                continue;
            }

            // DTO 객체 생성하여 리스트에 추가
            CharacterListResponse characterDto = new CharacterListResponse(
                    serverName1,
                    character.getString("CharacterName"),
                    character.getInt("CharacterLevel"),
                    character.getString("CharacterClassName"),
                    character.getString("ItemAvgLevel"),
                    character.getString("ItemMaxLevel")
            );

            filteredCharacters.add(characterDto);
        }

        return filteredCharacters;
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
