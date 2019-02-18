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

import java.io.Serializable;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.ObjectUtils;

/**
 * A data item where a key is associated with a value (typically numerical).
 */
public final class DefaultKeyedValue<T> implements KeyedValue<T>, Serializable {

    /** The key. */
    private Comparable<?> key;

    /** The value. */
    private T value;

    /**
     * Creates a new instance.
     * 
     * @param key  the key ({@code null} not permitted).
     * @param value  the value.
     */
    public DefaultKeyedValue(Comparable<?> key, T value) {
        ArgChecks.nullNotPermitted(key, "key");
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key.
     * 
     * @return The key (never {@code null}).
     */
    @Override
    public Comparable<?> getKey() {
        return this.key;
    }

    /**
     * Returns the value.
     * 
     * @return The value (possibly {@code null}).
     */
    @Override
    public T getValue() {
        return this.value;
    }
  
    /**
     * Sets the value.
     * 
     * @param value  the value ({@code null} permitted).
     */
    public void setValue(T value) {
        this.value = value;
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
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
        if (!(obj instanceof DefaultKeyedValue<?>)) {
            return false;
        }
        DefaultKeyedValue<?> that = (DefaultKeyedValue<?>) obj;
        if (!this.key.equals(that.key)) {
            return false;
        }
        if (!ObjectUtils.equals(this.value, that.value)) {
            return false;
        }
        return true;
    }
  
    @Override
    public String toString() {
        return "(" + this.key.toString() + ", " + this.value + ")";
    }
}
