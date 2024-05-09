package org.gn.buffer;

import org.gn.utils.Color;

public class IntMatrixImageBuffer {
    private int[] buffer;
    private int width, height;
    private final String brightness = " `.-':_,^=;><+!rc*/z?sLTv)J7(|Fi{C}fI31tlu[neoZ5Yxjya]2ESwqkP6h9d4VpOGbUAKXHm8RD#$Bg0MNWQ%&@";

    public IntMatrixImageBuffer(int width, int height) {
        this.buffer = new int[width*height];
        this.width = width;
        this.height = height;
    }

    public int[] getBuffer() {
        return buffer;
    }

    public int[][] getBlackWhite(){
        int[][] pixelsBW = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++){
                var C = new java.awt.Color(buffer[j * width + i]);
                double grayscale = 0.3 * C.getRed() + 0.59 * C.getGreen() + 0.11 * C.getBlue();
                pixelsBW[i][j] = new Color(grayscale, grayscale, grayscale).toARGB();
        }
        return pixelsBW;
    }

    public int[][] getPixels(){
        int[][] pixels = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                pixels[i][j] = buffer[j * width + i];
        return pixels;
    }

    public void setARGB(int x, int y, Color color) {
        buffer[y * width + x] = color.toARGB();
    }

    @Override
    public String toString() {
        String frame = "";
        //int[][] pixels = this.getBlackWhite();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                var color = new java.awt.Color(buffer[j * width + i], true);
                //double BW = C.getRed();
                double grayscale = ((0.2126 * color.getRed()) + (0.7152 * color.getGreen()) + (0.0722 * color.getBlue())) / 255;
                //double grayscale = (0.3 * color.getRed() + 0.59 * color.getGreen() + 0.11 * color.getBlue() + 0.2 * color.getAlpha())/255;
                char ch = lookupChar(grayscale);
                frame += ch;
                //frame += brightness.charAt(((int) (BW / (double) brightness.length())));
            }
            frame += "\n";
        }
        return frame;
    }

    char lookupChar(double greyscale) {
        // Round our greyscale value to the closest whole number
        int length = brightness.length();
        int place = (int) Math.floor(((length-1) * greyscale) + 0.5);
        return brightness.charAt(place);
    }

}
