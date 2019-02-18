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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.orsoncharts.android.LineStyle;
import com.orsoncharts.android.Range;
import com.orsoncharts.android.TextStyle;
import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.renderer.ColorScale;
import com.orsoncharts.android.table.AbstractTableElement;
import com.orsoncharts.android.table.ElementDimension;
import com.orsoncharts.android.table.Insets;
import com.orsoncharts.android.table.TableElement;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.Fit2D;
import com.orsoncharts.android.util.Orientation;
import com.orsoncharts.android.util.TextAnchor;
import com.orsoncharts.android.util.TextUtils;

/**
 * A {@link TableElement} that displays a {@link ColorScale}.
 * 
 * @since 1.1
 */
public class ColorScaleElement extends AbstractTableElement 
        implements TableElement {

    /** The color scale. */
    private ColorScale scale;
    
    /** The orientation (horizontal or vertical). */
    private Orientation orientation;
    
    /** The length of the bar. */
    private float barLength;
    
    /** The width of the bar. */
    private float barWidth;
    
    /** The gap between the color scale bar and the text labels. */
    private float textOffset;
    
    /** The font for the text labels. */
    private TextStyle font;
    
    /** The number formatter. */
    private NumberFormat formatter;
    
    /**
     * Creates a new <code>ColorScaleElement</code> with the specified 
     * attributes.
     * 
     * @param scale  the color scale ({@code null} not permitted).
     * @param orientation  the orientation ({@code null} not permitted).
     * @param barWidth  the bar width.
     * @param barLength  the bar length.
     * @param font  the font ({@code null} not permitted).
     */
    public ColorScaleElement(ColorScale scale, Orientation orientation, 
            float barWidth, float barLength, TextStyle font) {
        super();
        ArgChecks.nullNotPermitted(scale, "scale");
        ArgChecks.nullNotPermitted(orientation, "orientation");
        ArgChecks.nullNotPermitted(font, "font");
        this.scale = scale;
        this.orientation = orientation;
        this.barWidth = barWidth;
        this.barLength = barLength;
        this.textOffset = 2;
        this.font = font;
        this.formatter = new DecimalFormat("0.00");
    }
    
    /**
     * Returns the color scale.
     * 
     * @return The color scale (never {@code null}).
     */
    public ColorScale getColorScale() {
        return this.scale;
    }
    
    /**
     * Returns the orientation.
     * 
     * @return The orientation (never {@code null}).
     */
    public Orientation getOrientation() {
        return this.orientation;
    }
    
    /**
     * Returns the bar width.
     * 
     * @return The bar width.
     */
    public double getBarWidth() {
        return this.barWidth;
    }
    
    /**
     * Returns the bar length.
     * 
     * @return The bar length. 
     */
    public double getBarLength() {
        return this.barLength;
    }
    
    /**
     * Returns the font used to display the labels on the color scale.
     * 
     * @return The font (never {@code null}).
     */
    public TextStyle getFont() {
        return this.font;
    }

    /**
     * Returns the preferred size for this element.
     * 
     * @param g2  the graphics target.
     * @param bounds  the available drawing space.
     * @param constraints  layout constraints (ignored here).
     * 
     * @return The preferred size (never {@code null}).
     */
    @Override
    public Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        this.font.applyToPaint(paint);
        Range r = this.scale.getRange();
        String minStr = this.formatter.format(r.getMin());
        String maxStr = this.formatter.format(r.getMax());
        RectF minStrBounds = TextUtils.getTextBounds(minStr, paint);
        RectF maxStrBounds = TextUtils.getTextBounds(maxStr, paint);
        float maxStrWidth = Math.max(minStrBounds.width(),
                maxStrBounds.width());
        Insets insets = getInsets();
        float w, h;
        if (this.orientation == Orientation.HORIZONTAL) {
            w = Math.min(this.barLength + insets.left + insets.right, 
                bounds.width());
            h = Math.min(insets.top + this.barWidth + this.textOffset 
                    + minStrBounds.height() + insets.bottom,
                bounds.height());
        } else {
            w = Math.min(insets.left + this.barWidth + this.textOffset 
                    + maxStrWidth + insets.right, bounds.width());
            h = Math.min(insets.top + this.barLength + this.textOffset 
                    + minStrBounds.height() + insets.bottom,
                bounds.height());
           
        }
        return new ElementDimension(w, h);
    }

    /**
     * Performs a layout of this table element, returning a list containing
     * a single item (the bounding rectangles for the element).
     * 
     * @param canvas  the graphics target.
     * @param paint  the paint (contains font settings).
     * @param bounds  the bounds.
     * @param constraints  the constraints (if any).
     * 
     * @return A list containing the bounding rectangle. 
     */    
    @Override
    public List<RectF> layoutElements(Canvas canvas, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        List<RectF> result = new ArrayList<RectF>(1);
        Dimension2D prefDim = preferredSize(canvas, paint, bounds);
        Fit2D fitter = Fit2D.getNoScalingFitter(getRefPoint());
        RectF dest = fitter.fit(prefDim, bounds);
        result.add(dest);
        return result;
    }

    /**
     * Draws the element within the specified bounds.
     * 
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     */
    @Override
    public void draw(Canvas canvas, Paint paint, RectF bounds) {
        List<RectF> layoutInfo = layoutElements(canvas, paint, bounds, null);
        RectF dest = layoutInfo.get(0);
        
        paint.setColor(getBackgroundPaint());
        paint.setStyle(Style.FILL);
        canvas.drawRect(dest, paint);
   
        paint.setColor(getForegroundPaint());
        this.font.applyToPaint(paint);
        Range r = this.scale.getRange();
        String minStr = this.formatter.format(r.getMin());
        String maxStr = this.formatter.format(r.getMax());
        RectF minStrBounds = TextUtils.getTextBounds(minStr, paint);
        RectF maxStrBounds = TextUtils.getTextBounds(maxStr, paint);
        Insets insets = getInsets();
        if (this.orientation == Orientation.HORIZONTAL) {
            float x0 = dest.left + insets.left 
                    + minStrBounds.width() / 2.0f;
            float x1 = dest.right - insets.right 
                    - maxStrBounds.width() / 2.0f;
            float y0 = dest.top + insets.top;
            float y1 = y0 + this.barWidth;
            
            drawHorizontalScale(this.scale, canvas, paint, new RectF(
                    (int) x0, (int) y0, (int) x1, (int) (y0 + this.barWidth)));
            // fill the bar with the color scale
            paint.setColor(getForegroundPaint());
            TextUtils.drawAlignedString(minStr, canvas, paint, x0, 
                    y1 + this.textOffset, TextAnchor.TOP_CENTER);
            TextUtils.drawAlignedString(maxStr, canvas, paint, x1, 
                    y1 + this.textOffset, TextAnchor.TOP_CENTER);
            
        } else { // VERTICAL
            double maxStrWidth = Math.max(minStrBounds.width(), 
                    maxStrBounds.width());
            double x1 = dest.right - insets.right - maxStrWidth 
                    - this.textOffset;
            double x0 = x1 - this.barWidth;
            double y0 = dest.top + insets.top 
                    + maxStrBounds.height() / 2.0f;
            double y1 = y0 + this.barLength;            
            
            drawVerticalScale(this.scale, canvas, paint, new RectF(
                    (int) x0, (int) y0, (int) x1, (int) (y0 + this.barLength)));
            paint.setColor(getForegroundPaint());
            TextUtils.drawAlignedString(minStr, canvas, paint,
                    (float) (x1 + this.textOffset), (float) y1, 
                    TextAnchor.HALF_ASCENT_LEFT);
            TextUtils.drawAlignedString(maxStr, canvas, paint,
                    (float) (x1 + this.textOffset), (float) y0, 
                    TextAnchor.HALF_ASCENT_LEFT);
        }
    }
    
    /**
     * Draws the color scale horizontally within the specified bounds.
     * 
     * @param colorScale  the color scale.
     * @param canvas  the canvas.
     * @param paint  the paint.
     * @param bounds  the bounds.
     */
    private void drawHorizontalScale(ColorScale colorScale, Canvas canvas, 
            Paint paint, RectF bounds) {
        LineStyle.STANDARD.applyToPaint(paint);
        for (int x = (int) bounds.left; x < bounds.right; x++) {
            double p = (x - bounds.left) / bounds.width();
            double value = colorScale.getRange().value(p);
            paint.setColor(colorScale.valueToColor(value));
            canvas.drawLine(x, bounds.top, x, bounds.bottom, paint);
        }    
    }
    
    private void drawVerticalScale(ColorScale colorScale, Canvas canvas, 
            Paint paint, RectF bounds) {
        LineStyle.STANDARD.applyToPaint(paint);
        for (int y = (int) bounds.top; y < bounds.bottom; y++) {
            double p = (y - bounds.top) / bounds.height();
            double value = colorScale.getRange().value(1 - p);
            paint.setColor(this.scale.valueToColor(value));
            canvas.drawLine((int) bounds.left, y, (int) bounds.right, y, paint);
        }    
    }

}

