package org.gn;

import org.gn.GENMath.matrix.Matrix4f;
import org.gn.GENMath.vector.Vector3;
import org.gn.buffer.IntMatrixImageBuffer;
import org.gn.canvas.BufferAWTImageDrawingObject;
import org.gn.canvas.BufferIntMatrixDrawingObject;
import org.gn.canvas.DrawingObject;
import org.gn.canvas.Functional;
import org.gn.output.Window;
import org.gn.shapes.AbstractShape;
import org.gn.shapes.ShapesObject;
import org.gn.time.RenderTimer;
import org.gn.time.Timer;
import org.gn.time.TimerEvent;
import org.gn.utils.Color;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class Scene {
    public final int WIDTH;
    public final int HEIGHT;
    public static int[][] ZBUFFER;
    public static AbstractShape[][] OBUFFER;
    public static Camera camera;
    private boolean Vaxis = false;
    private boolean Vcenter = false;
    public ArrayList<ShapesObject> objects = new ArrayList<>();
    public static Matrix4f projection;
    public static final List<Light> lights = new ArrayList<>();
    public final int threadNum;
    public static final AtomicBoolean bodyChanged = new AtomicBoolean(true);
    public final Thread[] threadPool;
    public final DrawingObject<?> drawingObject;
    private volatile int fps;
    private final Window window;
    private JPanel panel;
    private Consumer<TimerEvent> action;

    private RenderTimer renderTimer;

    private Timer gameTimer;

    public Scene(int w, int h, Color color, DrawingObject<?> drawingObject, Window window) {
        WIDTH = w;
        HEIGHT = h;

        this.window = window;

        threadNum = Runtime.getRuntime().availableProcessors()*2;
        System.out.println(threadNum);
        threadPool = new Thread[threadNum+1];

        Light light = new Light(new Vector3(100,100,-100));
        this.addLight(light);

        drawingObject.ini(WIDTH, HEIGHT, color);

        this.drawingObject = drawingObject;

        ZBUFFER = new int[WIDTH][HEIGHT];
        for (int[] ints : ZBUFFER) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        OBUFFER = new AbstractShape[WIDTH][HEIGHT];
        for (AbstractShape[] ints : OBUFFER) {
            Arrays.fill(ints, null);
        }

        Matrix4f matrix4f = new Matrix4f();

        camera = new Camera(this, new Vector3(0, 0, 1000), new Vector3(0, 0, 0));
        projection = matrix4f.projection(70.0f, (float) WIDTH / (float) HEIGHT, 1.0f, 500.0f);
    }
    public void addLight(Light light){
        lights.add(light);
    }

    public void add(ShapesObject o){
        o.drawingObject = this.drawingObject;
        this.objects.add(o);
        bodyChanged.set(true);
    }
    public void addAll(Collection<ShapesObject> o){
        for (ShapesObject shapesObject : o) {
            this.add(shapesObject);
        }
    }

    /**Draw*/
    public void draw() {
        drawingObject.clear();
        camera.update();
        for (int[] ints : ZBUFFER) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        for (AbstractShape[] ints : OBUFFER) {
            Arrays.fill(ints, null);
        }

        //TODO now works only for 1 ShapesObject becouse of method iniThreadPool()
//        this.objects.parallelStream().forEach(shapesObject -> {
//            shapesObject.draw();//TODO for old one thread version
//        });

        if(!this.objects.isEmpty()) {
            this.objects.forEach(shapesObject -> {
                iniThreadPool(shapesObject);
                for (int i = 0; i < threadPool.length; i++) {
                    if(threadPool[i] != null)
                        threadPool[i].start();
                }
                for (int i = 0; i < threadPool.length; i++) {
                    try {
                        if(threadPool[i] != null)
                            threadPool[i].join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    private void iniThreadPool(ShapesObject object){
        for (int i = 0; i < threadPool.length; i++) {
            if(threadPool[i] != null)
                threadPool[i].interrupt();
        }
        object.refreshBuffers();
        AbstractShape[] array = object.body.toArray(new AbstractShape[0]);
        int size = array.length;
        int poligonsPerThread = size / threadNum;
        if(poligonsPerThread == 0 || poligonsPerThread == size){
            threadPool[0] = new Thread(() -> {
                for (int j = 0; j < array.length-1; j++) {
                    array[j].draw();
                }
            });
        }else {
            int threadI = 0;
            for (int i = 0; i < threadNum; i++){
                int fromIndex = i * poligonsPerThread;
                Thread thread = new Thread(() -> {
                    for (int j = fromIndex; j < fromIndex + poligonsPerThread; j++) {
                        array[j].draw();
                    }
                });
                threadPool[threadI++] = thread;
            }
            int fromIndex = (threadNum-1) * poligonsPerThread;
            threadPool[threadI] = new Thread(() -> {
                for (int j = fromIndex; j < array.length-1; j++) {
                    array[j].draw();
                }
            });
        }
        bodyChanged.set(false);
    }

    /**Show XOY coord axis*/
    public void setCoordVisible(boolean b){
        this.Vaxis = b;
    }

    /**Show XOY center objects*/
    public void setCenterVisible(boolean b){
        this.Vcenter = b;
    }

    public void setCameraObject(ShapesObject cameraObject) {
        camera = new Camera(this, new Vector3(cameraObject.position.x, cameraObject.position.y, cameraObject.position.z + 100), new Vector3(0, 0, 0));
    }

    public void repaint() {
        drawingObject.paint(this::draw);
    }

    public  <R> R getImage() {
        return (R) drawingObject.get();//TODO
    }

    public void show() {
        AtomicInteger frameCount = new AtomicInteger(0);

        gameTimer.start();

        this.renderTimer = new RenderTimer(timerEvent -> {
            this.repaint();
            renderFrame();
            frameCount.getAndIncrement();
        }, timerEvent -> {
            fps = frameCount.get();
            window.setFPS(fps);
            frameCount.set(0);
        });
        this.renderTimer.start();
    }

    private void renderFrame(){
        if(drawingObject instanceof BufferAWTImageDrawingObject)
            panel.repaint();
        else if(drawingObject instanceof BufferIntMatrixDrawingObject) {
            IntMatrixImageBuffer pixelsBuffer = this.getImage();
            System.out.println(pixelsBuffer);
        }
    }

    public int getFPS(){
        return fps;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void setAction(long duration, Consumer<TimerEvent> action) {
        this.action = action;
        this.gameTimer = new Timer(duration, timerEvent -> {
            this.action.accept(timerEvent);
        });
    }
}