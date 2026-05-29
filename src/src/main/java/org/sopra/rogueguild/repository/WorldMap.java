package org.sopra.rogueguild.repository;

import org.sopra.rogueguild.repository.model.City;

public class WorldMap {
    private final City ironhold;
    private final City ashenvale;
    private final City valdoria;
    private final City thornwick;
    private final City duskport;
    private final City grimstone;
    private final City shadowfen;

    public WorldMap() {
        ironhold = new City("Ironhold");
        ashenvale = new City("Ashenvale");
        valdoria = new City("Valdoria");
        thornwick = new City("Thornwick");
        duskport = new City("Duskport");
        grimstone = new City("Grimstone");
        shadowfen = new City("Shadowfen");

        ironhold.addConnection(ashenvale);
        ashenvale.addConnection(valdoria);
        ashenvale.addConnection(duskport);
        valdoria.addConnection(thornwick);
        thornwick.addConnection(grimstone);
        duskport.addConnection(shadowfen);
    }

    public City getIronhold() {
        return ironhold;
    }

    public City getAshenvale() {
        return ashenvale;
    }

    public City getValdoria() {
        return valdoria;
    }

    public City getThornwick() {
        return thornwick;
    }

    public City getDuskport() {
        return duskport;
    }

    public City getGrimstone() {
        return grimstone;
    }

    public City getShadowfen() {
        return shadowfen;
    }

    public City getStartingCity() {
        return ironhold;
    }
}
