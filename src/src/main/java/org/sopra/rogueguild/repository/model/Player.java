package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();

    public Player(String name, int gold) {
        this.name = name;
        this.gold = gold;
    }

    public String getName() { return name; }
    public int getGold() { return gold; }

    public void buy(Item item) {
        this.gold -= item.getPrice();
        this.addItem(item);
    }
    public void addItem(Item item) {
        inventory.add(item);
    }
    public void removeItem(Item item) {
        inventory.remove(item);
    }
    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }
    public void sell(Item item, int amount) {
        this.gold += amount;
        this.removeItem(item);
    }
    public int addGold(int amount) {
        int space = 500 - this.gold;
        int actualAmount = Math.min(amount, space);
        this.gold += actualAmount;
        return actualAmount;
    }


}
