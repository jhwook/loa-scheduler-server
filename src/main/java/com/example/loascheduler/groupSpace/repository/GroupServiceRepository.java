package com.example.loascheduler.groupSpace.repository;

import com.example.loascheduler.groupSpace.entity.GroupSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupServiceRepository extends JpaRepository<GroupSpace, Long> {
}
