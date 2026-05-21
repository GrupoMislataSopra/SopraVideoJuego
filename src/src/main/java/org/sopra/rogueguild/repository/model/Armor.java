package org.sopra.rogueguild.repository.model;

public class Armor extends Item {

  private int shield;

  public Armor(String name, int price, int shield,double basePrice) {
    super(name, price, ItemCategory.ARMOR,basePrice);
    this.shield = shield;
  }
}
