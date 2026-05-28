package org.sopra.rogueguild.repository;

import org.sopra.rogueguild.repository.model.ItemCategory;
import org.sopra.rogueguild.repository.model.Quest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestRepository {
    private List<Quest> quests;

    public QuestRepository() {
        this.quests = new ArrayList<>();
        loadQuests();
    }

    public List<Quest> getQuests() {
        return new ArrayList<>(quests);
    }
    public List<Quest> getPendingQuest(){
        return quests.stream().filter(quest -> !quest.isCompleted()).toList();

    }

    private void loadQuests() {
        quests.add(new Quest("Danza de muerte", 50, Map.of(ItemCategory.WEAPON, 2)));
        quests.add(new Quest("Caballero del Fénix", 150, Map.of(ItemCategory.WEAPON, 1, ItemCategory.ARMOR, 1, ItemCategory.HELMET, 1, ItemCategory.BOOTS, 1)));
        quests.add(new Quest("Guerrero de hierro", 100, Map.of(), 30, 0));
        quests.add(new Quest("Bastión de acero", 150, Map.of(), 0, 20));
    }

    public Quest getIdQuest(int id){
        if (id<1||id > quests.size()){
            return null;
        }
        return quests.get(id-1);
    }
}
