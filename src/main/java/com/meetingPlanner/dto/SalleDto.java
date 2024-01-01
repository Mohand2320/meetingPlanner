package com.meetingPlanner.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SalleDto {

    private Long id;
    private String nom;
//    @Pattern(regexp = "^(\\+213|0)([ \\-_/]*)(\\d[ \\-_/]*){9}$", message = "format invalid")
    private int capacite;
    private String outils ;
}
