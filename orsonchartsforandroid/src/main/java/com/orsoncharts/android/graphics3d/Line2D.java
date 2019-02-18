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
 * A line in two dimensions.
 */
public class Line2D {
    
    /** The x-coordinate of the starting point. */
    private float x1;
    
    /** The y-coordinate of the starting point. */
    private float y1;
    
    /** The x-coordinate of the ending point. */
    private float x2;
    
    /** The y-coordinate of the ending point. */
    private float y2;
    
    /**
     * Creates a new line defined by the two points.
     * 
     * @param pt1  the starting point ({@code null} not permitted).
     * @param pt2  the ending point ({@code null} not permitted).
     */
    public Line2D(Point2D pt1, Point2D pt2) {
        this(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
    }
    
    /**
     * Creates a new line from <code>(x1, y1)</code> to <code>(x2, y2)</code>.
     * 
     * @param x1  the x-coordinate for the starting point.
     * @param y1  the y-coordinate for the starting point.
     * @param x2  the x-coordinate for the ending point.
     * @param y2  the y coordinate for the ending point.
     */
    public Line2D(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Returns the x-coordinate of the starting point.
     * 
     * @return The x-coordinate of the starting point.
     */
    public float getX1() {
        return this.x1;
    }
    
    /**
     * Returns the y-coordinate of the starting point.
     * 
     * @return The y-coordinate of the starting point.
     */
    public float getY1() {
        return this.y1;
    }
    
    /**
     * Returns the x-coordinate of the ending point.
     * 
     * @return The x-coordinate of the ending point.
     */
    public float getX2() {
        return this.x2;
    }
    
    /**
     * Returns the y-coordinate of the ending point.
     * 
     * @return The y-coordinate of the ending point.
     */
    public float getY2() {
        return this.y2;
    }
    
    /**
     * Returns an indicator of the position of the point relative to the
     * line.
     * 
     * @param pt  the point ({@code null} not permitted).
     * 
     * @return -1, 0 or 1.
     */
    public int relativeCCW(Point2D pt) {
        return relativeCCW(pt.getX(), pt.getY());
    }
    
    /**
     * Returns an indicator of the position of the point relative to the
     * line.
     * 
     * @param px  the x-coordinate of the point.
     * @param py  the y-coordinate of the point.
     * 
     * @return -1, 0 or 1.
     */
    public int relativeCCW(float px, float py) {
        float xx2 = this.x2 - this.x1;
        float yy2 = this.y2 - this.y1;
        px -= this.x1;
        py -= this.y1;
        double ccw = px * yy2 - py * xx2;
        if (ccw == 0.0) {
            ccw = px * xx2 + py * yy2;
            if (ccw > 0.0) {
                px -= xx2;
                py -= yy2;
                ccw = px * xx2 + py * yy2;
                if (ccw < 0.0) {
                    ccw = 0.0;
                }
            }
        }
        return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }
    
}
