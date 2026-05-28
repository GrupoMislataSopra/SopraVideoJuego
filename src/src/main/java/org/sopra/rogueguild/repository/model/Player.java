package org.sopra.rogueguild.repository.model;

import java.util.*;

public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    private final Map<ItemCategory, List<Item>>itemEquipped= new HashMap<>(Map.of(
            ItemCategory.WEAPON, new ArrayList<>(),
            ItemCategory.ARMOR, new ArrayList<>(),
            ItemCategory.HELMET, new ArrayList<>(),
            ItemCategory.BOOTS, new ArrayList<>()

    ));

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
    public void sellItemIfIsNotEquipped(Item item, int amount) {
        if (item == null)return;
        if(isEquipped(item)){
            System.out.println("No se puede vender un item equipado");
            return;
        }

        if (!inventory.contains(item)) return;
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
        if(!inventory.contains(item))return;

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

    private void replaceItem(Item newItem,List<Item> slots, ItemCategory category) {
        Item removeItem;
        if (category == ItemCategory.WEAPON){
            removeItem = slots.getFirst();
            for (Item equippedItem : slots){
                Weapon currentItemWeapon = (Weapon) equippedItem;
                Weapon weaponToRemove = (Weapon) removeItem;

                if (currentItemWeapon.getDamage() < weaponToRemove.getDamage()){
                    removeItem = equippedItem;
                }
            }
        }else {
            removeItem = slots.getFirst();
        }

        slots.remove(removeItem);
        slots.add(newItem);
        inventory.add(removeItem);
    }

    public boolean isEquipped(Item item){
        if (item == null) return false;
        List<Item>slots = itemEquipped.get(item.getCategory());


        return slots != null && slots.contains(item);
    }


}
