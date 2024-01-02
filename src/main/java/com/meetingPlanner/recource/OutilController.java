package com.meetingPlanner.recource;

import com.meetingPlanner.entity.Outil;
import com.meetingPlanner.service.OutilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/outil")
public class OutilController {

    private final OutilService outilService;

    public OutilController(OutilService outilService) {
        this.outilService = outilService;
    }

    @GetMapping
    public ResponseEntity<List<Outil>> getAllOutils() {
        List<Outil> outils = outilService.getAllOutils();
        return ResponseEntity.ok(outils);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Outil> getOutilById(@PathVariable Long id) {
        Outil outil = outilService.getOutilById(id);
        if (outil != null) {
            return ResponseEntity.ok(outil);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Outil> saveOutil(@RequestBody Outil outil) {
        Outil savedOutil = outilService.saveOutil(outil);
        return ResponseEntity.ok(savedOutil);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutil(@PathVariable Long id) {
        Outil existingOutil = outilService.getOutilById(id);
        if (existingOutil != null) {
            outilService.deleteOutil(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
