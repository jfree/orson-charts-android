/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.graphics3d;

/**
 * The dimensions for a 2D shape.  Instances of this class are immutable.
 */
public class Dimension2D {

    /** The width. */
    private float width;
    
    /** The height. */
    private float height;
    
    /**
     * Creates a new instance.
     * 
     * @param width  the width.
     * @param height  the height.
     */
    public Dimension2D(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Returns the width.
     * 
     * @return The width.
     */
    public float getWidth() {
        return this.width;
    }
    
    /**
     * Returns the height.
     * 
     * @return The height.
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * Returns a string representation of this dimension, primarily for
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return "Dimension2D(" + this.width + ", " + this.height + ")";
    }

}
