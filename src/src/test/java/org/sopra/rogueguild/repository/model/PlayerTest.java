package org.sopra.rogueguild.repository.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Nested
    class AddGold {
        @Test
        void whenGoldIsAdded_shouldIncreasePlayerGold() {
            Player player = new Player("Test", 100);

            player.addGold(50);

            assertEquals(150, player.getGold());
        }

        @Test
        void whenGoldExceedsLimit_shouldReturn500() {
            Player player = new Player("Test", 100);

            player.addGold(450);

            assertEquals(500, player.getGold());
        }

        @Test
        void whenGoldExceedsLimit_shouldReturnActualGoldAdded() {
            Player player = new Player("Test", 400);

            int result = player.addGold(200);

            assertEquals(100, result);
        }
    }

    @Nested
    class Buy {
        @Test
        void whenPlayerBuysItem_shouldDecreaseGoldAndAddToInventory() {
            Player player = new Player("Test", 200);
            Weapon weapon = new Weapon("Espada", 100, 20);

            player.buy(weapon);

            assertEquals(100, player.getGold());
            assertTrue(player.getInventory().contains(weapon));
        }
    }

    @Nested
    class Sell {
        @Test
        void whenPlayerSellsItem_shouldIncreaseGoldAndRemoveFromInventory() {
            Player player = new Player("Test", 100);
            Weapon weapon = new Weapon("Espada", 100, 20);
            player.addItem(weapon);

            player.sell(weapon, 80);

            assertEquals(180, player.getGold());
            assertFalse(player.getInventory().contains(weapon));
        }
    }
}