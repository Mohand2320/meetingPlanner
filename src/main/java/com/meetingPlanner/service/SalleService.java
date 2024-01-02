package com.meetingPlanner.service;

import com.meetingPlanner.dto.SalleDTO;
import com.meetingPlanner.entity.Outil;
import com.meetingPlanner.entity.Salle;
import com.meetingPlanner.exception.EntityAleadyExisteException;
import com.meetingPlanner.repository.OutilRepository;
import com.meetingPlanner.repository.SalleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SalleService {

    private final SalleRepository salleRepository;
    private final OutilRepository outilRepository;
    private final ModelMapper modelMapper;

    public SalleService(SalleRepository salleRepository, OutilRepository outilRepository, ModelMapper modelMapper) {
        this.salleRepository = salleRepository;
        this.outilRepository = outilRepository;
        this.modelMapper = modelMapper;
    }

    public List<SalleDTO> getAllSalles() {

//        List<Salle> salleList = salleRepository.findAll();
//
//        return  salleList;
        return salleRepository.findAll().stream()
                .map(salle -> modelMapper.map(salle, SalleDTO.class))
                .collect(Collectors.toList());
    }

    public SalleDTO getSalleById(Long id) {
        Salle salle = salleRepository.findById(id).get();
        return modelMapper.map(salle, SalleDTO.class);

    }

    public SalleDTO saveSalle(SalleDTO salleDTO) {

        Optional<Salle> facture = salleRepository.findByNom(salleDTO.getNom());
        if(facture.isPresent()){
            throw new EntityAleadyExisteException("Cette salle éxiste déja");
        }else {
            Salle salle = modelMapper.map(salleDTO, Salle.class);
             return modelMapper.map(salleRepository.save(salle), SalleDTO.class);

        }




    }

    public void deleteSalle(Long id) {
        salleRepository.deleteById(id);
    }

    public SalleDTO ajouterOutilSalle(Long salleId, Long outilId) {
        Salle salle = salleRepository.findById(salleId).get();
        Outil outil = outilRepository.findById(outilId).get();
        salle.getOutils().add(outil);
        return modelMapper.map(salleRepository.save(salle), SalleDTO.class);
    }

    public  int calculerNombre(int total, double pourcentage) {
        return (int) Math.round(total * (pourcentage / 100));
    }
}
