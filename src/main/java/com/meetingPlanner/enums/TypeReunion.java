package com.meetingPlanner.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TypeReunion {
//    VC,     // Visioconférence
//    SPEC,   // Séance de partage et d'étude de cas
//    RS,     // Réunion simple
//    RC      // Réunion couplée


    VC("VC", Arrays.asList("Ecran", "Pieuvre", "Webcam")),
    SPEC("SPEC", Arrays.asList("Tableau")),
    RS("RS", new ArrayList<>()),
    RC("RC", Arrays.asList("Tableau", "Ecran", "Pieuvre"));

    private final String nom;
    private final List<String> equipements;

    TypeReunion(String nom, List<String> equipements) {
        this.nom = nom;
        this.equipements = equipements;
    }

    public String getNom() {
        return nom;
    }

    public List<String> getEquipements() {
        return equipements;
    }
}