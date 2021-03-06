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

import java.util.Collection;
import java.util.List;

import com.orsoncharts.android.util.ArgChecks;

/**
 * A world is a model containing a collection of objects in 3D space and a 
 * direction vector for the sunlight.  A viewing point ({@link ViewPoint3D}) is 
 * specified externally.
 */
public class World {

    /** The sunlight vector. */
    private double sunX = -1 / (Math.sqrt(3));
    private double sunY = this.sunX;
    private double sunZ = -this.sunY;

    /** The objects. */
    private List<Object3D> objects;

    /**
     * Creates a new empty world.
     */
    public World() {
        this.objects = new java.util.ArrayList<Object3D>();
    }

  
    /**
     * Returns the x-component of the sunlight vector.
     *
     * @return The x-component of the sunlight vector.
     */
    public double getSunX() {
        return this.sunX;
    }

    /**
     * Returns the y-component of the sunlight vector.
     *
     * @return The y-component of the sunlight vector.
     */
    public double getSunY() {
        return this.sunY;
    }

    /**
     * Returns the z-component of the sunlight vector.
     *
     * @return The z-component of the sunlight vector.
     */
    public double getSunZ() {
        return this.sunZ;
    }
    
    /**
     * Adds an object to the world.
     *
     * @param object  the object ({@code null} not permitted).
     */
    public void add(Object3D object) {
        ArgChecks.nullNotPermitted(object, "object");
        this.objects.add(object);
    }

    /**
     * Adds a collection of objects to the world.
     * 
     * @param objects  the objects ({@code null} not permitted).
     */
    public void addAll(Collection<Object3D> objects) {
        ArgChecks.nullNotPermitted(objects, "objects");
        for (Object3D object : objects) {
            add(object);
        }
    }

    /**
     * Returns the total number of vertices for all objects in this world.
     *
     * @return The total number of vertices.
     */
    public int getVertexCount() {
        int count = 0;
        for (Object3D object: this.objects) {
            count += object.getVertexCount();
        }
        return count;
    }

    /**
     * Returns an array containing the vertices for all objects in this
     * world, transformed to eye coordinates.
     *
     * @param vp  the view point ({@code null} not permitted).
     *
     * @return The eye coordinates.
     */
    public Point3D[] calculateEyeCoordinates(ViewPoint3D vp) {
        Point3D[] result = new Point3D[getVertexCount()];
        int index = 0;
        for (Object3D object : this.objects) {
            Point3D[] vertices = object.calculateEyeCoordinates(vp);
            System.arraycopy(vertices, 0, result, index, vertices.length);
            index = index + vertices.length;
        }
        return result;
    }

    /**
     * Calculates the projected points in 2D-space for all the vertices of the
     * objects in the world.
     * 
     * @param vp  the view point ({@code null} not permitted).
     * @param d  the distance.
     * 
     * @return The projected points.
     */
    public Point2D[] calculateProjectedPoints(ViewPoint3D vp, float d) {
        Point2D[] result = new Point2D[getVertexCount()];
        int index = 0;
        for (Object3D object : this.objects) {
            Point2D[] pts = object.calculateProjectedPoints(vp, d);
            System.arraycopy(pts, 0, result, index, pts.length);
            index = index + pts.length;
        }
        return result;
    }

    /**
     * Fetches the faces for all the objects in this world, updating the
     * offset to match the current position.
     *
     * @return A list of faces.
     */
    public List<Face> getFaces() {
        List<Face> result = new java.util.ArrayList<Face>();
        int offset = 0;
        for (Object3D object : this.objects) {
            for (Face f : object.getFaces()) {
                f.setOffset(offset);
            }
            offset += object.getVertexCount();
            result.addAll(object.getFaces());
        }
        return result;
    }

}
