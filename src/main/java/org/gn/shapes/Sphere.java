package org.gn.shapes;

public class Sphere extends ShapesObject{

    public Sphere(String name, int id){
        super(name, id);
        this.model = "sphere.obj";
        this.loadShape();
    }
}
