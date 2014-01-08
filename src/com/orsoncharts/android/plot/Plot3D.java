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

import java.util.List;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.World;
import com.orsoncharts.android.legend.LegendItemInfo;
import com.orsoncharts.android.Chart3D;

/**
 * A plot for a {@link Chart3D}.  In Orson Charts, the <code>Chart3D</code> is
 * the umbrella object for all charts, but it is the <code>Plot3D</code>
 * instance that determines the real structure of the chart.  Built-in 
 * implementations include {@link PiePlot3D}, {@link CategoryPlot3D} and 
 * {@link XYZPlot}.
 */
public interface Plot3D {

    /**
     * Returns the dimensions for the plot in the 3D world in which it will 
     * be composed.
     * 
     * @return The dimensions (never <code>null</code>). 
     */
    Dimension3D getDimensions();
  
    /**
     * Adds 3D objects representing the current data for the plot to the 
     * specified world.  After the world has been populated (or constructed) in
     * this way, it is ready for rendering.
     * 
     * @param world  the world (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    void compose(World world, double xOffset, double yOffset, double zOffset);

    /**
     * Returns a list containing legend item info, typically one item for
     * each series in the chart.  This is intended for use in the construction
     * of a chart legend.  
     * <br><br>
     * If you are implementing a new plot type that does not require a legend, 
     * return an empty list.
     * 
     * @return A list containing legend item info (never <code>null</code>).
     */
    List<LegendItemInfo> getLegendInfo();
    
    /**
     * Registers a listener to receive notification of changes to the plot.
     * 
     * @param listener  the listener (<code>null</code> not permitted). 
     */
    void addChangeListener(Plot3DChangeListener listener);
  
    /**
     * De-registers a listener so that it no longer receives notification of
     * changes to the plot.
     * 
     * @param listener  the listener (<code>null</code> not permitted).
     */
    void removeChangeListener(Plot3DChangeListener listener);
  
}
