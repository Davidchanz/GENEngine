package org.gn.shapes;

import org.gn.GENMath.vector.Vector3;

import java.util.ArrayList;

/**Interface for Sahpes on scene*/
public interface ShapeMethods {
    void fill(ArrayList<Vector3> dots);
    void border(ArrayList<Vector3> dots);
    void paint();
    ArrayList<Vector3> getVertices(ArrayList<Vector3> vertices);
    boolean isVisible();
}
