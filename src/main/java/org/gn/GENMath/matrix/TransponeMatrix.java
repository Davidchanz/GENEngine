package org.gn.GENMath.matrix;

import org.gn.GENMath.vector.Vector3;

public class TransponeMatrix {
    public static final float[][] Matrix_Offset = new float[4][4];

    static {
    Matrix_Offset[0][0] = 1; Matrix_Offset[0][1] = 0; Matrix_Offset[0][2] = 0; Matrix_Offset[0][3] = 0;
    Matrix_Offset[1][0] = 0; Matrix_Offset[1][1] = 1; Matrix_Offset[1][2] = 0; Matrix_Offset[1][3] = 0;
    Matrix_Offset[2][0] = 0; Matrix_Offset[2][1] = 0; Matrix_Offset[2][2] = 1; Matrix_Offset[2][3] = 0;
    Matrix_Offset[3][0] = 0; Matrix_Offset[3][1] = 0; Matrix_Offset[3][2] = 0; Matrix_Offset[3][3] = 1;}

    public static final float[][] Matrix_Scaling = new float[4][4];

    static {
    Matrix_Scaling[0][0] = 1; Matrix_Scaling[0][1] = 0; Matrix_Scaling[0][2] = 0; Matrix_Scaling[0][3] = 0;
    Matrix_Scaling[1][0] = 0; Matrix_Scaling[1][1] = 1; Matrix_Scaling[1][2] = 0; Matrix_Scaling[1][3] = 0;
    Matrix_Scaling[2][0] = 0; Matrix_Scaling[2][1] = 0; Matrix_Scaling[2][2] = 1; Matrix_Scaling[2][3] = 0;
    Matrix_Scaling[3][0] = 0; Matrix_Scaling[3][1] = 0; Matrix_Scaling[3][2] = 0; Matrix_Scaling[3][3] = 1;}

    public static final float[][] Matrix_ReflectionOX = new float[4][4];

    static {
    Matrix_ReflectionOX[0][0] = -1; Matrix_ReflectionOX[0][1] = 0; Matrix_ReflectionOX[0][2] = 0; Matrix_ReflectionOX[0][3] = 0;
    Matrix_ReflectionOX[1][0] = 0; Matrix_ReflectionOX[1][1] = 1; Matrix_ReflectionOX[1][2] = 0; Matrix_ReflectionOX[1][3] = 0;
    Matrix_ReflectionOX[2][0] = 0; Matrix_ReflectionOX[2][1] = 0; Matrix_ReflectionOX[2][2] = 1; Matrix_ReflectionOX[2][3] = 0;
    Matrix_ReflectionOX[3][0] = 0; Matrix_ReflectionOX[3][1] = 0; Matrix_ReflectionOX[3][2] = 1; Matrix_ReflectionOX[3][3] = 1;}

    public static final float[][] Matrix_ReflectionOY = new float[4][4];

    static {
    Matrix_ReflectionOY[0][0] = 1; Matrix_ReflectionOY[0][1] = 0; Matrix_ReflectionOY[0][2] = 0; Matrix_ReflectionOY[0][3] = 0;
    Matrix_ReflectionOY[1][0] = 0; Matrix_ReflectionOY[1][1] = -1; Matrix_ReflectionOY[1][2] = 0; Matrix_ReflectionOY[1][3] = 0;
    Matrix_ReflectionOY[2][0] = 0; Matrix_ReflectionOY[2][1] = 0; Matrix_ReflectionOY[2][2] = 1; Matrix_ReflectionOY[2][3] = 0;
    Matrix_ReflectionOY[3][0] = 0; Matrix_ReflectionOY[3][1] = 0; Matrix_ReflectionOY[3][2] = 0; Matrix_ReflectionOY[3][3] = 1;}

    public static final float[][] Matrix_ReflectionXY = new float[4][4];

