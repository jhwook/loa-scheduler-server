package com.example.loascheduler.groupspace.repository;

import com.example.loascheduler.groupspace.entity.GroupSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupServiceRepository extends JpaRepository<GroupSpace, Long> {
}
