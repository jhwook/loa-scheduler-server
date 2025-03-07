package com.example.loascheduler.character.dto.response;

import lombok.Getter;

@Getter
public class CharacterListResponse {
    private String serverName;
    private String characterName;
    private Integer characterLevel;
    private String characterClassName;
    private String itemLevel;

    public CharacterListResponse(String serverName, String characterName, Integer characterLevel,
                                String characterClassName, String itemLevel) {
        this.serverName = serverName;
        this.characterName = characterName;
        this.characterLevel = characterLevel;
        this.characterClassName = characterClassName;
        this.itemLevel = itemLevel;
    }
}
