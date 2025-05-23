package classes;

import java.util.Arrays;
import java.util.List;

import engine.classes.Color;

public class Human {
    private final String name;
    private final String desc;
    private final Rarity rarity;

    public Human(String name, String desc, Rarity rarity) {
        this.name = name;
        this.desc = desc;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Rarity getRarityClass() {
        return rarity;
    }

    public String getRarity() {
        return rarity.getName();
    }

    public int getPrice() {
        return rarity.getPrice();
    }

    public int getChance() {
        return rarity.getChance();
    }

    public Color getColor() {
        return rarity.getColor();
    }

    public String getColorName() {
        return rarity.getColorName();
    }

    @Override
    public String toString() {
        return getRarity()+" "+getName();
    }
}
