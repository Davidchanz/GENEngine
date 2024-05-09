package org.gn;

import org.gn.GENMath.vector.Vector3;
import org.gn.shapes.ShapesObject;

import java.awt.event.KeyEvent;

/**Class Camera*/
public class Camera {
    public Vector3 position, rotation;
    private final float moveSpeed = 2.5f, mouseSensitivity = 0.05f;
    private float horizontalAngle = 0, verticalAngle = 0, distance = 2.0f;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;
    private Scene scene;
    public Camera(Scene scene, Vector3 position, Vector3 rotation) {
        this.scene = scene;
        this.position = position;
        this.rotation = rotation;
    }

    /**Projection object on Screen*/
    public void update(ShapesObject object) {
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();
        var tmp = new Vector3((float) newMouseX, (float)newMouseY, 0);
        scene.drawingObject.toSceneCoord(tmp);
        newMouseX = tmp.x;
        newMouseY = tmp.y;

        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        if (Input.isButtonDown(Input.lb)) {
            verticalAngle -= dy * mouseSensitivity;
            horizontalAngle += dx * mouseSensitivity;
        }
        if (Input.isButtonDown(Input.rb)) {
            if (distance > 0) {
                distance += dy * mouseSensitivity / 4;
            } else {
                distance = 0.1f;
            }
        }

        System.out.println(distance);

        float horizontalDistance = (float) (distance * Math.cos(Math.toRadians(verticalAngle)));
        float verticalDistance = (float) (distance * Math.sin(Math.toRadians(verticalAngle)));

        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));

        Vector3 newPos = new Vector3(position);
        scene.drawingObject.toSceneCoord(newPos);
        newPos = new Vector3();

        position.set(newPos.getX() + xOffset, newPos.getY() - verticalDistance, newPos.getZ() + zOffset);

        rotation.set(verticalAngle, -horizontalAngle, 0);

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public void update() {
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();
       /* var tmp = new Vector3((float) newMouseX, (float)newMouseY, 0);
        Scene.toSceneCoord(tmp);
        newMouseX = tmp.x;
        newMouseY = tmp.y;*/

        float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
        float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;

        if (Input.isKeyDown(KeyEvent.VK_A)) position.add(new Vector3(-z, 0, x));
        if (Input.isKeyDown(KeyEvent.VK_D)) position.add(new Vector3(z, 0, -x));
        if (Input.isKeyDown(KeyEvent.VK_W)) position.add(new Vector3(-x, 0, -z));
        if (Input.isKeyDown(KeyEvent.VK_S)) position.add(new Vector3(x, 0, z));
        if (Input.isKeyDown(KeyEvent.VK_Q)) position.add(new Vector3(0, moveSpeed, 0));
        if (Input.isKeyDown(KeyEvent.VK_E)) position.add(new Vector3(0, -moveSpeed, 0));

        if (Input.isKeyDown(KeyEvent.VK_LEFT)) rotation.add(new Vector3(0, -moveSpeed, 0));
        if (Input.isKeyDown(KeyEvent.VK_RIGHT)) rotation.add(new Vector3(0, moveSpeed, 0));
        if (Input.isKeyDown(KeyEvent.VK_UP)) rotation.add(new Vector3(moveSpeed, 0, 0));
        if (Input.isKeyDown(KeyEvent.VK_DOWN)) rotation.add(new Vector3(-moveSpeed, 0, 0));


        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        rotation.add(new Vector3(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public void update2() {
        newMouseX = Input.getMouseX();
        newMouseY = Input.getMouseY();
        var tmp = new Vector3((float) newMouseX, (float)newMouseY, 0);
        scene.drawingObject.toSceneCoord(tmp);
        newMouseX = tmp.x;
        newMouseY = tmp.y;

        float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
        float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;

        if (Input.isKeyDown(KeyEvent.VK_A)) position.add(new Vector3(-z, 0, x));
        if (Input.isKeyDown(KeyEvent.VK_D)) position.add(new Vector3(z, 0, -x));
        if (Input.isKeyDown(KeyEvent.VK_W)) position.add(new Vector3(-x, 0, -z));
        if (Input.isKeyDown(KeyEvent.VK_S)) position.add(new Vector3(x, 0, z));
        if (Input.isKeyDown(KeyEvent.VK_Q)) position.add(new Vector3(0, moveSpeed, 0));
        if (Input.isKeyDown(KeyEvent.VK_E)) position.add(new Vector3(0, -moveSpeed, 0));

        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        rotation.add(new Vector3(dy * mouseSensitivity, -dx * mouseSensitivity, 0));

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    //TODO
    public Vector3 getForward(){
        Vector3 x = new Vector3((float) Math.cos(rotation.x), 0, (float)Math.sin(rotation.x));
        Vector3 y = new Vector3(0, (float) Math.cos(rotation.y), (float)Math.sin(rotation.y));
        Vector3 z = new Vector3(0,  (float)Math.sin(rotation.z), (float) Math.cos(rotation.z));

        Vector3 vector3 = new Vector3(position);

        var res = vector3.sub(rotation).nor();
        return new Vector3(position);

        //return x.add(y).add(z);
    }
}
