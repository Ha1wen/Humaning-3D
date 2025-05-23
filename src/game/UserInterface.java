

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

import classes.Human;
import classes.Rod;
import engine.Renderer;
import engine.Window;
import engine.classes.*;

public class UserInterface {
    private Player player;
    private Renderer renderer;
    private Window window;

    public boolean fishingPrompt;
    public boolean humanPrompt;
    public boolean sellingPrompt;
    public boolean buyingPrompt;

    public UserInterface(Player player, Renderer renderer, Window window) {
        this.player = player;
        this.renderer = renderer;
        this.window = window;
    }

    public void render() {

        renderer.renderText("MONEY:", Text.OFFSET, Text.OFFSET, 5, Color.WHITE);
        renderer.renderText("$"+player.getMoney(), 200, Text.OFFSET, 5, Color.YELLOW);

        renderer.renderText("INVENTORY:", Text.OFFSET, 200, 4, Color.WHITE);
        int ypos = 250;
        for (int i = 1; i <= player.getInventory().getSize(); i++) {
            Human human = player.getInventory().getHuman(i);

            if (human != null) 
                renderer.renderText(human.getName(), Text.OFFSET, ypos, 3, human.getColor());
            else
                renderer.renderText("(empty)", Text.OFFSET, ypos, 3, Color.GRAY);

            ypos+=50;
        }

        if (humanPrompt) {
            Human human = player.getCurrentHuman();
            if (human == null) return;
            
            renderer.renderText("NICE! YOU CAUGHT A", 170, 300, 4, Color.WHITE);
            renderer.renderText(human.toString(), 220, 350, 5, human.getColor());
            //player.caught = true;
            int space = player.getInventory().getSpace();
            String text = space > 0 ? ("You have " + space +" slots left") : "Your inventory is full!";
            
            renderer.renderText(text, 150, 400, 4, Color.WHITE);
            renderer.renderText("PRESS ENTER TO CONTINUE", 250f, 450, 2, Color.WHITE);
        } 
        else if (fishingPrompt) {
            renderer.renderText("PRESS \"E\" TO GO HUMANING", 150, 300, 4, Color.WHITE);
        }
        else if (sellingPrompt) {
            renderer.renderText("PRESS \"E\" TO SELL HUMANS", 150, 300, 4, Color.WHITE);
        }
        else if (buyingPrompt) {
            Rod rod = player.getNextRod();
            renderer.renderText("PRESS \"E\" TO UPGRADE ROD", 150, 300, 4, Color.WHITE);
            renderer.renderText(rod.getRarityClass().getName()+" ("+rod.getPrice()+")", 200, 350, 4, Color.get(rod.getRarityClass().getColorName()));
        }
    }

    
}
