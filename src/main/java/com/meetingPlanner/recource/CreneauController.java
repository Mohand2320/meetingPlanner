package com.meetingPlanner.recource;

import com.meetingPlanner.dto.CreneauDTO;
import com.meetingPlanner.entity.Creneau;
import com.meetingPlanner.service.CreneauService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/creneaux")
public class CreneauController {

    private final CreneauService creneauService;

    public CreneauController(CreneauService creneauService) {
        this.creneauService = creneauService;
    }

    @GetMapping
    public List<Creneau> getAllCreneaux() {
        return creneauService.getAllCreneaux();
    }

    @GetMapping("{id}")
    public Creneau getCreneauById(@PathVariable("id") Long id) {
        return creneauService.getCreneauById(id);
    }

    @PostMapping
    public ResponseEntity<CreneauDTO> saveCreneau(@RequestBody CreneauDTO creneauDTO) {
        CreneauDTO savedCreneau = creneauService.saveCreneau(creneauDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCreneau.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedCreneau);
    }

    @PutMapping("{id}")
    public void updateCreneau(@PathVariable("id") Long id, @RequestBody CreneauDTO creneau) {
        // Assurez-vous que l'ID dans le chemin correspond à l'ID dans le corps de la requête
        if (!Objects.equals(id, creneau.getId())) {
            throw new IllegalArgumentException("L'ID dans le chemin ne correspond pas à l'ID dans le corps de la requête.");
        }
        creneauService.updateCreneau(creneau);
    }

    @DeleteMapping("{id}")
    public void deleteCreneau(@PathVariable("id") Long id) {
        creneauService.deleteCreneau(id);
    }
}
