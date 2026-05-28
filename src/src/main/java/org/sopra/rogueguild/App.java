package org.sopra.rogueguild;

import org.sopra.rogueguild.controller.ShopController;
import org.sopra.rogueguild.repository.QuestRepository;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShopRepository repository = new ShopRepository();
        QuestRepository questRepository = new QuestRepository();
        ViewDisplay view = new ViewDisplay();

        System.out.println("Escribe tu nombre  del perspnaje:");
        String nombre= sc.nextLine().trim();
        Player player = new Player( nombre, 250);

        ShopController controller = new ShopController(player, view, repository, questRepository);
        controller.start();
    }
}