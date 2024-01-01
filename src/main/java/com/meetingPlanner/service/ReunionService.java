package com.meetingPlanner.service;

import com.meetingPlanner.entity.Reunion;
import com.meetingPlanner.repository.ReunionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReunionService {

    private final ReunionRepository reunionRepository;

    public ReunionService(ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    public List<Reunion> getAllReunions() {
        return reunionRepository.findAll();
    }

    public Reunion getReunionById(Long id) {
        return reunionRepository.findById(id).orElse(null);
    }

    public Reunion saveReunion(Reunion reunion) {
        return reunionRepository.save(reunion);
    }

    public void deleteReunion(Long id) {
        reunionRepository.deleteById(id);
    }
}
