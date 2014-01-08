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

package com.orsoncharts.android.renderer;

import com.orsoncharts.android.Range;

/**
 * A color scale converts a data value into a color according to some
 * predefined color scale.
 * 
 * @since 1.1
 */
public interface ColorScale {

    /** 
     * Returns the range over which the scale is defined.
     * 
     * @return The range (never <code>null</code>).
     */
    Range getRange();
    
    /**
     * Returns the color on the scale that corresponds to the specified
     * value.
     * 
     * @param value  the value.
     * 
     * @return The color.
     */
    int valueToColor(double value);

}