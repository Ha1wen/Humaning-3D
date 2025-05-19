import java.util.ArrayList;

import engine.*;
import engine.classes.*;
import engine.objects.*;

public class Map {
    private ArrayList<Object3D> objects;

    private Object3D floor;
    private Object3D skybox;
    private Object3D pond;

    public Map() {
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

        objects.add(floor);
        objects.add(skybox);
        objects.add(pond);
    }   

    public void events() {
        //pond.cframe = pond.cframe.add(new CFrame(0.1f, 0.1f, 0.1f));
    }

    public ArrayList<Object3D> getObjects() {
        return objects;
    }
}
