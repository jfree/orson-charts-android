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

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A data series containing a sequence of <code>(x, y, z)</code> data items.  
 * The series has a key to identify it, and can be added to an 
 * {@link XYZSeriesCollection} to create a dataset.
 */
public class XYZSeries implements Serializable {

    /** The series key (never {@code null}). */
    private Comparable<?> key;

    /** The data items in the series. */
    private List<XYZDataItem> items;

    /**
     * Creates a new series with the specified key.
     * 
     * @param key  the key ({@code null} not permitted).
     */
    public XYZSeries(Comparable<?> key) {
        ArgChecks.nullNotPermitted(key, "key");
        this.key = key;
        this.items = new ArrayList<XYZDataItem>();
    }

    /**
     * Returns the series key.
     * 
     * @return The series key (never {@code null}).
     */
    public Comparable<?> getKey() {
        return this.key;
    }
    
    /**
     * Returns the number of items in the series.
     * 
     * @return The number of items in the series. 
     */
    public int getItemCount() {
        return this.items.size();
    }
    
    /**
     * Returns the x-value for the specified item.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The x-value. 
     */
    public double getXValue(int itemIndex) {
        return this.items.get(itemIndex).getX();
    }
  
    /**
     * Returns the y-value for the specified item.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The y-value. 
     */
    public double getYValue(int itemIndex) {
        return this.items.get(itemIndex).getY();
    }

    /**
     * Returns the z-value for the specified item.
     * 
     * @param itemIndex  the item index.
     * 
     * @return The z-value. 
     */
    public double getZValue(int itemIndex) {
        return this.items.get(itemIndex).getZ();
    }

    /**
     * Adds a new data item to the series.
     * 
     * @param x  the x-value.
     * @param y  the y-value.
     * @param z  the z-value.
     */
    public void add(double x, double y, double z) {
        add(new XYZDataItem(x, y, z));   
    }

    /**
     * Adds a new data item to the series.
     * 
     * @param item  the data item ({@code null} not permitted).
     */
    public void add(XYZDataItem item) {
        ArgChecks.nullNotPermitted(item, "item");
        this.items.add(item);
    }

    /**
     * Tests this series for equality with an arbitrary object.
     * 
     * @param obj  the object to test ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZSeries)) {
            return false;
        }
        XYZSeries that = (XYZSeries) obj;
        if (!this.key.equals(that.key)) {
            return false;
        }
        if (!this.items.equals(that.items)) {
            return false;
        }
        return true;
    }
}
