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

package com.orsoncharts.android.legend;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.TextStyle;
import com.orsoncharts.android.plot.Plot3D;
import com.orsoncharts.android.table.TableElement;
import com.orsoncharts.android.util.Anchor2D;
import com.orsoncharts.android.util.Orientation;

/**
 * A legend builder is responsible for creating a legend for a chart.  The API
 * has been kept to a minimum intentionally, so as not to overly constrain 
 * developers that want to implement a custom legend builder.  The 
 * <code>get/setItemFont()</code> methods have been added for convenience
 * because changing the font of the legend item text is a very common 
 * operation.
 * <p>
 * Classes that implement this interface should also implement 
 * <code>java.io.Serializable</code> if you intend to serialize and deserialize 
 * chart objects.
 * 
 * @see Chart3D#setLegendBuilder(LegendBuilder) 
 */
public interface LegendBuilder {

    /**
     * Creates a legend for the specified plot.  If this method returns 
     * {@code null}, no legend will be displayed.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * @param anchor  the anchor ({@code null} not permitted).
     * @param orientation  the legend orientation ({@code null} not
     *         permitted).
     * 
     * @return A legend (possibly {@code null}).
     * 
     * @since 1.1
     */
    TableElement createLegend(Plot3D plot, Anchor2D anchor,
            Orientation orientation);

    /**
     * Returns the font used for each item within the legend.
     * 
     * @return The font (never {@code null}).
     */
    TextStyle getItemFont();
    
    /**
     * Sets the font used for each item within the legend.
     * 
     * @param font  the font ({@code null} not permitted).
     */
    void setItemFont(TextStyle font);

}
