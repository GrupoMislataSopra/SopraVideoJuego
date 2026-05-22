package org.sopra.rogueguild.repository.model;

public abstract class Item { ;
    private String name;
    private int price;
    private ItemCategory category;
    private final double basePrice;



    public Item(String name, int price, ItemCategory category, double basePrice) {

        this.name = name;
        this.price = price;
        this.category = category;
        this.basePrice =  price;

    }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public double getBasePrice() {
        return basePrice;
    }
    public ItemCategory getCategory() {
        return category;
    }
    public String toString() { return name + " (" + price + " oro)"; }

}
