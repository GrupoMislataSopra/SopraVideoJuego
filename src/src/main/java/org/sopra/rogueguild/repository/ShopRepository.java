package org.sopra.rogueguild.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import org.sopra.rogueguild.repository.model.*;

public class ShopRepository {
    private Map<Integer, Item> stock;
    private ItemGenerator item;
    private final ItemGenerator itemGenerator;
    private static final int INITIAL_STOCK_SIZE = 4;

    public ShopRepository() {
        stock = new LinkedHashMap<>();
        itemGenerator= new ItemGenerator();
        loadStock();
    }

    private void loadStock() {
        for (int i = 1; i <= INITIAL_STOCK_SIZE; i++) {
            stock.put(i, itemGenerator.generateItemForShop());
        }
    }

    public void refreshStock() {
        stock.clear();
        loadStock();
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