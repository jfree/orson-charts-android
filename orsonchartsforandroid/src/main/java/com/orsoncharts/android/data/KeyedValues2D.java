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

import java.util.List;

/**
 * A two dimensional grid of data values where each value is uniquely 
 * identified by two keys (the <code>xKey</code> and the <code>yKey</code>).
 * Any instance of <code>Comparable</code> can be used as a key
 * (<code>String</code> objects are instances of <code>Comparable</code>, 
 * making them convenient key objects).
 */
public interface KeyedValues2D<T> extends Values2D<T> {

    /**
     * Returns the x-key with the specified index.
     * 
     * @param xIndex  the index.
     * 
     * @return The key. 
     */
    public Comparable<?> getXKey(int xIndex);

    /**
     * Returns the y-key with the specified index.
     * 
     * @param yIndex  the index.
     * 
     * @return The key. 
     */
    public Comparable<?> getYKey(int yIndex);

    /**
     * Returns the index of the specified key, or <code>-1</code> if there
     * is no such key.
     * 
     * @param xkey  the x-key ({@code null} not permitted).
     * 
     * @return The index, or <code>-1</code>. 
     */
    public int getXIndex(Comparable<?> xkey);

    /**
     * Returns the index of the specified key, or <code>-1</code> if there
     * is no such key.
     * 
     * @param ykey  the y-key ({@code null} not permitted).
     * 
     * @return The index, or <code>-1</code>. 
     */
    public int getYIndex(Comparable<?> ykey);
  
    /**
     * Returns a list of the x-keys (the order is significant, since data 
     * values can be accessed by index as well as by key).
     * <br><br>
     * NOTE: this method must be implemented so that modifications to the
     * returned list do not impact the underlying data structure.
     * 
     * @return A list of x-keys.
     */
    public List<Comparable<?>> getXKeys();

    /**
     * Returns a list of the y-keys (the order is significant, since data 
     * values can be accessed by index as well as by key).
     * <br><br>
     * NOTE: this method must be implemented so that modifications to the
     * returned list do not impact the underlying data structure.
     * 
     * @return A list of y-keys.
     */
    public List<Comparable<?>> getYKeys();

    /**
     * Returns the value (possibly {@code null}) associated with the
     * specified keys.  If either or both of the keys is not defined in this
     * data structure, a runtime exception will be thrown.
     * 
     * @param xKey  the x-key ({@code null} not permitted).
     * @param yKey  the y-key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}).
     */
    public T getValue(Comparable<?> xKey, Comparable<?> yKey);

}
