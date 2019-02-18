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

package com.orsoncharts.android.axis;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;

import com.orsoncharts.android.LineStyle;
import com.orsoncharts.android.Range;
import com.orsoncharts.android.graphics3d.Line2D;
import com.orsoncharts.android.graphics3d.Point2D;
import com.orsoncharts.android.graphics3d.Utils2D;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.plot.XYZPlot;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.ObjectUtils;
import com.orsoncharts.android.util.TextAnchor;
import com.orsoncharts.android.util.TextUtils;

/**
 * A numerical axis for use with 3D plots (implements {@link ValueAxis3D}).
 * In a {@link CategoryPlot3D} the value axis (the vertical one) is numerical, 
 * and in an {@link XYZPlot} all the axes (x, y and z) are numerical - for
 * all these cases an instance of this class can be used.
 */
public class NumberAxis3D extends AbstractAxis3D implements ValueAxis3D,
        Serializable {

    /** A flag that determines whether or not the axis will be drawn. */
    private boolean visible;
    
    /** The axis range. */
    private Range range;

    /** 
     * A flag that controls whether or not the axis range is automatically
     * adjusted to display all of the data items in the dataset.
     */
    private boolean autoAdjustRange;
    
    /** The percentage margin to leave at the lower end of the axis. */
    private double lowerMargin;
    
    /** The percentage margin to leave at the upper end of the axis. */
    private double upperMargin;

    /** 
     * A flag indicating whether or not the auto-range calculation should
     * include zero.
     */
    private boolean autoRangeIncludesZero;
    
    /**
     * A flag that controls how zero is handled when it falls within the
     * margins.  If <code>true</code>, the margin is truncated at zero, if
     * <code>false</code> the margin is not changed.
     */
    private boolean autoRangeStickyZero;
    
    /** 
     * The default range to apply when there is no data in the dataset and the
     * autoAdjustRange flag is true.  A sensible default is going to depend on
     * the context, so the user should change it as necessary.
     */
    private Range defaultAutoRange;
    
    /** 
     * The tick selector (if not {@code null}, then auto-tick selection
     * is used). 
     */
    private TickSelector tickSelector;

    /** 
     * The tick size.  If the tickSelector is not {@code null} then it is
     * used to auto-select an appropriate tick size and format.
     */
    private double tickSize;

    /** The tick formatter (never {@code null}). */
    private Format tickLabelFormatter;

    /** The tick label factor (defaults to 1.4). */
    private double tickLabelFactor;    

    /** The tick label offset. */
    private double tickLabelOffset;
    
    /** The length of tick marks.  Can be set to 0.0. */
    private double tickMarkLength;
    
    /** The tick mark stroke (never {@code null}). */
    private LineStyle tickMarkStroke;
    
    /** The tick mark paint (never {@code null}). */
    private int tickMarkPaint;
    
    /**
     * Creates a new axis with the specified label and default attributes.
     * 
     * @param label  the axis label ({@code null} permitted).
     */
    public NumberAxis3D(String label) {
        this(label, new Range(0.0, 1.0));
    }
    
    /**
     * Creates a new axis with the specified label and range.
     *
     * @param label  the axis label ({@code null} permitted).
     * @param range  the range ({@code null} not permitted).
     */
    public NumberAxis3D(String label, Range range) {
        super(label);
        this.visible = true;
        this.range = range;
        this.autoAdjustRange = true;
        this.lowerMargin = 0.05;
        this.upperMargin = 0.05;
        this.autoRangeIncludesZero = false;
        this.autoRangeStickyZero = true;
        this.defaultAutoRange = new Range(0.0, 1.0);
        this.tickSelector = new NumberTickSelector();
        this.tickLabelFactor = 1.4;
        this.tickSize = range.getLength() / 10.0;
        this.tickLabelFormatter = new DecimalFormat("0.00");
        this.tickLabelOffset = 5.0;
        this.tickMarkLength = 3.0;
        this.tickMarkStroke = new LineStyle(0.5f);
        this.tickMarkPaint = Color.GRAY;
    }
  
    /**
     * Returns the flag that determines whether or not the axis is drawn 
     * on the chart.
     * 
     * @return A boolean.
     * 
     * @see #setVisible(boolean) 
     */
    @Override
    public boolean isVisible() {
        return this.visible;
    }
    
    /**
     * Sets the flag that determines whether or not the axis is drawn on the
     * chart and sends an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param visible  the flag.
     * 
     * @see #isVisible() 
     */
    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        fireChangeEvent();
    }
    
    /**
     * Returns the axis range.  You can set the axis range manually or you can
     * rely on the autoAdjustRange feature to set the axis range to match
     * the data being plotted.
     * 
     * @return the axis range (never {@code null}).
     */
    @Override
    public Range getRange() {
        return this.range;
    }
  
    /**
     * Sets the axis range (bounds) and sends an {@link Axis3DChangeEvent} to 
     * all registered listeners.
     * 
     * @param range  the new range (must have positive length and 
     *     {@code null} is not permitted).
     */
    @Override
    public void setRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        if (range.getLength() <= 0.0) {
            throw new IllegalArgumentException(
                    "Requires a range with length > 0");
        }
        this.range = range;
        this.autoAdjustRange = false;
        fireChangeEvent();
    }
    
    /**
     * Sets the axis range and sends an {@link Axis3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param min  the lower bound for the range (requires min &lt; max).
     * @param max  the upper bound for the range (requires max &gt; min).
     */
    @Override
    public void setRange(double min, double max) {
        setRange(new Range(min, max));
    }
    
    /**
     * Returns the flag that controls whether or not the axis range is 
     * automatically updated in response to dataset changes.  The default 
     * value is <code>true</code>.
     * 
     * @return A boolean. 
     */
    public boolean isAutoAdjustRange() {
        return this.autoAdjustRange;
    }
    
    /**
     * Sets the flag that controls whether or not the axis range is 
     * automatically updated in response to dataset changes, and sends an
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param autoAdjust  the new flag value. 
     */
    public void setAutoAdjustRange(boolean autoAdjust) {
        this.autoAdjustRange = autoAdjust;
        fireChangeEvent();
    }
    
    /**
     * Returns the size of the lower margin that is added by the auto-range
     * calculation, as a percentage of the data range.  This margin is used to 
     * prevent data items from being plotted right at the edges of the chart.  
     * The default value is <code>0.05</code> (five percent).
     * 
     * @return The lower margin.
     */
    public double getLowerMargin() {
        return this.lowerMargin;
    }
    
    /**
     * Sets the size of the lower margin that will be added by the auto-range
     * calculation and sends an {@link Axis3DChangeEvent} to all registered
     * listeners.
     * 
     * @param margin  the margin as a percentage of the data range 
     *     (0.05 = five percent).
     * 
     * @see #setUpperMargin(double) 
     */
    public void setLowerMargin(double margin) {
        this.lowerMargin = margin;
        fireChangeEvent();
    }
    
    /**
     * Returns the size of the upper margin that is added by the auto-range
     * calculation, as a percentage of the data range.  This margin is used to 
     * prevent data items from being plotted right at the edges of the chart.  
     * The default value is <code>0.05</code> (five percent).
     * 
     * @return The upper margin.
     */
    public double getUpperMargin() {
        return this.upperMargin;
    }
    
    /**
     * Sets the size of the upper margin that will be added by the auto-range
     * calculation and sends an {@link Axis3DChangeEvent} to all registered
     * listeners.
     * 
     * @param margin  the margin as a percentage of the data range 
     *     (0.05 = five percent).
     * 
     * @see #setLowerMargin(double) 
     */
    public void setUpperMargin(double margin) {
        this.upperMargin = margin;
        fireChangeEvent();
    }
    
    /**
     * Returns the flag that determines whether or not the auto range 
     * mechanism should force zero to be included in the range.  The default
     * value is <code>false</code>.
     * 
     * @return A boolean.
     */
    public boolean getAutoRangeIncludesZero() {
        return this.autoRangeIncludesZero;
    }
    
    /**
     * Sets the flag that controls whether or not the auto range mechanism 
     * should force zero to be included in the axis range, and sends an
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param include  the new flag value.
     */
    public void setAutoRangeIncludeZero(boolean include) {
        this.autoRangeIncludesZero = include;
        fireChangeEvent();
    }
    
    /**
     * Returns the flag that controls the behaviour of the auto range 
     * mechanism when zero falls into the axis margins.  The default value
     * is <code>true</code>.
     * 
     * @return A boolean. 
     * 
     * @see #setAutoRangeStickyZero(boolean) 
     */
    public boolean getAutoRangeStickyZero() {
        return this.autoRangeStickyZero;
    }
    
    /**
     * Sets the flag that controls the behaviour of the auto range mechanism 
     * when zero falls into the axis margins.  If <code>true</code>, when
     * zero is in the axis margin the axis range is truncated at zero.  If
     * <code>false</code>, there is no special treatment.
     * 
     * @param sticky  the new flag value. 
     */
    public void setAutoRangeStickyZero(boolean sticky) {
        this.autoRangeStickyZero = sticky;
        fireChangeEvent();
    }
    
    /**
     * Returns the default range used when the <code>autoAdjustRange</code>
     * flag is <code>true</code> but the dataset contains no values.  The
     * default range is <code>(0.0 to 1.0)</code>, depending on the context
     * you may want to change this.
     * 
     * @return The default range (never {@code null}).
     * 
     * @see #setDefaultAutoRange(com.orsoncharts.android.Range) 
     */
    public Range getDefaultAutoRange() {
        return this.defaultAutoRange;
    }
    
    /**
     * Sets the default range used  when the <code>autoAdjustRange</code>
     * flag is <code>true</code> but the dataset contains no values, and sends
     * an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param range  the range ({@code null} not permitted).
     *
     * @see #getDefaultAutoRange() 
     */
    public void setDefaultAutoRange(Range range) {
        this.defaultAutoRange = range;
        fireChangeEvent();
    }
  
    /**
     * Returns the tick selector, an object that is responsible for choosing
     * standard tick units for the axis.  The default value is a default
     * instance of {@link NumberTickSelector}.
     * 
     * @return The tick selector. 
     * 
     * @see #setTickSelector(TickSelector) 
     */
    public TickSelector getTickSelector() {
        return this.tickSelector;    
    }
    
    /**
     * Sets the tick selector and sends an {@link Axis3DChangeEvent} to all
     * registered listeners.
     * 
     * @param selector  the selector ({@code null} permitted).
     * 
     * @see #getTickSelector() 
     */
    public void setTickSelector(TickSelector selector) {
        this.tickSelector = selector;
        fireChangeEvent();
    }
    
    /**
     * Returns the tick size (to be used when the tick selector is 
     * {@code null}).
     * 
     * @return The tick size.
     */
    public double getTickSize() {
        return this.tickSize;
    }

    /**
     * Sets the tick size and sends a change event to all registered listeners.
     * 
     * @param tickSize  the new tick size.
     */
    public void setTickSize(double tickSize) {
        this.tickSize = tickSize;
        fireChangeEvent();
    }
    
    /**
     * Returns the tick label formatter.  The default value is
     * <code>DecimalFormat("0.00")</code>.
     * 
     * @return The tick label formatter (never {@code null}).
     */
    public Format getTickLabelFormatter() {
        return this.tickLabelFormatter;
    }
    
    /**
     * Sets the formatter for the tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param formatter  the formatter ({@code null} not permitted).
     */
    public void setTickLabelFormatter(Format formatter) {
        ArgChecks.nullNotPermitted(formatter, "formatter");
        this.tickLabelFormatter = formatter;
        fireChangeEvent();
    }
    
    /**
     * Returns the tick label factor, a multiplier for the label height to
     * determine the maximum number of tick labels that can be displayed.  
     * The default value is <code>1.4</code>.
     * 
     * @return The tick label factor. 
     */
    public double getTickLabelFactor() {
        return this.tickLabelFactor;
    }
    
    /**
     * Sets the tick label factor and sends an {@link Axis3DChangeEvent}
     * to all registered listeners.  This should be at least 1.0, higher values
     * will result in larger gaps between the tick marks.
     * 
     * @param factor  the factor. 
     */
    public void setTickLabelFactor(double factor) {
        this.tickLabelFactor = factor;
        fireChangeEvent();
    }
    
    /**
     * Returns the tick label offset, the gap between the tick marks and the
     * tick labels.  The default value is <code>5.0</code>.
     * 
     * @return The tick label offset.
     */
    public double getTickLabelOffset() {
        return this.tickLabelOffset;
    }
    
    /**
     * Sets the tick label offset and sends an {@link Axis3DChangeEvent} to
     * all registered listeners.
     * 
     * @param offset  the offset.
     */
    public void setTickLabelOffset(double offset) {
        this.tickLabelOffset = offset;
    }
    
    /**
     * Returns the length of the tick marks.  The default
     * value is <code>3.0</code>.
     * 
     * @return The length of the tick marks. 
     */
    public double getTickMarkLength() {
        return this.tickMarkLength;
    }
    
    /**
     * Sets the length of the tick marks and sends an {@link Axis3DChangeEvent}
     * to all registered listeners.  You can set this to <code>0.0</code> if
     * you prefer no tick marks to be displayed on the axis.
     * 
     * @param length  the length. 
     */
    public void setTickMarkLength(double length) {
        this.tickMarkLength = length;
        fireChangeEvent();
    }

    /**
     * Returns the stroke used to draw the tick marks.  The default value is
     * <code>BasicStroke(0.5f)</code>.
     * 
     * @return The tick mark stroke (never {@code null}).
     */
    public LineStyle getTickMarkStroke() {
        return this.tickMarkStroke;
    }
    
    /**
     * Sets the stroke used to draw the tick marks and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke ({@code null} not permitted).
     */
    public void setTickMarkStroke(LineStyle stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.tickMarkStroke = stroke;
        fireChangeEvent();
    }
    
    /**
     * Returns the paint used to draw the tick marks.  The default value is
     * <code>Color.GRAY</code>.
     * 
     * @return The tick mark paint. 
     */
    public int getTickMarkPaint() {
        return this.tickMarkPaint;
    }
    
    /**
     * Sets the paint used to draw the tick marks and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setTickMarkPaint(int paint) {
        this.tickMarkPaint = paint;
        fireChangeEvent();
    }

    /**
     * Adjusts the range by adding the lower and upper margins and taking into
     * account any other settings.
     * 
     * @param range  the range ({@code null} not permitted).
     * 
     * @return The adjusted range. 
     */
    private Range adjustedDataRange(Range range) {
        ArgChecks.nullNotPermitted(range, "range");
        double lm = range.getLength() * this.lowerMargin;
        double um = range.getLength() * this.upperMargin;
        double lowerBound = range.getMin() - lm;
        double upperBound = range.getMax() + um;
        // does zero fall in the margins?
        if (this.autoRangeStickyZero) {
            if (0.0 <= range.getMin() && 0.0 > lowerBound) {
                lowerBound = 0.0;
            }
            if (0.0 >= range.getMax() && 0.0 < upperBound) {
                upperBound = 0.0;
            }
        }
        return new Range(lowerBound, upperBound);
    }
    
    /**
     * Configures the axis to be used as the value axis for the specified
     * plot.  This method is used internally, you should not need to call it
     * directly.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    @Override
    public void configureAsValueAxis(CategoryPlot3D plot) {
        if (this.autoAdjustRange) {
            Range valueRange = plot.getRenderer().findValueRange(
                    plot.getDataset());
            if (valueRange != null) {
                this.range = adjustedDataRange(valueRange);
            } else {
                this.range = this.defaultAutoRange;
            }
        }
    }
    
    /**
     * Configures the axis to be used as the x-axis for the specified plot.  
     * This method is used internally, you should not need to call it
     * directly.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    @Override
    public void configureAsXAxis(XYZPlot plot) {
        if (this.autoAdjustRange) {
            Range xRange = plot.getRenderer().findXRange(plot.getDataset());
            if (xRange != null) {
                this.range = adjustedDataRange(xRange);
            } else {
                this.range = this.defaultAutoRange;
            }
        }
    }

    /**
     * Configures the axis to be used as the y-axis for the specified plot.  
     * This method is used internally, you should not need to call it
     * directly.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    @Override
    public void configureAsYAxis(XYZPlot plot) {
        if (this.autoAdjustRange) {
            Range yRange = plot.getRenderer().findYRange(plot.getDataset());
            if (yRange != null) {
                this.range = adjustedDataRange(yRange);
            } else {
                this.range = this.defaultAutoRange;
            }
        }
    }

    /**
     * Configures the axis to be used as the z-axis for the specified plot.  
     * This method is used internally, you should not need to call it
     * directly.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    @Override
    public void configureAsZAxis(XYZPlot plot) {
        if (this.autoAdjustRange) {
            Range zRange = plot.getRenderer().findZRange(plot.getDataset());
            if (zRange != null) {
                this.range = adjustedDataRange(zRange);
            } else {
                this.range = this.defaultAutoRange;
            }
        }
    }
    
    /**
     * Draws the axis using the supplied graphics device, with the
     * specified starting and ending points for the line.  This method is used
     * internally, you should not need to call it directly.
     *
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param pt0  the starting point ({@code null} not permitted).
     * @param pt1  the ending point ({@code null} not permitted).
     * @param opposingPt  an opposing point (to determine which side of the 
     *     axis line the labels should appear, {@code null} not permitted).
     * @param tickData  tick details ({@code null} not permitted).
     */
    @Override
    public void draw(Canvas canvas, Paint paint, Point2D pt0, Point2D pt1, 
            Point2D opposingPt, boolean labels, List<TickData> tickData) {
        
        if (!isVisible()) {
            return;
        }
        
        // draw a line for the axis
        LineStyle lineStroke = getLineStroke();
        lineStroke.applyToPaint(paint);
        paint.setColor(getLineColor());
        canvas.drawLine(pt0.getX(), pt0.getY(), pt1.getX(), pt1.getY(), paint);
        
        // draw the tick marks and labels
        Line2D axisLine = new Line2D(pt0, pt1);
        double maxTickLabelWidth = 0.0;
        for (TickData t : tickData) {
            Line2D perpLine = Utils2D.createPerpendicularLine(axisLine, 
                    t.getAnchorPt(), this.tickMarkLength 
                    + this.tickLabelOffset, opposingPt);
            
            if (this.tickMarkLength > 0.0) {
                Line2D tickLine = Utils2D.createPerpendicularLine(axisLine, 
                       t.getAnchorPt(), this.tickMarkLength, 
                       opposingPt);
                paint.setColor(this.tickMarkPaint);
                this.tickMarkStroke.applyToPaint(paint);
                canvas.drawLine(tickLine.getX1(), tickLine.getY1(), 
                        tickLine.getX2(), tickLine.getY2(), paint);
            }
            
            if (getTickLabelsVisible()) {
                double theta = Utils2D.calculateTheta(axisLine);
                double thetaAdj = theta + Math.PI / 2.0;
                if (thetaAdj < -Math.PI / 2.0) {
                    thetaAdj = thetaAdj + Math.PI;
                }
                if (thetaAdj > Math.PI / 2.0) {
                    thetaAdj = thetaAdj - Math.PI;
                }

                double perpTheta = Utils2D.calculateTheta(perpLine);  
                TextAnchor textAnchor = TextAnchor.CENTER_LEFT;
                if (Math.abs(perpTheta) > Math.PI / 2.0) {
                    textAnchor = TextAnchor.CENTER_RIGHT;
                } 
                getTickLabelFont().applyToPaint(paint);
                paint.setColor(getTickLabelPaint());
                String tickLabel = this.tickLabelFormatter.format(
                        t.getDataValue());
                maxTickLabelWidth = Math.max(maxTickLabelWidth, 
                        paint.measureText(tickLabel));
                TextUtils.drawRotatedString(tickLabel, canvas, paint, 
                        perpLine.getX2(), perpLine.getY2(), 
                        textAnchor, thetaAdj, textAnchor);
            }
        }

        // draw the axis label (if any)...
        if (getLabel() != null) {
            getLabelFont().applyToPaint(paint);
            paint.setColor(getLabelPaint());
            Line2D labelPosLine = Utils2D.createPerpendicularLine(axisLine, 0.5, 
                    this.tickMarkLength + this.tickLabelOffset 
                    + maxTickLabelWidth + 10.0, 
                    opposingPt);
            double theta = Utils2D.calculateTheta(axisLine);
            if (theta < -Math.PI / 2.0) {
                theta = theta + Math.PI;
            }
            if (theta > Math.PI / 2.0) {
                theta = theta - Math.PI;
            }
            TextUtils.drawRotatedString(getLabel(), canvas, paint, 
                    labelPosLine.getX2(), 
                    labelPosLine.getY2(), TextAnchor.CENTER, theta, 
                    TextAnchor.CENTER);
        }
    }

    /**
     * Converts a data value to world coordinates, taking into account the
     * current axis range (assumes the world axis is zero-based and has the
     * specified length).
     * 
     * @param value  the data value (in axis units).
     * @param length  the length of the (zero based) world axis.
     * 
     * @return A world coordinate.
     */
    @Override
    public double translateToWorld(double value, double length) {
        return length * (value - this.range.getMin()) / this.range.getLength();
    }
  
    /**
     * Selects a tick size that is appropriate for drawing the axis from
     * {@code pt0} to {@code pt1}.
     * 
     * @param paint  the paint ({@code null} not permitted).
     * @param pt0  the axis starting point.
     * @param pt1  the axis ending point.
     * @param opposingPt  an opposing point (to determine on which side of the
     *     axis line the tick labels will be drawn).
     */
    @Override
    public double selectTick(Paint paint, Point2D pt0, Point2D pt1, 
            Point2D opposingPt) {
        
        if (this.tickSelector == null) {
            return this.tickSize;
        }
        
        // based on the font height, we can determine roughly how many tick
        // labels will fit in the length available
        double length = pt0.distance(pt1);
        getTickLabelFont().applyToPaint(paint);
        FontMetrics fm = paint.getFontMetrics();
        // the tickLabelFactor allows some control over how dense the labels
        // will be
        float height = -(fm.top - fm.bottom);
        int maxTicks = (int) (length / (height * this.tickLabelFactor));
        if (maxTicks > 2 && this.tickSelector != null) {
            this.tickSelector.select(this.range.getLength() / 2.0);
            // step through until we have too many ticks OR we run out of 
            // tick sizes
            int tickCount = (int) (this.range.getLength() 
                    / this.tickSelector.getCurrentTickSize());
            while (tickCount < maxTicks) {
                this.tickSelector.previous();
                tickCount = (int) (this.range.getLength() 
                        / this.tickSelector.getCurrentTickSize());
            }
            this.tickSelector.next();
            this.tickSize = this.tickSelector.getCurrentTickSize();
            this.tickLabelFormatter 
                    = this.tickSelector.getCurrentTickLabelFormat();
        } else {
            this.tickSize = Double.NaN;
        }
        return this.tickSize;
    }

    /**
     * Returns a list of tick info for the specified tick unit.
     * 
     * @param tickUnit  the tick unit.
     * 
     * @return A list of tick info. 
     */
    @Override
    public List<TickData> generateTickData(double tickUnit) {
        List<TickData> result = new ArrayList<TickData>();
        if (Double.isNaN(tickUnit)) {
            result.add(new TickData(0, getRange().getMin()));
            result.add(new TickData(1, getRange().getMax()));
        } else {
            double x = tickUnit * Math.ceil(this.range.getMin() / tickUnit);
            while (x <= this.range.getMax()) {
                result.add(new TickData(this.range.percent(x), x));
                x += tickUnit;
            }
        }
        return result;
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NumberAxis3D)) {
            return false;
        }
        NumberAxis3D that = (NumberAxis3D) obj;
        if (this.visible != that.visible) {
            return false;
        }
        if (!this.range.equals(that.range)) {
            return false;
        }
        if (this.autoAdjustRange != that.autoAdjustRange) {
            return false;
        }
        if (this.lowerMargin != that.lowerMargin) {
            return false;
        }
        if (this.upperMargin != that.upperMargin) {
            return false;
        }
        if (this.autoRangeIncludesZero != that.autoRangeIncludesZero) {
            return false;
        }
        if (this.autoRangeStickyZero != that.autoRangeStickyZero) {
            return false;
        }
        if (!this.defaultAutoRange.equals(that.defaultAutoRange)) {
            return false;
        }
        if (this.tickSize != that.tickSize) {
            return false;
        }
        if (!ObjectUtils.equals(this.tickSelector, that.tickSelector)) {
            return false;
        }
        if (!this.tickLabelFormatter.equals(that.tickLabelFormatter)) {
            return false;
        }
        if (this.tickLabelFactor != that.tickLabelFactor) {
            return false;
        }
        if (this.tickLabelOffset != that.tickLabelOffset) {
            return false;
        }
        if (this.tickMarkLength != that.tickMarkLength) {
            return false;
        }
        if (this.tickMarkPaint != that.tickMarkPaint) {
            return false;
        }
        if (!this.tickMarkStroke.equals(that.tickMarkStroke)) {
            return false;
        }
        
        return super.equals(obj);
    }

    /**
     * Returns a hash code for this instance.
     * 
     * @return A hash code. 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + ObjectUtils.hashCode(this.range);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.tickSize) 
                ^ (Double.doubleToLongBits(this.tickSize) >>> 32));
        hash = 59 * hash + ObjectUtils.hashCode(this.tickLabelFormatter);
        return hash;
    }
    
}
