package org.sopra.rogueguild.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.sopra.rogueguild.repository.*;
import org.sopra.rogueguild.repository.model.*;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.controller.dto.BuyResponse;

public class ShopController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final QuestRepository questRepository;
    private final IncursionGenerator incursionGenerator;
    private final WorldMap worldMap;
    private final Scanner sc;

    public ShopController(Player p, ViewDisplay v, ShopRepository r, QuestRepository q, WorldMap w) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.questRepository = q;
        this.worldMap = w;
        this.incursionGenerator = new IncursionGenerator(new ItemGenerator());
        this.sc = new Scanner(System.in);
    }
    public void start() {
        int opt;
        WorldEvent event = WorldEventGenerator.generate();
        repository.applyWorldEvent(event);
        view.showMessage(event.getDescription());
        view.pressKeyMessage();
        sc.nextLine();
        do {
            view.landingPage();
            view.playerStatus(player);
            try {
                opt = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                view.showMessage("Introduce un número válido.");
                opt = -1;
                view.pressKeyMessage();
                sc.nextLine();
                continue;
            }
            switch (opt) {
                case 1 -> shopProcess();
                case 2 -> inventoryProcess();
                case 3 -> incursionProcess();
                case 4 -> questProcess();
                case 0 -> view.quitMessage();
            }
            view.pressKeyMessage();
            sc.nextLine();
        } while (opt != 0);
    }

    private void shopProcess() {
        view.displayStock(repository.getAllStock(), false);
        view.shopMenu();

        int opt;
        try {
            opt = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            view.showMessage("Introduce un número válido.");
            return;
        }

        switch (opt) {
            case 1 -> {
                view.displayStock(repository.getAllStock(), true);
                try {
                    int itemId = Integer.parseInt(sc.nextLine());
                    BuyResponse buyResponse = buyProcess(itemId);
                    view.buyResult(buyResponse);
                } catch (NumberFormatException e) {
                    view.showMessage("Introduce un número válido.");
                }
            }
            case 0 -> {}
            default -> view.showMessage("Opción no válida.");
        }
    }

    private void inventoryProcess() {
        view.displayInventory(player.getInventory(), false);
        view.inventoryMenu();

        int opt;
        try {
            opt = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            view.showMessage("Introduce un número válido.");
            return;
        }

        switch (opt) {
            case 1 -> equipProcess();
            case 2 -> unequipProcess();
            case 3 -> {
                view.displayInventory(player.getInventory(), true);
                try {
                    int sellItem = Integer.parseInt(sc.nextLine());
                    sellProcess(sellItem);
                } catch (NumberFormatException e) {
                    view.showMessage("Introduce un número válido.");
                }
            }
            case 4 -> {
                view.displayInventory(player.getInventory(), true);
                try {
                    int removeItemId = Integer.parseInt(sc.nextLine());
                    removeProcess(removeItemId);
                } catch (NumberFormatException e) {
                    view.showMessage("Introduce un número válido.");
                }
            }

            case 5 -> travelProcess();

            case 0 -> {}
            default -> view.showMessage("Opción no válida.");
        }
    }

    private BuyResponse buyProcess(int id) {
        Item item = repository.getItem(id);
        if (item == null) {
            return BuyResponse.notFound(id);
        }
        if (player.getGold() < item.getPrice()) {
            return BuyResponse.notEnoughGold(item, player.getGold());
        }

        player.buy(item);
        repository.removeItem(id);

        if (item instanceof Potion potion) {
            player.removeItem(potion);
            player.heal(potion.getHealingPoints());
        }

        return BuyResponse.success(item);
    }

    private void removeProcess(int id) {
        if (player.getInventory().isEmpty()) {
            return;
        }

        if (id < 1 || id > player.getInventory().size()) {
            view.showMessage("Opción no válida.");
            return;
        }

        Item item = player.getInventory().get(id - 1);
        player.removeItem(item);
        view.showMessage("Has eliminado " + item.getName());
    }

    private void sellProcess(int id) {
        if(player.getInventory().isEmpty()){
            view.showMessage("Tu inventario está vacío.");
            return;
        }

        if (id < 1 || id > player.getInventory().size()) {
            view.showMessage("Opción no válida.");
            return;
        }

        Item item = player.getInventory().get(id - 1);
        int amountGold = (int) (Math.round(item.getBasePrice() * 0.8 / 5) * 5);

        if (!player.sellItemIfIsNotEquipped(item, amountGold)) {
            view.showMessage("No puedes vender un ítem equipado.");
            return;
        }

        repository.addItem(id,item);

        view.showMessage("Has vendido " + item.getName() + " por " + amountGold + " monedas.");
    }

    private void incursionProcess(){
        view.displayIncursion();

        int option;
        try {
            option= Integer.parseInt(sc.nextLine());
        }catch (NumberFormatException n){
            view.showMessage("Introduce un numero valido");
            return;
        }

        Incursion incursion;
          switch (option){
              case 1:
                  incursion=incursionGenerator.generateConquest();
                  break;

              case 2:
                  incursion=incursionGenerator.generateLoot();
                  break;

              case 3:
                  incursion=incursionGenerator.generateMinor();
                  break;
              case 0:
                  return;
              default:
                  view.showMessage("Opción no válida");
                  return;
        }
        int gold = player.addGold(incursion.getGoldReward());
        view.incursionView(incursion,gold);

        if(incursion.getItemReward()!=null){
            player.addItem(incursion.getItemReward());
        }

        if(gold<incursion.getGoldReward()){
            view.showMessage("Limite excedido. Oro perdido");
        }
        repository.refreshStock();
    }
    private void questProcess() {
        List<Quest> pending = questRepository.getPendingQuest();

        if (pending.isEmpty()) {
            view.showMessage("No hay misiones disponibles.");
            return;
        }

        view.misionView(pending);

        int questId;
        try {
            questId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException n) {
            view.showMessage("Introduce un valor correcto.");
            return;
        }

        if (questId == 0) {
            return;
        }

        if (questId < 1 || questId > pending.size()) {
            view.showMessage("Opción no válida.");
            return;
        }

        Quest quest = pending.get(questId - 1);

        if (quest.completeQuest(player)) {
            view.showMessage("Misión completada: " + quest.getDescription() + ". Has obtenido " + quest.getGoldReward() + " de oro.");
        } else {
            view.showMessage("Tienes que cumplir los requisitos.");
        }
    }

    private void equipProcess() {
        if (player.getInventory().isEmpty()) {
            view.showMessage("Tu inventario está vacío.");
            return;
        }

        view.displayEquipMenu(player.getInventory(), player.getItemEquipped());

        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            view.showMessage("Introduce un número válido.");
            return;
        }

        if (id == 0) return;

        if (id < 1 || id > player.getInventory().size()) {
            view.showMessage("Opción no válida.");
            return;
        }

        Item item = player.getInventory().get(id - 1);
        if (player.equipItem(item)) {
            view.showMessage("Has equipado " + item.getName());
        } else {
            view.showMessage("Este objeto no se puede equipar.");
        }
    }

    private void unequipProcess() {
        Map<ItemCategory, List<Item>> equipped = player.getItemEquipped();

        List<Item> equippedList = equipped.values().stream().flatMap(List::stream).toList();

        if (equippedList.isEmpty()) {
            view.showMessage("No tienes ningún ítem equipado.");
            return;
        }

        view.displayInventory(equippedList, true);

        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            view.showMessage("Introduce un número válido.");
            return;
        }

        if (id == 0) return;

        if (id < 1 || id > equippedList.size()) {
            view.showMessage("Opción no válida.");
            return;
        }

        Item item = equippedList.get(id - 1);
        player.unequipItem(item);
        view.showMessage("Has desequipado " + item.getName());
    }

    private void travelProcess() {
        view.displayTravelMenu(player.getCurrentCity());

        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            view.showMessage("Introduce un número válido.");
            return;
        }

        if (id == 0) return;

        List<City> connections = player.getCurrentCity().getConnections();

        if (id < 1 || id > connections.size()) {
            view.showMessage("Opción no válida.");
            return;
        }

        City destination = connections.get(id - 1);

        if (player.travelTo(destination)) {
            view.showMessage("Has viajado a " + destination.getName());
        } else {
            view.showMessage("No se puede viajar a esa ciudad.");
        }
    }
}
