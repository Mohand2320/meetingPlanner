package com.meetingPlanner.service;

import com.meetingPlanner.dto.ReunionDTO;
import com.meetingPlanner.entity.Creneau;
import com.meetingPlanner.entity.Reunion;
import com.meetingPlanner.entity.Salle;
import com.meetingPlanner.exception.EntityAleadyExisteException;
import com.meetingPlanner.repository.CreneauRepository;
import com.meetingPlanner.repository.ReunionRepository;
import com.meetingPlanner.repository.SalleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Service
public class ReunionService {

    private final ReunionRepository reunionRepository;
    private final SalleRepository salleRepository;

    private final CreneauRepository creneauRepository;

    private final ModelMapper modelMapper;

    public ReunionService(ReunionRepository reunionRepository, SalleRepository salleRepository, CreneauRepository creneauRepository, ModelMapper modelMapper) {
        this.reunionRepository = reunionRepository;
        this.salleRepository = salleRepository;
        this.creneauRepository = creneauRepository;
        this.modelMapper = modelMapper;
    }


    public List<Salle> getSallesWithOutilsAndCapacite(List<Salle> salles, List<String> nomsOutils, int nombrePersonnes) {
        return salles.stream()
                .filter(salle -> nomsOutils.stream()
                        .allMatch(nomOutil -> salle.getOutils().stream()
                                .anyMatch(outil -> outil.getNom().equals(nomOutil))))
                .filter(salle -> salle.getCapacite() >= nombrePersonnes)
                .collect(Collectors.toList());
    }


    public Optional<Reunion> affecterCreneaux(Long idReunion){
        Optional<Reunion> reunion = reunionRepository.findById(idReunion);
        List<Salle> salleList = getSallesWithOutilsAndCapacite(salleRepository.findAll(), reunion.get().getType()
                .getEquipements(), reunion.get().getNombrePersonne());

        for (Salle salle : salleList) {
            boolean find = false;
            Creneau foundCreneau = null;
            for (Creneau creneau : salle.getCreneaux()) {
                if (creneau.getHeureDebut().isEqual(reunion.get().getHeureDebut())
                        && creneau.getHeureFin().isEqual(reunion.get().getHeureFin())) {
                    find =true;
                    if(!creneau.isReserve()){
                        foundCreneau = creneau;
                        break;
                    }
                }
            }

            if (foundCreneau != null) {
                foundCreneau.setReserve(true);
                foundCreneau.setReunion(reunion.get());
                reunion.get().setCreneau(foundCreneau);
//                reunion.get().setSalle(salle);
                creneauRepository.save(foundCreneau);
//                salleRepository.save(salle);
                reunionRepository.save(reunion.get());
                break;
            } else if(find==false) {
                Creneau newCreneau = new Creneau();
                newCreneau.setHeureDebut(reunion.get().getHeureDebut());
                newCreneau.setHeureFin(reunion.get().getHeureFin());
                newCreneau.setSalle(salle);
                newCreneau.setReserve(true);
                newCreneau.setReunion(reunion.get());
                salle.addCrenau(newCreneau);
                reunion.get().setCreneau(newCreneau);
//                reunion.get().setSalle(salle);
                creneauRepository.save(newCreneau);
//                salleRepository.save(salle);
                reunionRepository.save(reunion.get());
                break;
            }
        }
        return reunion;
    }






    public List<Reunion> getAllReunions() {

        return reunionRepository.findAll();
    }

    public Reunion getReunionById(Long id) {

        return reunionRepository.findById(id).orElse(null);
    }

    public Optional<Reunion> saveReunion(ReunionDTO reunionDTO) {
        if (isValidDateTime(reunionDTO.getHeureDebut()) && isValidDateTime(reunionDTO.getHeureFin())) {
            Reunion reunion = modelMapper.map(reunionDTO , Reunion.class);
            return Optional.of(reunionRepository.save(reunion));
        } else {
            throw new EntityAleadyExisteException("change les heures  !");
        }
    }

    public Optional<Reunion> updateReunion(ReunionDTO reunionDTO){
        return Optional.empty();
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

        return dateTime.isEqual(now) || dateTime.isAfter(now)
                && !time.isBefore(startTime)
                && !time.isAfter(endTime)
                && day != DayOfWeek.FRIDAY
                && day != DayOfWeek.SATURDAY;
    }
}
