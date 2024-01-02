package com.meetingPlanner.entity;

import jakarta.persistence.*;
import lombok.*;
//import javax.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
//    @Pattern(regexp = "", message = "format invalid")
    private String nom;

    @Column(nullable = false)
    private int capacite;

    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Outil> outils = new ArrayList<>();

    @OneToMany(mappedBy = "salle")
    private Set<Creneau> creneaux = new HashSet<>();

    public void addCrenau(Creneau c){
        this.creneaux.add(c);
    }

}