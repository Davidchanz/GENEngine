package org.gn.shapes;

public class Torus extends ShapesObject{

    public Torus(String name, int id){
        super(name, id);
        this.model = "torus.obj";
        this.loadShape();
    }
}
