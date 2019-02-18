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
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Typeface;

import com.orsoncharts.android.LineStyle;
import com.orsoncharts.android.TextStyle;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.ObjectUtils;

/**
 * A base class that can be used to create an {@link Axis3D} implementation.
 * This class implements the core axis attributes as well as the change 
 * listener mechanism required to enable automatic repainting of charts.
 */
public abstract class AbstractAxis3D implements Axis3D, Serializable {
    
    /** The axis label (if {@code null}, no label is displayed). */
    private String label;
  
    /** The label font (never {@code null}). */
    private TextStyle labelFont;
    
    /** The color used to draw the axis label. */
    private int labelColor;
 
    /** The stroke used to draw the axis line. */
    private LineStyle lineStroke;

    /** The color used to draw the axis line. */
    private int lineColor;
  
    /** Draw the tick labels? */
    private boolean tickLabelsVisible;
    
    /** The font used to display tick labels (never {@code null}) */
    private TextStyle tickLabelFont;
    
    /** The tick label paint (never {@code null}). */
    private transient int tickLabelPaint;

    /** Storage for registered change listeners. */
    private transient List<Axis3DChangeListener> listenerList;
    
    /**
     * Creates a new label with the specified label.  If the supplied label
     * is {@code null}, the axis will be shown without a label.
     * 
     * @param label  the axis label ({@code null} permitted).
     */
    public AbstractAxis3D(String label) {
        this.label = label;
        this.labelFont = new TextStyle(Typeface.create(Typeface.SANS_SERIF, 
                Typeface.BOLD), 12);
        this.labelColor = Color.BLACK;
        this.lineStroke = new LineStyle(1.0f);
        this.lineColor = Color.GRAY;
        this.tickLabelsVisible = true;
        this.tickLabelFont = new TextStyle(Typeface.SANS_SERIF, 10);
        this.tickLabelPaint = Color.BLACK;
        this.listenerList = new ArrayList<Axis3DChangeListener>();
    }

    /**
     * Returns the axis label.
     * 
     * @return The axis label (possibly {@code null}).
     */
    public String getLabel() {
        return this.label;
    }
  
    /**
     * Sets the axis label and sends an {@link Axis3DChangeEvent} to all 
     * registered listeners.  If the supplied label is {@code null},
     * the axis will be drawn without a label.
     * 
     * @param label  the label ({@code null} permitted).
     */
    public void setLabel(String label) {
        this.label = label;
        fireChangeEvent();
    }

    /**
     * Returns the font used to display the main axis label.  The default value
     * is <code>Font("SansSerif", Font.BOLD, 12)</code>.
     * 
     * @return The font used to display the axis label (never {@code null}).
     */
    @Override
    public TextStyle getLabelFont() {
        return this.labelFont;
    }
   
    /**
     * Sets the font used to display the main axis label and sends an
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param font  the new font ({@code null} not permitted).
     */
    @Override
    public void setLabelFont(TextStyle font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.labelFont = font;
        fireChangeEvent();
    }

    /**
     * Returns the paint used for the label.  The default value is 
     * <code>Color.BLACK</code>.
     * 
     * @return The label paint. 
     */
    public int getLabelPaint() {
        return this.labelColor;
    }
    
    /**
     * Sets the paint used to draw the axis label and sends a 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param color  the color. 
     */
    public void setLabelPaint(int color) {
        this.labelColor = color;
        fireChangeEvent();
    }
    
    /**
     * Returns the stroke used to draw the axis line.
     * 
     * @return The stroke used to draw the axis line (never {@code null}).
     */
    public LineStyle getLineStroke() {
        return this.lineStroke;
    } 
  
    /**
     * Sets the stroke used to draw the axis line and sends a change event
     * to all registered listeners.
     * 
     * @param stroke  the new stroke ({@code null} not permitted).
     */
    public void setLineStroke(LineStyle stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.lineStroke = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the color used to draw the axis line.
     * 
     * @return The color used to draw the axis line. 
     */
    public int getLineColor() {
        return this.lineColor;
    }
  
    /**
     * Sets the color used to draw the axis line and sends a change event to 
     * all registered listeners.
     * 
     * @param color  the new color. 
     */
    public void setLineColor(int color) {
        this.lineColor = color;
        fireChangeEvent();
    }

    /**
     * Returns the flag that controls whether or not the tick labels are
     * drawn.  The default value is <code>true</code>.
     * 
     * @return A boolean.
     */
    public boolean getTickLabelsVisible() {
        return this.tickLabelsVisible;
    }
    
    /**
     * Sets the flag that controls whether or not the tick labels are drawn,
     * and sends an {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param visible  visible?
     */
    public void setTickLabelsVisible(boolean visible) {
        this.tickLabelsVisible = visible;
        fireChangeEvent();
    }
    
    /**
     * Returns the font used to display the tick labels.
     * 
     * @return The font (never {@code null}).
     */
    @Override
    public TextStyle getTickLabelFont() {
        return this.tickLabelFont;
    }
  
    /**
     * Sets the font used to display tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted).
     */
    @Override
    public void setTickLabelFont(TextStyle font) {
        ArgChecks.nullNotPermitted(font, "font");
        this.tickLabelFont = font;
        fireChangeEvent();
    }
    
    /**
     * Returns the foreground color for the tick labels.  The default value
     * is <code>Color.BLACK</code>.
     * 
     * @return The foreground color. 
     */
    public int getTickLabelPaint() {
        return this.tickLabelPaint;
    }
    
    /**
     * Sets the foreground color for the tick labels and sends an 
     * {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint.
     */
    public void setTickLabelPaint(int paint) {
        this.tickLabelPaint = paint;
        fireChangeEvent();
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
        if (!(obj instanceof AbstractAxis3D)) {
            return false;
        }
        AbstractAxis3D that = (AbstractAxis3D) obj;
        if (!ObjectUtils.equals(this.label, that.label)) {
            return false;
        }
        if (!this.labelFont.equals(that.labelFont)) {
            return false;
        }
        if (this.labelColor != that.labelColor) {
            return false;
        }
        if (!this.lineStroke.equals(that.lineStroke)) {
            return false;
        }
        if (this.lineColor != that.lineColor) {
            return false;
        }
        if (this.tickLabelsVisible != that.tickLabelsVisible) {
            return false;
        }
        if (!this.tickLabelFont.equals(that.tickLabelFont)) {
            return false;
        }
        if (this.tickLabelPaint != that.tickLabelPaint) {
            return false;
        }
        return true;
    }
    
    @Override
    public void addChangeListener(Axis3DChangeListener listener) {
        this.listenerList.add(listener);   
    }
  
    @Override
    public void removeChangeListener(Axis3DChangeListener listener) {
        this.listenerList.remove(listener);  
    }
  
    /**
     * Notifies all registered listeners that the plot has been modified.
     *
     * @param event  information about the change event.
     */
    public void notifyListeners(Axis3DChangeEvent event) {
        for (Axis3DChangeListener listener : this.listenerList) {
            listener.axisChanged(event);
        }
    }
  
    /**
     * Sends a {@link Axis3DChangeEvent} to all registered listeners.
     */
    protected void fireChangeEvent() {
        notifyListeners(new Axis3DChangeEvent(this));
    }

}
