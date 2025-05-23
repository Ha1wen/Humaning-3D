import java.util.ArrayList;

import engine.*;
import engine.classes.*;
import engine.objects.*;

public class Map {
    private ArrayList<Object3D> objects;

    private Object3D floor;
    private Object3D skybox;
    private Object3D pond;
    private Object3D sell;
    private Object3D upgrade;

    private Player player;
    private PlayerModel playerModel;
    private UserInterface ui;

    public Map(Player player) {
        this.player = player;
        this.playerModel = player.getModel();
        this.ui = player.getUI();
        objects = new ArrayList<>();

        floor = new Box(
            new CFrame(), 
            new Vector3(20, 1, 20), 
            "res/Grass.png"
        );
        skybox = new Box(
            new CFrame(0,0,0,90,0,0), 
            new Vector3(200, 200, 200), 
            "res/Sky.jpg"
        );
        pond = new Box(
            new CFrame(0, 0, -7.5f), 
            new Vector3(15, 1.1f, 5), 
            "res/Water.jpg"
        );
        sell = new Box(
            new CFrame(-7.5f, 1, 0f), 
            Vector3.ONE,
            "res/Money.jpeg"
        );
        upgrade = new Box(
            new CFrame(7.5f, 1, 0f), 
            Vector3.ONE,
            "res/Upgrade.jpeg"
        );

        objects.add(floor);
        objects.add(skybox);
        objects.add(pond);
        objects.add(sell);
        objects.add(upgrade);
    }   

    public void events() {
        //pond.cframe = pond.cframe.add(new CFrame(0.1f, 0.1f, 0.1f));
        ui.fishingPrompt = playerModel.cframe.position.z >= 3;
        ui.sellingPrompt = playerModel.cframe.position.distance(sell.cframe.position.scale(-1)) < 5;
        ui.buyingPrompt = playerModel.cframe.position.distance(upgrade.cframe.position.scale(-1)) < 5;

        //System.out.println(playerModel.cframe.position.distance(pond.cframe.position.scale(-1)));
    }

    public ArrayList<Object3D> getObjects() {
        return objects;
    }
}
