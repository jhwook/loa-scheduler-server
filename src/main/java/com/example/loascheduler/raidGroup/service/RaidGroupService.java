package com.example.loascheduler.raidGroup.service;

import com.example.loascheduler.character.dto.response.CharacterInfoResponse;
import com.example.loascheduler.character.entity.Characters;
import com.example.loascheduler.character.repository.CharacterRepository;
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

    @Transactional(readOnly = true)
    public List<RaidGroupListResponse> getRaidGroup(String day) {
        List<RaidGroup> raidGroups = raidGroupRepository.findAllByDayWithCharacters(day);

        return raidGroups.stream().map(raidGroup -> {
            RaidGroupListResponse response = new RaidGroupListResponse();
            response.setRaidGroupId(raidGroup.getId());
            response.setName(raidGroup.getRaidName());
            response.setDay(raidGroup.getDay());

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
