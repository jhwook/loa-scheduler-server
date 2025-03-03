package com.example.loascheduler.raidgroup.dto.response;

import lombok.Getter;

@Getter
public class RaidGroupResponse {
    private String day;
    private Long id;

    public RaidGroupResponse(Long id, String day) {
        this.id = id;
        this.day = day;
    }
}
