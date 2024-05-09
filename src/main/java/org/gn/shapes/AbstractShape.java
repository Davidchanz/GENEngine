package org.gn.shapes;

import org.gn.utils.Color;
import org.gn.GENMath.matrix.Matrix4f;
import org.gn.GENMath.matrix.TransponeMatrix;
import org.gn.GENMath.vector.Vector2;
import org.gn.GENMath.vector.Vector3;
import org.gn.Light;
import org.gn.Scene;

import java.util.ArrayList;

/**Abstract class for Shapes on scene*/
public abstract class AbstractShape implements ShapeMethods{
    public int id;
    public Vector3 position;
    public Vector3 center;
    public ArrayList<Vector3> vertices;
    public Vector3 normal;

    public Color color;
    public boolean CENTER;
    public boolean colored;
    public Vector3 rotation;
    public ShapesObject parent;
    protected AbstractShape(Color c){
        this.rotation = new Vector3();
        this.vertices = new ArrayList<>();
        if(c != null) {this.color = c; this.colored = true;}
        else this.colored = false;
    }

    private float[][] get2DimMatrix(Matrix4f in){
        float[][] transform2d = new float[4][4];

        float[] transform1d = in.getAll();
        int row = 0;
        int col = 0;
        for(int k = 0; k < transform1d.length; k++){
            transform2d[row][col] = transform1d[k];
            if(++col >= 4) {
                row++;
                col = 0;
            }
        }
        return transform2d;
    }

    /**Get list of vertices in screen coord in camera projection after transformation*/
    @Override
    public ArrayList<Vector3> getVertices(ArrayList<Vector3> vertices) {
        ArrayList<Vector3> dots = new ArrayList<>();
        for (var i : vertices){
            Vector3 screen_coord = new Vector3( (i.x),  (i.y),  (i.z));
            parent.drawingObject.toSceneCoord(screen_coord);
            Vector3 newPoint = new Vector3(screen_coord);
            Vector3 sceneCenter = new Vector3(0, 0, 0);
            parent.drawingObject.toSceneCoord(sceneCenter);

            Vector3 newPos = new Vector3(position);
            parent.drawingObject.toSceneCoord(newPos);

            Vector3 newCamPos = new Vector3(Scene.camera.position);
            parent.drawingObject.toSceneCoord(newCamPos);

            Matrix4f matrix4f = new Matrix4f();

            var center = matrix4f.transform(sceneCenter.mul(-1), Vector3.add(rotation, parent.rotation), new Vector3(1f,1f,1f));
            var model = matrix4f.transform(position, new Vector3(0,0,0), new Vector3(1f,1f,1f));
            var view = matrix4f.view(Scene.camera.position, Scene.camera.rotation);
            var projection = Scene.projection;

            float[][] newDot = new float[4][1];
            newDot[0][0] = newPoint.x;
            newDot[1][0] = newPoint.y;
            newDot[2][0] = newPoint.z;
            newDot[3][0] = 1;

            TransponeMatrix transponeMatrix = new TransponeMatrix();

            newDot = transponeMatrix.MatrixMultiplication( get2DimMatrix(center), newDot, 4, 1, 4);
            newDot = transponeMatrix.MatrixMultiplication( get2DimMatrix(model), newDot, 4, 1, 4);
            newDot = transponeMatrix.MatrixMultiplication( get2DimMatrix(view), newDot, 4, 1, 4);
            newDot = transponeMatrix.MatrixMultiplication( get2DimMatrix(projection), newDot, 4, 1, 4);

            Vector3 point = new Vector3();
            point.x = (newDot[0][0]);
            point.y = (newDot[1][0]);
            point.z = (newDot[2][0]);

            Vector2 proection = ScreenProjection(point);
            if(point == null)
                return null;
            if(proection == null || proection.x < 0 || proection.x >= parent.drawingObject.getWidth() || proection.y < 0 || proection.y >= parent.drawingObject.getHeight()) return null;
            else dots.add(new Vector3(proection.x, proection.y, point.z));

        }
        return dots;
    }

