package com.example.loascheduler.user.dto.response;

import lombok.Getter;

@Getter
public class CharacterInfoResponseDto {
    private String characterName;
    private String itemLevel;
    private String characterClassName;

    public CharacterInfoResponseDto(String name, String itemAvgLevel, String characterClassName) {
        this.characterName = name;
        this.itemLevel = itemAvgLevel;
        this.characterClassName = characterClassName;
    }
}
