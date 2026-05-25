package org.sopra.rogueguild.repository;

import org.sopra.rogueguild.repository.model.Incursion;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;
import org.sopra.rogueguild.repository.model.ItemGenerator;

import java.util.List;
import java.util.Random;

public class IncursionGenerator {

    private static final Random random = new Random();
    private final ItemGenerator itemGenerator;

    public IncursionGenerator(ItemGenerator itemGenerator) {
        this.itemGenerator = itemGenerator;
    }

    public Incursion generateConquest() {
        List<ItemCategory> highValue = List.of(ItemCategory.WEAPON, ItemCategory.ARMOR);
        ItemCategory category = highValue.get(random.nextInt(1));
        Item item = itemGenerator.generateItemForIncursion(category);
        int gold = random.nextInt(4) * 5;
        return new Incursion(
                gold,
                item,
                "Una campaña de conquista sobre territorios enemigos. La victoria trae consigo equipo valioso.",
                "Conquista"
        );
    }

    public Incursion generateLoot() {
        List<ItemCategory> lowValue = List.of(ItemCategory.POTION);
        ItemCategory category = lowValue.get(random.nextInt(1));
        Item item = random.nextBoolean() ? itemGenerator.generateItemForIncursion(category) : null;
        int gold = (random.nextInt(41) + 20) * 5;
        return new Incursion(
                gold,
                item,
                "Un asalto rápido a una caravana de mercaderes. El botín en oro es generoso.",
                "Saqueo"
        );
    }

    public Incursion generateMinor() {
       ItemCategory itemCategory = ItemCategory.POTION;
        Item item = itemGenerator.generateItemForIncursion(itemCategory);
        int gold = random.nextInt(7) * 5;
        return new Incursion(
                gold,
                item,
                "Una escaramuza rápida en las afueras. Modestas recompensas pero sin grandes riesgos.",
                "Menor"
        );
    }
}