    public Vector2 ScreenProjection(Vector3 v) {
        var cameraFront = new Vector3(0.0F, 0.0F, 300.0F + Scene.camera.position.z);
        var ObserveRange = 1.5707964F;
        var Scale = (float)parent.drawingObject.getWidth() / (float)((double)(2.0F * cameraFront.z) * Math.tan((double)(ObserveRange / 2.0F)));
        var ScreenDist = 0.1f;

        Vector3 local = new Vector3(v.x - Scene.camera.position.x, v.y - Scene.camera.position.y, v.z + Scene.camera.position.z);
        if (local.z < ScreenDist)
        {
            return null;
        }
        float delta =  cameraFront.z / local.z * Scale;
        Vector2 projection = (new Vector2(local.x, local.y)).mul(delta);
        Vector2 screen = (new Vector2((float)(parent.drawingObject.getWidth() / 2), (float)(-parent.drawingObject.getHeight() / 2))).add(projection);
        Vector2 screenCoords = new Vector2(screen.x, -screen.y);
        return screenCoords;

        /*var local = new Vector3(v.x - Scene.camera.position.x, v.y - Scene.camera.position.y, v.z + Scene.camera.position.z);
        //игнорируем точки сзади камеры
        if (local.z < ScreenDist)
        {
            return new Vector2();
        }
        //через подобные треугольники находим проекцию и умножаем ее на коэффициент растяжения
        var delta = ScreenDist / local.z * Scale;
        var projection = new Vector2(local.x, local.y).mul(delta);
        //этот код нужен для перевода проекции в экранные координаты
        var screen = new Vector2(Scene.WIDTH  / 2.0f, -Scene.HEIGHT / 2.0f).add(projection);
        var screenCoords = new Vector2(screen.x, -screen.y);
        //если точка принадлежит экранной области - вернем ее
        if (screenCoords.x >= 0 && screenCoords.x < Scene.WIDTH && screenCoords.y >= 0 && screenCoords.y < Scene.HEIGHT)
        {
            return screenCoords;
        }
        return new Vector2();*/
    }

    /**Interpolete to points*///todo explore this function
    public ArrayList<Integer> interpolate(float i0, float d0, float i1, float d1) {
        var values = new ArrayList<Integer>();
        if (i0 == i1) {
            values.add((int)d0);
            return values;
        }
        float a = (d1 - d0) / (i1 - i0);
        float d = d0;
        for (int i = (int)i0; i <= (int)i1; ++i) {
            values.add((int)d);
            d = d + a;
        }
        return values;
    }

    /**Draw filled triangle*///todo explore
    public void drawFilledTriangle(Vector3 v0, Vector3 v1, Vector3 v2) {
        // Сортировка точек так, что y0 <= y1 <= y2
        if (v0.y > v1.y) {
            var tmp = v0;
            v0 = v1;
            v1 = tmp;
        }
        if (v0.y > v2.y) {
            var tmp = v0;
            v0 = v2;
            v2 = tmp;
        }
        if (v1.y > v2.y) {
            var tmp = v1;
            v1 = v2;
            v2 = tmp;
        }

        // Вычисление координат x и значений z для рёбер треугольника
        var x01 = interpolate(v0.y, v0.x, v1.y, v1.x);
        var z01 = interpolate(v0.y, v0.z, v1.y, v1.z);

        var x12 = interpolate(v1.y, v1.x, v2.y, v2.x);
        var z12 = interpolate(v1.y, v1.z, v2.y, v2.z);

        var x02 = interpolate(v0.y, v0.x, v2.y, v2.x);
        var z02 = interpolate(v0.y, v0.z, v2.y, v2.z);

        //# Конкатенация коротких сторон
        x01.remove(x01.size() - 1);
        x01.addAll(x12);
        var x012 = x01;

        z01.remove(z01.size() - 1);
        z01.addAll(z12);
        var z012 = z01;

        //# Определяем, какая из сторон левая и правая
        ArrayList<Integer> x_left;
        ArrayList<Integer> x_right;
        ArrayList<Integer> z_left;
        ArrayList<Integer> z_right;
        var m = x012.size() / 2;
        if (x02.get(m) < x012.get(m)) {
            x_left = x02;
            x_right = x012;

            z_left = z02;
            z_right = z012;
        } else {
            x_left = x012;
            x_right = x02;

            z_left = z012;
            z_right = z02;
        }

        //# Отрисовка горизонтальных отрезков
        for (int y = (int) v0.y; y <= v2.y; ++y) {
            var x_l = x_left.get(y - (int) v0.y);
            var x_r = x_right.get(y - (int) v0.y);

            var z_segment = interpolate(x_l, z_left.get(y - (int) v0.y), x_r, z_right.get(y - (int) v0.y));
            for (int x = x_l; x <= x_r; ++x) {
                var z = z_segment.get(x - x_l);
                if(x < 0 || y < 0 || x >= Scene.ZBUFFER.length || y >= Scene.ZBUFFER[0].length)
                    return;
                if (z < Scene.ZBUFFER[x][y]) {
                    double shadawdiff = Math.cos(shadow(new Vector3(x, y, z)));
                    double DiffuseCoef = 0.1f;
                    double Intensivity = 10f;
                    var diffuseVal = Math.max(shadawdiff, 0) * Intensivity;
                    Color c = new Color(
                            (float) Math.min(1.0f, this.color.getRed() * diffuseVal * DiffuseCoef),
                            (float) Math.min(1.0f, this.color.getGreen() * diffuseVal * DiffuseCoef),
                            (float) Math.min(1.0f, this.color.getBlue() * diffuseVal * DiffuseCoef));
                    synchronized (parent.drawingObject) {
                        Scene.ZBUFFER[x][y] = z;
                        parent.drawingObject.draw(x, y, c);

                        Scene.OBUFFER[x][y] = this;
                    }
                }
            }
        }
    }

