package org.sopra.rogueguild.repository.model;

public class Incursion {
    private final int goldReward;
    private final Item itemReward;
    private final String description;
    private final String shortName;

    public Incursion(int goldReward, Item itemReward, String description, String shortName) {
        this.goldReward = (int) Math.round(goldReward / 5.0) * 5;
        this.itemReward = itemReward;
        this.description = description;
        this.shortName = shortName;
    }

    public int getGoldReward() { return goldReward; }
    public Item getItemReward() { return itemReward; }
    public String getDescription() { return description; }
    public String getShortName() { return shortName; }
}
