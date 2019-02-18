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

/**
 * Represents a single <code>(x, y, z)</code> data item, which can be added to 
 * a {@link XYZSeries}.  Instances of this class are immutable.
 */
public class XYZDataItem implements Serializable {

    /** The x-value. */
    private double x;

    /** The y-value. */
    private double y;

    /** The z-value. */
    private double z;

    /**
     * Creates a new (immutable) instance.
     * 
     * @param x  the x-value.
     * @param y  the y-value.
     * @param z  the z-value.
     */
    public XYZDataItem(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the x-value.
     * 
     * @return The x-value. 
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y-value.
     * 
     * @return The y-value.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns the z-value.
     * 
     * @return The z-value. 
     */
    public double getZ() {
        return this.z;
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
        if (!(obj instanceof XYZDataItem)) {
            return false;
        }
        XYZDataItem that = (XYZDataItem) obj;
        if (this.x != that.x) {
            return false;
        }
        if (this.y != that.y) {
            return false;
        }
        if (this.z != that.z) {
            return false;
        }
        return true;
    }

}
