package org.sopra.rogueguild.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.sopra.rogueguild.repository.model.GeneratedItem;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ItemGeneratorTest {
    @Nested
    class generateItemWhitCategory{
        @Test
        @DisplayName("Este test deberia devolver un nombre random")
        void shouldReturnAnItemNotNullAndCategoryPotion(){
            ItemGenerator generator = new ItemGenerator();
            Item result = generator.generateItem(ItemCategory.POTION);
            assertAll(
                    ()->assertNotNull(result),
                    ()->assertNotNull(result.getName()),
                    ()->assertEquals(ItemCategory.POTION,result.getCategory()),
                    ()->assertEquals(0,result.getPrice()%5)
            );
        }
        @Test
        @DisplayName("Este test deberia devolver una poción")
        void shouldReturnAnItemNotNullAndCategoryWeapon(){
            ItemGenerator generator = new ItemGenerator();
            Item result = generator.generateItem(ItemCategory.WEAPON);
            assertAll(
                    ()->assertNotNull(result),
                    ()->assertNotNull(result.getName()),
                    ()->assertEquals(ItemCategory.WEAPON,result.getCategory()),
                    ()->assertEquals(0,result.getPrice()%5)
            );
        }
        @Test
        @DisplayName("Este test deberia devolver una armadura")
        void shouldReturnAnItemNotNullAndCategoryArmor(){
            ItemGenerator generator = new ItemGenerator();
            Item result = generator.generateItem(ItemCategory.ARMOR);
            assertAll(
                    ()->assertNotNull(result),
                    ()->assertNotNull(result.getName()),
                    ()->assertEquals(ItemCategory.ARMOR,result.getCategory()),
                    ()->assertEquals(0,result.getPrice()%5)
            );

        }
        @Test
        @DisplayName("Este test deberia devolver un escudo")
        void shouldReturnAnItemNotNullAndCategoryHelmet(){
            ItemGenerator generator = new ItemGenerator();
            Item result = generator.generateItem(ItemCategory.HELMET);
            assertAll(
                    ()->assertNotNull(result),
                    ()->assertNotNull(result.getName()),
                    ()->assertEquals(ItemCategory.HELMET,result.getCategory()),
                    ()->assertEquals(0,result.getPrice()%5)
            );
        }
        @Test
        @DisplayName("Este test deberia devolver unas botas")
        void shouldReturnAnItemNotNullAndCategoryBoots(){
            ItemGenerator generator = new ItemGenerator();
            Item result = generator.generateItem(ItemCategory.BOOTS);
            assertAll(
                    ()->assertNotNull(result),
                    ()->assertNotNull(result.getName()),
                    ()->assertEquals(ItemCategory.BOOTS,result.getCategory()),
                    ()->assertEquals(0,result.getPrice()%5)
            );
        }
        @Test
        @DisplayName("Este test deberia devolver otros")
        void shouldReturnAnItemNotNullAndCategoryOthers(){
            ItemGenerator generator = new ItemGenerator();
            Item result = generator.generateItem(ItemCategory.OTHERS);

            assertAll(
                    ()->assertNotNull(result),
                    ()->assertNotNull(result.getName()),
                    ()->assertEquals(ItemCategory.OTHERS,result.getCategory()),
                    ()->assertEquals(0,result.getPrice()%5)
            );

        }

    }
    @Nested
    class GenerateItemWithRandomWhitoutCategoryAndMaxPrice{
        @Test
        @DisplayName("Deberia de devolver un objeto random")
        void ShouldReturnARnadomItem() {
            ItemGenerator itemGenerator = new ItemGenerator();
            Item result = itemGenerator.generateItem();
            assertAll(

                    ()->assertNotNull(result),
                    ()->assertNotNull(result.getName()),
                    ()->assertTrue(ItemGenerator.CATEGORIES.contains(result.getCategory())),
                    ()->assertEquals(0,result.getPrice()%5)
            );

        }
    }
    @Nested
    class GenerateItemWithMaxPrice{
        @Test
        @DisplayName("Deberia devolver un precio que sea = o < que el precio maximo ")
        void shouldReturnTrueAMaxPriceMinor50(){
            ItemGenerator itemGenerator = new ItemGenerator();
            Item item = itemGenerator.generateItem(50);
            assertAll(
                    ()->assertNotNull(item),
                    ()->assertNotNull(item.getName()),
                    ()->assertTrue(item.getPrice()<=50),
                    ()->assertEquals(0,item.getPrice()%5)
            );

        }
        @Test
        @DisplayName("No deberia devolver un precio si es mayor > que el precio maximo ")
        void shouldReturnFalseAMaxPriceMinor50(){
            ItemGenerator itemGenerator = new ItemGenerator();
            Item item = itemGenerator.generateItem(50);
            assertAll(
                    ()->assertNotNull(item),
                    ()->assertNotNull(item.getName()),
                    ()->assertFalse(item.getPrice()>51),
                    ()->assertEquals(0,item.getPrice()%5)
            );

        }

    }
    @Nested
    class generatePrice {


        @DisplayName("Rango de precio de categorias")
        @ParameterizedTest
        @CsvSource({
                "ARMOR,200,50",
                "BOOTS,100,20",
                "HELMET,150,20",
                "WEAPON,300,100",
                "POTION,40,10",
                "OTHERS,300,250",
        })
        void shouldReturnPriceIftheRangesAreGood(ItemCategory category, int max, int min) {
            ItemGenerator itemGenerator = new ItemGenerator();

            int price = itemGenerator.generatePriceItem(category);

            assertAll(
                    () -> assertTrue(price <= max),
                    () -> assertTrue(price >= min),
                    () -> assertEquals(0, price % 5)


            );

        }
    }
}


