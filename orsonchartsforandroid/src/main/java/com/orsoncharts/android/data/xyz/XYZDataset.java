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

package com.orsoncharts.android.data.xyz;

import java.util.List;

import com.orsoncharts.android.data.Dataset3D;
import com.orsoncharts.android.plot.XYZPlot;

/**
 * Defines the methods used to access data in the form of multiple series
 * containing <code>(x, y, z)</code> data items.  This is the standard
 * dataset format used by the {@link XYZPlot} class.
 */
public interface XYZDataset extends Dataset3D {

    /**
     * Returns the number of series in the dataset.
     * 
     * @return The number of series in the dataset.
     */
    int getSeriesCount();

    /**
     * Returns a list of the series-keys for the dataset.  Modifying this
     * list will have no impact on the underlying dataset.
     * 
     * @return A list of the series-keys (possibly empty, but never 
     *     {@code null}).
     */
    List<Comparable<?>> getSeriesKeys();
    
    /**
     * Returns the index of the specified series key, or <code>-1</code> if
     * the key is not found.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The index of the key, or <code>-1</code>. 
     */
    int getSeriesIndex(Comparable<?> key);
    
    /**
     * Returns the number of items in a given series.
     * 
     * @param series  the series index.
     * 
     * @return The item count.
     */
    int getItemCount(int series);

    /**
     * Returns the x-value for an item in a series.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The x-value. 
     */
    double getX(int series, int item);

    /**
     * Returns the y-value for an item in a series.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The y-value. 
     */
    double getY(int series, int item);

    /**
     * Returns the z-value for an item in a series.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The z-value. 
     */
    double getZ(int series, int item);

}
