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

/**
 * A point in 2D space.  Instances of this class are immutable.
 */
public class Point2D {
    
    /** The x-coordinate. */
    private float x;
    
    /** The y-coordinate. */
    private float y;
    
    /**
     * Creates a new point instance.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    public Point2D(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns the x-coordinate.
     * 
     * @return The x-coordinate.
     */
    public float getX() {
        return this.x;
    }
    
    /**
     * Returns the y-coordinate.
     * 
     * @return The y-coordinate.
     */
    public float getY() {
        return this.y;
    }

    /**
     * Calculates and returns the distance between this point and the supplied
     * point.
     * 
     * @param p  the point ({@code null} not permitted).
     * 
     * @return The distance.
     */
    public float distance(Point2D p) {
        double dx = p.x - this.x;
        double dy = p.y - this.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