    static {
    Matrix_ReflectionXY[0][0] = 1; Matrix_ReflectionXY[0][1] = 0; Matrix_ReflectionXY[0][2] = 0; Matrix_ReflectionXY[0][3] = 0;
    Matrix_ReflectionXY[1][0] = 0; Matrix_ReflectionXY[1][1] = 1; Matrix_ReflectionXY[1][2] = 0; Matrix_ReflectionXY[1][3] = 0;
    Matrix_ReflectionXY[2][0] = 0; Matrix_ReflectionXY[2][1] = 0; Matrix_ReflectionXY[2][2] = -1; Matrix_ReflectionXY[2][3] = 0;
    Matrix_ReflectionXY[3][0] = 0; Matrix_ReflectionXY[3][1] = 0; Matrix_ReflectionXY[3][2] = 0; Matrix_ReflectionXY[3][3] = 1;}

    public static final float[][] Matrix_RotationZ = new float[4][4];

    static {
    Matrix_RotationZ[0][0] = (float)Math.cos(Math.PI / 2); Matrix_RotationZ[0][1] = (float)Math.sin(Math.PI / 2); Matrix_RotationZ[0][2] = 0; Matrix_RotationZ[0][3] = 0;
    Matrix_RotationZ[1][0] = (float)-Math.sin(Math.PI / 2); Matrix_RotationZ[1][1] = (float)Math.cos(Math.PI / 2); Matrix_RotationZ[1][2] = 0; Matrix_RotationZ[1][3] = 0;
    Matrix_RotationZ[2][0] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) + 50 * Math.sin(Math.PI / 2)); Matrix_RotationZ[2][1] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) - 50 * Math.sin(Math.PI / 2)); Matrix_RotationZ[2][2] = 1; Matrix_RotationZ[2][3] = 0;
    Matrix_RotationZ[3][0] = (float)0.0; Matrix_RotationZ[3][1] = 0; Matrix_RotationZ[3][2] = 0; Matrix_RotationZ[3][3] = 1;}
    public static final float[][] Matrix_RotationY = new float[4][4];

    static {
    Matrix_RotationY[0][0] = (float)Math.cos(Math.PI / 2); Matrix_RotationY[0][1] = (float)(Math.sin(Math.PI / 2)); Matrix_RotationY[0][2] = 0;
    Matrix_RotationY[1][0] = (float)-Math.sin(Math.PI / 2); Matrix_RotationY[1][1] = (float)(Math.cos(Math.PI / 2)); Matrix_RotationY[1][2] = 0;
    Matrix_RotationY[2][0] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) + 50 * Math.sin(Math.PI / 2)); Matrix_RotationY[2][1] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) - 50 * Math.sin(Math.PI / 2)); Matrix_RotationY[2][2] = 1;}

    public static final float[][] Matrix_RotationX = new float[4][4];

    static {
    Matrix_RotationX[0][0] = (float)Math.cos(Math.PI / 2); Matrix_RotationX[0][1] = (float)Math.sin(Math.PI / 2); Matrix_RotationX[0][2] = 0;
    Matrix_RotationX[1][0] = (float)-Math.sin(Math.PI / 2); Matrix_RotationX[1][1] = (float)Math.cos(Math.PI / 2); Matrix_RotationX[1][2] = 0;
    Matrix_RotationX[2][0] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) + 50 * Math.sin(Math.PI / 2)); Matrix_RotationY[2][1] = (float)(-50 * (Math.cos(Math.PI / 2) - 1) - 50 * Math.sin(Math.PI / 2)); Matrix_RotationX[2][2] = 1;}

    public float[][] MatrixMultiplication(float[][] matrixA, float[][] matrixB, int a, int b, int c) {
        float[][] matrixC = new float[a][b];

        for (int i = 0; i < a; i++)
        {
            for (int j = 0; j < b; j++)
            {
                matrixC[i][j] = 0;

                for (int k = 0; k < c; k++)
                {
                    matrixC[i][j] += (matrixA[i][k] * matrixB[k][j]);
                }
            }
        }

        return matrixC;
    }

    public Vector3 RotationX(int ct, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        double angel = 0.0;

        angel = (-ct * Math.PI) / 180.0;

        Matrix_RotationX[0][0] = 1; Matrix_RotationX[0][1] = 0; Matrix_RotationX[0][2] = 0; Matrix_RotationX[0][3] = 0;
        Matrix_RotationX[1][0] = 0; Matrix_RotationX[1][1] = (float)Math.cos(angel); Matrix_RotationX[1][2] = (float)-Math.sin(angel); Matrix_RotationX[1][3] = 0;
        Matrix_RotationX[2][0] = 0; Matrix_RotationX[2][1] = (float)Math.sin(angel); Matrix_RotationX[2][2] = (float)Math.cos(angel); Matrix_RotationX[2][3] = 0;
        Matrix_RotationX[3][0] = 0; Matrix_RotationX[3][1] = 0; Matrix_RotationX[3][2] = 0; Matrix_RotationX[3][3] = 1;

        newDot = MatrixMultiplication(newDot, Matrix_RotationX, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }
    public Vector3 RotationY(int ct, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        double angel = 0.0;

        angel = (-ct * Math.PI) / 180.0;

        Matrix_RotationY[0][0] = (float)Math.cos(angel); Matrix_RotationY[0][1] = 0; Matrix_RotationY[0][2] = (float)Math.sin(angel); Matrix_RotationY[0][3] = 0;
        Matrix_RotationY[1][0] = 0; Matrix_RotationY[1][1] = 1; Matrix_RotationY[1][2] = 0; Matrix_RotationY[1][3] = 0;
        Matrix_RotationY[2][0] = (float)-Math.sin(angel); Matrix_RotationY[2][1] = 0; Matrix_RotationY[2][2] = (float)Math.cos(angel); Matrix_RotationY[2][3] = 0;
        Matrix_RotationY[3][0] = 0; Matrix_RotationY[3][1] = 0; Matrix_RotationY[3][2] = 0; Matrix_RotationY[3][3] = 1;

        newDot = MatrixMultiplication(newDot, Matrix_RotationY, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }
    public Vector3 RotationZ(int ct, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        double angel = 0.0;

        angel = (-ct * Math.PI) / 180.0;

        Matrix_RotationZ[0][0] = (float)Math.cos(angel); Matrix_RotationZ[0][1] = (float)Math.sin(angel); Matrix_RotationZ[0][2] = 0; Matrix_RotationZ[0][3] = 0;
        Matrix_RotationZ[1][0] = (float)-Math.sin(angel); Matrix_RotationZ[1][1] = (float)Math.cos(angel); Matrix_RotationZ[1][2] = 0; Matrix_RotationZ[1][3] = 0;
        Matrix_RotationZ[2][0] = 0; Matrix_RotationZ[2][1] = 0; Matrix_RotationZ[2][2] = 1; Matrix_RotationZ[2][3] = 0;
        Matrix_RotationZ[3][0] = 0; Matrix_RotationZ[3][1] = 0; Matrix_RotationZ[3][2] = 0; Matrix_RotationZ[3][3] = 1;

        newDot = MatrixMultiplication(newDot, Matrix_RotationZ, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }
    public Vector3 Scaling(float ck, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        Matrix_Scaling[0][0] = ck;
        Matrix_Scaling[1][1] = ck;
        Matrix_Scaling[2][2] = ck;

        newDot = MatrixMultiplication(newDot, Matrix_Scaling, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }
    public Vector3 Offset(float cx, float cy, float cz, Vector3 point) {
        float[][] newDot = new float[1][4];
        newDot[0][0] = point.x;
        newDot[0][1] = point.y;
        newDot[0][2] = point.z;
        newDot[0][3] = 1;

        Matrix_Offset[3][0] = 1 * cx;
        Matrix_Offset[3][1] = 1 * cy;
        Matrix_Offset[3][2] = -1 * cz;

        newDot = MatrixMultiplication(newDot, Matrix_Offset, 1, 4, 4);

        point.x = (int)(newDot[0][0]);
        point.y = (int)(newDot[0][1]);
        point.z = (int)(newDot[0][2]);
        return point;
    }
}