    /**Draw line using intorpolation*///todo exlpore this function
    public void brezenheim(Vector2 v1, Vector2 v2) {
        float dx = v2.x - v1.x;
        float dy = v2.y - v1.y;
        if (Math.abs(dx) > Math.abs(dy)) {
            if (v1.x > v2.x) {
                var tmp = v1;
                v1 = v2;
                v2 = tmp;
            }
            var ys = interpolate(v1.x, v1.y, v2.x, v2.y);

            for (int scanlineY = (int) v1.x; scanlineY <= v2.x; scanlineY++) {
                this.parent.drawingObject.draw(scanlineY, ys.get(scanlineY - (int)v1.x), Color.BLACK);
            }
        } else{
            if (v1.y > v2.y) {
                var tmp = v1;
                v1 = v2;
                v2 = tmp;
            }
            var xs = interpolate(v1.y, v1.x, v2.y, v2.x);

            synchronized (this.parent.drawingObject) {
                for (int scanlineY = (int) v1.y; scanlineY <= v2.y; scanlineY++) {
                    this.parent.drawingObject.draw(xs.get(scanlineY - (int) v1.y), scanlineY, Color.BLACK);
                }
            }
        }
    }

    /**Draw 3Dline*///todo explore
    public void brezenheim(Vector3 v1, Vector3 v2) {
        ArrayList<Vector3> ListOfPoints = new ArrayList<>();
        ListOfPoints.add(new Vector3((int)v1.x, (int)v1.y, (int)v1.z));
        int dx = (int)Math.abs(v2.x - v1.x);
        int dy = (int)Math.abs(v2.y - v1.y);
        int dz = (int)Math.abs(v2.z - v1.z);
        int xs;
        int ys;
        int zs;
        if (v2.x > v1.x) {
            xs = 1;
        } else {
            xs = -1;
        }
        if (v2.y > v1.y) {
            ys = 1;
        } else {
            ys = -1;
        }
        if (v2.z > v1.z) {
            zs = 1;
        } else {
            zs = -1;
        }

        //Driving axis is X -axis "
        if (dx >= dy && dx >= dz) {
            int p1 = 2 * dy - dx;
            int p2 = 2 * dz - dx;
            while (v1.x != v2.x) {
                v1.x += xs;
                if (p1 >= 0) {
                    v1.y += ys;
                    p1 -= 2 * dx;
                }
                if (p2 >= 0) {
                    v1.z += zs;
                    p2 -= 2 * dx;
                }
                p1 += 2 * dy;
                p2 += 2 * dz;
                ListOfPoints.add(new Vector3((int)v1.x, (int)v1.y, (int)v1.z));
            }
        }

        //Driving axis is Y -axis "
        else if (dy >= dx && dy >= dz) {
            int p1 = 2 * dx - dy;
            int p2 = 2 * dz - dy;
            while (v1.y != v2.y) {
                v1.y += ys;
                if (p1 >= 0) {
                    v1.x += xs;
                    p1 -= 2 * dy;
                }
                if (p2 >= 0) {
                    v1.z += zs;
                    p2 -= 2 * dy;
                }
                p1 += 2 * dx;
                p2 += 2 * dz;
                ListOfPoints.add(new Vector3((int)v1.x, (int)v1.y, (int)v1.z));
            }
        }
        //Driving axis is Z -axis "
        else {
            int p1 = 2 * dy - dz;
            int p2 = 2 * dx - dz;
            while (v1.z != v2.z) {
                v1.z += zs;
                if (p1 >= 0) {
                    v1.y += ys;
                    p1 -= 2 * dz;
                }
                if (p2 >= 0) {
                    v1.x += xs;
                    p2 -= 2 * dz;
                }
                p1 += 2 * dy;
                p2 += 2 * dx;
                ListOfPoints.add(new Vector3((int)v1.x, (int)v1.y, (int)v1.z));
            }
        }
        ListOfPoints.parallelStream().forEach(it -> {
            if (it.z < Scene.ZBUFFER[(int)it.x][(int)it.y]) {
                Scene.ZBUFFER[(int)it.x][(int)it.y] = (int)it.z;
                this.parent.drawingObject.draw((int)it.x, (int)it.y, Color.BLACK);
            }

        });
    }

