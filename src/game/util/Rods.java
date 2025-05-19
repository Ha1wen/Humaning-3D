package util;

import java.util.ArrayList;

import classes.Rarity;
import classes.Rod;

public class Rods {
    private static ArrayList<Rod> rods = createRods();

    private static ArrayList<Rod> createRods() {
        ArrayList<Rod> array = new ArrayList<>();

        for (Rarity rarity: Rarities.getArray()) {
            array.add(new Rod("Rod", rarity));
        }

        return array;
    }

    public static int getAmount() {
        return rods.size();
    }

    public static ArrayList<Rod> getRods() {
        return rods;
    }

    public static Rod getRod(int index) {
        return rods.get(index);
    }

    public static void addRod(Rod rod) {
        rods.add(rod);
    }

    public static Rod getDefault() {
        return rods.get(0);
    }

    private static String getLongestName() {
        String longest = "";
        for (Rod rod: rods) {
            if (rod.getName().length() > longest.length()) longest = rod.getName();
        }

        return longest;
    }

    private static String getLongestRarity() {
        String longest = "";
        for (Rod rod: rods) {
            if (rod.getRarity().length() > longest.length()) longest = rod.getRarity();
        }

        return longest;
    }

    public static String getString(boolean prices, int sold) {
        String string = "";
        int nameSpace = getLongestName().length();
        int raritySpace = getLongestRarity().length();

        int c = 1;
        for (Rod rod: rods) {
            String color = rod.getColorName();
            String rarity = rod.getRarity();
            String name = rod.getName();
            String price = "$"+rod.getPrice();
            String soldEffect = ";SOFT;STROKE";

            if (!(sold == c || sold ==0)) soldEffect = "";

            string+=" {R,ITALIC;SOFT}"+c;
            string+=" {R;BOLD;"+color+soldEffect+"}"+Screen.align(rarity, raritySpace);
            string+=" {R"+soldEffect+"}: "+Screen.align(name, nameSpace);

            if (prices) string+="{R"+soldEffect+"} : {green}"+price+"{R}";

            if (c++ < rods.size()) {
                string += "\n";
            }
        }
        return string;
    }

    public static String getString(boolean prices) {
        return getString(prices, -1);
    }

    public static String getString() {
        return getString(false);
    }
}
