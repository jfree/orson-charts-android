/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/android/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.android.data;

/**
 * A <code>(key, value)</code> pair that is used as a building block for some
 * data structures for the charts.
 */
public interface KeyedValue<T> {
  
    /**
     * Returns the key (by design, this key is required to be 
     * non-<code>null</code>).
     * 
     * @return The key (never <code>null</code>). 
     */
    public Comparable<?> getKey();
  
    /**
     * Returns the value.
     * 
     * @return The value (possibly <code>null</code>). 
     */
    public T getValue();
  
}

