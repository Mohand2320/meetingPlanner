package com.meetingPlanner.recource;

import com.meetingPlanner.dto.SalleDTO;
import com.meetingPlanner.entity.Salle;
import com.meetingPlanner.service.SalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;



@RestController
@RequestMapping("api/v1/salle")
public class SalleController {

    private final SalleService salleService;

    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    @GetMapping("/")
    public ResponseEntity<List<SalleDTO>> getAllSalles() {

            return new ResponseEntity<>(salleService.getAllSalles(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SalleDTO> getSalleById(@PathVariable Long id) {
        SalleDTO salleDTO = salleService.getSalleById(id);
        return ResponseEntity.ok(salleDTO);
    }

    @PostMapping
    public ResponseEntity<SalleDTO> saveSalle(@RequestBody SalleDTO salleDTO) {
        SalleDTO savedSalle = salleService.saveSalle(salleDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSalle.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedSalle);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable Long id) {
        salleService.deleteSalle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{salleId}/addOutil/{outilId}")
    public ResponseEntity<SalleDTO> ajouterOutilSalle(
            @PathVariable Long salleId,
            @PathVariable Long outilId
    ) {
        SalleDTO salleDTO = salleService.ajouterOutilSalle(salleId, outilId);
        return ResponseEntity.ok(salleDTO);
    }
}
