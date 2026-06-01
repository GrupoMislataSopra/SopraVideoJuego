package org.sopra.rogueguild.repository.model;

public class Potion extends GeneratedItem{

    private int healingPoints;
    public Potion(String name, int price, int healingPoints) {
        super(name, price, ItemCategory.POTION);
        this.healingPoints=healingPoints;
    }

    public int getHealingPoints(){
        return healingPoints;
    }
}
