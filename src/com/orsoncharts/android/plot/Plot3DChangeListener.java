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

package com.orsoncharts.android.plot;

import java.util.EventListener;
import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.Chart3DChangeEvent;

/**
 * An interface used to receive change events from {@link Plot3D} instances.
 * The {@link Chart3D} class will register with its plot to receive change
 * notifications - and upon receiving a change notification, it will pass it
 * on as a {@link Chart3DChangeEvent}.
 */
public interface Plot3DChangeListener extends EventListener {

    /**
     * Receives notification that a plot has changed.
     * 
     * @param event  event info. 
     */
    void plotChanged(Plot3DChangeEvent event);

}
