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

package com.orsoncharts.android.legend;

import java.io.Serializable;

import android.graphics.Typeface;

import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.plot.Plot3D;
import com.orsoncharts.android.plot.XYZPlot;
import com.orsoncharts.android.renderer.ColorScale;
import com.orsoncharts.android.renderer.ColorScaleRenderer;
import com.orsoncharts.android.renderer.FixedColorScale;
import com.orsoncharts.android.renderer.category.CategoryRenderer3D;
import com.orsoncharts.android.renderer.xyz.XYZRenderer;
import com.orsoncharts.android.table.TableElement;
import com.orsoncharts.android.util.Anchor2D;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.Orientation;
import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.TextStyle;

/**
 * A legend builder that creates a legend representing a {@link ColorScale}.
 * This builder will only create a legend if the plot uses a renderer
 * that implements the {@link ColorScaleRenderer} interface.
 * <br><br>
 * The orientation and anchor point for the legend are properties of the 
 * {@link Chart3D} class.
 * 
 * @since 1.1
 */
public class ColorScaleLegendBuilder implements LegendBuilder, Serializable {

    /** The default font for legend items. */
    public static final TextStyle DEFAULT_ITEM_FONT = new TextStyle(
            Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL), 10);
    
    /** The width of the bar showing the color scale. */
    private float barWidth;
    
    /** The length of the bar showing the color scale. */
    private float barLength;
    
    /** The item font. */
    private TextStyle itemFont;
    
    /** 
     * A flag to determine whether or not FixedColorScale is ignored (defaults 
     * to <code>true</code>). 
     */
    private boolean ignoreFixedColorScale;
    
    /**
     * Creates a new instance.
     */
    public ColorScaleLegendBuilder() {
        this.itemFont = DEFAULT_ITEM_FONT;
        this.barWidth = 16.0f;
        this.barLength = 140.0f;
        this.ignoreFixedColorScale = true;
    }
    
    /**
     * Returns the width of the bar displaying the color scale.
     * 
     * @return The width. 
     */
    public float getBarWidth() {
        return this.barWidth;
    }
    
    /**
     * Sets the width of the bar displaying the color scale.
     * 
     * @param width  the width. 
     */
    public void setBarWidth(float width) {
        this.barWidth = width;
    }
    
    /**
     * Returns the length of the bar displaying the color scale.
     * 
     * @return The length. 
     */
    public float getBarLength() {
        return this.barLength;
    }
    
    /**
     * Sets the length of the bar displaying the color scale.
     * 
     * @param length  the length. 
     */
    public void setBarLength(float length) {
        this.barLength = length;
    }
    
    /**
     * Returns the item font.
     * 
     * @return The item font (never {@code null}).
     */
    @Override
    public TextStyle getItemFont() {
        return this.itemFont;
    }

    /**
     * Sets the font used to show the value labels along the color scale.
     * 
     * @param font  the font ({@code null} not permitted).
     */
    @Override
    public void setItemFont(TextStyle font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.itemFont = font;
    }
    
    /**
     * Returns the flag that controls whether or not a {@link FixedColorScale}
     * will be ignored for the purposes of generating a legend.
     * 
     * @return A boolean. 
     */
    public boolean getIgnoreFixedColorScale() {
        return this.ignoreFixedColorScale;
    }
    
    /**
     * Sets the flag that controls whether or not a {@link FixedColorScale}
     * will be ignored for the purposes of generating a legend.
     * 
     * @param ignore  the new flag value. 
     */
    public void setIgnoreFixedColorScale(boolean ignore) {
        this.ignoreFixedColorScale = ignore;
    }
    
    /**
     * Creates a new color scale legend with the specified orientation.
     * If the plot does not use a renderer that implements 
     * {@link ColorScaleRenderer} then this method will return {@code null}
     * and no legend will be drawn on the chart.
     * 
     * @param plot  the plot ({@code null} not permitted).
     * @param anchor  the anchor ({@code null} not permitted).
     * @param orientation  the orientation ({@code null} not permitted).
     * 
     * @return A color scale legend (possibly {@code null}).
     */
    @Override
    public TableElement createLegend(Plot3D plot, Anchor2D anchor,
            Orientation orientation) {
        ColorScaleRenderer renderer = null;
        if (plot instanceof CategoryPlot3D) {
            CategoryRenderer3D r = ((CategoryPlot3D) plot).getRenderer();
            if (r instanceof ColorScaleRenderer) {
                renderer = (ColorScaleRenderer) r;
            }
        } else if (plot instanceof XYZPlot) {
            XYZRenderer r = ((XYZPlot) plot).getRenderer();
            if (r instanceof ColorScaleRenderer) {
                renderer = (ColorScaleRenderer) r;
            }
        }
        if (renderer == null) {
            return null;
        }
        // it doesn't make much sense to display a color scale legend for a
        // FixedColorScale so we check for that and ignore it (unless the
        // developer switched the ignoreFixedColorScale flag, in which case
        // you can have your legend)...
        if (this.ignoreFixedColorScale 
                && renderer.getColorScale() instanceof FixedColorScale) {
            return null;
        }
        return createColorScaleLegend(renderer, orientation, anchor);  
    }

    private TableElement createColorScaleLegend(ColorScaleRenderer r, 
            Orientation orientation, Anchor2D anchor) {
        ColorScale scale = r.getColorScale();
        ColorScaleElement element = new ColorScaleElement(scale, orientation, 
                this.barWidth, this.barLength, this.itemFont);
        element.setRefPoint(anchor.getRefPt());
        return element;
    }

    /**
     * Tests this builder for equality with an arbitrary object.
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
        if (!(obj instanceof ColorScaleLegendBuilder)) {
            return false;
        }
        ColorScaleLegendBuilder that = (ColorScaleLegendBuilder) obj;
        if (this.barWidth != that.barWidth) {
            return false;
        }
        if (this.barLength != that.barLength) {
            return false;
        }
        if (!this.itemFont.equals(that.itemFont)) {
            return false;
        }
        return true;
    }
}
