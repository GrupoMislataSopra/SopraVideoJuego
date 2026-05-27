package org.sopra.rogueguild.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.ItemCategory;
import org.sopra.rogueguild.repository.model.WorldEvent;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ShopRepositoryTest {

    @Nested
    class RefreshStock {
        @Test
        void afterRefresh_stockShouldNotBeEmpty() {
            ShopRepository repository = new ShopRepository();

            repository.refreshStock();

            assertFalse(repository.getAllStock().isEmpty());
        }

        @Test
        void afterRefresh_stockSizeShouldBeInitialSize() {
            ShopRepository repository = new ShopRepository();
            int initialSize = repository.getAllStock().size();

            repository.refreshStock();

            assertEquals(initialSize, repository.getAllStock().size());
        }

        @Test
        void afterRefresh_oldItemsShouldBeReplaced() {
            ShopRepository repository = new ShopRepository();
            var oldStock = new LinkedHashMap<>(repository.getAllStock());

            repository.refreshStock();

            assertNotEquals(oldStock, repository.getAllStock());
        }
    }

    @Nested
    class ApplyWorldEvent {
        @Test
        void whenMultiplierIsAboveOne_shouldIncreasePrice() {
            ShopRepository repository = new ShopRepository();
            int originalPrice = repository.getAllStock().get(1).getPrice();

            repository.applyWorldEvent(new WorldEvent(1.2f, null));

            assertTrue(repository.getAllStock().get(1).getPrice() > originalPrice);
        }

        @Test
        void whenMultiplierIsBelowOne_shouldDecreasePrice() {
            ShopRepository repository = new ShopRepository();
            int originalPrice = repository.getAllStock().get(1).getPrice();

            repository.applyWorldEvent(new WorldEvent(0.8f, null));

            assertTrue(repository.getAllStock().get(1).getPrice() < originalPrice);
        }

        @Test
        void whenCategoryIsSpecified_shouldOnlyAffectThatCategory() {
            ShopRepository repository = new ShopRepository();

            repository.applyWorldEvent(new WorldEvent(2.0f, ItemCategory.POTION));

            repository.getAllStock().values().stream()
                    .filter(item -> item.getCategory() != ItemCategory.POTION)
                    .forEach(item -> assertEquals(item.getBasePrice(), item.getPrice()));
        }
    }
}