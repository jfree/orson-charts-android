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

import com.orsoncharts.android.renderer.xyz.SurfaceRenderer;

/**
 * An enumeration of the different methods used by renderers for composing the
 * items in a chart.  Most renderers work on a per-item basis, where the plot
 * iterates over the items in a dataset then asks the renderer to compose one 
 * item at a time.  An alternative approach is where the renderer does the
 * entire composition in one pass (the {@link SurfaceRenderer} does this,
 * since it plots a function rather than a dataset (performing sampling of
 * the function on-the-fly).
 */
public enum ComposeType {
    
    /** Compose individual items one at a time. */
    PER_ITEM,
    
    /** Compose all items at once. */
    ALL
    
}
