package classes;

import java.util.Arrays;
import java.util.List;

import Screen;

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

    public String getColor() {
        return rarity.getColor();
    }

    public String getColorName() {
        return rarity.getColorName();
    }

    public String getProperties() {
        String propertyColor = "{R;ITALIC;SOFT}";

        List<String> properties = Arrays.asList(
            "Rarity",       "{BOLD}"+getColor()+getRarity(),
            "Name",         getColor()+name,
            "Desc",  getDesc(),
            "Chance",       "1 in "+getChance(),
            "Price",        "{darkgreen}$"+getPrice()
        );

        String string = "";
        int c=0;

        for (String property: properties) {
            if (c++%2==0) {
                string+=" "+propertyColor+Screen.align(property, 7)+"{R}: ";
            } else {
                string+=property+"{R}";
                if (c+1<properties.size()) string+="\n";
            }
        }

        return string;
    }

    @Override
    public String toString() {
        return "{BOLD}"+getColor()+getRarity()+" : {R}"+getColor()+name+"{R}";
    }
}
