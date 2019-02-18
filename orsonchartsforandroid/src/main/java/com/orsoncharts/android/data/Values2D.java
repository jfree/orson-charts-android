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

package com.orsoncharts.android.data;

/**
 * A generic representation of a two dimensional grid of data values.
 */
public interface Values2D<T> {
    
    /**
     * Returns the number of items in the x-dimension.
     * 
     * @return The number of items in the x-dimension. 
     */
    int getXCount();
  
    /**
     * Returns the number of items in the y-dimension.
     * 
     * @return The number of items in the y-dimension. 
     */
    int getYCount();
  
    /**
     * Returns the data item at the specified position.
     * 
     * @param xIndex  the x-index.
     * @param yIndex  the y-index.
     * 
     * @return The data value (possibly {@code null}).
     */
    T getValue(int xIndex, int yIndex);

    /**
     * Returns the data value at the specified position as a double primitive,
     * or <code>Double.NaN</code> if the value is not an instance of 
     * <code>Number</code>.  Where the {@link #getValue(int, int)} method 
     * returns {@code null}, this method returns <code>Double.NaN</code>.
     * 
     * @param xIndex  the x-index.
     * @param yIndex  the y-index.
     * 
     * @return The data value.
     */
    double getDoubleValue(int xIndex, int yIndex);

}
