package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.repository.model.Quest;

import java.io.PrintStream;
import java.util.List;

public class MisionesView {
    private PrintStream out;

    public MisionesView(PrintStream out) {
        this.out = out;
    }

    public void displayMisiones(List<Quest> quests){
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |           SISTEMA DE MISIONES                 | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        for (int i = 0; i<quests.size();i++){
            Quest quest = quests.get(i);

            if (!quest.isCompleted()){
                out.printf("|| | [%d] %-28s %4d oro     | ||%n",
                        i +1,
                        quest.getDescription(),
                        quest.getGoldReward());
            }
        }
        out.println("|| | [0] Volver al menu principal                  | ||");
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
        out.print("\nElección: ");
    }
}
