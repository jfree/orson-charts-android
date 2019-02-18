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

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.orsoncharts.android.Range;
import com.orsoncharts.android.TextStyle;
import com.orsoncharts.android.graphics3d.Point2D;

/**
 * An interface that must be supported by axes for 3D plots.
 */
public interface Axis3D {

    /**
     * Returns the font that is used to display the main axis label.
     * 
     * @return The font (never {@code null}).
     */
    TextStyle getLabelFont();
    
    /**
     * Sets the font for the axis label (the main label, not the tick labels)
     * and sends a {@link Axis3DChangeEvent} to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted).
     */
    void setLabelFont(TextStyle font);
    
    /**
     * Returns the font that is used to display the tick labels.
     * 
     * @return The font (never {@code null}).
     */
    TextStyle getTickLabelFont();
    
    /**
     * Sets the font for the tick labels and sends a {@link Axis3DChangeEvent} 
     * to all registered listeners.
     * 
     * @param font  the font ({@code null} not permitted).
     */
    void setTickLabelFont(TextStyle font);
    
    /**
     * Returns the axis range.
     * 
     * @return The axis range (never {@code null}).
     */
    Range getRange();

    /**
     * Sets the axis range and sends an {@link Axis3DChangeEvent} to all
     * registered listeners.
     * 
     * @param range  the range ({@code null} not permitted).
     */
    public void setRange(Range range);
  
    /**
     * Sets the axis range and sends an {@link Axis3DChangeEvent} to all
     * registered listeners.
     * 
     * @param min  the lower bound for the axis.
     * @param max  the upper bound for the axis.
     */
    public void setRange(double min, double max);
    
    /**
     * Translates a data value to a world coordinate.  Since we draw the charts
     * in a box that has one corner at <code>(0, 0, 0)</code>, we only need to 
     * know the length of the side of the box along which we are translating in 
     * order to do the calculation.
     * 
     * @param value  the data value.
     * @param length  the box side length.
     * 
     * @return The translated value. 
     */
    double translateToWorld(double value, double length);

    /**
     * Draws the axis along an arbitrary line (between <code>startPt</code> 
     * and <code>endPt</code>).  The opposing point is used as a reference
     * point to know on which side of the axis to draw the labels.
     * 
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param startPt  the starting point ({@code null} not permitted).
     * @param endPt  the end point ({@code null} not permitted)
     * @param opposingPt  an opposing point ({@code null} not permitted).
     * @param labels  draw labels?
     * @param tickData  the tick data.
     */
    void draw(Canvas canvas, Paint paint, Point2D startPt, Point2D endPt, 
            Point2D opposingPt, boolean labels, List<TickData> tickData);

    /**
     * Registers a listener so that it receives notification of changes to the
     * axis.
     * 
     * @param listener  the listener ({@code null} not permitted).
     */
    void addChangeListener(Axis3DChangeListener listener);
    
    /**
     * Deregisters a listener so that it no longer receives notification of
     * changes to the axis.
     * 
     * @param listener  the listener ({@code null} not permitted).
     */
    void removeChangeListener(Axis3DChangeListener listener);

}
