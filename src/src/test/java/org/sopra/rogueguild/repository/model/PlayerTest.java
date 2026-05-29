package org.sopra.rogueguild.repository.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.WorldMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Nested
    class AddGold {
        @Test
        void whenGoldIsAdded_shouldIncreasePlayerGold() {
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());

            player.addGold(50);

            assertEquals(150, player.getGold());
        }

        @Test
        void whenGoldExceedsLimit_shouldReturn500() {
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());

            player.addGold(450);

            assertEquals(500, player.getGold());
        }

        @Test
        void whenGoldExceedsLimit_shouldReturnActualGoldAdded() {
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());

            int result = player.addGold(200);

            assertEquals(100, result);
        }
    }

    @Nested
    class Buy {
        @Test
        void whenPlayerBuysItem_shouldDecreaseGoldAndAddToInventory() {
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());
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
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());            Weapon weapon = new Weapon("Espada", 100, 20);
            player.addItem(weapon);

            player.sellItemIfIsNotEquipped(weapon, 80);

            assertEquals(180, player.getGold());
            assertFalse(player.getInventory().contains(weapon));
        }
    }
}