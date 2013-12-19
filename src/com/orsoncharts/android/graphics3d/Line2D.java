/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
     * @param pt1  the starting point (<code>null</code> not permitted).
     * @param pt2  the ending point (<code>null</code> not permitted).
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
     * @param pt  the point (<code>null</code> not permitted).
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
