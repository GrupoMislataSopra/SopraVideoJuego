package org.sopra.rogueguild.repository.model;

public class Weapon extends Item {

    private int damage;

    public Weapon(String name, int price, int damage, double basePrice) {
        super(name, price, ItemCategory.WEAPON);
        this.damage = damage;
    }
}
