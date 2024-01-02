package com.meetingPlanner.service;
import com.meetingPlanner.dto.CreneauDTO;
import com.meetingPlanner.entity.Creneau;
import com.meetingPlanner.entity.Salle;
import com.meetingPlanner.exception.EntityAleadyExisteException;
import com.meetingPlanner.exception.InvalidTimeException;
import com.meetingPlanner.repository.CreneauRepository;
import com.meetingPlanner.repository.SalleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CreneauService {

    private final CreneauRepository creneauRepository;
    private final SalleRepository salleRepository;
    private final ModelMapper modelMapper;

    public CreneauService(CreneauRepository creneauRepository, SalleRepository salleRepository, ModelMapper modelMapper) {
        this.creneauRepository = creneauRepository;
        this.salleRepository = salleRepository;
        this.modelMapper = modelMapper;
    }

    public List<Creneau> getAllCreneaux() {
        return creneauRepository.findAll();
    }

    public Creneau getCreneauById(Long id) {
        return creneauRepository.findById(id).orElse(null);
    }

    public CreneauDTO saveCreneau(CreneauDTO creneauDTO) {
        if(isValidDateTime(creneauDTO.getHeureDebut()) || isValidDateTime(creneauDTO.getHeureFin())  ){
            Optional<Salle> salle = salleRepository.findById(creneauDTO.getSalleId());
            if(!salle.isPresent()) new EntityAleadyExisteException("La salle n'existe pas !");
            Salle salle1 = salle.get();
            Creneau creneau = modelMapper.map(creneauDTO, Creneau.class);
            Creneau creneauSaved = creneauRepository.save(creneau);
            salle1.addCrenau(creneauSaved);
            salleRepository.save(salle1);

            return modelMapper.map(creneauSaved, CreneauDTO.class);
        }else {
            new InvalidTimeException("La date ou l'heure ne sont pas dans l'intervalle temporel valide !");
            return null;
        }

    }

    public CreneauDTO updateCreneau (CreneauDTO creneauDTO){
        return null;
    }

    public  boolean isValidDateTime(LocalDateTime dateTime) {
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

    public void deleteCreneau(Long id) {
        creneauRepository.deleteById(id);
    }
}
