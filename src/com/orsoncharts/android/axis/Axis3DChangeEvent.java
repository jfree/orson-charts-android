/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.axis;

import java.util.EventObject;
import com.orsoncharts.android.util.ArgChecks;

/**
 * An event associated with a change to an {@link Axis3D}.  These change 
 * events will be generated by an axis and broadcast to the plot that owns the
 * axis (in the standard setup, the plot will then trigger its own change event
 * to notify the chart that a subcomponent of the plot has changed). 
 */
public class Axis3DChangeEvent extends EventObject {
  
    /** The axis associated with this event. */
    private Axis3D axis;
  
    /**
     * Creates a new event.
     * 
     * @param axis  the axis (<code>null</code> not permitted). 
     */
    public Axis3DChangeEvent(Axis3D axis) {
        this(axis, axis);
    }
    
    /**
     * Creates a new event.
     * 
     * @param source  the event source.
     * @param axis  the axis (<code>null</code> not permitted).
     */
    public Axis3DChangeEvent(Object source, Axis3D axis) {
        super(source);
        ArgChecks.nullNotPermitted(axis, "axis");
        this.axis = axis;
    }
  
    /**
     * Returns the axis associated with this event.
     * 
     * @return The axis (never <code>null</code>). 
     */
    public Axis3D getAxis() {
        return this.axis;
    }

}
