package org.sopra.rogueguild.view.components;

import java.io.PrintStream;

import org.sopra.rogueguild.repository.model.WorldEvent;
import org.sopra.rogueguild.view.utils.FramePrinter;

import static org.sopra.rogueguild.view.utils.Ansi.LGRAY;
import static org.sopra.rogueguild.view.utils.Ansi.c;

public class MessageView {
    private final FramePrinter frame;

    public MessageView(PrintStream out, int width) {
        this.frame = new FramePrinter(out, width);
    }

    public void showMessage(String message) {
        frame.box(message);
    }

    public void pressKeyMessage() {
        showPrompt(c(LGRAY, "\n[ Pulsa ENTER para continuar... ]"));
    }

    public void quitMessage() {
        showMessage("Nos vemos pronto.");
    }

    public void showPrompt(String prompt) {
        System.out.print(prompt);
    }

    public void showWorldEvent(WorldEvent event) {
        String arrow = event.getMultiplier() > 1 ? "▲" : "▼";
        String line = "─".repeat(33);
        frame.line("  ✦ " + line + " ✦");
        frame.line("      " + arrow + "   " + event.getDescription());
        frame.line("  ✦ " + line + " ✦");
    }
}
