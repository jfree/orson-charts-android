/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.table;

import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.orsoncharts.android.graphics3d.Dimension2D;

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
     * Calculates the preferred size for the element, with the only constraint
     * being that the element fits within the specified bounds.
     * 
     * @param g2  the graphics target.
     * @param paint  the paint settings.
     * @param bounds  the bounds.
     * 
     * @return The preferred size.
     */
    Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds);
    
    /**
     * Returns the preferred size of the element, subject to the supplied
     * constraints.
     * 
     * @param canvas  the graphics target (<code>null</code> not permitted).
     * @param paint  the paint (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     * @param constraints  the constraints (<code>null</code> permitted).
     * 
     * @return The preferred size. 
     */
    Dimension2D preferredSize(Canvas canvas, Paint paint, RectF bounds, 
            Map<String, Object> constraints);
    
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
     * @param canvas  the graphics target (<code>null</code> not permitted).
     * @param paint  the paint (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     */
    void draw(Canvas canvas, Paint paint, RectF bounds);
    
}
