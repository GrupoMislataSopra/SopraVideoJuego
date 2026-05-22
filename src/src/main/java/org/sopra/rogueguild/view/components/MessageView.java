package org.sopra.rogueguild.view.components;

import java.io.PrintStream;

import org.sopra.rogueguild.view.utils.FramePrinter;

import static org.sopra.rogueguild.view.utils.Ansi.GRAY;
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
        showPrompt(c(GRAY, "\n[ Pulsa ENTER para continuar... ]"));
    }

    public void quitMessage() {
        showMessage("Nos vemos pronto.");
    }

    public void showPrompt(String prompt) {
        System.out.print(prompt);
    }
}
