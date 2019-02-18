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

import java.util.List;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.World;
import com.orsoncharts.android.legend.LegendItemInfo;
import com.orsoncharts.android.Chart3D;

/**
 * A plot for a {@link Chart3D}.  In Orson Charts, the <code>Chart3D</code> is
 * the umbrella object for all charts, but it is the <code>Plot3D</code>
 * instance that determines the real structure of the chart.  Built-in 
 * implementations include {@link PiePlot3D}, {@link CategoryPlot3D} and 
 * {@link XYZPlot}.
 */
public interface Plot3D {

    /**
     * Returns the dimensions for the plot in the 3D world in which it will 
     * be composed.
     * 
     * @return The dimensions (never {@code null}).
     */
    Dimension3D getDimensions();
  
    /**
     * Adds 3D objects representing the current data for the plot to the 
     * specified world.  After the world has been populated (or constructed) in
     * this way, it is ready for rendering.
     * 
     * @param world  the world ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    void compose(World world, double xOffset, double yOffset, double zOffset);

    /**
     * Returns a list containing legend item info, typically one item for
     * each series in the chart.  This is intended for use in the construction
     * of a chart legend.  
     * <br><br>
     * If you are implementing a new plot type that does not require a legend, 
     * return an empty list.
     * 
     * @return A list containing legend item info (never {@code null}).
     */
    List<LegendItemInfo> getLegendInfo();
    
    /**
     * Registers a listener to receive notification of changes to the plot.
     * 
     * @param listener  the listener ({@code null} not permitted).
     */
    void addChangeListener(Plot3DChangeListener listener);
  
    /**
     * De-registers a listener so that it no longer receives notification of
     * changes to the plot.
     * 
     * @param listener  the listener ({@code null} not permitted).
     */
    void removeChangeListener(Plot3DChangeListener listener);
  
}
