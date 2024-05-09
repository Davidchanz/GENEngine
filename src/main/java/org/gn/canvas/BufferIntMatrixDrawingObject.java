package org.gn.canvas;

import org.gn.utils.Color;
import org.gn.buffer.IntMatrixImageBuffer;

import java.util.Arrays;

public class BufferIntMatrixDrawingObject implements DrawingObject<IntMatrixImageBuffer>{
    private static int[] BACKGROUND_COLOR_ARRAY;
    private IntMatrixImageBuffer pixelBuffer;
    private int width, height;
    private Color backgroundColor;
    @Override
    public IntMatrixImageBuffer get() {
        return pixelBuffer;
    }

    @Override
    public void draw(int x, int y, Color color) {
        this.pixelBuffer.setARGB(x, y, color);
    }

    @Override
    public void clear() {
        setPixels(BACKGROUND_COLOR_ARRAY);
    }

    @Override
    public void ini(int width, int height, Color backgroundColor) {
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        BACKGROUND_COLOR_ARRAY = new int[width * height];
        Arrays.fill(BACKGROUND_COLOR_ARRAY, this.backgroundColor.toARGB());
        this.pixelBuffer = new IntMatrixImageBuffer(width, height);
        setPixels(BACKGROUND_COLOR_ARRAY);
    }

    @Override
    public void resize(int width, int height) {
        this.pixelBuffer = new IntMatrixImageBuffer(width, height);
    }

    @Override
    public void paint(Functional function) {
        function.action();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setPixels(int[] rawPixels) {
        System.arraycopy(rawPixels, 0, this.pixelBuffer.getBuffer(), 0, rawPixels.length);
    }
}
