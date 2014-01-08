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

package com.orsoncharts.android.table;

/**
 * Represents the insets for a table element.
 */
public class Insets {

    /** The left insets. */
    public float left;
    
    /** The top insets. */
    public float top;
    
    /** The right insets. */
    public float right;

    /** The bottom insets. */
    public float bottom;
    
    /**
     * Creates a new instance.
     * 
     * @param left  the left insets.
     * @param top  the top insets.
     * @param right  the right insets.
     * @param bottom  the bottom insets.
     */
    public Insets(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
}
