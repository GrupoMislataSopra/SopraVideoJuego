package org.sopra.rogueguild.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.sopra.rogueguild.repository.model.Incursion;
import org.sopra.rogueguild.repository.model.ItemCategory;

import static org.junit.jupiter.api.Assertions.*;

class IncursionGeneratorTest {

    @Nested
    class GenerateConquest {
        @Test
        void shouldReturnConquestWithCorrectShortName() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateConquest();

            assertEquals("Conquista", incursion.getShortName());
        }

        @Test
        void shouldReturnConquestWithCorrectDescription() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateConquest();

            assertEquals(
                    "Una campaña de conquista sobre territorios enemigos. La victoria trae consigo equipo valioso.",
                    incursion.getDescription()
            );
        }

        @Test
        void shouldReturnConquestWithItemReward() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateConquest();

            assertNotNull(incursion.getItemReward());
        }

        @RepeatedTest(20)
        void goldRewardShouldBeMultipleOfFive() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateConquest();

            assertEquals(0, incursion.getGoldReward() % 5);
        }

        @RepeatedTest(20)
        void itemRewardCategoryShouldBeWeaponArmorOrOthers() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateConquest();

            ItemCategory category = incursion.getItemReward().getCategory();
            assertTrue(
                    category == ItemCategory.WEAPON ||
                            category == ItemCategory.ARMOR  ||
                            category == ItemCategory.OTHERS
            );
        }
    }

    @Nested
    class GenerateLoot {
        @Test
        void shouldReturnLootWithCorrectShortName() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateLoot();

            assertEquals("Saqueo", incursion.getShortName());
        }

        @Test
        void shouldReturnLootWithCorrectDescription() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateLoot();

            assertEquals(
                    "Un asalto rápido a una caravana de mercaderes. El botín en oro es generoso.",
                    incursion.getDescription()
            );
        }

        @RepeatedTest(20)
        void goldRewardShouldBeWithinRange() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateLoot();

            assertTrue(incursion.getGoldReward() >= 100 && incursion.getGoldReward() <= 300,
                    "Gold reward out of range: " + incursion.getGoldReward());
        }

        @RepeatedTest(20)
        void goldRewardShouldBeMultipleOfFive() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateLoot();

            assertEquals(0, incursion.getGoldReward() % 5);
        }
    }

    @Nested
    class GenerateMinor {
        @Test
        void shouldReturnMinorWithCorrectShortName() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateMinor();

            assertEquals("Menor", incursion.getShortName());
        }

        @Test
        void shouldReturnMinorWithCorrectDescription() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateMinor();

            assertEquals(
                    "Una escaramuza rápida en las afueras. Modestas recompensas pero sin grandes riesgos.",
                    incursion.getDescription()
            );
        }

        @Test
        void shouldAlwaysHaveItemReward() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateMinor();

            assertNotNull(incursion.getItemReward());
        }

        @RepeatedTest(20)
        void itemRewardPriceShouldNotExceed50() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateMinor();

            assertTrue(incursion.getItemReward().getPrice() <= 50,
                    "Item price exceeds 50: " + incursion.getItemReward().getPrice());
        }

        @RepeatedTest(20)
        void goldRewardShouldNotExceed30() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateMinor();

            assertTrue(incursion.getGoldReward() <= 30,
                    "Gold reward exceeds 30: " + incursion.getGoldReward());
        }

        @RepeatedTest(20)
        void goldRewardShouldBeMultipleOfFive() {
            IncursionGenerator generator = new IncursionGenerator(new ItemGenerator());
            Incursion incursion = generator.generateMinor();

            assertEquals(0, incursion.getGoldReward() % 5);
        }
    }
}