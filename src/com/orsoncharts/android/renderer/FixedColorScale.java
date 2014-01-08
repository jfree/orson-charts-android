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

package com.orsoncharts.android.renderer;

import java.io.Serializable;

import com.orsoncharts.android.Range;
import com.orsoncharts.android.renderer.xyz.SurfaceRenderer;

/**
 * A {@link ColorScale} that returns the same color for every value on the
 * scale.  This is used by the {@link SurfaceRenderer} when there is no need 
 * to represent different value levels by color.
 * 
 * @since 1.1
 */
public class FixedColorScale implements ColorScale, Serializable {
    
    /** The fixed color. */
    private int color;
    
    /** 
     * The range (in fact this is here just to have something to return in the 
     * getRange() method, it is not used by this implementation since we 
     * always return the same color for all values).
     */
    private Range range;
    
    /**
     * Creates a new <code>FixedColorScale</code> instance.
     * 
     * @param color  the color. 
     */
    public FixedColorScale(int color) {
        this.color = color;
        this.range = new Range(0, 1);
    }

    /**
     * Returns the range <code>0.0</code> to <code>1.0</code> always.
     * 
     * @return The range (never <code>null</code>). 
     */
    @Override
    public Range getRange() {
        return this.range;
    }

    /**
     * Returns a single color (as specified in the constructor) for all values.
     * 
     * @param value  the value.
     * 
     * @return The fixed color.
     */
    @Override
    public int valueToColor(double value) {
        return this.color;
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
        if (!(obj instanceof FixedColorScale)) {
            return false;
        }
        FixedColorScale that = (FixedColorScale) obj;
        if (this.color != that.color) {
            return false;
        }
        return true;
    }
}

