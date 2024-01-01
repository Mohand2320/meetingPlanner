package com.meetingPlanner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

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
    private Date heureDebut;

    @Column(nullable = false)
    private Date heureFin;

    @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salle salle;

    @Column(nullable = false)
    private boolean reserve;

    @OneToOne(mappedBy = "creneau")
    private Reunion reunion;
}
