package org.sopra.rogueguild.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.sopra.rogueguild.repository.model.Incursion;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncursionGeneratorTest {

    @Mock
    private Random random;
    @Mock
    ItemGenerator itemGenerator;
    @InjectMocks
    Item item;
    @Nested
    class incursionGeneretaConquest{
        @Test
        void shouldReturnFiveOtherItem_AItemCategoryAndGold(){
            when(random.nextInt(100)).thenReturn(4);
            when(random.nextInt(4)).thenReturn(2);
            when(itemGenerator.generateItem(ItemCategory.OTHERS)).thenReturn(item);
            when(item.getCategory()).thenReturn(ItemCategory.OTHERS);

            IncursionGenerator incursionGenerator = new IncursionGenerator(itemGenerator);
            Incursion incursion = incursionGenerator.generateConquest();

            assertAll(
                    ()->assertEquals("Conquista",incursion.getShortName()),
                    ()->assertEquals("Una campaña de conquista sobre territorios enemigos. La victoria trae consigo equipo valioso.",incursion.getDescription()),
                    ()->assertEquals(10,incursion.getGoldReward()),
                    ()->assertEquals(ItemCategory.OTHERS,incursion.getItemReward().getCategory())

            );

        }

    }

}