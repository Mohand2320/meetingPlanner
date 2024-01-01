package com.meetingPlanner.service;

import com.meetingPlanner.entity.Outil;
import com.meetingPlanner.repository.OutilRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutilService {

    private final OutilRepository outilRepository;

    public OutilService(OutilRepository outilRepository) {
        this.outilRepository = outilRepository;
    }

    public List<Outil> getAllOutils() {
        return outilRepository.findAll();
    }

    public Outil getOutilById(Long id) {
        return outilRepository.findById(id).orElse(null);
    }

    public Outil saveOutil(Outil outil) {
        return outilRepository.save(outil);
    }

    public void deleteOutil(Long id) {
        outilRepository.deleteById(id);
    }
}