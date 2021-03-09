package fr.openclassrooms.rayane.demo.dto;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class VisitedAttractionDTO {
    public final UUID userId;
    public final UUID attractionId;
}
