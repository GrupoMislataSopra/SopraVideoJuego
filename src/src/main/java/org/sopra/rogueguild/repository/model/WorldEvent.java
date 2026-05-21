package org.sopra.rogueguild.repository.model;

import java.util.Random;

public class WorldEvent {
    private final float multiplier;
    private final ItemCategory affectedCategory;
    private final String description;

    public WorldEvent(float multiplier, ItemCategory affectedCategory) {
        this.multiplier = multiplier;
        this.affectedCategory = affectedCategory;
        this.description = buildDescription();
    }

    private String translateCategory(ItemCategory category) {
        return switch (category) {
            case WEAPON -> "armas";
            case ARMOR -> "armaduras";
            case POTION -> "pociones";
            case HELMET -> "cascos";
            case BOOTS -> "botas";
            case OTHERS -> "otros objetos";
        };
    }

    private static final String[] RISE_CAUSES = {
            "Una plaga de dragones ha disparado",
            "Una guerra en el reino ha encarecido",
            "La escasez de materiales ha aumentado"
    };

    private static final String[] DROP_CAUSES = {
            "Las rebajas de temporada han reducido",
            "Un mercader generoso ha bajado",
            "La sobreproducción ha desplomado"
    };

    private String buildDescription() {
        Random random = new Random();
        String[] causes = (multiplier > 1) ? RISE_CAUSES : DROP_CAUSES;
        String cause = causes[random.nextInt(causes.length)];
        String affectedItems = (affectedCategory == null) ? "toda la mercancía" : translateCategory(affectedCategory);
        int percentage = Math.round(Math.abs(1 - multiplier) * 100);
        return cause + " el precio de " + affectedItems + " un " + percentage + "%.";
    }

    public float getMultiplier() { return multiplier; }
    public ItemCategory getAffectedCategory() { return affectedCategory; }
    public String getDescription() { return description; }
}