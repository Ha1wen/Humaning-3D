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

    public static int getIndex(Rod iRod) {
        for(int i = 0; i<rods.size(); i++) {
            Rod rod = rods.get(i);
            if (rod.getRarity().equals(iRod.getRarity()))
                return i;
        }
        return -1;
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
}