    @Override
    public boolean isVisible() {
        if(1==1)
            return true;

        Vector3 newPoint = new Vector3(normal.x, normal.y, normal.z);

        Matrix4f matrix4f = new Matrix4f();
        var center = matrix4f.transform(new Vector3(0, 0, 0), Vector3.add(this.rotation, this.parent.rotation), new Vector3(1f, 1f, 1f));

        float[][] newDot = new float[4][1];
        newDot[0][0] = newPoint.x;
        newDot[1][0] = newPoint.y;
        newDot[2][0] = newPoint.z;
        newDot[3][0] = 1;

        TransponeMatrix transponeMatrix = new TransponeMatrix();

        newDot = transponeMatrix.MatrixMultiplication(get2DimMatrix(center), newDot, 4, 1, 4);

        Vector3 norm = new Vector3();
        norm.x = (newDot[0][0]);
        norm.y = (newDot[1][0]);
        norm.z = (newDot[2][0]);

        Vector3 gPos = Scene.camera.getForward();

        var cos = norm.dot(gPos) / (norm.len() * gPos.len());

        return cos > 0;
    }

    private double shadow(Vector3 dot) {
        if(1==0)
            return 1;

        Vector3 newPoint = new Vector3(normal.x, normal.y, normal.z);

        Matrix4f matrix4f = new Matrix4f();

        var center = matrix4f.transform(new Vector3(0,0,0), Vector3.add(this.rotation, this.parent.rotation), new Vector3(1f, 1f, 1f));

        float[][] newDot = new float[4][1];
        newDot[0][0] = newPoint.x;
        newDot[1][0] = newPoint.y;
        newDot[2][0] = newPoint.z;
        newDot[3][0] = 1;

        TransponeMatrix transponeMatrix = new TransponeMatrix();

        newDot = transponeMatrix.MatrixMultiplication(get2DimMatrix(center), newDot, 4, 1, 4);

        Vector3 norm = new Vector3();
        norm.x = (newDot[0][0]);
        norm.y = (newDot[1][0]);
        norm.z = (newDot[2][0]);

        double res = 1;

        for (Light light : Scene.lights) {
            Vector3 l = new Vector3(light.getPosition());
            parent.drawingObject.toSceneCoord(l);
            res = (dot.sub(l).nor().dot(norm));
        }

        return res;
    }

    public void draw(){
        if (parent.drawingObject != null && this.isVisible())
            this.paint();
    }
}
