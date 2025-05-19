package engine;

import engine.classes.*;
import engine.objects.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.stb.STBEasyFont.*;
import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.stb.STBImage.*;


public class Renderer {
    private final ArrayList<Object3D> objects;

    private final ByteBuffer charBuffer;
    private final int maxBufferSize = 99999;

    private final Camera camera;

    public Renderer(Window window) {
        objects = new ArrayList<>();
        camera = new Camera();

        charBuffer = BufferUtils.createByteBuffer(maxBufferSize);

        int width = window.getWidth();
        int height = window.getHeight();

        camera.aspect = (float) width / height;

        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glViewport(0, 0, width, height);

        updatePerspective();
    }

    public void updateCFrame() {
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        Vector3 rot = camera.cframe.rotation;
        Vector3 pos = camera.cframe.position;

        glRotatef(-rot.x, 1, 0, 0);
        glRotatef(rot.y, 0, 1, 0);
        glRotatef(rot.z, 0, 0, 1);

        glTranslatef(pos.x, -pos.y, pos.z);  // Camera
    }

    private void updatePerspective() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        float fH = (float) Math.tan(Math.toRadians(camera.fov / 2)) * camera.zNear;
        float fW = fH * camera.aspect;
        glFrustum(-fW, fW, -fH, fH, camera.zNear, camera.zFar);
    }

    public void addObject(Object3D obj) {
        objects.add(obj);
    }

    public void addObjects(ArrayList<Object3D> objs) {
        for (Object3D obj: objs) {
            addObject(obj);
        }
    }

    public void removeObject(Object3D obj) {
        objects.remove(obj);
    }

    public void clearObjects() {
        objects.clear();
    }

    public static int loadTexture(String path) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load(path, width, height, channels, 4);
        if (image == null) {
            throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
        }

        int texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0,
                GL_RGBA, GL_UNSIGNED_BYTE, image);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        stbi_image_free(image);

        return texID;
    }

    public void renderText(String text, float x, float y, float size, Color color) {
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0, 800, 600, 0, -1, 1); // Replace 800x600 with your actual window size

        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        glDisable(GL_DEPTH_TEST); // Draw on top of 3D

        glTranslatef(x, y, 0);   // Move to position
        glScalef(size, size, 1); // Scale text

        int numQuads = stb_easy_font_print(0, 0, text, null, charBuffer);

        glColor3f(color.r, color.g, color.b); // Set custom color

        glBegin(GL_QUADS);
        for (int i = 0; i < numQuads * 4; i++) {
            float vx = charBuffer.getFloat(i * 16);
            float vy = charBuffer.getFloat(i * 16 + 4);
            glVertex2f(vx, vy);
        }
        glEnd();

        glEnable(GL_DEPTH_TEST); // Restore depth test

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
    }




    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        updatePerspective();
        updateCFrame();

        for (Object3D obj : objects) {
            if (!(obj instanceof Box)) continue;

            Box box = (Box) obj;
            Vector3[] corners = box.getCorners();

            Color c = box.color;
            int texId = box.getTextureId();

            // Load texture if not loaded yet
            if (texId == 0 && box.getImg() != null) {
                texId = loadTexture(box.getImg());
                box.setTextureId(texId);
            }

            if (texId > 0) {
                glEnable(GL_TEXTURE_2D);
                glBindTexture(GL_TEXTURE_2D, texId);

                // Enable blending for transparency
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

                glColor4f(1f, 1f, 1f, 1f); // White with full opacity
            } else {
                glDisable(GL_TEXTURE_2D);
                glDisable(GL_BLEND);
                glColor3f(c.r, c.g, c.b);
            }

            glBegin(GL_QUADS);
            drawFaceTextured(corners[0], corners[1], corners[5], corners[4]);
            drawFaceTextured(corners[3], corners[2], corners[6], corners[7]);
            drawFaceTextured(corners[4], corners[5], corners[6], corners[7]);
            drawFaceTextured(corners[0], corners[1], corners[2], corners[3]);
            drawFaceTextured(corners[0], corners[3], corners[7], corners[4]);
            drawFaceTextured(corners[1], corners[2], corners[6], corners[5]);
            glEnd();

            // Disable texturing and blending after rendering each box
            glDisable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
        }
    }


    private void drawFaceTextured(Vector3 a, Vector3 b, Vector3 c, Vector3 d) {
        glTexCoord2f(0f, 1f); glVertex3f(a.x, a.y, a.z);
        glTexCoord2f(1f, 1f); glVertex3f(b.x, b.y, b.z);
        glTexCoord2f(1f, 0f); glVertex3f(c.x, c.y, c.z);
        glTexCoord2f(0f, 0f); glVertex3f(d.x, d.y, d.z);
    }

    private void drawFace(Vector3 a, Vector3 b, Vector3 c, Vector3 d) {
        glVertex3f(a.x, a.y, a.z);
        glVertex3f(b.x, b.y, b.z);
        glVertex3f(c.x, c.y, c.z);
        glVertex3f(d.x, d.y, d.z);
    }

    public Camera getCamera() {
        return camera;
    }
}
