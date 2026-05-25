package org.sopra.rogueguild.controller;

import java.util.Scanner;

import org.sopra.rogueguild.repository.QuestRepository;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.WorldEventGenerator;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.WorldEvent;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.controller.dto.BuyResponse;

public class ShopController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final QuestRepository questRepository;
    private final Scanner sc;

    public ShopController(Player p, ViewDisplay v, ShopRepository r, QuestRepository q) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.questRepository = q;
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
                case 1:
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    view.displayStock(repository.getAllStock(), true);
                    int itemId;
                    try {
                        itemId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        view.showMessage("Introduce un número válido.");
                        break;
                    }
                    BuyResponse buyResponse = buyProcess(itemId);
                    view.buyResult(buyResponse);
                    break;
                case 3:
                    view.displayInventory(player.getInventory(), false);
                    break;
                case 4:
                    view.displayInventory(player.getInventory(), true);
                    int removeItemId;
                    try {
                        removeItemId = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        view.showMessage("Introduce un número válido.");
                        break;
                    }
                    removeProcess(removeItemId);
                    break;
                case 5:

                    view.displayInventory(player.getInventory(),true);
                    int sellItem;
                    try {
                        sellItem = Integer.parseInt(sc.nextLine());
                    }
                    catch (NumberFormatException n){
                        view.showMessage("Introduce un numero valido");
                        break;
                    }
                    sellProcess(sellItem);
                    break;


                case 0:
                    view.quitMessage();
                    break;
            }
            view.pressKeyMessage();
            sc.nextLine();
        } while (opt != 0);
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
            return;
        }

        if (id < 1 || id > player.getInventory().size()) {
            view.showMessage("Opción no válida.");
            return;
        }

        Item item = player.getInventory().get(id-1);
        int amountGold = (int) (Math.round(item.getBasePrice() * 0.8 / 5) * 5);

        player.sell(item, amountGold);
        repository.addItem(id,item);

        view.showMessage("Has vendido " + item.getName() + " por " + amountGold + " monedas.");
    }
}
