/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.plot;

import java.util.EventObject;
import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.data.Dataset3DChangeEvent;
import com.orsoncharts.android.util.ArgChecks;

/**
 * An event used to signal a change to a {@link Plot3D}.  Any object that
 * implements the {@link Plot3DChangeListener} interface can register with a 
 * plot to receive change notifications.  By default, the {@link Chart3D}
 * object will register with the plot it manages to monitor changes to the plot
 * and its subcomponents.
 */
public class Plot3DChangeEvent extends EventObject {

    /** The plot. */
    private Plot3D plot;
  
    /**
     * Creates a new event.  The <code>source</code> of the event can be
     * either the plot instance or another event that was received by the
     * plot (for example, a {@link Dataset3DChangeEvent}).
     * 
     * @param source  the event source (<code>null</code> not permitted).
     * @param plot  the plot (<code>null</code> not permitted).
     */
    public Plot3DChangeEvent(Object source, Plot3D plot) {
        super(source);
        ArgChecks.nullNotPermitted(plot, "plot");
        this.plot = plot;
    }
 
    /**
     * Returns the plot from which the event came.
     * 
     * @return The plot (never <code>null</code>). 
     */
    public Plot3D getPlot() {
        return this.plot;
    }
}
