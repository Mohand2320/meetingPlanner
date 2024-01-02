package com.meetingPlanner.dto;


import com.meetingPlanner.entity.Outil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SalleDTO {

    private Long id;
    private int capacite;
    private String nom;
//    private String equipements;
    private List<Outil> outils = new ArrayList<>();
}
