package classes;


import java.util.ArrayList;

import Screen;
import util.Rods;

public class Inventory {
    public static int DEFAULT_SIZE = 5;

    private final ArrayList<Human> humans;
    private int size;
    private Rod rod;

    public Inventory() {
        humans = new ArrayList<>();
        size = DEFAULT_SIZE;
        rod = Rods.getDefault();
    }

    public int addHuman(Human human) {
        if (humans.size() == size) {
            return -1;
        }

        humans.add(human);
        return size-humans.size();
    }

    public void removeHuman(int index) {
        humans.remove(index-1);
    }

    public void removeHumans() {
        humans.clear();
    }

    public boolean findHuman(String name) {
        for (Human human: humans) {
            if (human.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public Human getHuman(int index) {
        if (index > humans.size()) {
            return null;
        }
        return humans.get(index-1);
    }

    public int getSize() {
        return size;
    }

    public int getAmount() {
        return humans.size();
    }

    public void setRod(Rod rod) {
        this.rod = rod;
    }

    public void changeSize(int newSize) {
        size = newSize;
    }

    public void incrementSize(int increment) {
        size += increment;
    }

    public void clear() {
        humans.clear();
        size = DEFAULT_SIZE;
    }

    public int getWorth() {
        int worth = 0;

        for (Human human : humans) worth+=human.getPrice();

        return worth;
    }

    public String getLongestName() {
        String longest = "";
        for (Human human: humans) {
            if (human.getName().length() > longest.length()) longest = human.getName();
        }

        return longest;
    }

    public String getLongestRarity() {
        String longest = "";
        for (Human human: humans) {
            if (human.getRarity().length() > longest.length()) longest = human.getRarity();
        }

        return longest;
    }

    public String toString(boolean prices, int sold) {
        String string = "";
        int nameSpace = getLongestName().length();
        int raritySpace = getLongestRarity().length();

        if (!prices) {
            string+="{ITALIC}Rod:\n {R,BOLD} "+rod+"{R}\n\n";

            if (getAmount() > 0) string+= "{ITALIC}Humans:{R}\n";
        }

        if (prices && getAmount() >1) {
            string+=" {R;ITALIC;SOFT}0{R} All : {green}$"+getWorth()+"{R}\n\n";
        }

        int c = 1;
        for (Human human: humans) {
            String color = human.getColorName();
            String rarity = human.getRarity();
            String name = human.getName();
            String price = "$"+human.getPrice();
            String soldEffect = ";SOFT;STROKE";

            if (!(sold == c || sold ==0)) soldEffect = "";

            string+=" {R,ITALIC;SOFT}"+c;
            string+=" {R;BOLD;"+color+soldEffect+"}"+Screen.align(rarity, raritySpace);
            string+=" {R"+soldEffect+"}: "+Screen.align(name, nameSpace);

            if (prices) string+="{R"+soldEffect+"} : {green}"+price+"{R}";

            if (c++ < humans.size()) {
                string += "\n";
            }
        }
        return string;
    }

    public String toString(boolean prices) {
        return toString(prices, -1);
    }

    @Override
    public String toString() {
        return toString(false, -1);
    }

    public Rod getRod() {
        return rod;
    }
}
