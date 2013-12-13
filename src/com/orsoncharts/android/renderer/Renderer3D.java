/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.renderer;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.plot.XYZPlot;

/**
 * A renderer is an object responsible for constructing objects in a 3D model
 * that correspond to data items in a dataset.  The renderer's methods will be 
 * called by the plot ({@link CategoryPlot3D} or {@link XYZPlot}) that it is 
 * assigned to.  
 * <p>
 * All renderers support a change listener mechanism so that registered 
 * listeners can be notified whenever any attribute of the renderer is modified.
 * Typically the plot that the renderer is assigned to will listen for 
 * change events, and pass these events along to the {@link Chart3D} object.
 * <p>
 * Renderers should implement the <code>java.io.Serializable</code> interface,
 * so that charts can be serialized and deserialized, but this is not a forced
 * requirement (if you never use serialization, it won't matter if you 
 * implement a renderer that does not support it).
 */
public interface Renderer3D {
    
    /**
     * Registers a listener to receive notification of changes to the
     * renderer.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    void addChangeListener(Renderer3DChangeListener listener);
    
    /**
     * Deregisters a listener so that it no longer receives notification of
     * changes to the renderer.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    void removeChangeListener(Renderer3DChangeListener listener);

}
