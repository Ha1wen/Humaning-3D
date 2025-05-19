

import java.io.IOException;

import Player;
import util.Colors;

public class Screen {
    private static final String CLEAR_STRING = "\033[H\033[2J";
    private static final String TITLE_COLOR = "{INVERT;BOLD}";
    private static final String PLAYER_COLOR = "{backdarkblack}";
    private static final int BAR_SIZE = 50;

    public static void reset() {
        try {
            new ProcessBuilder("/bin/sh","-c","stty sane -echo").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void clear() {
        reset();         
        System.out.print(CLEAR_STRING);
    }

    public static void print(Player player, String title, String content) {
        reset();
        print(getBar(player, title)+content);
    }

    public static void print(String text) {
        System.out.printf(color(text)+"\n");
        //System.out.println();
    }

    public static void printBar(Player player, String title) {
        reset();
        print(getBar(player, title));
    }

    public static String getBar(Player player, String title) {
        int marginSize = (BAR_SIZE-title.length())/2;
        String space = space(marginSize);

        title = space+title+space;
        if (title.length()%2!=0) title+=" ";

        String name = player.getName();
        int length = title.length();
        int money = player.getMoney();

        String playerBar = PLAYER_COLOR+Screen.align(" "+name, length-(String.valueOf(money).length()+2)) + "{green}$"+money+" {R}";
        String titleBar = TITLE_COLOR+title+"{R}\n";

        String bar = getClear() + playerBar + "\n" + titleBar;

        return bar;
    }

    public static String color(String string) {
        return Colors.getString(string)+Colors.reset();
    }

    public static String getClear() {
        return CLEAR_STRING;
    }

    public static String align(String string, int length) {
        return string + space(length - string.length());
    }

    public static String space(int length) {
        return " ".repeat(length);
    }
}
