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

import com.orsoncharts.android.Range;
import com.orsoncharts.android.data.xyz.XYZDataset;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.World;
import com.orsoncharts.android.plot.XYZPlot;
import com.orsoncharts.android.renderer.ComposeType;
import com.orsoncharts.android.renderer.Renderer3D;

/**
 * A renderer that can display data from an {@link XYZDataset} on an
 * {@link XYZPlot}.
 */
public interface XYZRenderer extends Renderer3D {

    /**
     * Returns the plot that this renderer is assigned to.
     * 
     * @return The plot (possibly {@code null}).
     */
    XYZPlot getPlot();
  
    /**
     * Sets the plot that the renderer is assigned to.  Although this method
     * is part of the public API, client code should not need to call it.
     * 
     * @param plot  the plot ({@code null} permitted).
     */
    void setPlot(XYZPlot plot);
    
    /**
     * Returns the color source for the renderer, which is an object that
     * is responsible for providing the colors used by the renderer to draw
     * data (and legend) items.
     * 
     * @return The paint source (never {@code null}).
     */
    XYZColorSource getColorSource();

    /**
     * Returns the range that should be set on the x-axis in order for this 
     * renderer to be able to display all the data in the supplied dataset.
     * If the dataset contains no data, this method returns {@code null}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The range (possibly {@code null}).
     */
    Range findXRange(XYZDataset dataset);
    
    /**
     * Returns the range that should be set on the y-axis in order for this 
     * renderer to be able to display all the data in the supplied dataset.
     * If the dataset contains no data, this method returns {@code null}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The range. 
     */
    Range findYRange(XYZDataset dataset);
    
    /**
     * Returns the range that should be set on the z-axis in order for this 
     * renderer to be able to display all the data in the supplied dataset.
     * If the dataset contains no data, this method returns {@code null}.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The range. 
     */
    Range findZRange(XYZDataset dataset);

    /**
     * Returns the type of composition performed by the renderer.  This
     * determines whether the plot will call the <code>composeItem()</code>
     * method (once for each data item) or just call the 
     * <code>composeAll()</code> method once.
     * 
     * @return The type of composition (never {@code null}).
     * 
     * @since 1.1
     */
    ComposeType getComposeType();
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  The {@link XYZPlot} class will iterate over its dataset and
     * and call this method for each item (in other words, you don't need to 
     * call this method directly).
     * 
     * @param dataset the dataset ({@code null} not permitted).
     * @param series  the series index.
     * @param item  the item index.
     * @param world  the world ({@code null} not permitted).
     * @param dimensions  the dimensions ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    void composeItem(XYZDataset dataset, int series, int item, 
            World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset);

    /**
     * Composes all the 3D objects that this renderer needs to present.  This
     * method only needs to be implemented if the <code>getComposeType()</code>
     * method returns <code>ALL</code>, otherwise it can be left empty.
     * 
     * @param plot  the plot.
     * @param world  the world ({@code null} not permitted).
     * @param dimensions  the dimensions ({@code null} not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * 
     * @since 1.1
     */
    void composeAll(XYZPlot plot, World world, Dimension3D dimensions, 
            double xOffset, double yOffset, double zOffset);

}
