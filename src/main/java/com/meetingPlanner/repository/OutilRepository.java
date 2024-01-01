package com.meetingPlanner.repository;

import com.meetingPlanner.entity.Outil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OutilRepository extends JpaRepository<Outil, Long> {
}