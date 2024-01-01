package com.meetingPlanner.service;

import com.meetingPlanner.entity.Outil;
import com.meetingPlanner.entity.Salle;
import com.meetingPlanner.repository.OutilRepository;
import com.meetingPlanner.repository.SalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SalleService {

    private final SalleRepository salleRepository;
    private final OutilRepository outilRepository;

    public SalleService(SalleRepository salleRepository, OutilRepository outilRepository) {
        this.salleRepository = salleRepository;
        this.outilRepository = outilRepository;
    }

    public List<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    public Salle getSalleById(Long id) {
        return salleRepository.findById(id).orElse(null);
    }

    public Salle saveSalle(Salle salle) {
        return salleRepository.save(salle);
    }

    public void deleteSalle(Long id) {
        salleRepository.deleteById(id);
    }

    public Salle ajouterOutilSalle(Long salleId, Long outilId) {
        Salle salle = salleRepository.findById(salleId).get();
        Outil outil = outilRepository.findById(outilId).get();

        salle.getOutils().add(outil);
        return salleRepository.save(salle);
    }
}
