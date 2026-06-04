package org.sopra.rogueguild.repository.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.WorldMap;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuestTest {
    @Nested
    class whenCreated {
        @Test
        void quest_shouldStartNotCompleted() {
            Quest quest = new Quest("Danza de muerte", 50, Map.of(ItemCategory.WEAPON, 2));

            assertFalse(quest.isCompleted());
        }

        @Test
        void goldReward_shouldBeRoundedToMultipleOfFive() {
            Quest quest = new Quest("Danza de muerte", 53, Map.of(ItemCategory.WEAPON, 2));

            assertEquals(55, quest.getGoldReward());
        }
    }

    @Nested
    class CheckRequirement {
        @Test
        void whenPlayerHasRequiredItems_shouldReturnTrue() {
            Quest quest = new Quest("Danza de muerte", 50, Map.of(ItemCategory.WEAPON, 2));
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());
            Weapon weapon1 = new Weapon("Espada", 100, 20);
            Weapon weapon2 = new Weapon("Hacha", 150, 50);

            player.addItem(weapon1);
            player.addItem(weapon2);

            assertTrue(quest.checkRequirement(player));
        }

        @Test
        void whenPlayerHasNotRequiredItems_shouldReturnFalse() {
            Quest quest = new Quest("Danza de muerte", 50, Map.of(ItemCategory.WEAPON, 2));
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());
            Weapon weapon1 = new Weapon("Espada", 100, 20);

            player.addItem(weapon1);

            assertFalse(quest.checkRequirement(player));
        }
    }

    @Nested
    class CompleteQuest {
        @Test
        void whenPlayerCompletesQuest_shouldAddGold() {
            Quest quest = new Quest("Danza de muerte", 50, Map.of(ItemCategory.WEAPON, 2));
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon1 = new Weapon("Espada", 100, 20);
            Weapon weapon2 = new Weapon("Hacha", 150, 50);

            player.addItem(weapon1);
            player.addItem(weapon2);

            quest.completeQuest(player);
            assertEquals(150, player.getGold());
        }

        @Test
        void whenQuestIsAlreadyCompleted_shouldNotBeAble() {
            Quest quest = new Quest("Danza de muerte", 50, Map.of(ItemCategory.WEAPON, 2));
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());
            Weapon weapon1 = new Weapon("Espada", 100, 20);
            Weapon weapon2 = new Weapon("Hacha", 150, 50);

            player.addItem(weapon1);
            player.addItem(weapon2);

            quest.completeQuest(player);
            boolean result = quest.completeQuest(player);
            assertFalse(result);
        }

        @Test
        void whenPlayerHasNotRequiredItems_shouldNotAddGold() {
            Quest quest = new Quest("Danza de muerte", 50, Map.of(ItemCategory.WEAPON, 2));
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 100, PlayerRol.GUERRERO, worldMap.getStartingCity());
            Weapon weapon1 = new Weapon("Espada", 100, 20);

            player.addItem(weapon1);

            boolean result = quest.completeQuest(player);
            assertFalse(result);
            assertEquals(100, player.getGold());
        }
    }
}
