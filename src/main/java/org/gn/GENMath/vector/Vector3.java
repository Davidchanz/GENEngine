package org.gn.GENMath.vector;

import java.io.Serializable;

public class Vector3 implements Serializable, Vector<Vector3> {
    private static final long serialVersionUID = 3840054589595372522L;

    /** the x-component of this vector **/
    public float x=0;
    /** the y-component of this vector **/
    public float y=0;
    /** the z-component of this vector **/
    public float z=0;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public final static Vector3 X = new Vector3(1, 0, 0);
    public final static Vector3 Y = new Vector3(0, 1, 0);
    public final static Vector3 Z = new Vector3(0, 0, 1);
    public final static Vector3 Zero = new Vector3(0, 0, 0);

    /** Constructs a vector at (0,0,0) */
    public Vector3 () {
    }

    /** Creates a vector with the given components
     * @param x The x-component
     * @param y The y-component
     * @param z The z-component */
    public Vector3 (float x, float y, float z) {
        this.set(x, y, z);
    }
    public Vector3 mul(float val) {
        float x = this.x * val;
        float y = this.y * val;
        float z = this.z * val;
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /** Creates a vector from the given vector
     * @param vector The vector */
    public Vector3 (final Vector3 vector) {
        this.set(vector);
    }

    /** Creates a vector from the given array. The array must have at least 3 elements.
     *
     * @param values The array */
    public Vector3 (final float[] values) {
        this.set(values[0], values[1], values[2]);
    }

    /** Creates a vector from the given vector and z-component
     *
     * @param vector The vector
     * @param z The z-component */
    public Vector3 (final Vector2 vector, float z) {
        this.set(vector.x, vector.y, z);
    }

    /** Sets the vector to the given components
     *
     * @param x The x-component
     * @param y The y-component
     * @param z The z-component
     * @return this vector for chaining */
    public Vector3 set (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Vector3 set (final Vector3 vector) {
        return this.set(vector.x, vector.y, vector.z);
    }

    /** Sets the components from the array. The array must have at least 3 elements
     *
     * @param values The array
     * @return this vector for chaining */
    public Vector3 set (final float[] values) {
        return this.set(values[0], values[1], values[2]);
    }

    /** Sets the components of the given vector and z-component
     *
     * @param vector The vector
     * @param z The z-component
     * @return This vector for chaining */
    public Vector3 set (final Vector2 vector, float z) {
        return this.set(vector.x, vector.y, z);
    }

    /** Sets the components from the given spherical coordinate
     * @param azimuthalAngle The angle between x-axis in radians [0, 2pi]
     * @param polarAngle The angle between z-axis in radians [0, pi]
     * @return This vector for chaining */
    public Vector3 setFromSpherical (float azimuthalAngle, float polarAngle) {
        float cosPolar = MathUtils.cos(polarAngle);
        float sinPolar = MathUtils.sin(polarAngle);

        float cosAzim = MathUtils.cos(azimuthalAngle);
        float sinAzim = MathUtils.sin(azimuthalAngle);

        return this.set(cosAzim * sinPolar, sinAzim * sinPolar, cosPolar);
    }

    @Override
    public Vector3 setToRandomDirection () {
        float u = MathUtils.random();
        float v = MathUtils.random();

        float theta = MathUtils.PI2 * u; // azimuthal angle
        float phi = (float)Math.acos(2f * v - 1f); // polar angle

        return this.setFromSpherical(theta, phi);
    }

    @Override
    public Vector3 cpy () {
        return new Vector3(this);
    }

    public synchronized static Vector3 add(Vector3 v1, Vector3 v2){
        return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    @Override
    public Vector3 add (final Vector3 vector) {
        return this.add(vector.x, vector.y, vector.z);
    }

    /** Adds the given vector to this component
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @param z The z-component of the other vector
     * @return This vector for chaining. */
    public Vector3 add (float x, float y, float z) {
        return this.set(this.x + x, this.y + y, this.z + z);
    }

    /** Adds the given value to all three components of the vector.
     *
     * @param values The value
     * @return This vector for chaining */
    public Vector3 add (float values) {
        return this.set(this.x + values, this.y + values, this.z + values);
    }

    @Override
    public Vector3 sub (final Vector3 a_vec) {
        Vector3 vector3 = new Vector3(this);
        return vector3.sub(a_vec.x, a_vec.y, a_vec.z);
    }

    /** Subtracts the other vector from this vector.
     *
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @param z The z-component of the other vector
     * @return This vector for chaining */
    public Vector3 sub (float x, float y, float z) {
        return this.set(this.x - x, this.y - y, this.z - z);
    }

    /** Subtracts the given value from all components of this vector
     *
     * @param value The value
     * @return This vector for chaining */
    public Vector3 sub (float value) {
        return this.set(this.x - value, this.y - value, this.z - value);
    }

    @Override
    public Vector3 scl (float scalar) {
        return this.set(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    @Override
    public Vector3 scl (final Vector3 other) {
        return this.set(x * other.x, y * other.y, z * other.z);
    }

    /** Scales this vector by the given values
     * @param vx X value
     * @param vy Y value
     * @param vz Z value
     * @return This vector for chaining */
    public Vector3 scl (float vx, float vy, float vz) {
        return this.set(this.x * vx, this.y * vy, this.z * vz);
    }

    @Override
    public Vector3 mulAdd (Vector3 vec, float scalar) {
        this.x += vec.x * scalar;
        this.y += vec.y * scalar;
        this.z += vec.z * scalar;
        return this;
    }

    @Override
    public Vector3 mulAdd (Vector3 vec, Vector3 mulVec) {
        this.x += vec.x * mulVec.x;
        this.y += vec.y * mulVec.y;
        this.z += vec.z * mulVec.z;
        return this;
    }

    /** @return The euclidean length */
    public static float len (final float x, final float y, final float z) {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public float len () {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    /** @return The squared euclidean length */
    public static float len2 (final float x, final float y, final float z) {
        return x * x + y * y + z * z;
    }

    @Override
    public float len2 () {
        return x * x + y * y + z * z;
    }

    /** @param vector The other vector
     * @return Whether this and the other vector are equal */
    public boolean idt (final Vector3 vector) {
        return x == vector.x && y == vector.y && z == vector.z;
    }

    /** @return The euclidean distance between the two specified vectors */
    public static float dst (final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        final float a = x2 - x1;
        final float b = y2 - y1;
        final float c = z2 - z1;
        return (float)Math.sqrt(a * a + b * b + c * c);
    }

    @Override
    public float dst (final Vector3 vector) {
        final float a = vector.x - x;
        final float b = vector.y - y;
        final float c = vector.z - z;
        return (float)Math.sqrt(a * a + b * b + c * c);
    }

    /** @return the distance between this point and the given point */
    public float dst (float x, float y, float z) {
        final float a = x - this.x;
        final float b = y - this.y;
        final float c = z - this.z;
        return (float)Math.sqrt(a * a + b * b + c * c);
    }

    /** @return the squared distance between the given points */
    public static float dst2 (final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        final float a = x2 - x1;
        final float b = y2 - y1;
        final float c = z2 - z1;
        return a * a + b * b + c * c;
    }

    @Override
    public float dst2 (Vector3 point) {
        final float a = point.x - x;
        final float b = point.y - y;
        final float c = point.z - z;
        return a * a + b * b + c * c;
    }

    /** Returns the squared distance between this point and the given point
     * @param x The x-component of the other point
     * @param y The y-component of the other point
     * @param z The z-component of the other point
     * @return The squared distance */
    public float dst2 (float x, float y, float z) {
        final float a = x - this.x;
        final float b = y - this.y;
        final float c = z - this.z;
        return a * a + b * b + c * c;
    }

    @Override
    public Vector3 nor () {
        Vector3 vector3 = new Vector3(this);
        final float len2 = vector3.len2();
        if (len2 == 0f || len2 == 1f) return new Vector3(this);
        return vector3.scl(1f / (float)Math.sqrt(len2));
    }

    /** @return The dot product between the two vectors */
    public static float dot (float x1, float y1, float z1, float x2, float y2, float z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    @Override
    public float dot (final Vector3 vector) {
        return x * vector.x + y * vector.y + z * vector.z;
    }

    /** Returns the dot product between this and the given vector.
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @param z The z-component of the other vector
     * @return The dot product */
    public float dot (float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    /** Sets this vector to the cross product between it and the other vector.
     * @param vector The other vector
     * @return This vector for chaining */
    public Vector3 crs (final Vector3 vector) {
        return this.set(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x);
    }

    /** Sets this vector to the cross product between it and the other vector.
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @param z The z-component of the other vector
     * @return This vector for chaining */
    public Vector3 crs (float x, float y, float z) {
        return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    @Override
    public boolean isUnit () {
        return isUnit(0.000000001f);
    }

    @Override
    public boolean isUnit (final float margin) {
        return Math.abs(len2() - 1f) < margin;
    }

    @Override
    public boolean isZero () {
        return x == 0 && y == 0 && z == 0;
    }

    @Override
    public boolean isZero (final float margin) {
        return len2() < margin;
    }

    @Override
    public boolean isOnLine (Vector3 other, float epsilon) {
        return len2(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x) <= epsilon;
    }

    @Override
    public boolean isOnLine (Vector3 other) {
        return len2(y * other.z - z * other.y, z * other.x - x * other.z,
                x * other.y - y * other.x) <= MathUtils.FLOAT_ROUNDING_ERROR;
    }

    @Override
    public boolean isCollinear (Vector3 other, float epsilon) {
        return isOnLine(other, epsilon) && hasSameDirection(other);
    }

    @Override
    public boolean isCollinear (Vector3 other) {
        return isOnLine(other) && hasSameDirection(other);
    }

    @Override
    public boolean isCollinearOpposite (Vector3 other, float epsilon) {
        return isOnLine(other, epsilon) && hasOppositeDirection(other);
    }

    @Override
    public boolean isCollinearOpposite (Vector3 other) {
        return isOnLine(other) && hasOppositeDirection(other);
    }

    @Override
    public boolean isPerpendicular (Vector3 vector) {
        return MathUtils.isZero(dot(vector));
    }

    @Override
    public boolean isPerpendicular (Vector3 vector, float epsilon) {
        return MathUtils.isZero(dot(vector), epsilon);
    }

    @Override
    public boolean hasSameDirection (Vector3 vector) {
        return dot(vector) > 0;
    }

    @Override
    public boolean hasOppositeDirection (Vector3 vector) {
        return dot(vector) < 0;
    }

    @Override
    public Vector3 lerp (final Vector3 target, float alpha) {
        x += alpha * (target.x - x);
        y += alpha * (target.y - y);
        z += alpha * (target.z - z);
        return this;
    }

    @Override
    public Vector3 interpolate (Vector3 target, float alpha, Interpolation interpolator) {
        return lerp(target, interpolator.apply(0f, 1f, alpha));
    }

    /** Spherically interpolates between this vector and the target vector by alpha which is in the range [0,1]. The result is
     * stored in this vector.
     *
     * @param target The target vector
     * @param alpha The interpolation coefficient
     * @return This vector for chaining. */
    public Vector3 slerp (final Vector3 target, float alpha) {
        final float dot = dot(target);
        // If the inputs are too close for comfort, simply linearly interpolate.
        if (dot > 0.9995 || dot < -0.9995) return lerp(target, alpha);

        // theta0 = angle between input vectors
        final float theta0 = (float)Math.acos(dot);
        // theta = angle between this vector and result
        final float theta = theta0 * alpha;

        final float st = (float)Math.sin(theta);
        final float tx = target.x - x * dot;
        final float ty = target.y - y * dot;
        final float tz = target.z - z * dot;
        final float l2 = tx * tx + ty * ty + tz * tz;
        final float dl = st * ((l2 < 0.0001f) ? 1f : 1f / (float)Math.sqrt(l2));

        return scl((float)Math.cos(theta)).add(tx * dl, ty * dl, tz * dl).nor();
    }

    /** Converts this {@code Vector3} to a string in the format {@code (x,y,z)}.
     * @return a string representation of this object. */
    @Override
    public String toString () {
        return "(" + x + "," + y + "," + z + ")";
    }

    /** Sets this {@code Vector3} to the value represented by the specified string according to the format of {@link #toString()}.
     * @param v the string.
     * @return this vector for chaining */
    public Vector3 fromString (String v) {
        int s0 = v.indexOf(',', 1);
        int s1 = v.indexOf(',', s0 + 1);
        if (s0 != -1 && s1 != -1 && v.charAt(0) == '(' && v.charAt(v.length() - 1) == ')') {
            try {
                float x = Float.parseFloat(v.substring(1, s0));
                float y = Float.parseFloat(v.substring(s0 + 1, s1));
                float z = Float.parseFloat(v.substring(s1 + 1, v.length() - 1));
                return this.set(x, y, z);
            } catch (NumberFormatException ex) {
                // Throw a GdxRuntimeException
            }
        }
        throw new GdxRuntimeException("Malformed Vector3: " + v);
    }

    @Override
    public Vector3 limit (float limit) {
        return limit2(limit * limit);
    }

    @Override
    public Vector3 limit2 (float limit2) {
        float len2 = len2();
        if (len2 > limit2) {
            scl((float)Math.sqrt(limit2 / len2));
        }
        return this;
    }

    @Override
    public Vector3 setLength (float len) {
        return setLength2(len * len);
    }

    @Override
    public Vector3 setLength2 (float len2) {
        float oldLen2 = len2();
        return (oldLen2 == 0 || oldLen2 == len2) ? this : scl((float)Math.sqrt(len2 / oldLen2));
    }

    @Override
    public Vector3 clamp (float min, float max) {
        final float len2 = len2();
        if (len2 == 0f) return this;
        float max2 = max * max;
        if (len2 > max2) return scl((float)Math.sqrt(max2 / len2));
        float min2 = min * min;
        if (len2 < min2) return scl((float)Math.sqrt(min2 / len2));
        return this;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + NumberUtils.floatToIntBits(x);
        result = prime * result + NumberUtils.floatToIntBits(y);
        result = prime * result + NumberUtils.floatToIntBits(z);
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector3 other = (Vector3)obj;
        if (NumberUtils.floatToIntBits(x) != NumberUtils.floatToIntBits(other.x)) return false;
        if (NumberUtils.floatToIntBits(y) != NumberUtils.floatToIntBits(other.y)) return false;
        if (NumberUtils.floatToIntBits(z) != NumberUtils.floatToIntBits(other.z)) return false;
        return true;
    }

    @Override
    public boolean epsilonEquals (final Vector3 other, float epsilon) {
        if (other == null) return false;
        if (Math.abs(other.x - x) > epsilon) return false;
        if (Math.abs(other.y - y) > epsilon) return false;
        if (Math.abs(other.z - z) > epsilon) return false;
        return true;
    }

    /** Compares this vector with the other vector, using the supplied epsilon for fuzzy equality testing.
     * @return whether the vectors are the same. */
    public boolean epsilonEquals (float x, float y, float z, float epsilon) {
        if (Math.abs(x - this.x) > epsilon) return false;
        if (Math.abs(y - this.y) > epsilon) return false;
        if (Math.abs(z - this.z) > epsilon) return false;
        return true;
    }

    /** Compares this vector with the other vector using MathUtils.FLOAT_ROUNDING_ERROR for fuzzy equality testing
     *
     * @param other other vector to compare
     * @return true if vector are equal, otherwise false */
    public boolean epsilonEquals (final Vector3 other) {
        return epsilonEquals(other, MathUtils.FLOAT_ROUNDING_ERROR);
    }

    /** Compares this vector with the other vector using MathUtils.FLOAT_ROUNDING_ERROR for fuzzy equality testing
     *
     * @param x x component of the other vector to compare
     * @param y y component of the other vector to compare
     * @param z z component of the other vector to compare
     * @return true if vector are equal, otherwise false */
    public boolean epsilonEquals (float x, float y, float z) {
        return epsilonEquals(x, y, z, MathUtils.FLOAT_ROUNDING_ERROR);
    }

    @Override
    public Vector3 setZero () {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        return this;
    }
}


/*
public class Vector3 {//todo
    public float X, Y, Z;//todo
    Vector3(int x, int y, int z){//todo

    }
    Vector3(float x, float y, float z){//todo

    }
    static Vector3 cross(Vector3 v1, Vector3 v2){//todo
        Vector3 returnValue = new Vector3(0,0,0);
        returnValue.X = v1.Y * v2.Z - v1.Z * v2.Y;
        returnValue.Y = v1.Z * v2.X - v1.X * v2.Z;
        returnValue.Z = v1.X * v2.Y - v1.Y * v2.X;
        return returnValue;
    }
    static double dot(Vector3 v1, Vector3 v2){//todo
        return 1.0;
    }
    static Vector3 normalize(Vector3 v){//todo
        return new Vector3(1,1,1);
    }
    static Vector3 minus(Vector3 v1, Vector3 v2){//todo
        return new Vector3(1,1,1);
    }
    float length(){//todo
        return 1.0f;
    }
}
*/
