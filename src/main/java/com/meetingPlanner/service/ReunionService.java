package com.meetingPlanner.service;

import com.meetingPlanner.dto.ReunionDTO;
import com.meetingPlanner.entity.Creneau;
import com.meetingPlanner.entity.Reunion;
import com.meetingPlanner.entity.Salle;
import com.meetingPlanner.exception.EntityAleadyExisteException;
import com.meetingPlanner.exception.InvalidTimeException;
import com.meetingPlanner.repository.CreneauRepository;
import com.meetingPlanner.repository.ReunionRepository;
import com.meetingPlanner.repository.SalleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        // touver la liste des salle avec les conditions de capacité n type de reunion
        List<Salle> salleList = getSallesWithOutilsAndCapacite(salleRepository.findAll(), reunion.get().getType()
                .getEquipements(), reunion.get().getNombrePersonne());
        // trouve le creneau
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

            if (foundCreneau != null) { // si le crenau déja cree mais pas reseve !
                foundCreneau.setReserve(true);
                foundCreneau.setReunion(reunion.get());
                reunion.get().setCreneau(foundCreneau);
//                reunion.get().setSalle(salle);
                creneauRepository.save(foundCreneau);
//                salleRepository.save(salle);
                reunionRepository.save(reunion.get());
                break;
            } else if(find==false) {
                // chercher s'il exite un creneau avant l'heur de la reunion
                Boolean b = salle.getCreneaux().stream()
                        .anyMatch(creneau -> (creneau.getHeureFin().isEqual(reunion.get().getHeureDebut().minusHours(1))
                                || creneau.getHeureFin().isAfter(reunion.get().getHeureDebut().minusHours(1)))
                                && creneau.isReserve());

                if(b) {  //si le creneau precedent n est pas resevé
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
                } else { //  si le creneau precedent est reserve
                    break;
                }
            }
        }
        return reunion;
    }



    public List<ReunionDTO> getAllReunions() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<Reunion> reunions = reunionRepository.findAll();
        List<ReunionDTO> reunionDTOs = reunions.stream()
                .map(reunion -> {
                    ReunionDTO dto = modelMapper.map(reunion, ReunionDTO.class);
                    if(reunion.getCreneau() != null) {
                        dto.setCreneauHoraire(reunion.getCreneau().getHeureDebut().format(formatter) + " - " +
                                reunion.getCreneau().getHeureFin().format(formatter) + "| salle : "+ reunion.getCreneau().getSalle().getNom());
                    } else {
                        dto.setCreneauHoraire(" - ");
                    }
                    return dto;
                })
                .collect(Collectors.toList());
        return reunionDTOs;
    }

    public Reunion getReunionById(Long id) {

        return reunionRepository.findById(id).orElse(null);
    }

    public Optional<Reunion> saveReunion(ReunionDTO reunionDTO) {
        if (isValidDateTime(reunionDTO.getHeureDebut()) && isValidDateTime(reunionDTO.getHeureFin())) {
            Reunion reunion = modelMapper.map(reunionDTO , Reunion.class);
            return Optional.of(reunionRepository.save(reunion));
        } else {
            throw new EntityAleadyExisteException(" choisissez une autre heure ou journee pour la reservation !");
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
