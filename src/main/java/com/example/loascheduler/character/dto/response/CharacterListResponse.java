package com.example.loascheduler.character.dto.response;

import lombok.Getter;

@Getter
public class CharacterListResponse {
    private String serverName;
    private String characterName;
    private int characterLevel;
    private String characterClassName;
    private String itemAvgLevel;
    private String itemMaxLevel;

    public CharacterListResponse(String serverName, String characterName, int characterLevel,
                                String characterClassName, String itemAvgLevel, String itemMaxLevel) {
        this.serverName = serverName;
        this.characterName = characterName;
        this.characterLevel = characterLevel;
        this.characterClassName = characterClassName;
        this.itemAvgLevel = itemAvgLevel;
        this.itemMaxLevel = itemMaxLevel;
    }
}
