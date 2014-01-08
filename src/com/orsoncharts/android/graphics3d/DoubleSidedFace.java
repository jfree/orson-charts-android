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
 * A double-sided face.  This is identical to a normal face except that during
 * rendering these faces will be drawn no matter which side they are viewed
 * from.
 * 
 * @since 1.1
 */
public class DoubleSidedFace extends Face {
 
    /**
     * Creates a new double-sided face.
     * 
     * @param vertices  the vertices.
     * @param color  the color.
     * @param outline  draw the outline?
     */
    public DoubleSidedFace(int[] vertices, int color, boolean outline) {
        super(vertices, color, outline); 
    }
}
