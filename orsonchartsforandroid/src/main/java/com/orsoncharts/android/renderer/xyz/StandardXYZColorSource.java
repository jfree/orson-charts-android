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
     * @param colors  the colors ({@code null} not permitted).
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
     * @return The color (never {@code null}).
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
     * @return The color (never {@code null}).
     */
    @Override
    public int getLegendColor(int series) {
        return this.colors[series % this.colors.length];
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
