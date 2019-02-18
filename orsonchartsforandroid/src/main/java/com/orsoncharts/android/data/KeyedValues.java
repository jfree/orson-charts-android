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
 * A list of values (of type T) that are associated with unique keys.
 */
public interface KeyedValues<T> extends Values<T> { 

    /**
     * Returns the key for the specified item in the list.
     * 
     * @param index  the item index.
     * 
     * @return The key. 
     */
    public Comparable<?> getKey(int index);
  
    /**
     * Returns the index for the specified key, or <code>-1</code> if the key
     * is not present in the list.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The item index, or <code>-1</code>. 
     */
    public int getIndex(Comparable<?> key);
  
    /**
     * Returns a list of all the keys.  Note that the list will be a copy, so
     * modifying it will not impact this data structure.
     * 
     * @return A list of keys (possibly empty, but never {@code null}).
     */
    public List<Comparable<?>> getKeys();

    /**
     * Returns the value associated with the specified key, or 
     * {@code null}.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}).
     */
    public T getValue(Comparable<?> key);

}
