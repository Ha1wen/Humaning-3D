import engine.*;
import engine.classes.*;
import engine.objects.*;
import util.*;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

import classes.*;

public class Game {
    private Window window;
    private Renderer renderer;
    private UserInterface ui;

    private Map map;

    private PlayerModel playerModel;
    private Camera camera;
    
    private final Player player;
    private final Inventory inventory;

    public Game() {

        window = new Window(1920, 1080, "Game");
        renderer = new Renderer(window);

        playerModel = new PlayerModel(new CFrame(0, 1.5f, 0), renderer.getCamera());
        playerModel.setController(new Controller(window, playerModel.cframe, Controller.Mode.PLAYER));
        new Text("PRESS \"E\" TO GO HUMANING", 300, Text.OFFSET, 5, Color.WHITE);
        player = new Player("Player");
        player.setModel(playerModel);



        inventory = player.getInventory();

        ui = new UserInterface(player, renderer, window);
        player.setUI(ui);

        map = new Map(player);

        renderer.addObjects(map.getObjects());
        renderer.addObject(playerModel.getRod());
    }

    public void inputs() {
        if (ui.fishingPrompt && window.getKeyDown(GLFW_KEY_E) && !player.caught) {
            humaning();
            ui.humanPrompt = true;
            player.caught = true;
        }
        else if (player.caught && window.getKeyDown(GLFW_KEY_ENTER)) {
            ui.humanPrompt = false;
            player.caught = false;
        }
        else if (ui.sellingPrompt && window.getKeyDown(GLFW_KEY_E)) {
            sell();
        }
        else if (ui.buyingPrompt && window.getKeyDown(GLFW_KEY_E)) {
            upgrade();
        }
    }

    public void sell() {
        if (inventory.getAmount() <= 0) return;

        player.sellHumans();
    }

    public void upgrade() {
        Rod rod = player.getNextRod();
        int price = rod.getPrice();

        if (price > player.getMoney()) return;

        player.spendMoney(price);
        player.setRot(rod);
    }

    public void humaning() {
        if (inventory.getAmount() >= inventory.getSize()) return;

        Human human = Humans.getRandomHuman(player.getLuck());
        int left = inventory.addHuman(human);  

        System.out.println(player.getLuck());
    }

    public void play() {
        loop();

        window.cleanup();
    }

    private void loop() {
        while (!window.shouldClose()) {
            inputs();
            playerModel.move();
            map.events();
            //System.out.println(player.cframe);

            renderer.render();

            ui.render();
            // ui.newFrame();
            // ui.layout();
            // ui.render();

            window.swapBuffers();
            window.pollEvents();
        }
    }
}
