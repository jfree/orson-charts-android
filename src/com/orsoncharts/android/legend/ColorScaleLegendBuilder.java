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
     * @return The item font (never <code>null</code>). 
     */
    @Override
    public TextStyle getItemFont() {
        return this.itemFont;
    }

    /**
     * Sets the font used to show the value labels along the color scale.
     * 
     * @param font  the font (<code>null</code> not permitted). 
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
     * {@link ColorScaleRenderer} then this method will return <code>null</code>
     * and no legend will be drawn on the chart.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * @param anchor  the anchor (<code>null</code> not permitted).
     * @param orientation  the orientation (<code>null</code> not permitted).
     * 
     * @return A color scale legend (possibly <code>null</code>). 
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
     * @param obj  the object (<code>null</code> permitted).
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
