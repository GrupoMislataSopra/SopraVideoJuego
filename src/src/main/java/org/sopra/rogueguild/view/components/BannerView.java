package org.sopra.rogueguild.view.components;

import static org.sopra.rogueguild.view.utils.Ansi.*;

import java.io.PrintStream;

import org.sopra.rogueguild.view.utils.Ansi;

public class BannerView {
    private final PrintStream out;

    public BannerView(PrintStream out) { this.out = out; }

    public void landingPage()  {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |  " + c(RED, " ___                          ") + c(PURP, " _        _ ") + "   | ||");
        out.println("|| |  " + c(RED, "| _ \\___  __ _ _  _ ___  ") + c(PURP, " __ _(_)_ _ __| |") + "   | ||");
        out.println("|| |  " + c(RED, "|   / _ \\/ _` | || / -_) ") + c(PURP, "/ _` | | | / _` |") + "   | ||");
        out.println("|| |  " + c(RED, "|_|_\\___/\\__, |\\_,_\\___| ") + c(PURP, "\\__, |_|_|_\\__,_|") + "   | ||");
        out.println("|| |  " + c(RED, "         |___/           ") + c(PURP, "|___/            ") + "   | ||");
        out.println("|| |                                               | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |  [1] Tienda                                   | ||");
        out.println("|| |  [2] Inventario                               | ||");
        out.println("|| |  [3] Incursiones                              | ||");
        out.println("|| |  [4] Misiones                                 | ||");
        out.println("|| |  [5] Viajar                                   | ||");
        out.println("|| |" + c(Ansi.GRAY, "  [0] Salir                                    ") + "| ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }

    public void shopMenu() {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |              " + c(PURP, "TIENDA") + "                          | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |  [1] Comprar                                  | ||");
        out.println("|| |" + c(Ansi.GRAY, "  [0] Volver                                   ") + "| ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
        out.print("\nElección: ");
    }

    public void inventoryMenu() {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |              " + c(PURP, "INVENTARIO") + "                      | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |  [1] Equipar objeto                           | ||");
        out.println("|| |  [2] Desequipar objeto                        | ||");
        out.println("|| |  [3] Vender objeto                            | ||");
        out.println("|| |  [4] Tirar objeto                             | ||");
        out.println("|| |" + c(Ansi.GRAY, "  [0] Volver                                   ") + "| ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
        out.print("\nElección: ");
    }
}
