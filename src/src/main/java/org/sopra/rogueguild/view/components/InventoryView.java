package org.sopra.rogueguild.view.components;

import org.sopra.rogueguild.repository.model.Item;

import java.io.PrintStream;
import java.util.List;

public class InventoryView {
    private final PrintStream out;

    public InventoryView(PrintStream out) { this.out = out; }

    public void displayInventory(List<Item> inventory, boolean inRemoveProcess) {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |              TU INVENTARIO                    | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");

        if (inventory.isEmpty()) {
            out.println("|| |  Tu inventario está vacío.                    | ||");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                Item item = inventory.get(i);
                if (inRemoveProcess) {
                    out.printf("|| |  [%d] %-42s| ||%n", i + 1, item.getName());
                } else {
                    out.printf("|| |  [-] %-42s| ||%n", item.getName());
                }
            }
        }

        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
        if (inRemoveProcess) {
            out.print("\nElección: ");
        }
    }
}