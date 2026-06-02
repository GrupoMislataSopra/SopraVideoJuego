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
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            player.addGold(50);

            assertEquals(150, player.getGold());
        }

        @Test
        void whenGoldExceedsLimit_shouldCapAt500() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            player.addGold(450);

            assertEquals(500, player.getGold());
        }

        @Test
        void whenGoldExceedsLimit_shouldReturnActualGoldAdded() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 400, PlayerRol.GUERRERO, worldMap.getStartingCity());

            int result = player.addGold(200);

            assertEquals(100, result);
        }

        @Test
        void whenGoldIsZero_shouldNotChange() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            player.addGold(0);

            assertEquals(100, player.getGold());
        }
    }

    @Nested
    class Buy {
        @Test
        void whenPlayerBuysItem_shouldDecreaseGoldAndAddToInventory() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 200, PlayerRol.GUERRERO, worldMap.getStartingCity());
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
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon = new Weapon("Espada", 100, 20);
            player.addItem(weapon);

            player.sellItemIfIsNotEquipped(weapon, 80);

            assertEquals(180, player.getGold());
            assertFalse(player.getInventory().contains(weapon));
        }

        @Test
        void whenItemIsEquipped_shouldNotBeSold() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon = new Weapon("Espada", 100, 20);
            player.addItem(weapon);
            player.equipItem(weapon);

            boolean result = player.sellItemIfIsNotEquipped(weapon, 80);

            assertFalse(result);
            assertEquals(100, player.getGold());
        }

        @Test
        void whenItemNotInInventory_shouldReturnFalse() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon = new Weapon("Espada", 100, 20);

            boolean result = player.sellItemIfIsNotEquipped(weapon, 80);

            assertFalse(result);
        }

        @Test
        void whenItemIsNull_shouldReturnFalse() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            boolean result = player.sellItemIfIsNotEquipped(null, 80);

            assertFalse(result);
        }
    }

    @Nested
    class EquipItem {
        @Test
        void whenEquippingWeapon_shouldMoveFromInventoryToEquipped() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon = new Weapon("Espada", 100, 20);
            player.addItem(weapon);

            boolean result = player.equipItem(weapon);

            assertTrue(result);
            assertFalse(player.getInventory().contains(weapon));
            assertTrue(player.isEquipped(weapon));
        }

        @Test
        void whenEquippingArmor_shouldMoveFromInventoryToEquipped() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Armor armor = new Armor("Peto", 100, 10);
            player.addItem(armor);

            boolean result = player.equipItem(armor);

            assertTrue(result);
            assertFalse(player.getInventory().contains(armor));
            assertTrue(player.isEquipped(armor));
        }

        @Test
        void whenEquippingItemNotInInventory_shouldReturnFalse() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon = new Weapon("Espada", 100, 20);

            boolean result = player.equipItem(weapon);

            assertFalse(result);
        }

        @Test
        void canEquipUpToTwoWeapons() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon w1 = new Weapon("Espada", 100, 20);
            Weapon w2 = new Weapon("Hacha",  100, 30);
            player.addItem(w1);
            player.addItem(w2);

            player.equipItem(w1);
            player.equipItem(w2);

            assertTrue(player.isEquipped(w1));
            assertTrue(player.isEquipped(w2));
        }

        @Test
        void whenWeaponSlotFull_shouldReplaceLowestDamageWeapon() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weak   = new Weapon("Daga",     50,  5);
            Weapon medium = new Weapon("Espada",  100, 20);
            Weapon strong = new Weapon("Mandoble", 150, 40);
            player.addItem(weak);
            player.addItem(medium);
            player.addItem(strong);
            player.equipItem(weak);
            player.equipItem(medium);

            player.equipItem(strong);

            assertTrue(player.isEquipped(medium));
            assertTrue(player.isEquipped(strong));
            assertFalse(player.isEquipped(weak));
            assertTrue(player.getInventory().contains(weak));
        }

        @Test
        void whenWeaponSlotFullWithTieDamage_shouldReplaceFirstEquipped() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon first  = new Weapon("Espada", 100, 20);
            Weapon second = new Weapon("Hacha",  100, 20);
            Weapon third  = new Weapon("Lanza",  100, 20);
            player.addItem(first);
            player.addItem(second);
            player.addItem(third);
            player.equipItem(first);
            player.equipItem(second);

            player.equipItem(third);

            assertFalse(player.isEquipped(first));
            assertTrue(player.getInventory().contains(first));
        }

        @Test
        void whenArmorSlotFull_shouldReplaceCurrentArmor() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Armor old      = new Armor("Cota",  80,  5);
            Armor newArmor = new Armor("Peto", 100, 15);
            player.addItem(old);
            player.addItem(newArmor);
            player.equipItem(old);

            player.equipItem(newArmor);

            assertTrue(player.isEquipped(newArmor));
            assertFalse(player.isEquipped(old));
            assertTrue(player.getInventory().contains(old));
        }

        @Test
        void whenEquippingPotion_shouldReturnFalse() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Potion potion = new Potion("Elixir", 20, 5);
            player.addItem(potion);

            boolean result = player.equipItem(potion);

            assertFalse(result);
        }
    }

    @Nested
    class UnequipItem {
        @Test
        void whenUnequippingItem_shouldMoveBackToInventory() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon = new Weapon("Espada", 100, 20);
            player.addItem(weapon);
            player.equipItem(weapon);

            player.unequipItem(weapon);

            assertFalse(player.isEquipped(weapon));
            assertTrue(player.getInventory().contains(weapon));
        }

        @Test
        void whenUnequippingItemNotEquipped_shouldNotCrash() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon = new Weapon("Espada", 100, 20);
            player.addItem(weapon);

            assertDoesNotThrow(() -> player.unequipItem(weapon));
            assertTrue(player.getInventory().contains(weapon));
        }
    }

    @Nested
    class GetTotalDamage {
        @Test
        void whenNoWeaponsEquipped_shouldReturnZero() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            assertEquals(0, player.getTotalDamage());
        }

        @Test
        void whenOneWeaponEquipped_shouldReturnItsDamage() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon = new Weapon("Espada", 100, 25);
            player.addItem(weapon);
            player.equipItem(weapon);

            assertEquals(25, player.getTotalDamage());
        }

        @Test
        void whenTwoWeaponsEquipped_shouldReturnSumOfDamage() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon w1 = new Weapon("Espada", 100, 20);
            Weapon w2 = new Weapon("Hacha",  100, 30);
            player.addItem(w1);
            player.addItem(w2);
            player.equipItem(w1);
            player.equipItem(w2);

            assertEquals(50, player.getTotalDamage());
        }
    }

    @Nested
    class GetTotalShield {
        @Test
        void whenNoArmorEquipped_shouldReturnZero() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            assertEquals(0, player.getTotalShield());
        }

        @Test
        void whenArmorEquipped_shouldReturnItsShield() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Armor armor = new Armor("Peto", 100, 15);
            player.addItem(armor);
            player.equipItem(armor);

            assertEquals(15, player.getTotalShield());
        }
    }

    @Nested
    class Heal {
        @Test
        void initialHitPoints_shouldBe20() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            assertEquals(20, player.getHitPoints());
        }

        @Test
        void whenAlreadyAtMax_shouldNotExceedMaxHitPoints() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            player.heal(5);

            assertEquals(player.getPlayerHitPoints(), player.getHitPoints());
        }

        @Test
        void whenHealingWithLargeAmount_shouldCapAtMaxHitPoints() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            player.heal(100);

            assertEquals(player.getPlayerHitPoints(), player.getHitPoints());
        }
    }

    @Nested
    class TravelTo {
        @Test
        void whenTravelingToAdjacentCity_shouldUpdateCurrentCity() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            boolean result = player.travelTo(worldMap.getAshenvale());

            assertTrue(result);
            assertEquals(worldMap.getAshenvale(), player.getCurrentCity());
        }

        @Test
        void whenTravelingToNonAdjacentCity_shouldFindRouteAndArrive() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            boolean result = player.travelTo(worldMap.getValdoria());

            assertTrue(result);
            assertEquals(worldMap.getValdoria(), player.getCurrentCity());
        }

        @Test
        void whenTravelingToDistantCity_shouldArriveThere() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            // Ironhold -> Ashenvale -> Valdoria -> Thornwick -> Grimstone
            boolean result = player.travelTo(worldMap.getGrimstone());

            assertTrue(result);
            assertEquals(worldMap.getGrimstone(), player.getCurrentCity());
        }

        @Test
        void whenTravelingToSameCity_shouldReturnFalse() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            boolean result = player.travelTo(worldMap.getIronhold());

            assertFalse(result);
            assertEquals(worldMap.getIronhold(), player.getCurrentCity());
        }

        @Test
        void whenTravelingToNull_shouldReturnFalse() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());

            boolean result = player.travelTo(null);

            assertFalse(result);
        }

        @Test
        void whenCityIsUnreachable_shouldReturnFalseAndNotMove() {
            WorldMap worldMap = new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            City isolated = new City("Isolated");

            boolean result = player.travelTo(isolated);

            assertFalse(result);
            assertEquals(worldMap.getIronhold(), player.getCurrentCity());
        }
    }
}