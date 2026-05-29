package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.repository.model.City;

import java.io.PrintStream;
import java.util.List;

public class TravelView {
    private final PrintStream out;

    public TravelView(PrintStream out) {
        this.out = out;
    }

    public void displayTravelMenu(City currentCity) {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |           SISTEMA DE VIAJE                   | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        out.printf ("|| |  Ciudad actual: %-30s| ||%n", currentCity.getName());
        out.println("|| |                                               | ||");
        out.println("|| |  Ciudades conectadas:                         | ||");

        List<City> connections = currentCity.getConnections();
        for (int i = 0; i < connections.size(); i++) {
            out.printf("|| |  [%d] %-42s| ||%n", i + 1, connections.get(i).getName());
        }

        out.println("|| |                                               | ||");
        out.println("|| |  [0] Volver                                   | ||");
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }
}