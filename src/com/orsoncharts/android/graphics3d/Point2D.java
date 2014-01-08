/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/android/index.html
 * 
 * Redistribution of this source file is prohibited.
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
     * @param p  the point (<code>null</code> not permitted).
     * 
     * @return The distance.
     */
    public float distance(Point2D p) {
        double dx = p.x - this.x;
        double dy = p.y - this.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
