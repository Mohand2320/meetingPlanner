package com.meetingPlanner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data@AllArgsConstructor @NoArgsConstructor
public class CreneauDTO {
    private Long id;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private Long salleId;
    private boolean reserve;
}