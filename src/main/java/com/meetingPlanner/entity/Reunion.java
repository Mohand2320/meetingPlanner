package com.meetingPlanner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meetingPlanner.enums.TypeReunion;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sujet;
    private int nombrePersonne;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
//    @ManyToOne
//    private Employe organisateur;
//    @ManyToOne
//    @JoinColumn(name = "salle_id")
//    @JsonIgnore
//    private Salle salle;
    @Enumerated(EnumType.STRING)
    private TypeReunion type;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creneau_id", referencedColumnName = "id")
    private Creneau creneau;
}