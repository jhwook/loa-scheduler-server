package com.example.loascheduler.classType.entity;

import com.example.loascheduler.common.entity.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class ClassType extends Timestamped {
    @Id
    private Long id;

    private String className;

    private String imgUrl;
}
