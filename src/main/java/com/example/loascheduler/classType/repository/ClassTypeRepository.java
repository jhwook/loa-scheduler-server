package com.example.loascheduler.classType.repository;

import com.example.loascheduler.classType.entity.ClassType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassTypeRepository extends JpaRepository<ClassType, Long> {
    ClassType findByClassName(String className);
}
