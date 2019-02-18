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

/**
 * A source of <code>Color</code> values for an {@link XYZRenderer}.
 * <p>
 * Classes that implement this interface should also implement 
 * <code>java.io.Serializable</code>, otherwise it will not be possible to 
 * serialize and deserialize charts that use the non-serializable instance.
 */
public interface XYZColorSource {

    /**
     * Returns the color for one item in the plot.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The color. 
     */
    int getColor(int series, int item);
    
    /**
     * Returns the color that represents the specified series.
     * 
     * @param series  the series index.
     * 
     * @return The color (never {@code null}).
     */
    int getLegendColor(int series);

}
