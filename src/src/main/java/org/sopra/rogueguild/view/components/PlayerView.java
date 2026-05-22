package org.sopra.rogueguild.view.components;

import java.io.PrintStream;

import static org.sopra.rogueguild.view.utils.Ansi.*;

import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Player;

public class PlayerView {
    private final PrintStream out;

    public PlayerView(PrintStream out) { this.out = out; }

    public void playerStatus(Player player) {
        out.println();
        out.println("    +---------------------------------------------------+");
        out.println("    |                 " + c(GRAY, "ESTADO COMPRADOR") + "                  |");
        out.println("    +--+------------------------------------------------+");
        out.println("       | ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        out.println("       | ░    NOMBRE:        " + player.getName());
        out.println("       | ░    ORO:           " + c(YELO,player.getGold() + " monedas"));
        if (player.getInventory().isEmpty()) {
            out.println("       | ░    INVENTARIO:    Vacío");
        } else {
            out.println("       | ░    INVENTARIO:    " + player.getInventory().size() + " objetos");
        }       out.println();
    }
}
