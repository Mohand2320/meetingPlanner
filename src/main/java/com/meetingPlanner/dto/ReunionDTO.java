package com.meetingPlanner.dto;

import com.meetingPlanner.enums.TypeReunion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReunionDTO {

    private Long id;
    private String sujet;
    private int nombrePersonne;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    @Enumerated(EnumType.STRING)
    private TypeReunion type;

    private String creneauHoraire ;
}
