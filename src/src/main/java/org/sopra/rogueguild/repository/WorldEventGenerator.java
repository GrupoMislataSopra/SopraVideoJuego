package org.sopra.rogueguild.repository;

import java.util.Random;
import org.sopra.rogueguild.repository.model.ItemCategory;
import org.sopra.rogueguild.repository.model.WorldEvent;

public class WorldEventGenerator {

    private static final float[] MULTIPLIERS = {0.5f, 0.8f, 1.2f, 1.3f};

    public static WorldEvent generate() {
        Random random = new Random();
        float multiplier = MULTIPLIERS[random.nextInt(MULTIPLIERS.length)];

        ItemCategory[] categories = ItemCategory.values();
        int index = random.nextInt(categories.length + 1);
        ItemCategory affectedCategory = (index == categories.length) ? null : categories[index];

        return new WorldEvent(multiplier, affectedCategory);
    }
}