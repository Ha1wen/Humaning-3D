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
        float rot = (float)Math.toRadians(cframe.rotation.y+ 30);
        controller.update();
        rod.cframe = new CFrame(cframe.position.scale(-1), new Vector3(cframe.rotation.x, -cframe.rotation.y, 0)).add(new Vector3(0.5f * (float)Math.sin(rot), 3f, -0.5f * (float)Math.cos(rot)));

        updateCam();
    }

    private void updateCam() {
        if (!camOn) return;

        camera.cframe = cframe;
    }
}
