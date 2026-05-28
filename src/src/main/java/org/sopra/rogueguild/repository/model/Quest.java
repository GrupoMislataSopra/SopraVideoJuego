package org.sopra.rogueguild.repository.model;

import java.util.HashMap;
import java.util.Map;

public class Quest {
    private String description;
    private int goldReward;
    private Map<ItemCategory, Integer> requiredItems;
    private boolean isCompleted;
    private int minDamage;
    private int minShield;

    public Quest(String description, int goldReward, Map<ItemCategory, Integer> requiredItems) {
        this.description = description;
        this.goldReward = (int) Math.round(goldReward / 5.0) * 5;
        this.requiredItems = requiredItems;
    }

    public Quest(String description, int goldReward, Map<ItemCategory, Integer> requiredItems, int minDamage, int minShield) {
        this(description, goldReward, requiredItems);
        this.minDamage = minDamage;
        this.minShield = minShield;
    }

    public String getDescription() {
        return description;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public Map<ItemCategory, Integer> getRequiredItems() {
        return new HashMap<>(requiredItems);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean checkRequirement(Player p) {
        for (Map.Entry<ItemCategory, Integer> entry : requiredItems.entrySet()) {
            ItemCategory category = entry.getKey();
            int quantity = entry.getValue();
            long count = p.getInventory().stream().filter(item -> item.getCategory() == category).count();
            if (count < quantity) {
                return false;
            }
        }
        return true;
    }

    public boolean completeQuest(Player p) {
        if (isCompleted || !checkRequirement(p)) {
            return false;
        }
        p.addGold(goldReward);
        isCompleted = true;
        return true;
    }
}
