package com.meetingPlanner.recource;

import com.meetingPlanner.dto.ReunionDTO;
import com.meetingPlanner.entity.Reunion;
import com.meetingPlanner.service.ReunionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reunions")
public class ReunionController {

    private final ReunionService reunionService;

    public ReunionController(ReunionService reunionService) {
        this.reunionService = reunionService;
    }


    /**
     * tourver un creneau pour une reunioin
     * @param id de la reunion
     * @return
     */
    @GetMapping("/getCreneau/{id}")
    public ResponseEntity<Optional<Reunion>> getCreneau(@PathVariable Long id) {
        Optional<Reunion> reunion  = reunionService.affecterCreneaux(id);
        return ResponseEntity.ok(reunion);
//        if (reunion.isPresent()) {
//                return ResponseEntity.ok(reunion);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }


    @PostMapping
    public ResponseEntity<Reunion> saveReunion(@RequestBody ReunionDTO reunion) {
        Optional<Reunion> savedReunionOpt = reunionService.saveReunion(reunion);
        if (savedReunionOpt.isPresent()) {
            Reunion savedReunion = savedReunionOpt.get();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedReunion.getId())
                    .toUri();
            return ResponseEntity.created(location).body(savedReunion);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<ReunionDTO>> getAllReunions() {
        List<ReunionDTO> reunions = reunionService.getAllReunions();
        return ResponseEntity.ok(reunions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reunion> getReunionById(@PathVariable Long id) {
        Reunion reunion = reunionService.getReunionById(id);
        if (reunion != null) {
            return ResponseEntity.ok(reunion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<Optional<Reunion>> updateReunion(@PathVariable Long id, @RequestBody ReunionDTO reunion) {
        Optional<Reunion> existingReunion = reunionService.updateReunion(reunion);
        if (!existingReunion.isPresent()) {
            reunion.setId(id); // Assurez-vous que l'ID est correctement défini
            Optional<Reunion> updatedReunion = reunionService.saveReunion(reunion);
            return ResponseEntity.ok(updatedReunion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReunion(@PathVariable Long id) {
        Reunion existingReunion = reunionService.getReunionById(id);
        if (existingReunion != null) {
            reunionService.deleteReunion(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
