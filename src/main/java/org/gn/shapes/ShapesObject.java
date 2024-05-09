package org.gn.shapes;

import org.gn.utils.Color;
import org.gn.GENMath.vector.Vector3;
import org.gn.Main;
import org.gn.Scene;
import org.gn.canvas.DrawingObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

//todo

/**Class for scene object which especially is a list of AbstractShape elements*/
public class ShapesObject {
    public final ArrayList<AbstractShape> body;
    public String name;
    public int id;
    public Vector3 rotation;
    public Vector3 center;
    public Vector3 position;
    public DrawingObject<?> drawingObject;
    protected String model;

    private Vector3 rotationBuffer = new Vector3();
    public ShapesObject(){
        this("Template", 0);
    }

    public ShapesObject(String name, int id, String model){
        this(name, id);
        this.model = model + ".obj";
        this.loadShape();
    }

    public ShapesObject(String name, int id){
        this.id = id;
        this.name = name;
        this.body = new ArrayList<>();
        this.center = new Vector3(0,0, 0);
        this.position = new Vector3(0,0, 0);
        this.rotation = Vector3.Zero;
    }

    public ShapesObject(String name, int id, Vector3 pos){
        this(name, id);
        this.position = new Vector3(pos);
    }
    public void move(Vector3 dir){
        this.position.add(dir);
        this.center.add(dir);
        for(var i: this.body){
            i.position.add(dir);
        }
    }

    public void setCenterVisible(boolean b){
        for(var shape: this.body) {
            shape.CENTER = b;
        }
    }

    /**Add new element on object*/
    public void add(AbstractShape o){
        o.id = this.id;
        this.body.add(o);

        computeCenter();

        Scene.bodyChanged.set(true);
    }

    private void computeCenter() {
        float sumX=0;
        float sumY=0;
        float sumZ=0;
        for(var i: this.body){
            sumX+= i.center.x + i.position. x;
            sumY+= i.center.y + i.position. y;
            sumZ+= i.center.z + i.position. z;
        }
        this.center = new Vector3(sumX/this.body.size(), sumY/this.body.size(), sumZ/this.body.size());//todo compute
        this.center.add(this.position);

        //this.position.add(o.position);//ini position
        for(var i: this.body){
            i.parent = this;//ini shape parent
            i.center = new Vector3(center);//ini shape parent
        }
    }

    /**Add new element collection on object*/
    public void addAll(Collection<AbstractShape> o) {
        for (var i : o) {
            add(i);
        }
    }

    public void refreshBuffers(){
        this.rotation.set(this.rotationBuffer);
    }

    public void draw() {
        refreshBuffers();
        this.body.sort((o1, o2) -> Float.compare(o2.position.z, o1.position.z));

        this.body.parallelStream().forEach(abstractShape -> {
            if (drawingObject != null && abstractShape.isVisible())
                abstractShape.paint();
        });
    }

    /**load object from .obj file*/
    public void loadShape() {
        InputStream fr = (Main.class.getClassLoader().getResourceAsStream("shapes/" + model));
        Scanner sc = new Scanner(fr);
        String buff = "";

        ArrayList<Vector3> vertex = new ArrayList<>();
        ArrayList<Vector3> normal = new ArrayList<>();
        ArrayList<Face[]> Faces = new ArrayList<>();
        while(sc.hasNext()){
            buff = sc.nextLine();
            if (buff.startsWith("v") && !buff.startsWith("vt") && !buff.startsWith("vn")) {
                //read vertices
                Scanner buffSc = new Scanner(buff);
                buffSc.useDelimiter(" ");
                buffSc.next();
                double X = buffSc.nextDouble();
                double Y = buffSc.nextDouble();
                double Z = buffSc.nextDouble();
                Vector3 newPoint = new Vector3((int)X,(int)Y,(int)Z);
                vertex.add(newPoint);
            } else if (buff.startsWith("vn") && !buff.startsWith("vt")) {
                //read normal
                Scanner buffSc = new Scanner(buff);
                buffSc.useDelimiter(" ");
                buffSc.next();
                double X = buffSc.nextDouble();
                double Y = buffSc.nextDouble();
                double Z = buffSc.nextDouble();
                Vector3 newNormal = new Vector3((float) X,(float)Y,(float)Z);
                normal.add(newNormal);
            } else if (buff.startsWith("vt")) {

            } else if(buff.startsWith("f")){
                //read face
                Scanner buffSc = new Scanner(buff);
                buffSc.useDelimiter(" ");
                buffSc.next();
                Face[] face = new Face[4];
                String f = "";
                for(int i = 0; i < 4; ++i) {
                    if(buffSc.hasNext()){
                        f = buffSc.next();
                        Scanner fSc = new Scanner(f);
                        fSc.useDelimiter("/");
                        int vertId = fSc.nextInt();
                        fSc.nextInt();
                        int normId = fSc.nextInt();
                        face[i] = new Face(vertId - 1, -1, normId - 1);
                    }
                    else {
                        Scanner fSc = new Scanner(f);
                        fSc.useDelimiter("/");
                        int vertId = fSc.nextInt();
                        fSc.nextInt();
                        int normId = fSc.nextInt();
                        face[i] = new Face(vertId - 1, -1, normId - 1);
                    }
                }
                Faces.add(face);
            }
        }
        int q = 0;
        Color c = Color.MAGENTA;
        String s = "MAGENTA";
        for(var face: Faces){
            //set plane from face
            Vector3 d1 = vertex.get(face[0].vertex);
            Vector3 d2 = vertex.get(face[1].vertex);
            Vector3 d3 = vertex.get(face[2].vertex);
            Vector3 d4 = vertex.get(face[3].vertex);
            Vector3 norm = normal.get(face[0].normal);
            if(q == 0) {
                c = Color.BLUE;
                s = "BLUE";
            }
            if(q == 1) {
                c = Color.RED;
                s = "RED";
            }
            if(q == 2) {
                s = "GREEN";
                c = Color.GREEN;
            }
            if(q == 3) {
                s = "YELLOW";
                c = Color.CYAN;
            }
            if(q == 4) {
                s = "WHITE";
                c = Color.WHITE;
            }
            if(q == 5) {
                s = "ORANGE";
                c = Color.ORANGE;
            }
            var r = new Rectangle(d1, d2, d3, d4, new Vector3(0,0,0), norm, c);
            this.add(r);
            q++;
        }
    }

    public void setRotation(Vector3 rotation){
        this.rotation.x = rotation.x;
        this.rotation.y = rotation.y;
        this.rotation.z = rotation.z;
    }

    public void rotate(Vector3 rotation){
        this.rotationBuffer.x += rotation.x;
        this.rotationBuffer.y += rotation.y;
        this.rotationBuffer.z += rotation.z;
    }

}
