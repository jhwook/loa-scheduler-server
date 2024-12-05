package com.example.loascheduler.client;

import com.example.loascheduler.user.dto.response.CharacterInfoResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class CharacterInfoService {
    private String bearerToken = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDA0NzMzODEifQ.mcaqyCtheQ0ys2U6IF5P6l5C7wuPDgANm33JsMEsDXJkK-Sg2QEFiGARyK-fhd2w9ji0jvz-6n9jMAQXaa2F6k70jexhpt19ISIAkrcs95Mb1bzQ-elFxZ4omYtS9aGw5RJ8695N_SYk9yLIGgnkrordB7E2qeyCUdYajcv9R1IiDDZH728jPpjm_ls65P4o4ZSmiobYDeMHYNOK-XWjeNuZrcBI9JyfoSUyWGA59nLUiNwHyQCakruuz6ORbOKgwSXZp4AbRebYLHulnvqWIrCpgJe6dJVk-Gd_RsYRF6niqLVkLi9DZ6QSmmKOxzaumWaMrl4GlkJ69_-4RaO3oQ";

    private final RestTemplate restTemplate;

    public CharacterInfoService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public CharacterInfoResponseDto getMyCharacter(String characterName) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://developer-lostark.game.onstove.com/armories/characters/")
                .path(characterName)
                .path("/profiles")
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .header("Authorization", bearerToken)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        JSONObject json = new JSONObject(response.getBody());

        String name = String.valueOf(json.get("CharacterName"));
        String itemAvgLevel = String.valueOf(json.get("ItemAvgLevel"));
        String characterClassName = String.valueOf(json.get("CharacterClassName"));

        log.info(name + " / " + itemAvgLevel + " / " + characterClassName);

        return new CharacterInfoResponseDto(name, itemAvgLevel, characterClassName);
    }
}
