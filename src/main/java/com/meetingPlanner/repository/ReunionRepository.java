package com.meetingPlanner.repository;

import com.meetingPlanner.entity.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long> {
}