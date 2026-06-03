package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.repository.model.Incursion;
import org.sopra.rogueguild.repository.model.Quest;

import java.io.PrintStream;
import java.util.List;

import static org.sopra.rogueguild.view.utils.Ansi.GRAY;
import static org.sopra.rogueguild.view.utils.Ansi.c;

public class IncursionView {
    private final PrintStream out;

    public IncursionView(PrintStream out) { this.out = out; }

    public void displayIncursions() {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |           SISTEMA DE INCURSIONES              | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        out.println("|| |  [1] Conquista  - Equipo de alto valor        | ||");
        out.println("|| |  [2] Saqueo     - Oro en abundancia           | ||");
        out.println("|| |  [3] Menor      - Recompensa mixta limitada   | ||");
        out.println("|| |                                               | ||");
        out.println("|| |" + c(GRAY, "  [0] Volver al menu principal") + "                 | ||");
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }

    public void incursionResult(Incursion incursion, int actualGold) {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |           RESULTADO DE LA INCURSION           | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");

        if (actualGold > 0) {
            out.printf("|| |  Oro obtenido: %-30s| ||%n", actualGold + " monedas");
        }
        if (incursion.getItemReward() != null) {
            out.printf("|| |  Objeto:       %-30s| ||%n", incursion.getItemReward().getName());
        }
        if (actualGold == 0 && incursion.getItemReward() == null) {
            out.println("|| |  No has obtenido ninguna recompensa.          | ||");
        }

        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }
}