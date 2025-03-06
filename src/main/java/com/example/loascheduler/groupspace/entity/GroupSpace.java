package com.example.loascheduler.groupspace.entity;

import com.example.loascheduler.common.entity.Timestamped;
import jakarta.persistence.*;

@Entity
@Table(name = "group_space")
public class GroupSpace extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


}
