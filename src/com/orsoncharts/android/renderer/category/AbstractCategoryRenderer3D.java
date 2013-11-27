/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.renderer.category;

import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.Range;
import com.orsoncharts.android.data.DataUtils;
import com.orsoncharts.android.data.Values3D;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.renderer.AbstractRenderer3D;
import com.orsoncharts.android.renderer.Renderer3DChangeEvent;

/**
 * A base class that can be used to implement renderers for a 
 * {@link CategoryPlot3D}.
 */
public abstract class AbstractCategoryRenderer3D extends AbstractRenderer3D 
        implements CategoryRenderer3D {
    
    /** A reference to the plot that the renderer is currently assigned to. */
    private CategoryPlot3D plot;
   
    /** 
     * The color source is used to determine the color for each item drawn
     * by the renderer (never <code>null</code>).
     */
    private CategoryColorSource colorSource;
    
    /**
     * Default constructor.
     */
    public AbstractCategoryRenderer3D() {
        this.colorSource = new StandardCategoryColorSource();
    }
    
    /**
     * Returns the plot that the renderer is currently assigned to, if any.
     * 
     * @return The plot or <code>null</code>.
     */
    @Override
    public CategoryPlot3D getPlot() {
        return this.plot;
    }
    
    /**
     * Sets the plot that the renderer is assigned to.  You do not need to 
     * call this method yourself, the plot takes care of it when you call
     * the <code>setRenderer()</code> method on the plot.
     * 
     * @param plot  the plot (<code>null</code> permitted).
     */
    @Override
    public void setPlot(CategoryPlot3D plot) {
        this.plot = plot;
    }

    /**
     * Returns the color source for the renderer.  This is used to determine
     * the colors used for individual items in the chart, and the color to 
     * display for a series in the chart legend.
     * 
     * @return The color source (never <code>null</code>). 
     */
    @Override
    public CategoryColorSource getColorSource() {
        return this.colorSource;
    }
    
    /**
     * Sets the color source for the renderer and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param colorSource  the color source (<code>null</code> not permitted). 
     */
    @Override
    public void setColorSource(CategoryColorSource colorSource) {
        ArgChecks.nullNotPermitted(colorSource, "colorSource");
        this.colorSource = colorSource;
        fireChangeEvent();
    }
    
    /**
     * Returns the range of values that will be required on the value axis
     * to see all the data from the dataset.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * 
     * @return The range (possibly <code>null</code>) 
     */
    @Override
    public Range findValueRange(Values3D data) {
        return DataUtils.findValueRange(data);
    }
    
    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } 
        if (!(obj instanceof AbstractCategoryRenderer3D)) {
            return false;
        }
        AbstractCategoryRenderer3D that = (AbstractCategoryRenderer3D) obj;
        if (!this.colorSource.equals(that.colorSource)) {
            return false;
        }
        return super.equals(obj);
    }
    
}
