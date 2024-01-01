package com.meetingPlanner.service;
import com.meetingPlanner.entity.Creneau;
import com.meetingPlanner.repository.CreneauRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CreneauService {

    private final CreneauRepository creneauRepository;

    public CreneauService(CreneauRepository creneauRepository) {
        this.creneauRepository = creneauRepository;
    }

    public List<Creneau> getAllCreneaux() {
        return creneauRepository.findAll();
    }

    public Creneau getCreneauById(Long id) {
        return creneauRepository.findById(id).orElse(null);
    }

    public Creneau saveCreneau(Creneau creneau) {
        return creneauRepository.save(creneau);
    }

    public void deleteCreneau(Long id) {
        creneauRepository.deleteById(id);
    }
}
