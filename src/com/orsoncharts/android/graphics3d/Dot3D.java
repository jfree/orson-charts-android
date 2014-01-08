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
 * A 3D object that is simply a dot (single vertex).
 */
public class Dot3D extends Object3D {
        
    /** The color. */
    private int color;
    
    /**
     * Creates a new <code>Dot3D</code> instance.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param z  the z-coordinate.
     * @param color  the color.
     */
    public Dot3D(float x, float y, float z, int color) {
        addVertex(new Point3D(x, y, z));
        this.color = color;
    }
    
    /**
     * Returns the color that was specified via the constructor.
     * 
     * @return The color. 
     */
    public int getColor() {
        return this.color;
    }
    
}
