package com.example.loascheduler.groupSpace.entity;

import com.example.loascheduler.common.entity.Timestamped;
import com.example.loascheduler.raidGroup.entity.RaidGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "group_space")
@SequenceGenerator(name = "group_space_seq", sequenceName = "group_space_sequence", allocationSize = 1)
public class GroupSpace extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_space_seq")
    private Long id;

    // name
    private String name;

    // group member
    @OneToMany(mappedBy = "groupSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToMany(mappedBy = "groupSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RaidGroup> raidGroupList = new ArrayList<>();

}
