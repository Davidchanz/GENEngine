package org.gn;

import org.gn.GENMath.vector.Vector3;
import org.gn.output.Window;
import org.gn.shapes.Cube;
import org.gn.shapes.ShapesObject;
import org.gn.shapes.Torus;


public class RunCube extends Window {

    public RunCube(int w, int h) {
        super(w, h);

        Scene scene = this.getScene();

        ShapesObject cube = new Cube("Cube", 1);
        scene.add(cube);
        scene.setCameraObject(scene.objects.get(0));

        scene.setAction(10L, timerEvent -> {
            float speed = 1;
            cube.rotate(new Vector3(speed, speed, speed));
        });

        scene.show();
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");

        if (args.length == 2) {
            int w = Integer.parseInt(args[0]);
            int h = Integer.parseInt(args[1]);

            new RunCube(w, h);
        } else {
            new RunCube(800, 800);
        }
    }
}
