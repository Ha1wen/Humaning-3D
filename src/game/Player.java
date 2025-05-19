import classes.Human;
import classes.Inventory;
import classes.Rod;
import engine.Camera;
import engine.Controller;
import engine.classes.CFrame;
import engine.objects.PlayerModel;

public class Player {
    private static final String BAR_COLOR = "{backdarkblack}";
    private final Inventory inventory;
    private int money;
    private String name;

    private PlayerModel model;

    public Player(String playerName) {
        inventory = new Inventory();
        money = 0; 
        name = playerName;

        model = null;
    }

    public void setModel(PlayerModel model) {
        this.model = model;
    }
    public void changeName(String newName) {
        name = newName;
    }

    public void addMoney(int num) {
        money+=num;
    }

    public void setRot(Rod rod) {
        inventory.setRod(rod);
    }

    public boolean spendMoney(int num) {
        if (num > money) {
            return false;
        }

        money -= num;
        return true;
    }
    
    public int sellHuman(int index) {
        Human human = inventory.getHuman(index);
        int price = human.getPrice();
        money +=price;
        inventory.removeHuman(index);

        return price;
    }

    public int sellHumans() {
        int total = inventory.getWorth();
        for (int i = 0; i<inventory.getAmount(); i=0) {
            sellHuman(0);
        }

        return total;
    }

    public String getBar(int length) {
        return BAR_COLOR+Screen.align(" "+name, length-(String.valueOf(money).length()+2)) + "{green}$"+money+" ";
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public void clear() {
        inventory.clear();
        money = 0;
    }

    public int getLuck() {
        return inventory.getRod().getLuck();
    }
}
