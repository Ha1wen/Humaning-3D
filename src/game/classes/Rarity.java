package classes;

public class Rarity {
    private final String name;
    private final String color;
    private final int chance;
    private final int price;

    public Rarity (String name, String color, int chance, int price) {
        this.name = name;
        this.color = color;
        this.chance = chance;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getColorName() {
        return color;
    }

    public String getColor() {
        return "{"+color+"}";
    }
    
    public int getPrice() {
        return price;
    }

    public int getChance() {
        return chance;
    }
}