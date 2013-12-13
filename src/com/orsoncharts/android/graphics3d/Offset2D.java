/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.graphics3d;

import java.io.Serializable;

/**
 * An offset <code>(dx, dy)</code> in two dimensional space.  Instances of 
 * this class are immutable.
 */
public final class Offset2D implements Serializable {
    
    /** Zero offset. */
    public static final Offset2D ZERO_OFFSET = new Offset2D(0, 0);
    
    /** The x-offset. */
    private float dx;
    
    /** The y-offset. */
    private float dy;
    
    /**
     * Default constructor (<code>(0, 0)</code>).
     */
    public Offset2D() {
        this(0.0f, 0.0f);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param dx  the x-offset.
     * @param dy  the y-offset.
     */
    public Offset2D(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }
    
    /**
     * Returns the x-offset.
     * 
     * @return The x-offset. 
     */
    public float getDX() {
        return this.dx;
    }
    
    /**
     * Returns the y-offset.
     * 
     * @return The y-offset. 
     */
    public float getDY() {
        return this.dy;
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Offset2D)) {
            return false;
        }
        Offset2D that = (Offset2D) obj;
        if (this.dx != that.dx) {
            return false;
        }
        if (this.dy != that.dy) {
            return false;
        }
        return true;
    }

}
