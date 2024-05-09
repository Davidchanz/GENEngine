package org.gn.output;

import org.gn.Input;
import org.gn.Scene;
import org.gn.canvas.BufferAWTImageDrawingObject;
import org.gn.utils.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {
    private final Scene scene;
    private static int count = 0;
    private int avgFps;
    public Window(int w, int h){
        super("3DEngine 2");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("Average FPS:  " + avgFps/count);
            }
        });
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(w, h));

        Input input = new Input();
        this.addKeyListener(input.getKeyboardCallback());
        this.addMouseMotionListener(input.getMouseCallback());

        this.scene = new Scene(w, h, Color.BLACK, new BufferAWTImageDrawingObject(), this);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(scene.getImage(), 0, 0, null);
            }
        };
        panel.setSize(new Dimension(w, h));
        panel.setVisible(true);
        this.scene.setPanel(panel);

        this.add(panel);
        this.setVisible(true);
    }

    public void setFPS(int fps){
        this.setTitle("FPS: " + fps);
        count++;
        avgFps+= fps;
    }

    public Scene getScene() {
        return scene;
    }
}
