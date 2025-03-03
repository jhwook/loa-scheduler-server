package com.example.loascheduler.raidgroup.dto.response;

import com.example.loascheduler.character.dto.response.CharacterInfoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RaidGroupListResponse {
    private Long raidGroupId;
    private String name;
    private String day;
    private String time;
    private List<CharacterInfoResponse> characters;

}
