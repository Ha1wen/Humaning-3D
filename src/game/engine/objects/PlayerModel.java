package engine.objects;

import engine.Camera;
import engine.Controller;
import engine.classes.*;

public class PlayerModel {
    public CFrame cframe;
    private Camera camera;
    private Controller controller;

    private Object3D rod;

    private boolean camOn;

    public PlayerModel(CFrame cframe, Camera camera) {
        this.cframe = cframe;
        this.camera = camera;
        this.controller = null;

        rod = new Box(
            new CFrame(),
            new Vector3(0.01f, 0.5f, 0.5f),
            "res/Rod.png"
        );


        camOn = true;
    }

    public Object3D getRod() {
        return rod;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void move() {
        if (controller==null) return;

        controller.update();
        rod.cframe = camera.cframe;

        //System.out.println(cframe);

        updateCam();
    }

    private void updateCam() {
        if (!camOn) return;

        camera.cframe = cframe;
    }
}
