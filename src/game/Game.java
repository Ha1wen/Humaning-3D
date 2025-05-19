import engine.*;
import engine.classes.*;
import engine.objects.*;
import util.*;
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

        map = new Map();

        playerModel = new PlayerModel(new CFrame(0, 1.5f, 0), renderer.getCamera());
        playerModel.setController(new Controller(window, playerModel.cframe, Controller.Mode.PLAYER));

        player = new Player("Player");
        player.setModel(playerModel);

        inventory = player.getInventory();

        ui = new UserInterface(player, renderer);

        renderer.addObjects(map.getObjects());
        renderer.addObject(playerModel.getRod());
    }

    public void play() {
        loop();

        window.cleanup();
    }

    private void loop() {
        while (!window.shouldClose()) {
            playerModel.move();

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
