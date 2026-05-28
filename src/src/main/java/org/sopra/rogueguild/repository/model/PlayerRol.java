package org.sopra.rogueguild.repository.model;

public enum PlayerRol {
    GUERRERO("Guerrero"),
    HECHICERO("Hechicero"),
    PICARO("Pícaro"),
    ARQUERO("Arquero");

    private final String name;

    PlayerRol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
