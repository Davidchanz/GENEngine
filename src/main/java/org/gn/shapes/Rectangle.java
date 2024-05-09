package org.gn.shapes;

import org.gn.utils.Color;
import org.gn.GENMath.vector.Vector2;
import org.gn.GENMath.vector.Vector3;

import java.util.ArrayList;

/**Class for Rectangles*/
public class Rectangle extends AbstractShape{
    public Triangle Top;
    public Triangle Bot;

    public Rectangle(int size, Vector3 pos, Color c){
        this(new Vector3(-size,-size, 0), new Vector3(size,-size, 0), new Vector3(size,size, 0), new Vector3(-size,size, 0), pos, c);
    }
    public Rectangle(int height, int width, Vector3 pos, Color c){
        this(new Vector3(-width,-height, 0), new Vector3(width,-height, 0), new Vector3(width,height, 0), new Vector3(-width,height, 0), pos, c);
    }

    public Rectangle(Vector3 P0, Vector3 P1, Vector3 P2, Vector3 P3, Vector3 pos, Color c){
        super(c);
        this.position = new Vector3(pos);
        this.Top = new Triangle(P0, P1, P2, pos, this.color);
        this.Bot = new Triangle(P0, P3, P2, pos, this.color);
        this.center = new Vector3((Top.center.x + Bot.center.x)/2,(Top.center.y + Bot.center.y)/2, (Top.center.z + Bot.center.z)/2);
        this.vertices.addAll(this.Top.vertices);
        this.vertices.add(this.Bot.vertices.get(1));
    }
    public Rectangle(Vector3 P0, Vector3 P1, Vector3 P2, Vector3 P3, Vector3 pos, Vector3 norm, Color c){
        super(c);
        this.position = new Vector3(pos);
        this.Top = new Triangle(P0, P1, P2, pos, this.color);
        this.Bot = new Triangle(P0, P3, P2, pos, this.color);
        this.center = new Vector3((Top.center.x + Bot.center.x)/2,(Top.center.y + Bot.center.y)/2, (Top.center.z + Bot.center.z)/2);
        this.vertices.addAll(this.Top.vertices);
        this.vertices.add(this.Bot.vertices.get(1));
        this.normal = new Vector3(norm);
    }

    @Override
    public void fill(ArrayList<Vector3> dots) {
        if(colored) {
            drawFilledTriangle(dots.get(0), dots.get(1), dots.get(2));
            drawFilledTriangle(dots.get(0), dots.get(3), dots.get(2));
        }
    }

    @Override
    public void border(ArrayList<Vector3> dots) {
        brezenheim(new Vector2(dots.get(0).x, dots.get(0).y), new Vector2(dots.get(1).x, dots.get(1).y));
        brezenheim(new Vector2(dots.get(1).x, dots.get(1).y), new Vector2(dots.get(2).x, dots.get(2).y));
        brezenheim(new Vector2(dots.get(2).x, dots.get(2).y), new Vector2(dots.get(3).x, dots.get(3).y));
        brezenheim(new Vector2(dots.get(3).x, dots.get(3).y), new Vector2(dots.get(0).x, dots.get(0).y));
    }

    /**Paint Rectangle*/
    @Override
    public void paint() {
        ArrayList<Vector3> dots = getVertices(this.vertices);
        if(dots == null) return;
        //border(dots);
        fill(dots);
    }
}
