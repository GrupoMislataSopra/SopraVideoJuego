package org.sopra.rogueguild.view;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.sopra.rogueguild.repository.model.Incursion;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.repository.model.Quest;
import org.sopra.rogueguild.view.components.*;

public class ViewDisplay {
    private final BannerView banner;
    private final MessageView messages;
    private final PlayerView playerView;
    private final StockView stockView;
    private final BuyResultView buyResultView;
    private final InventoryView inventoryView;
    private final IncursionView incursionView;
    private final MisionesView misionesView;

    public ViewDisplay() {
        this(System.out, 59);
    }

    public ViewDisplay(PrintStream out, int width) {
        this.banner = new BannerView(out);
        this.messages = new MessageView(out, width);
        this.playerView = new PlayerView(out);
        this.stockView = new StockView(out);
        this.buyResultView = new BuyResultView(messages);
        this.inventoryView = new InventoryView(out);
        this.incursionView = new IncursionView(out);
        this.misionesView = new MisionesView(out);
    }

    public void landingPage() { banner.landingPage(); }
    public void showMessage(String msg) { messages.showMessage(msg); }
    public void pressKeyMessage() { messages.pressKeyMessage(); }
    public void quitMessage() { messages.quitMessage(); }
    public void showPrompt(String prompt) { messages.showPrompt(prompt); }

    public void playerStatus(Player player) { playerView.playerStatus(player); }

    public void displayStock(Map<Integer, Item> itemMap, boolean isInPurchaseProcess) {
        stockView.displayStock(itemMap, isInPurchaseProcess);
    }

    public void displayInventory(List<Item> inventory, boolean inRemoveProcess) {
        inventoryView.displayInventory(inventory, inRemoveProcess);
    }

    public void buyResult(BuyResponse r) {
        buyResultView.show(r);
    }

    public void displayIncursion(){
        incursionView.displayIncursions();
    }

    public void incursionView(Incursion incursion, int amountGold){
        incursionView.incursionResult(incursion,amountGold);

    }
    public void misionView(List<Quest>quests){
        misionesView.displayMisiones(quests);
    }
}
