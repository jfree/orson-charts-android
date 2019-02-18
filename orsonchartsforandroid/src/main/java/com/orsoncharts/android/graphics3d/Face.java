/* ========================
 * Orson Charts for Android
 * ========================
 *
 * (C)opyright 2013-2019, by Object Refinery Limited.  All rights reserved.
 *
 * https://github.com/jfree/orson-charts-android
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts for Android home page:
 *
 * https://www.object-refinery.com/orsoncharts/android/index.html
 *
 */

package com.orsoncharts.android.graphics3d;

import com.orsoncharts.android.util.ArgChecks;

/**
 * Represents a face in one {@link Object3D}, defined in terms of vertex
 * indices.
 */
public class Face {

    /** The offset into the global list of vertices. */
    private int offset;

    /** The indices of the vertices representing this face. */
    private int[] vertices;

    /** The color of the face. */
    private int color;
    
    /** 
     * A flag that controls whether or not an outline will be drawn for the 
     * face.  For pie charts and area charts, setting this to <code>true</code>
     * helps to remove gaps in the rendering, which results in better looking
     * charts.
     */
    private boolean outline;

    /**
     * Creates a new face.
     *
     * @param vertices  the indices of the vertices.
     * @param color  the face color.
     * @param outline  outline?
     */
    public Face(int[] vertices, int color, boolean outline) {
        if (vertices.length < 3) {
            throw new IllegalArgumentException("Faces must have at least 3 vertices.");
        }
        ArgChecks.nullNotPermitted(color, "color");
        this.vertices = vertices;
        this.offset = 0;
        this.color = color;
        this.outline = outline;
    }

    /**
     * Returns the offset to add to the vertex indices.
     *
     * @return The offset.
     */
    public int getOffset() {
        return this.offset;
    }

    /**
     * Sets the offset to add to the vertex indices.
     *
     * @param offset  the offset.
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Returns the face color.  When rendering, a different shade will be used
     * to simulate lighting.
     *  
     * @return The color.
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Returns the number of vertices in this face.
     *
     * @return The number of vertices in this face.
     */
    public int getVertexCount() {
        return this.vertices.length;
    }

    /**
     * Returns the index for the specified vertex.
     *
     * @param i  the vertex index.
     *
     * @return  The index.
     */
    public int getVertexIndex(int i) {
        return this.vertices[i] + this.offset;
    }

    /**
     * Returns the flag that controls whether or not the face should be 
     * drawn as well as filled when rendered.
     * 
     * @return A boolean. 
     */
    public boolean getOutline() {
        return this.outline;
    }
    
    /**
     * Calculates the normal vector for this face.
     *
     * @param points  the vertices of the object that this face belongs to
     *     (these can be in world or eye coordinates).
     *
     * @return The normal vector.
     */
    public double[] calculateNormal(Point3D[] points) {
        int iA = this.vertices[0] + this.offset;
        int iB = this.vertices[1] + this.offset;
        int iC = this.vertices[2] + this.offset;
        double aX = points[iA].x;
        double aY = points[iA].y;
        double aZ = points[iA].z;
        double bX = points[iB].x;
        double bY = points[iB].y;
        double bZ = points[iB].z;
        double cX = points[iC].x;
        double cY = points[iC].y;
        double cZ = points[iC].z;
        double u1 = bX - aX, u2 = bY - aY, u3 = bZ - aZ;
        double v1 = cX - aX, v2 = cY - aY, v3 = cZ - aZ;
        double a = u2 * v3 - u3 * v2,
               b = u3 * v1 - u1 * v3,
               c = u1 * v2 - u2 * v1,
               len = Math.sqrt(a * a + b * b + c * c);
               a /= len; b /= len; c /= len;
        return new double[] {a, b, c};
    }

    /**
     * Returns the average z-value.
     *
     * @param points  the points.
     *
     * @return The average z-value.
     */
    public float calculateAverageZValue(Point3D[] points) {
        float total = 0.0f;
        for (int i = 0; i < this.vertices.length; i++) {
            total = total + (float)points[this.vertices[i] + this.offset].z;
        }
        return total / this.vertices.length;
    }

    /**
     * Returns <code>true</code> if this face is front facing, and 
     * <code>false</code> otherwise.
     * 
     * @param projPts  the projection points.
     * 
     * @return A boolean. 
     */
    public boolean isFrontFacing(Point2D[] projPts) {
        return Utils2D.area2(projPts[getVertexIndex(0)], 
                projPts[getVertexIndex(1)], projPts[getVertexIndex(2)]) > 0;  
    }

    /**
     * Returns a string representation of this instance, primarily for
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i < this.vertices.length; i++) {
            result = result + this.vertices[i];
            if (i < this.vertices.length - 1) {
                result = result + ", ";
            }
        }
        return result + "]";
    }
}
