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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.android.data.AbstractDataset3D;
import com.orsoncharts.android.plot.XYZPlot;
import com.orsoncharts.android.renderer.xyz.XYZRenderer;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A collection of {@link XYZSeries} objects (implements the {@link XYZDataset}
 * interface so that it can be used as a source of data for an 
 * {@link XYZRenderer} on an {@link XYZPlot}).
 */
public class XYZSeriesCollection extends AbstractDataset3D 
        implements XYZDataset, Serializable {

    /** Storage for the data series. */
    private List<XYZSeries> series;

    /**
     * Creates a new (empty) <code>XYZSeriesCollection</code> instance.
     */
    public XYZSeriesCollection() {
        this.series = new ArrayList<XYZSeries>();
    }

    /**
     * Returns the number of series in the collection.
     * 
     * @return The number of series in the collection. 
     */
    @Override
    public int getSeriesCount() {
        return this.series.size();
    }
    
    /**
     * Returns the index of the series with the specified key, or 
     * <code>-1</code> if there is no series with the specified key.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The series index or <code>-1</code>. 
     */
    @Override
    public int getSeriesIndex(Comparable<?> key) {
        ArgChecks.nullNotPermitted(key, "key");
        return getSeriesKeys().indexOf(key);
    }

    /**
     * Returns a new list containing all the series keys.  Modifying this list 
     * will have no impact on the <code>XYZSeriesCollection</code> instance.
     * 
     * @return A list containing the series keys (possibly empty, but never 
     *     {@code null}).
     */
    @Override
    public List<Comparable<?>> getSeriesKeys() {
        List<Comparable<?>> result = new ArrayList<Comparable<?>>();
        for (XYZSeries s : this.series) {
            result.add(s.getKey());
        }
        return result;
    }

    /**
     * Adds a series to the collection (note that the series key must be
     * unique within the collection).
     * 
     * @param series  the series ({@code null} not permitted).
     */
    public void add(XYZSeries series) {
        ArgChecks.nullNotPermitted(series, "series");
        if (getSeriesIndex(series.getKey()) >= 0) {
            throw new IllegalArgumentException("Another series with the same key already exists within the collection.");
        }
        this.series.add(series);
    }

    /**
     * Returns the number of items in the specified series.
     * 
     * @param seriesIndex  the series index.
     * 
     * @return The number of items in the specified series. 
     */
    @Override
    public int getItemCount(int seriesIndex) {
        ArgChecks.nullNotPermitted(this, null);
        XYZSeries s = this.series.get(seriesIndex);
        return s.getItemCount();
    }

    /**
     * Returns the x-value for one item in a series.
     * 
     * @param seriesIndex  the series index.
     * @param itemIndex  the item index.
     * 
     * @return The x-value. 
     */
    @Override
    public double getX(int seriesIndex, int itemIndex) {
        XYZSeries s = this.series.get(seriesIndex);
        return s.getXValue(itemIndex);
    }

    /**
     * Returns the y-value for one item in a series.
     * 
     * @param seriesIndex  the series index.
     * @param itemIndex  the item index.
     * 
     * @return The y-value. 
     */
    @Override
    public double getY(int seriesIndex, int itemIndex) {
        XYZSeries s = this.series.get(seriesIndex);
        return s.getYValue(itemIndex);
    }

    /**
     * Returns the z-value for one item in a series.
     * 
     * @param seriesIndex  the series index.
     * @param itemIndex  the item index.
     * 
     * @return The z-value. 
     */
    @Override
    public double getZ(int seriesIndex, int itemIndex) {
        XYZSeries s = this.series.get(seriesIndex);
        return s.getZValue(itemIndex);
    }

    /**
     * Tests this dataset for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZSeriesCollection)) {
            return false;
        }
        XYZSeriesCollection that = (XYZSeriesCollection) obj;
        if (!this.series.equals(that.series)) {
            return false;
        }
        return true;
    }

}
