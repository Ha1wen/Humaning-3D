package classes;


import java.util.ArrayList;

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

    public int getSpace() {
        return getSize() - getAmount();
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

    public Rod getRod() {
        return rod;
    }
}
