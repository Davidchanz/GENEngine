package org.gn.shapes;

/**Class for 3DObject*/
public class Cube extends ShapesObject{
    /**object's plane set*/
    public Cube(String name, int id){
        super(name, id);
        this.model = "cube.obj";
        this.loadShape();
    }
}
