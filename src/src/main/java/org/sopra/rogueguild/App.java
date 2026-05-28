package org.sopra.rogueguild;

import org.sopra.rogueguild.controller.ShopController;
import org.sopra.rogueguild.repository.QuestRepository;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.PlayerRol;
import org.sopra.rogueguild.view.ViewDisplay;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShopRepository repository = new ShopRepository();
        QuestRepository questRepository = new QuestRepository();
        ViewDisplay view = new ViewDisplay();

        System.out.println("Escribe tu nombre  del perspnaje:");
        String name= sc.nextLine().trim();
        System.out.println("Elige tu rol de personaje:");
        System.out.println("1.Warrior");
        System.out.println("2.Mage");
        System.out.println("3.Rogue");
        System.out.println("4.Archer");
        int option= sc.nextInt();
        PlayerRol playerRol = null;
        switch (option){
            case 1:
                playerRol=PlayerRol.WARRIOR;
                break;
            case 2:
                playerRol=PlayerRol.MAGE;
                break;
            case 3:
                playerRol=PlayerRol.ROGUE;
                break;
            case 4:
                playerRol=PlayerRol.ARCHER;
                break;
        }


        Player player = new Player( name, 250,playerRol);

        ShopController controller = new ShopController(player, view, repository, questRepository);
        controller.start();
    }
}