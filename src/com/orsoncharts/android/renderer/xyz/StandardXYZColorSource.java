/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.renderer.xyz;

import java.io.Serializable;

import android.graphics.Color;

import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.ObjectUtils;

/**
 * A standard implementation of the {@link XYZColorSource} interface.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public class StandardXYZColorSource implements XYZColorSource, Serializable {

    /** The sequence of color objects to use for each series. */
    private int[] colors;
    
    /**
     * Creates a new instance with default colors.
     */
    public StandardXYZColorSource() {
        this(Color.RED, Color.BLUE, Color.YELLOW, Color.GRAY, Color.GREEN);    
    }
    
    /**
     * Creates a new instance with the supplied sequence of colors.  The
     * supplied array must have at least one entry.
     * 
     * @param colors  the colors (<code>null</code> not permitted). 
     */
    public StandardXYZColorSource(int... colors) {
        ArgChecks.nullNotPermitted(colors, "colors");
        if (colors.length == 0) {
            throw new IllegalArgumentException(
                    "Zero length array not permitted.");
        }
        this.colors = colors;
    }
    
    /**
     * Returns the color to use for the specified item.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The color (never <code>null</code>). 
     */
    @Override
    public int getColor(int series, int item) {
        return this.colors[series % this.colors.length];
    }

    /**
     * Returns the color to use in the legend for the specified series.
     * 
     * @param series  the series index.
     * 
     * @return The color (never <code>null</code>).
     */
    @Override
    public int getLegendColor(int series) {
        return this.colors[series % this.colors.length];
    }
    
    /**
     * Tests this paint source for equality with an arbitrary object.
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
        if (!(obj instanceof StandardXYZColorSource)) {
            return false;
        }
        StandardXYZColorSource that = (StandardXYZColorSource) obj;
        if (this.colors.length != that.colors.length) {
            return false;
        } 
        if (!ObjectUtils.equals(this.colors, that.colors)) {
            return false;
        }
        return true;
    }
    
}
