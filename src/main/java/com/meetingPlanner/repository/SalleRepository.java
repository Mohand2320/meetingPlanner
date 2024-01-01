package com.meetingPlanner.repository;

import com.meetingPlanner.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
}
