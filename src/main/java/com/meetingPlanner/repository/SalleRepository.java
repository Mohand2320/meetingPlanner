package com.meetingPlanner.repository;

import com.meetingPlanner.dto.SalleDTO;
import com.meetingPlanner.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {


    Optional<Salle> findByNom(String nom);
}
