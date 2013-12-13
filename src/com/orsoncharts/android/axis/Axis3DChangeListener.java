/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.axis;

import java.util.EventListener;

import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.plot.XYZPlot;

/**
 * A listener for axis change events.  The plot classes that have axes
 * ({@link CategoryPlot3D} and {@link XYZPlot}) implement this interface so 
 * that they can receive notification when the axes are modified.
 */
public interface Axis3DChangeListener extends EventListener {
    
    /**
     * Called to inform that an axis change event has occurred.
     * 
     * @param event  the event (<code>null</code> not permitted). 
     */
    public void axisChanged(Axis3DChangeEvent event);

}
