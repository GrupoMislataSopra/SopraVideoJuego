package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.sopra.rogueguild.view.utils.Ansi.*;

public class EquipView {
    private final PrintStream out;

    public EquipView(PrintStream out) { this.out = out; }

    public void displayEquipMenu(List<Item> inventory, Map<ItemCategory, List<Item>> equipped) {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |           " + c(PURP, "MENÚ DE EQUIPAMIENTO") + "            | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");
        out.println("|| |  " + c(GRAY, "EQUIPADO ACTUALMENTE:") + "                    | ||");

        for (Map.Entry<ItemCategory, List<Item>> entry : equipped.entrySet()) {
            if (entry.getValue().isEmpty()) {
                out.printf("|| |  %-10s -> %-30s| ||%n", entry.getKey(), "Vacío");
            } else {
                for (Item item : entry.getValue()) {
                    out.printf("|| |  %-10s -> %-30s| ||%n", entry.getKey(), item.getName());
                }
            }
        }

        out.println("|| |                                               | ||");
        out.println("|| |  " + c(GRAY, "INVENTARIO (elige para equipar):") + "        | ||");
        out.println("|| |                                               | ||");

        if (inventory.isEmpty()) {
            out.println("|| |  Tu inventario está vacío.                    | ||");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                out.printf("|| |  [%d] %-42s| ||%n", i + 1, inventory.get(i).getName());
            }
        }

        out.println("|| |                                               | ||");
        out.println("|| |  [0] Volver                                   | ||");
        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
        out.print("\nElección: ");

    }
}