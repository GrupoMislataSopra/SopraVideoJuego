package org.sopra.rogueguild.repository;

import org.sopra.rogueguild.repository.model.GeneratedItem;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;

import java.util.*;

public class ItemGenerator {
    private static final Random random = new Random();
    public static final List<ItemCategory> CATEGORIES = List.of(
            ItemCategory.WEAPON,
            ItemCategory.ARMOR,
            ItemCategory.BOOTS,
            ItemCategory.HELMET,
            ItemCategory.POTION
    );
    private final Map<ItemCategory, List<String>> prefixes = Map.of(
            ItemCategory.WEAPON, List.of("Espada", "Hacha", "Daga", "Lanza", "Mandoble", "Arco", "Maza", "Bastón"),
            ItemCategory.ARMOR, List.of("Armadura", "Cota", "Peto", "Coraza", "Malla"),
            ItemCategory.BOOTS, List.of("Botas", "Grebas", "Sandalias", "Escarpines"),
            ItemCategory.HELMET, List.of("Yelmo", "Casco", "Celada", "Capucha", "Visera"),
            ItemCategory.POTION, List.of("Poción", "Elixir", "Brebaje", "Ungüento", "Tintura"),
            ItemCategory.OTHERS, List.of("Rubí", "Zafiro", "Esmeralda", "Diamante", "Ámbar", "Ópalo","Lingote de oro", "Platino", "Acero estelar")
    );

    private static final List<String> sufixes = List.of(
            "de fuego", "de hielo", "del rayo", "de la tormenta", "de la sombra", "de la luz",
            "de hierro", "de plata", "de obsidiana", "de acero rúnico", "de bronce antiguo",
            "del dragón", "del fénix", "del caos", "del vacío", "del alba", "de la luna",
            "del norte", "de las ruinas", "del bosque maldito", "de las profundidades", "de la montaña"
    );

    private final List<String> usedNames = new ArrayList<>();

    public Item generateItem(){
        ItemCategory category;
        if (random.nextInt(100) < 5) {
            category = ItemCategory.OTHERS;
        } else {
            category = CATEGORIES.get(random.nextInt(CATEGORIES.size()));
        }
        return generateItem(category);
    }

    public Item generateItem(ItemCategory category) {
        String name = generateName(category);

        while (usedNames.contains(name)) {
            name = generateName(category);
        }
        usedNames.add(name);

        int price = generatePriceItem(category);
        return new GeneratedItem(name, price, category);
    }

    public Item generateItem(int maxPrice) {
        ItemCategory category = CATEGORIES.get(random.nextInt(CATEGORIES.size()));
        int price = Math.min(generatePriceItem(category), maxPrice);
        String name = generateName(category);
        while (usedNames.contains(name)) {
            name = generateName(category);
        }
        usedNames.add(name);
        return new GeneratedItem(name, price, category);
    }

    private String generateName(ItemCategory category) {
        List<String> prefixList = prefixes.get(category);

        int randomPrefix = random.nextInt(prefixList.size());
        int randomSufix = random.nextInt(sufixes.size());

        return prefixList.get(randomPrefix) + " " + sufixes.get(randomSufix);
    }

    public int generatePriceItem(ItemCategory category) {
        int price = switch (category) {
            case ARMOR -> random.nextInt(151) + 50;
            case BOOTS -> random.nextInt(81) + 20;
            case HELMET -> random.nextInt(131) + 20;
            case WEAPON -> random.nextInt(201) + 100;
            case POTION -> random.nextInt(31) + 10;
            case OTHERS -> random.nextInt(51) + 250;
        };
        return (int) Math.round(price / 5.0) * 5;
    }
}