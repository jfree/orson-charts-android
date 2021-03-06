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
import java.util.ArrayList;
import java.util.List;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A two dimensional grid of (typically numerical) data that is accessible by 
 * row and column keys.
 */
public final class DefaultKeyedValues2D<T> implements KeyedValues2D<T>, 
        Serializable {

    /** The x-keys. */
    List<Comparable<?>> xKeys;
    
    /** The y-keys. */
    List<Comparable<?>> yKeys;
    
    /** The data values. */
    List<DefaultKeyedValues<T>> data;  // one entry per xKey
  
    /**
     * Creates a new (empty) instance.
     */
    public DefaultKeyedValues2D() {
        this(new ArrayList<Comparable<?>>(), new ArrayList<Comparable<?>>());
    }
    
    /**
     * Creates a new instance with the specified keys and all data values 
     * initialized to {@code null}.
     * 
     * @param xKeys  the xKeys ({@code null} not permitted).
     * @param yKeys  the yKeys ({@code null} not permitted).
     */
    public DefaultKeyedValues2D(List<Comparable<?>> xKeys, 
            List<Comparable<?>> yKeys) {
        ArgChecks.nullNotPermitted(xKeys, "xKeys");
        ArgChecks.nullNotPermitted(yKeys, "yKeys");
        this.xKeys = new ArrayList<Comparable<?>>(xKeys);
        this.yKeys = new ArrayList<Comparable<?>>(yKeys);
        this.data = new ArrayList<DefaultKeyedValues<T>>();    
        for (Comparable<?> c : xKeys) {
            this.data.add(new DefaultKeyedValues<T>(yKeys));
        }
    }

    /**
     * Returns the x-key corresponding to the specified index.
     * 
     * @param xIndex  the index.
     * 
     * @return The key. 
     */
    @Override
    public Comparable<?> getXKey(int xIndex) {
        return this.xKeys.get(xIndex);
    }

    /**
     * Returns the y-key corresponding to the specified index.
     * 
     * @param yIndex  the index.
     * 
     * @return The key. 
     */
    @Override
    public Comparable<?> getYKey(int yIndex) {
        return this.yKeys.get(yIndex);
    }

    /**
     * Returns the index corresponding to the specified x-key.
     * 
     * @param xkey  the x-key ({@code null} not permitted).
     * 
     * @return The index. 
     */
    @Override
    public int getXIndex(Comparable<?> xkey) {
        ArgChecks.nullNotPermitted(xkey, "xkey");
        return this.xKeys.indexOf(xkey);
    }

    /**
     * Returns the index corresponding to the specified y-key.
     * 
     * @param ykey  the y-key ({@code null} not permitted).
     * 
     * @return The index. 
     */
    @Override
    public int getYIndex(Comparable<?> ykey) {
        ArgChecks.nullNotPermitted(ykey, "ykey");
        return this.yKeys.indexOf(ykey);
    }

    /**
     * Returns a copy of the list of xKeys.
     * 
     * @return A copy of the list of xKeys (never {@code null}).
     */
    @Override
    public List<Comparable<?>> getXKeys() {
        return new ArrayList<Comparable<?>>(this.xKeys);
    }

    /**
     * Returns a copy of the list of y-keys.
     * 
     * @return A copy of the list of y-keys (never {@code null}).
     */
    @Override
    public List<Comparable<?>> getYKeys() {
        return new ArrayList<Comparable<?>>(this.yKeys);
    }

    /**
     * Returns the number of x-keys in the table.
     * 
     * @return The number of x-keys in the table.
     */
    @Override
    public int getXCount() {
        return this.xKeys.size();
    }
    
    /**
     * Returns the number of y-keys in the data structure.
     * 
     * @return The number of y-keys.
     */
    @Override
    public int getYCount() {
        return this.yKeys.size();
    }

    /**
     * Returns a value from one cell in the table.
     * 
     * @param xKey  the x-key ({@code null} not permitted).
     * @param yKey  the y-key ({@code null} not permitted).
     * 
     * @return The value (possibly {@code null}).
     */
    @Override
    public T getValue(Comparable<?> xKey, Comparable<?> yKey) {
        // arg checking is handled in getXIndex() and getYIndex()
        int xIndex = getXIndex(xKey);
        int yIndex = getYIndex(yKey);
        return getValue(xIndex, yIndex);
    }

    /**
     * Returns the value from one cell in the table.
     * 
     * @param xIndex  the x-index.
     * @param yIndex  the y-index.
     * 
     * @return The value (possibly {@code null}).
     */
    @Override
    public T getValue(int xIndex, int yIndex) {
        return this.data.get(xIndex).getValue(yIndex);
    }

    /**
     * Returns the data item at the specified position as a double primitive.
     * Where the {@link #getValue(int, int)} method returns {@code null},
     * this method returns <code>Double.NaN</code>.
     * 
     * @param xIndex  the x-index.
     * @param yIndex  the y-index.
     * 
     * @return The data value.
     */
    @Override
    public double getDoubleValue(int xIndex, int yIndex) {
        T n = getValue(xIndex, yIndex);
        if (n != null && n instanceof Number) {
            return ((Number) n).doubleValue();
        }
        return Double.NaN;
    } 

    /**
     * Sets a value for one cell in the table.
     * 
     * @param n  the value ({@code null} permitted).
     * @param xKey  the x-key ({@code null} not permitted).
     * @param yKey  the y-key ({@code null} not permitted).
     */
    public void setValue(T n, Comparable<?> xKey, Comparable<?> yKey) {
        ArgChecks.nullNotPermitted(xKey, "xKey");
        ArgChecks.nullNotPermitted(yKey, "yKey");
        
        if (this.data.isEmpty()) {  // 1. no data - just add one new entry
            this.xKeys.add(xKey);
            this.yKeys.add(yKey);
            DefaultKeyedValues<T> dkvs = new DefaultKeyedValues<T>();
            dkvs.put(yKey, n);
            this.data.add(dkvs);
        } else {
            int xIndex = getXIndex(xKey);
            int yIndex = getYIndex(yKey);
            if (xIndex >= 0) {
                DefaultKeyedValues<T> dkvs = this.data.get(xIndex);
                if (yIndex >= 0) {
                    // 2.  Both keys exist - just update the value
                    dkvs.put(yKey, n);
                } else {
                    // 3.  xKey exists, but yKey does not (add the yKey to 
                    //     each series)
                    this.yKeys.add(yKey);
                    for (DefaultKeyedValues<T> kv : this.data) {
                        kv.put(yKey, null);
                    }
                    dkvs.put(yKey, n);
                }
            } else {
                if (yIndex >= 0) {
                    // 4.  xKey does not exist, but yKey does
                    this.xKeys.add(xKey);
                    DefaultKeyedValues<T> d = new DefaultKeyedValues<T>(
                            this.yKeys);
                    d.put(yKey, n);
                    this.data.add(d);
                } else {
                    // 5.  neither key exists, need to create the new series, 
                    //     plus the new entry in every series
                    this.xKeys.add(xKey);
                    this.yKeys.add(yKey);
                    for (DefaultKeyedValues<T> kv : this.data) {
                        kv.put(yKey, null);
                    }
                    DefaultKeyedValues<T> d = new DefaultKeyedValues<T>(
                            this.yKeys);
                    d.put(yKey, n);
                    this.data.add(d);
                }
            }
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultKeyedValues2D)) {
            return false;
        }
        DefaultKeyedValues2D<?> that = (DefaultKeyedValues2D<?>) obj;
        if (!this.xKeys.equals(that.xKeys)) {
            return false;
        }
        if (!this.yKeys.equals(that.yKeys)) {
            return false;
        }
        if (!this.data.equals(that.data)) {
            return false;
        }
        return true;
    }
}
