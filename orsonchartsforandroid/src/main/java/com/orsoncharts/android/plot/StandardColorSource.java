/* ========================
 * Orson Charts for Android
 * ========================
 *
 * (C)opyright 2013-2019, by Object Refinery Limited.  All rights reserved.
 *
 * https://github.com/jfree/orson-charts-android
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts for Android home page:
 *
 * https://www.object-refinery.com/orsoncharts/android/index.html
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
     * Creates a new instance with the supplied sequence of colors.  The
     * supplied array must have at least one entry.
     * 
     * @param colors  the colors ({@code null} not permitted).
     */
    public StandardColorSource(int... colors) {
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
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The color (never {@code null}).
     */
    @Override
    public int getColor(Comparable<?> key) {
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
     * @param key  the key ({@code null} not permitted).
     * @param color  the color (pass -1 to remove previous setting).
     */
    @Override
    public void setColor(Comparable<?> key, int color) {
        if (color >= 0) {
            this.colors.put(key, color);
        } else {
            this.colors.remove(key);
        }
    }
    
    /**
     * Tests this paint source for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
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