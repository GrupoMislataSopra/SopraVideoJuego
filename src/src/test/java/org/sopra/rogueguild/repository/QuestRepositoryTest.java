package org.sopra.rogueguild.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestRepositoryTest {
    @Nested
    class GetQuests {
        @Test
        void shouldReturnAllQuests() {
            QuestRepository repository = new QuestRepository();

            List<Quest> quests = repository.getQuests();

            assertEquals(4, quests.size());
        }

        @Test
        void shouldReturnDefensiveCopy() {
            QuestRepository repository = new QuestRepository();

            List<Quest> quests = repository.getQuests();
            quests.clear();

            assertEquals(4, repository.getQuests().size());
        }
    }

    @Nested
    class GetPendingQuest {
        @Test
        void whenNoQuestIsCompleted_shouldReturnAllQuests() {
            QuestRepository repository = new QuestRepository();

            List<Quest> pending = repository.getPendingQuest();

            assertEquals(4, pending.size());
        }

        @Test
        void whenOneQuestIsCompleted_shouldReturnOnlyPendingOnes() {
            QuestRepository repository = new QuestRepository();
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());
            player.addItem(new Weapon("Espada", 100, 20));
            player.addItem(new Weapon("Hacha", 100, 30));
            repository.getIdQuest(1).completeQuest(player);

            List<Quest> pending = repository.getPendingQuest();

            assertEquals(3, pending.size());
        }

        @Test
        void whenAllQuestsAreCompleted_shouldReturnEmptyList() {
            QuestRepository repository = new QuestRepository();
            WorldMap worldMap=new WorldMap();
            Player player = new Player("Test", 0, PlayerRol.GUERRERO,worldMap.getStartingCity());
            player.addItem(new Weapon("Espada", 100, 20));
            player.addItem(new Weapon("Hacha", 100, 30));
            player.addItem(new Armor("Peto", 100, 10));
            player.addItem(new GeneratedItem("Casco", 100, ItemCategory.HELMET));
            player.addItem(new GeneratedItem("Botas", 100, ItemCategory.BOOTS));

            Weapon w1 = new Weapon("Espada2", 100, 30);
            Armor a1 = new Armor("Peto2", 100, 20);
            player.addItem(w1);
            player.addItem(a1);
            player.equipItem(w1);
            player.equipItem(a1);


            repository.getIdQuest(1).completeQuest(player);
            repository.getIdQuest(2).completeQuest(player);
            repository.getIdQuest(3).completeQuest(player);
            repository.getIdQuest(4).completeQuest(player);

            List<Quest> pending = repository.getPendingQuest();

            assertTrue(pending.isEmpty());
        }
    }

    @Nested
    class GetIdQuest {
        @Test
        void whenIdIsOne_shouldReturnFirstQuest() {
            QuestRepository repository = new QuestRepository();

            Quest quest = repository.getIdQuest(1);

            assertNotNull(quest);
            assertEquals(repository.getQuests().get(0), quest);
        }

        @Test
        void whenIdIsLast_shouldReturnLastQuest() {
            QuestRepository repository = new QuestRepository();

            Quest quest = repository.getIdQuest(4);

            assertNotNull(quest);
            assertEquals(repository.getQuests().get(3), quest);
        }

        @Test
        void whenIdIsZero_shouldReturnNull() {
            QuestRepository repository = new QuestRepository();

            Quest quest = repository.getIdQuest(0);

            assertNull(quest);
        }

        @Test
        void whenIdIsNegative_shouldReturnNull() {
            QuestRepository repository = new QuestRepository();

            Quest quest = repository.getIdQuest(-1);

            assertNull(quest);
        }

        @Test
        void whenIdExceedsSize_shouldReturnNull() {
            QuestRepository repository = new QuestRepository();

            Quest quest = repository.getIdQuest(5);

            assertNull(quest);
        }
    }
}
