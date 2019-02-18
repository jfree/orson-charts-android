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

package com.orsoncharts.android.axis;

import java.util.List;

import android.graphics.Paint;

import com.orsoncharts.android.graphics3d.Point2D;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.plot.XYZPlot;

/**
 * An axis that displays a range of continuous values.  These can be used
 * for the value axis in a {@link CategoryPlot3D}, and for the X, Y or Z
 * axes in an {@link XYZPlot}.
 */
public interface ValueAxis3D extends Axis3D {
    
    /**
     * Returns a flag indicating whether or not the axis should be drawn.  
     * 
     * @return A boolean. 
     */
    boolean isVisible();
    
    /**
     * Sets the flag that controls whether or not the axis is drawn on the 
     * chart and sends an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param visible  the new flag value.
     */
    void setVisible(boolean visible);
    
    /**
     * Configure the axis as a value axis for the specified plot.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    void configureAsValueAxis(CategoryPlot3D plot);
    
    /**
     * Configure the axis as an x-axis for the specified plot.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    void configureAsXAxis(XYZPlot plot);

    /**
     * Configure the axis as an y-axis for the specified plot.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    void configureAsYAxis(XYZPlot plot);
    
    /**
     * Configure the axis as an z-axis for the specified plot.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    void configureAsZAxis(XYZPlot plot);
 
    /**
     * Selects an appropriate tick size and format for the axis based on
     * the axis being rendered from <code>pt0</code> to <code>pt1</code>.
     * 
     * @param paint  the graphics attributes.
     * @param pt0  the starting point.
     * @param pt1  the ending point.
     * @param opposingPt  a point on the opposite side of the axis from the 
     *     labels.
     * 
     * @return The tick size.
     */
    double selectTick(Paint paint, Point2D pt0, Point2D pt1, 
            Point2D opposingPt);
    
    /**
     * Generates a list of tick data for the specified tick unit.
     * 
     * @param tickUnit  the tick unit.
     * 
     * @return A list of tick data. 
     */
    List<TickData> generateTickData(double tickUnit);

}
