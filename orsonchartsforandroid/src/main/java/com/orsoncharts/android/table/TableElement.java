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

package com.orsoncharts.android.table;

import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.util.RefPt2D;

/**
 * An element (typically a single cell) in a table.  This interface defines
 * methods for determining the preferred size of the element, for laying out
 * the element (including sub-elements if there are any), and drawing the
 * element within specified bounds.  Various kinds of table elements will be
 * used to construct interesting tables.
 * <br><br>
 * It is important that these methods are implemented in a stateless manner. 
 * There is some redundancy in calculation between the layout and drawing 
 * methods in order to preserve the statelessness, but it is important to 
 * ensure that table elements can be rendered to multiple targets 
 * simultaneously.
 * 
 */
public interface TableElement {
    
    /**
     * Calculates the preferred size for the element, with reference to the 
     * specified bounds.  The preferred size can exceed the bounds, but the
     * bounds might influence how sub-elements are sized and/or positioned.
     * For example, in a {@link FlowElement}, the width of the bounds will
     * determine when the flow layout wraps.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * 
     * @return The preferred size (never {@code null}).
     */
    Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds);
    
    /**
     * Returns the preferred size of the element, subject to the supplied
     * constraints.
     * 
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param constraints  the constraints ({@code null} permitted).
     * 
     * @return The preferred size. 
     */
    Dimension2D preferredSize(Canvas canvas, Paint paint, RectF bounds, 
            Map<String, Object> constraints);
    
    /**
     * Returns the reference point used to align the element with the bounding
     * rectangle within which it is drawn.
     * 
     * @return The anchor point (never {@code null}).
     */
    RefPt2D getRefPoint();
    
    /**
     * Performs a layout of this table element, returning a list of bounding
     * rectangles for the element and its subelements.
     * 
     * @param canvas  the graphics target.
     * @param paint  the paint (contains font settings).
     * @param bounds  the bounds.
     * @param constraints  the constraints (if any).
     * 
     * @return A list of bounding rectangles. 
     */
    List<RectF> layoutElements(Canvas canvas, Paint paint, RectF bounds, 
            Map<String, Object> constraints);

    /**
     * Draws the element within the specified bounds.
     * 
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     */
    void draw(Canvas canvas, Paint paint, RectF bounds);
    
}
