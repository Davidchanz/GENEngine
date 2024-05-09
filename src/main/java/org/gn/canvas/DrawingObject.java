package org.gn.canvas;

import org.gn.GENMath.vector.Vector3;
import org.gn.utils.Color;

public interface DrawingObject<R> {
    R get();
    void draw(int x, int y, Color color);
    void clear();
    void ini(int width, int height, Color backgroundColor);
    void resize(int width, int height);
    void paint(Functional function);

    int getWidth();
    int getHeight();

    /**Offset form XOY coords to Screen coords ([0,0] = [WIDTH/2, HEIGHT/2])*/
    default void toSceneCoord(Vector3 point){
        point.x = point.x + getWidth()/2f;
        point.y = -(point.y - getHeight()/2f);
    }

    default void fromSceneCoord(Vector3 point){
        point.x = point.x - getWidth()/2f;
        point.y = getHeight()/2f - point.y;
    }
}
