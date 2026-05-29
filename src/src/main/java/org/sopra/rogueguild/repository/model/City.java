package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;
import java.util.List;

public class City {
    private final String name;
    private final List<City> connections;

    public City(String name) {
        this.name = name;
        this.connections = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<City> getConnections() {
        return new ArrayList<>(connections);
    }

    public void addConnection(City city) {
        if (!connections.contains(city)) {
            connections.add(city);
            city.connections.add(this);
        }
    }
}
