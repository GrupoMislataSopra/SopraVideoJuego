package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private String description;
    private int goldReward;
    private List<Item> requiredItems;
    private boolean isCompleted;

    public Quest(String description, int goldReward, List<Item> requiredItems) {
        this.description = description;
        this.goldReward = (int) Math.round(goldReward / 5.0) * 5;
        this.requiredItems = requiredItems;
    }

    public String getDescription() {
        return description;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public List<Item> getRequiredItems() {
        return new ArrayList<>(requiredItems);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean checkRequirement(Player p) {
        return p.getInventory().containsAll(requiredItems);
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