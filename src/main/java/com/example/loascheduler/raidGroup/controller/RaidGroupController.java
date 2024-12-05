package com.example.loascheduler.raidGroup.controller;

import com.example.loascheduler.raidGroup.dto.response.RaidGroupListResponse;
import com.example.loascheduler.raidGroup.dto.response.RaidGroupResponse;
import com.example.loascheduler.raidGroup.service.RaidGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RaidGroupController {
    private final RaidGroupService raidGroupService;

    // 레이드 추가
    @PostMapping("/raid/group")
    public ResponseEntity<RaidGroupResponse> addRaidGroup(@RequestParam String day) {
       return ResponseEntity.ok(raidGroupService.addRaidGroup(day));
    }

    // 레이드 목록 조회(요일별)
    @GetMapping("/raid/group")
    public ResponseEntity<List<RaidGroupListResponse>> getRaidGroup(@RequestParam String day) {
        return ResponseEntity.ok(raidGroupService.getRaidGroup(day));
    }

    // 레이드 이름 설정
    @PostMapping("/raid/name/{id}")
    public void setRaidName(@RequestParam String raidName, @PathVariable Long id) {
        raidGroupService.setRaidName(id, raidName);
    }

    // 레이드에 캐릭터 추가
    @PostMapping("/raid/character")
    public void addCharacterToRaidGroup(@RequestParam Long raidGroupId, @RequestParam String characterName) {
        System.out.println(characterName);
        raidGroupService.addCharacterToRaidGroup(raidGroupId, characterName);
    }

    // 레이드 캐릭터 삭제
    @DeleteMapping("/raid/character")
    public void deleteCharacterToRaidGroup(@RequestParam Long raidGroupId, @RequestParam String characterName) {
        raidGroupService.deleteCharacterToRaidGroup(raidGroupId, characterName);
    }

    // 레이드 삭제
    @DeleteMapping("/raid")
    public void deleteRaid(@RequestParam Long raidGroupId) {
        raidGroupService.deleteRaid(raidGroupId);
    }
}
