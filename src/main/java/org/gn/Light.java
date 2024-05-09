package org.gn;

import org.gn.GENMath.vector.Vector3;
import org.gn.utils.Color;

public class Light {
    private Vector3 position;
    private Color color;

    public Light(Vector3 position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Light(Vector3 position) {
        this.position = position;
        this.color = Color.WHITE;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
