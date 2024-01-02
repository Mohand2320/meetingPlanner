package com.meetingPlanner.service;

import com.meetingPlanner.entity.Reunion;
import com.meetingPlanner.entity.Salle;
import com.meetingPlanner.exception.EntityAleadyExisteException;
import com.meetingPlanner.repository.ReunionRepository;
import com.meetingPlanner.repository.SalleRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReunionService {

    private final ReunionRepository reunionRepository;
    private final SalleRepository salleRepository;

    public ReunionService(ReunionRepository reunionRepository, SalleRepository salleRepository) {
        this.reunionRepository = reunionRepository;
        this.salleRepository = salleRepository;
    }


    public List<Salle> getSallesWithOutilsAndCapacite(List<Salle> salles, List<String> nomsOutils, int nombrePersonnes) {
        return salles.stream()
                .filter(salle -> nomsOutils.stream()
                        .allMatch(nomOutil -> salle.getOutils().stream()
                                .anyMatch(outil -> outil.getNom().equals(nomOutil))))
                .filter(salle -> salle.getCapacite() >= nombrePersonnes)
                .collect(Collectors.toList());
    }

    public List<Salle> affecterCreneaux(Long idReunion){
        Optional<Reunion> reunion = reunionRepository.findById(idReunion);
        List<Salle> salleList = getSallesWithOutilsAndCapacite(salleRepository.findAll(), reunion.get().getType().getEquipements(), reunion.get().getNombrePersonne());
            return salleList;
    }

    public List<Reunion> getAllReunions() {

        return reunionRepository.findAll();
    }

    public Reunion getReunionById(Long id) {

        return reunionRepository.findById(id).orElse(null);
    }

    public Optional<Reunion> saveReunion(Reunion reunion) {
        if (isValidDateTime(reunion.getHeureDebut()) && isValidDateTime(reunion.getHeureFin())) {
            return Optional.of(reunionRepository.save(reunion));
        } else {
            throw new EntityAleadyExisteException("La salle n'existe pas !");
        }
    }


    public void deleteReunion(Long id) {
        reunionRepository.deleteById(id);
    }

    public boolean isValidDateTime(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(20, 0);
        LocalTime time = dateTime.toLocalTime();
        DayOfWeek day = dateTime.getDayOfWeek();

        return dateTime.isAfter(now)
                && !time.isBefore(startTime)
                && !time.isAfter(endTime)
                && day != DayOfWeek.FRIDAY
                && day != DayOfWeek.SATURDAY;
    }
}
