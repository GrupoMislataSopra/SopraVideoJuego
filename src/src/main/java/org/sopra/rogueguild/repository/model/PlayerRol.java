package org.sopra.rogueguild.repository.model;

public enum PlayerRol {
    WARRIOR("WATRRIOR"),
    MAGE("MAGE"),
    ROGUE("ROGUE"),
    ARCHER("ARCHER");

    private final String name;

    PlayerRol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}
