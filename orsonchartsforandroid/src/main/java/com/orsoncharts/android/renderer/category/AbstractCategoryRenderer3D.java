/* ========================
 * Orson Charts for Android
 * ========================
 *
 * (C)opyright 2013-2019, by Object Refinery Limited.  All rights reserved.
 *
 * https://github.com/jfree/orson-charts-android
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts for Android home page:
 *
 * https://www.object-refinery.com/orsoncharts/android/index.html
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
     * by the renderer (never {@code null}).
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
     * @return The plot or {@code null}.
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
     * @param plot  the plot ({@code null} permitted).
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
     * @return The color source (never {@code null}).
     */
    @Override
    public CategoryColorSource getColorSource() {
        return this.colorSource;
    }
    
    /**
     * Sets the color source for the renderer and sends a 
     * {@link Renderer3DChangeEvent} to all registered listeners.
     * 
     * @param colorSource  the color source ({@code null} not permitted).
     */
    @Override
    public void setColorSource(CategoryColorSource colorSource) {
        ArgChecks.nullNotPermitted(colorSource, "colorSource");
        this.colorSource = colorSource;
        fireChangeEvent();
    }
    
    /**
     * Sets a new color source for the renderer using the specified colors and
     * sends a {@link Renderer3DChangeEvent} to all registered listeners. This 
     * is a convenience method that is equivalent to 
     * <code>setColorSource(new StandardCategoryColorSource(colors))</code>.
     * 
     * @param colors  one or more colors ({@code null} not permitted).
     * 
     * @since 1.1
     */
    public void setColors(int... colors) {
        setColorSource(new StandardCategoryColorSource(colors));
    }
    
    /**
     * Returns the range of values that will be required on the value axis
     * to see all the data from the dataset.
     * 
     * @param data  the data ({@code null} not permitted).
     * 
     * @return The range (possibly {@code null})
     */
    @Override
    public Range findValueRange(Values3D<? extends Number> data) {
        return DataUtils.findValueRange(data);
    }
    
    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
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
