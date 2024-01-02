package com.meetingPlanner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Creneau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime heureDebut;

    @Column(nullable = false)
    private LocalDateTime heureFin;

    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    @JsonIgnore
    private Salle salle;


    private boolean reserve = false;

    @OneToOne(mappedBy = "creneau")
    private Reunion reunion;
}
