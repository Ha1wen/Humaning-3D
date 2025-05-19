import classes.*;
import util.*;

import java.util.Arrays;
import java.util.List;

public class BaseGame {
    private final Player player;
    private final Inventory inventory;

    private static final List<String> menuOptions = Arrays.asList(
        "Humaning",
        "Inventory",
        "Sell",
        "Shop"
    );

    public BaseGame(String playerName) {
        player = new Player(playerName);
        inventory = player.getInventory();
    }

    public void play() {
        //Screen.print("{Blue,Bold,White}Balls");
        while(true) {
            menu();
        }
    }

    public void menu() {
        String title = "What would you like to do?";
        String content = "\n";

        int c = 1;
        for (String name : menuOptions) {
            String unavailable = ";SOFT";
            String effect = "R;ITALIC";
            String string = name;
            String newLine = c < menuOptions.size() ? "\n" : "";

            if (name.equals("Humaning") && inventory.getAmount() >= inventory.getSize()) {
                effect += unavailable;
                string += " (no space)";
            }

            if (name.equals("Sell") && inventory.getAmount() <= 0) {
                effect += unavailable;
                string += " (no humans)";
            }

            if (name.equals("Shop") && player.getMoney() <= 0) {
                effect += unavailable;
                string += " (no money)";

            }
            effect = "{"+effect+"}";
            string = effect + " " +(c++)+" : "+string+newLine;
            content += string+newLine;
        }

        Screen.print(player, title, content);

        int option = Input.num(menuOptions.size());
        //Screen.clear();

        switch (option) {
            case 1 -> humaning();
            case 2 -> inventory();
            case 3 -> sell();
            case 4 -> shop();
            default -> Screen.print("Invalid Option");
        }
        //Screen.clear();
    }

    public void humaning() {
        if (inventory.getAmount() >= inventory.getSize()) return;

        Human human = Humans.getRandomHuman(player.getLuck()/(.25));
        String properties = human.getProperties();

        Screen.printBar(player, "YOU CAUGHT A HUMAN!");
        Screen.print("{R}"+properties+"\n");

        int left = inventory.addHuman(human);  
        String color = left > 0 ? "BLUE" : "RED";
        if (left < 0) {
            Screen.print("{BOLD}Unfortunately, your inventory is {"+color+"}full{R;BOLD}!");
        } else {
            Screen.print("{BOLD}You have {"+color+"}"+left+"{R;BOLD} inventory slots left!");
        }


        Input.cnt();
    }

    public void inventory() {
        Screen.printBar(player, "INVENTORY");
        Screen.print(inventory.toString());

        int options = inventory.getAmount();
        int selection = Input.num(1, options, true);

        if (selection == -1) {
            return;
        }

        Screen.printBar(player, "HUMAN");
        Screen.print(inventory.getHuman(selection).getProperties());

        Input.cnt();

        inventory();
    }

    public void sell() {
        if (inventory.getAmount() <= 0) return;

        Screen.printBar(player, "SELL HUMANS");
        Screen.print(inventory.toString(true));

        int options = inventory.getAmount();
        int selection = Input.num(0, options, true);

        if (selection == -1) {
            return;
        }

        //Screen.clear();
        int money = selection == 0 ? inventory.getWorth() : inventory.getHuman(selection).getPrice();
        player.addMoney(money);

        Screen.printBar(player, "SELL HUMANS");
        Screen.print(inventory.toString(true, selection));

        if (selection == 0) {
            //player.sellHumans();
            inventory.removeHumans();
        } else {
            //player.sellHuman(selection-1);
            inventory.removeHuman(selection);
        }

        Screen.print("\n{BOLD}Sold! You earned {green}$"+money+"!");
        Input.cnt();

        //Screen.clear();
        sell();
    }

    public void shop() {
        if (player.getMoney() <= 0) return;

        Screen.printBar(player, "HUMANING SHOP");
        Screen.print("{ITALIC}Rods:{R}\n"+Rods.getString(true));

        int options = Rods.getAmount();
        int selection = Input.num(1, options, true);

        if (selection == -1) {
            return;
        }

        Rod rod = Rods.getRod(selection-1);
        int price = rod.getPrice();
        if(price > player.getMoney()) {
            Screen.print("\n{BOLD}Not enough money! You need ${green}"+(price-player.getMoney())+"{R;BOLD} more!");
            Input.cnt();
            return;
        }

        player.spendMoney(price);
        player.setRot(rod);

        Screen.printBar(player, "HUMANING SHOP");
        Screen.print("{ITALIC}Rods:{R}\n"+Rods.getString(true, selection));

        

        Screen.print("\n{BOLD}Sold! You bought the "+rod+"{R,BOLD} for {green}$"+price);
        Input.cnt();

        //Screen.clear();
    }
}