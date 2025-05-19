package classes;

public class Rod {
    private static int priceMultiplier = 10;

    private Rarity rarity;
    private String name;
    private String desc;
    private String color;
    private int price;
    private int luck;

    public Rod(String name, Rarity rarity) {
        this.rarity = rarity;
        this.name = name;
        this.desc = "A humaning rod";
        this.color = rarity.getColorName();
        this.price = rarity.getPrice() * priceMultiplier;
        this.luck = rarity.getChance();
        
    }

    public String getName() {
        return name;
    }
    
    public String getDesc() {
        return desc;
    }

    public String getRarity() {
        return rarity.getName();
    }

    public Rarity getRarityClass() {
        return rarity;
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

    public int getLuck() {
        return luck;
    }

    @Override
    public String toString() {
        return getColor()+"{BOLD}"+getRarity()+"{R} : "+getColor()+getName();
    }
}
