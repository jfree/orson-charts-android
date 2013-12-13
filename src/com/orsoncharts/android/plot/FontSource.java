/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.plot;

import com.orsoncharts.android.TextStyle;

/**
 * An object that supplies <code>Font</code> instances associated with
 * keys.  This is used by the {@link PiePlot3D} class to obtain section label
 * fonts for each data item(segment) in the chart.  A default implementation
 * ({@link StandardFontSource}) is provided.
 */
public interface FontSource {

    /**
     * Returns a typeface based on the supplied key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return A typeface (never <code>null</code>). 
     */
    TextStyle getFont(Comparable<?> key);
    
    /**
     * Sets the font associated with a key.
     * 
     * @param key  the key (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> not permitted).
     */
    void setFont(Comparable<?> key, TextStyle font);
 
}
