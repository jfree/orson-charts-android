/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.graphics3d;

import com.orsoncharts.android.util.ArgChecks;

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
