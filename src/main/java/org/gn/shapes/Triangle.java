package org.gn.shapes;

import org.gn.utils.Color;
import org.gn.GENMath.vector.Vector3;

import java.util.ArrayList;

/**Class for Triangle*/
public class Triangle extends AbstractShape{
    public Vector3 P0;
    public Vector3 P1;
    public Vector3 P2;

    public Triangle(int size, Vector3 pos, Color c){
        this(new Vector3(-1,-1, 0), new Vector3(1,-1, 0), new Vector3(0,1, 0), pos, c);
    }
    public Triangle(int height, int width, Vector3 pos, Color c){
        this(new Vector3(-0.5f-width,-0.5f-height, 0), new Vector3(0.5f+width,-0.5f-height, 0), new Vector3(0.0f,0.5f+height, 0), pos, c);
    }
    public Triangle(Vector3 P0, Vector3 P1, Vector3 P2, Vector3 pos, Color c){
        super(c);
        this.P0 = new Vector3(P0);
        this.P1 = new Vector3(P1);
        this.P2 = new Vector3(P2);
        this.vertices.add(this.P0);
        this.vertices.add(this.P1);
        this.vertices.add(this.P2);
        this.center = new Vector3((this.P0.x + this.P1.x + this.P2.x)/3,(this.P0.y + this.P1.y + this.P2.y)/3, (this.P0.z + this.P1.z + this.P2.z)/3);
        this.position = new Vector3(pos);
    }

    @Override
    public void fill(ArrayList<Vector3> dots) {
        if(this.colored) {
            drawFilledTriangle(dots.get(0), dots.get(1), dots.get(2));
        }
    }

    @Override
    public void border(ArrayList<Vector3> dots) {
        brezenheim(dots.get(0), dots.get(1));
        brezenheim(dots.get(1), dots.get(2));
        brezenheim(dots.get(2), dots.get(0));
    }

    /**Paint Triangle*/
    @Override
    public void paint() {
        ArrayList<Vector3> dots = getVertices(this.vertices);
        if(dots == null) return;
        //border(dots);
        fill(dots);
    }
}
