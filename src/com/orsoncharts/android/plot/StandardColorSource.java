/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.plot;

import java.io.Serializable;
import java.util.Arrays;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.data.DefaultKeyedValues;
import com.orsoncharts.android.Colors;

/**
 * A standard implementation of the {@link ColorSource} interface.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public final class StandardColorSource implements ColorSource, 
        Serializable {

    /** 
     * An array of standard colors from which a color will be assigned if
     * there is not one already stored for a given key. 
     */
    private int[] standardColors;
    
    /** Storage for the colors assigned to keys. */
    private DefaultKeyedValues<Integer> colors;
    
    /**
     * Creates a new instance with default colors.
     */
    public StandardColorSource() {
        this(Colors.getDefaultColors());
    }
    
    /**
     * Creates a new instance with a single default color.
     * 
     * @param color  the color.
     */
    public StandardColorSource(int color) {
        this(new int[] { color });    
    }
    
    /**
     * Creates a new instance with the supplied sequence of colors.  The
     * supplied array must have at least one entry.
     * 
     * @param colors  the colors (<code>null</code> not permitted). 
     */
    public StandardColorSource(int[] colors) {
        ArgChecks.nullNotPermitted(colors, "colors");
        if (colors.length == 0) {
            throw new IllegalArgumentException(
                    "Zero length array not permitted.");
        }
        this.standardColors = colors.clone();
        this.colors = new DefaultKeyedValues<Integer>();
    }
 
    /**
     * Returns the color associated with the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The color (never <code>null</code>). 
     */
    @Override
    public int getColor(Comparable key) {
        // defer argument check
        Integer c = this.colors.getValue(key);
        if (c != null) {
            return c.intValue();
        }
        c = this.standardColors[this.colors.getItemCount() 
                % this.standardColors.length];
        this.colors.put(key, c);
        return c;
    }

    /**
     * Sets the color for the specified key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param color  the color (pass -1 to remove previous setting).
     */
    @Override
    public void setColor(Comparable key, int color) {
        if (color >= 0) {
            this.colors.put(key, color);
        } else {
            this.colors.remove(key);
        }
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
        if (!(obj instanceof StandardColorSource)) {
            return false;
        }
        StandardColorSource that = (StandardColorSource) obj;
        if (!Arrays.equals(this.standardColors, that.standardColors)) {
            return false;
        }
        if (!this.colors.equals(that.colors)) {
            return false;
        }
        return true;
    }

}