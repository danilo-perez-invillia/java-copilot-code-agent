package com.mergingtonhigh.schoolmanagement.domain.valueobjects;

/**
 * Enum representing the difficulty levels for activities.
 * Each level has a Portuguese label for display purposes.
 */
public enum DifficultyLevel {
    BEGINNER("Iniciante"),
    INTERMEDIATE("Intermediário"),
    ADVANCED("Avançado");

    private final String label;

    DifficultyLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}