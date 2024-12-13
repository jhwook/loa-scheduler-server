package com.example.loascheduler.raidGroup.service;

import com.example.loascheduler.character.dto.response.CharacterInfoResponse;
import com.example.loascheduler.character.entity.Characters;
import com.example.loascheduler.character.repository.CharacterRepository;
import com.example.loascheduler.common.exception.DuplicateCharacterException;
import com.example.loascheduler.raidGroup.dto.response.RaidGroupListResponse;
import com.example.loascheduler.raidGroup.dto.response.RaidGroupResponse;
import com.example.loascheduler.raidGroup.entity.RaidCharacters;
import com.example.loascheduler.raidGroup.entity.RaidGroup;
import com.example.loascheduler.raidGroup.repository.RaidCharactersRepository;
import com.example.loascheduler.raidGroup.repository.RaidGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RaidGroupService {

    private final RaidGroupRepository raidGroupRepository;
    private final RaidCharactersRepository raidCharactersRepository;
    private final CharacterRepository characterRepository;

    public RaidGroupResponse addRaidGroup(String day) {
        RaidGroup raidGroup = raidGroupRepository.save(new RaidGroup(day));
        return new RaidGroupResponse(raidGroup.getId(), raidGroup.getDay());
    }

    @Transactional
    public void setRaidName(Long id, String raidName) {
        RaidGroup raidGroup = raidGroupRepository.findById(id).orElseThrow();
        raidGroup.updateRaidName(raidName);
    }

    @Transactional
    public void setRaidTime(Long id, String raidTime) {
        RaidGroup raidGroup = raidGroupRepository.findById(id).orElseThrow();
        raidGroup.updateRaidTime(raidTime);
    }

    @Transactional(readOnly = true)
    public List<RaidGroupListResponse> getRaidGroup(String day, String raid) {

        List<RaidGroup> raidGroups;

        if("All".equals(day) && "All".equals(raid)) {
            raidGroups = raidGroupRepository.findAllWithCharacters();
        } else if("All".equals(day)) {
            raidGroups = raidGroupRepository.findAllRaidNameWithCharacters(raid);
        } else if ("All".equals(raid)) {
            raidGroups = raidGroupRepository.findAllByDayWithCharacters(day);
        } else {
            raidGroups = raidGroupRepository.findAllByDayAndRaidNameWithCharacters(day, raid);
        }

        return raidGroups.stream().map(raidGroup -> {
            RaidGroupListResponse response = new RaidGroupListResponse();
            response.setRaidGroupId(raidGroup.getId());
            response.setName(raidGroup.getRaidName());
            response.setDay(raidGroup.getDay());
            response.setTime(raidGroup.getRaidTime());

            List<CharacterInfoResponse> characters = raidGroup.getCharacters().stream()
                    .map(raidCharacter -> {
                        CharacterInfoResponse characterResponse = new CharacterInfoResponse(
                                raidCharacter.getCharacter().getClassName(),
                                raidCharacter.getCharacter().getLevel(),
                                raidCharacter.getCharacter().getNickName()
                        );
                        return characterResponse;
                    }).collect(Collectors.toList());

            response.setCharacters(characters);
            return response;
        }).collect(Collectors.toList());
    }

    public void addCharacterToRaidGroup(Long raidGroupId, String characterName) {
        RaidGroup raidGroup = raidGroupRepository.findById(raidGroupId).orElseThrow();
        Characters character = characterRepository.findByNickName(characterName);

        boolean isDuplicate = raidGroup.getCharacters().stream()
                .anyMatch(rc -> rc.getCharacter().getId().equals(character.getId()));

        if (isDuplicate) {
            throw new DuplicateCharacterException("이 캐릭터는 이미 해당 레이드 그룹에 추가되었습니다.");
        }

        RaidCharacters raidCharacters = new RaidCharacters(raidGroup, character);
        raidCharactersRepository.save(raidCharacters);
    }

    public void deleteCharacterToRaidGroup(Long raidGroupId, String characterName) {
        Characters characters = characterRepository.findByNickName(characterName);
        RaidCharacters raidCharacter = raidCharactersRepository.findByRaidGroupIdAndCharacterId(raidGroupId, characters.getId());
        raidCharactersRepository.delete(raidCharacter);
    }

    public void deleteRaid(Long raidGroupId) {
        RaidGroup raidGroup = raidGroupRepository.findById(raidGroupId).orElseThrow();
        raidGroupRepository.delete(raidGroup);
    }


}
