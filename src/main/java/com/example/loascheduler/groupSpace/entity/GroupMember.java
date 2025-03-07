package com.example.loascheduler.groupSpace.entity;

import com.example.loascheduler.common.entity.Timestamped;
import com.example.loascheduler.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "group_member_seq", sequenceName = "group_member_sequence", allocationSize = 1)
@Table(name = "group_member")
public class GroupMember extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_member_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupSpace groupSpace;
}
