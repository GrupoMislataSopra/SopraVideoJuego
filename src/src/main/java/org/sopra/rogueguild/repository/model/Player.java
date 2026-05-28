package org.sopra.rogueguild.repository.model;

import java.util.*;

public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    private final Map<ItemCategory, List<Item>> itemEquipped = new HashMap<>(Map.of(
            ItemCategory.WEAPON, new ArrayList<>(),
            ItemCategory.ARMOR, new ArrayList<>(),
            ItemCategory.HELMET, new ArrayList<>(),
            ItemCategory.BOOTS, new ArrayList<>()
    ));
    private PlayerRol playerRol;



    public Player(String name, int gold, PlayerRol playerRol) {
        this.name = name;
        this.gold = gold;
        this.playerRol = playerRol;
    }

    public String getName() { return name; }
    public int getGold() { return gold; }

    public int getTotalDamage() {
        return itemEquipped.get(ItemCategory.WEAPON).stream()
                .mapToInt(i -> ((Weapon) i).getDamage()).sum();
    }

    public int getTotalShield() {
        return itemEquipped.get(ItemCategory.ARMOR).stream()
                .mapToInt(i -> ((Armor) i).getShield()).sum();
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
    public PlayerRol getPlayerRol() {
        return playerRol;
    }
    public boolean sellItemIfIsNotEquipped(Item item, int amount) {
        if (item == null) return false;
        if (isEquipped(item)) return false;
        if (!inventory.contains(item)) return false;
        this.gold += amount;
        this.removeItem(item);
        return true;
    }

    public int addGold(int amount) {
        int space = 500 - this.gold;
        int actualAmount = Math.min(amount, space);
        this.gold += actualAmount;
        return actualAmount;
    }

    public int limitEquip(ItemCategory category) {
        return switch (category) {
            case WEAPON -> 2;
            case ARMOR, HELMET, BOOTS -> 1;
            default -> 0;
        };
    }

    public boolean equipItem(Item item) {
        ItemCategory category = item.getCategory();
        List<Item> slots = itemEquipped.get(category);

        if (slots == null) return false;
        if (!inventory.contains(item)) return false;

        inventory.remove(item);

        if (slots.size() < limitEquip(category)) {
            slots.add(item);
        } else {
            replaceItem(item, slots, category);
        }
        return true;
    }

    public void unequipItem(Item item) {
        List<Item> slots = itemEquipped.get(item.getCategory());

        if (slots == null || !slots.contains(item)) return;

        slots.remove(item);
        inventory.add(item);
    }

    private void replaceItem(Item newItem, List<Item> slots, ItemCategory category) {
        Item removeItem;
        if (category == ItemCategory.WEAPON) {
            removeItem = slots.getFirst();
            for (Item equippedItem : slots) {
                Weapon current = (Weapon) equippedItem;
                Weapon toRemove = (Weapon) removeItem;
                if (current.getDamage() < toRemove.getDamage()) {
                    removeItem = equippedItem;
                }
            }
        } else {
            removeItem = slots.getFirst();
        }
        slots.remove(removeItem);
        slots.add(newItem);
        inventory.add(removeItem);
    }

    public boolean isEquipped(Item item) {
        if (item == null) return false;
        List<Item> slots = itemEquipped.get(item.getCategory());
        return slots != null && slots.contains(item);
    }

    public Map<ItemCategory, List<Item>> getItemEquipped() {
        return new HashMap<>(itemEquipped);
    }
}
