package retailiq.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/** Small arrow-key menu used by the main and product screens. */
final class ArrowKeyMenu {

    private static Terminal terminal;

    private ArrowKeyMenu() { }

    static int select(String title, String breadcrumb, String[] items, String[] descriptions,
                      String footer) {
        int selected = 0;
        try {
            if (terminal == null) terminal = new DefaultTerminalFactory().createTerminal();
            terminal.enterPrivateMode();
            try {
                while (true) {
                    draw(terminal, title, breadcrumb, items, descriptions, footer, selected);
                    KeyStroke key = terminal.readInput();
                    if (key == null) return -1;
                    if (key.getKeyType() == KeyType.ArrowUp) {
                        selected = (selected + items.length - 1) % items.length;
                    } else if (key.getKeyType() == KeyType.ArrowDown) {
                        selected = (selected + 1) % items.length;
                    } else if (key.getKeyType() == KeyType.Enter) {
                        return selected;
                    } else if (key.getKeyType() == KeyType.Escape
                            || (key.getKeyType() == KeyType.Character
                            && (key.getCharacter() == 'q' || key.getCharacter() == 'Q'))) {
                        return -1;
                    }
                }
            } finally {
                terminal.exitPrivateMode();
            }
        } catch (IOException e) {
            System.out.println("Arrow-key menus need a supported terminal. "
                    + "Run the app in Windows Terminal or Eclipse's Terminal view.");
            return -2;
        }
    }

    private static void draw(Terminal terminal, String title, String breadcrumb,
                             String[] items, String[] descriptions, String footer, int selected)
            throws IOException {
        terminal.clearScreen();
        TerminalSize size = terminal.getTerminalSize();
        int width = Math.max(58, Math.min(size.getColumns(), 76));
        String border = "─".repeat(width - 2);
        terminal.setCursorPosition(0, 0);
        line(terminal, "┌" + border + "┐", TextColor.ANSI.CYAN);
        line(terminal, fit("│ " + title + "  " + breadcrumb, width) + "│", TextColor.ANSI.CYAN);
        line(terminal, "├" + border + "┤", TextColor.ANSI.CYAN);
        for (int i = 0; i < items.length; i++) {
            String marker = i == selected ? "❯ " : "  ";
            String description = i < descriptions.length ? descriptions[i] : "";
            String row = "│ " + marker + items[i] + "  " + description;
            line(terminal, fit(row, width) + "│", i == selected ? TextColor.ANSI.YELLOW : TextColor.ANSI.DEFAULT);
        }
        line(terminal, "├" + border + "┤", TextColor.ANSI.CYAN);
        line(terminal, fit("│ " + footer, width) + "│", TextColor.ANSI.WHITE);
        line(terminal, "└" + border + "┘", TextColor.ANSI.CYAN);
        terminal.flush();
    }

    private static String fit(String text, int width) {
        int contentWidth = width - 1;
        if (text.length() > contentWidth) return text.substring(0, contentWidth);
        return text + " ".repeat(contentWidth - text.length());
    }

    private static void line(Terminal terminal, String text, TextColor.ANSI color) throws IOException {
        terminal.setForegroundColor(color);
        terminal.putString(text);
        terminal.putCharacter('\n');
    }
}
