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

package com.orsoncharts.android.renderer;

import android.graphics.Color;

import com.orsoncharts.android.Range;

/**
 * A color scale that runs a linear gradient between two colors.
 * 
 * @since 1.1
 */
public class GradientColorScale extends AbstractColorScale 
        implements ColorScale {

    /** The color at the low end of the value range. */
    private int lowColor;
    
    /** The color at the high end of the value range. */
    private int highColor;
    
    /** Storage for the color samples. */
    private int[] colors;

    /**
     * Creates a new instance with the specified value range and colors.
     * 
     * @param range  the data value range ({@code null} not permitted).
     * @param lowColor  the color for the low end of the data range.
     * @param highColor  the color for the high end of the data range.
     */
    public GradientColorScale(Range range, int lowColor, int highColor) {
        super(range);
        this.lowColor = lowColor;
        this.highColor = highColor;
        this.colors = new int[255];
    }

    /**
     * Returns the color for the low end of the data value range.
     * 
     * @return The color. 
     */
    public int getLowColor() {
        return this.lowColor;
    }
    
    /**
     * Returns the color for the high end of the data value range.
     * 
     * @return The color. 
     */
    public int getHighColor() {
        return this.highColor;
    }
    
    /**
     * Returns the number of samples used by this color scale.
     * 
     * @return The number of samples. 
     */
    public int getSampleCount() {
        return this.colors.length;
    }

    /**
     * Returns the color corresponding to the specified data value.  If this
     * 
     * @param value  the data value.
     * 
     * @return The color (never {@code null}).
     */
    @Override
    public int valueToColor(double value) {
        Range r = getRange();
        if (value < r.getMin()) {
            return valueToColor(r.getMin());
        }
        if (value > r.getMax()) {
            return valueToColor(r.getMax());
        }
        double fraction = getRange().percent(value);
        int i = (int) (fraction * this.colors.length);
        if (this.colors[i] == 0) {
            float p = (float) fraction;
            int alpha = (int) (Color.alpha(this.lowColor) * (1 - p) + Color.alpha(this.highColor) * p);
            int red = (int) (Color.red(this.lowColor) * (1 - p) + Color.red(this.highColor) * p);
            int green = (int) (Color.green(this.lowColor) * (1 - p) + Color.green(this.highColor) * p);
            int blue = (int) (Color.blue(this.lowColor) * (1 - p) + Color.blue(this.highColor) * p);
            this.colors[i] = Color.argb(alpha, red, green, blue);
        }
        return this.colors[i];
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
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
        if (!(obj instanceof GradientColorScale)) {
            return false;
        }
        GradientColorScale that = (GradientColorScale) obj;
        if (this.lowColor != that.lowColor) {
            return false;
        }
        if (this.highColor != that.highColor) {
            return false;
        }
        return super.equals(obj);
    }

}
