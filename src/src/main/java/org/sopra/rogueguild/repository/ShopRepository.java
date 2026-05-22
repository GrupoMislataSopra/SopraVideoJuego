package org.sopra.rogueguild.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import org.sopra.rogueguild.repository.model.*;

public class ShopRepository {
    private Map<Integer, Item> stock;
    private ItemGenerator item;

    public ShopRepository() {
        stock = new LinkedHashMap<>();
        item= new ItemGenerator();
        loadInitialStock();
    }

    private void loadInitialStock() {
        stock.put(1,item.generateItem());
        stock.put(2,item.generateItem());
        stock.put(3,item.generateItem());
        stock.put(4,item.generateItem());
        stock.put(5,item.generateItem());
        stock.put(6,item.generateItem());
        stock.put(7,item.generateItem());
    }

    public Item getItem(int id) {
        return stock.get(id);
    }

    public void removeItem(int id) { stock.remove(id); }

    public Map<Integer, Item> getAllStock() {
        return stock;
    }

    public void addItem(int id, Item item) {
        stock.put(id, item);
    }

    public void applyWorldEvent(WorldEvent event) {
        for (Item item : stock.values()) {
            if (event.getAffectedCategory() == null || item.getCategory() == event.getAffectedCategory()) {
                int newPrice = Math.round(item.getBasePrice() * event.getMultiplier() / 5) * 5;
                item.setPrice(newPrice);
            }
        }
    }
}