package org.sopra.rogueguild;

import org.sopra.rogueguild.controller.ShopController;
import org.sopra.rogueguild.repository.QuestRepository;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.WorldMap;
import org.sopra.rogueguild.repository.model.City;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.PlayerRol;
import org.sopra.rogueguild.view.ViewDisplay;

import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShopRepository repository = new ShopRepository();
        QuestRepository questRepository = new QuestRepository();
        ViewDisplay view = new ViewDisplay();
        WorldMap worldMap = new WorldMap();

        System.out.print("Escribe el nombre de tu personaje: ");
        String name = sc.nextLine().trim();

        System.out.println("\nElige tu rol de personaje:");
        System.out.println("1. Guerrero");
        System.out.println("2. Hechicero");
        System.out.println("3. Pícaro");
        System.out.println("4. Arquero");
        System.out.print("\nElección: ");

        PlayerRol playerRol = null;
        while (playerRol == null) {
            try {
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1 -> playerRol = PlayerRol.GUERRERO;
                    case 2 -> playerRol = PlayerRol.HECHICERO;
                    case 3 -> playerRol = PlayerRol.PICARO;
                    case 4 -> playerRol = PlayerRol.ARQUERO;
                    default -> System.out.println("Opción no válida, elige entre 1 y 4:");
                }
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido:");
            }
        }

        System.out.println("\n¡Saludos, " + playerRol.getName().toLowerCase() + "/a " + name + "!\n");

        Player player = new Player(name, 250, playerRol, worldMap.getStartingCity());
        ShopController controller = new ShopController(player, view, repository, questRepository, worldMap);
        controller.start();
    }
}