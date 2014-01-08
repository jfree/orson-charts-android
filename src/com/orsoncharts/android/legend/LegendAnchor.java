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

package com.orsoncharts.android.legend;

import com.orsoncharts.android.TitleAnchor;
import com.orsoncharts.android.util.Anchor2D;

/**
 * Predefined legend anchor points, provided for convenience.  The anchor
 * points are simply instances of the {@link Anchor2D} class.
 */
public final class LegendAnchor {
    
    /**
     * An anchor point at the top-left of the chart area. 
     */
    public static final Anchor2D TOP_LEFT = TitleAnchor.TOP_LEFT;

    /**
     * An anchor point at the top-right of the chart area. 
     */
    public static final Anchor2D TOP_RIGHT = TitleAnchor.TOP_RIGHT;

    /**
     * An anchor point at the top-center of the chart area. 
     */
    public static final Anchor2D TOP_CENTER = TitleAnchor.TOP_CENTER;

    /**
     * An anchor point at the center-left of the chart area. 
     */
    public static final Anchor2D CENTER_LEFT = TitleAnchor.CENTER_LEFT;

    /**
     * An anchor point at the center-right of the chart area. 
     */
    public static final Anchor2D CENTER_RIGHT = TitleAnchor.CENTER_RIGHT;

    /**
     * An anchor point at the bottom-center of the chart area. 
     */
    public static final Anchor2D BOTTOM_CENTER = TitleAnchor.BOTTOM_CENTER;

    /**
     * An anchor point at the bottom-left of the chart area. 
     */
    public static final Anchor2D BOTTOM_LEFT = TitleAnchor.BOTTOM_LEFT;
    
    /**
     * An anchor point at the bottom-right of the chart area. 
     */
    public static final Anchor2D BOTTOM_RIGHT = TitleAnchor.BOTTOM_RIGHT;
    
    private LegendAnchor() {
        // no need to instantiate this
    }
}
