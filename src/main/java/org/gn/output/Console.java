package org.gn.output;

import org.gn.Input;
import org.gn.Scene;
import org.gn.canvas.BufferIntMatrixDrawingObject;
import org.gn.utils.Color;

import javax.swing.*;
import java.awt.*;

public class Console {
    private int WIDTH = 50;
    private int HEIGHT = 50;
    private Scene scene;

    public Console(int w, int h){
        WIDTH = w;
        HEIGHT = h;

        JFrame frame = new JFrame("Inpute");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH, HEIGHT));

        Input input = new Input();
        frame.addKeyListener(input.getKeyboardCallback());
        frame.addMouseMotionListener(input.getMouseCallback());

        this.scene = new Scene(WIDTH, HEIGHT, Color.BLACK, new BufferIntMatrixDrawingObject(), null);

        frame.setVisible(true);

        this.scene.show();
    }

    public Scene getScene() {
        return scene;
    }
}
