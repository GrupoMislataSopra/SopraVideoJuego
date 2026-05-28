package org.sopra.rogueguild.repository.model;

import java.util.*;

public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    private static Map<ItemCategory, List<Item>>itemEquipped= new HashMap<>(Map.of(
            ItemCategory.WEAPON, new ArrayList<>(),
            ItemCategory.ARMOR, new ArrayList<>(),
            ItemCategory.HELMET, new ArrayList<>(),
            ItemCategory.BOOTS, new ArrayList<>()

    ));

    private int totalDamage;
    private int totalShield;

    public Player(String name, int gold) {
        this.name = name;
        this.gold = gold;

    }

    public String getName() { return name; }
    public int getGold() { return gold; }

    public int getTotalDamage() {
        return itemEquipped.get(ItemCategory.WEAPON).stream().mapToInt(i -> ((Weapon) i).getDamage()).sum();
    }

    public int getTotalShield() {
        return itemEquipped.get(ItemCategory.ARMOR).stream().mapToInt(i -> ((Armor) i).getShield()).sum();
    }

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
    
    public int limitEquip(ItemCategory category){
        return switch (category){
            case WEAPON -> 2;
            case ARMOR, HELMET, BOOTS -> 1;
            default -> 0;

        };
    }

    public void equipItem(Item item) {
        ItemCategory category = item.getCategory();
        List<Item> slots = itemEquipped.get(category);

        if (slots == null) return;

        inventory.remove(item);

        if (slots.size() < limitEquip(category)) {
            slots.add(item);
        } else {
            replaceItem(item, slots, category);
        }
    }

    public void unequipItem(Item item) {
        List<Item> slots = itemEquipped.get(item.getCategory());

        if (slots == null || !slots.contains(item)) return;

        slots.remove(item);
        inventory.add(item);
    }
}
