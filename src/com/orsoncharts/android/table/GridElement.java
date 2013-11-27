/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.orsoncharts.android.data.DefaultKeyedValues2D;
import com.orsoncharts.android.graphics3d.Dimension2D;

/**
 * A table element that contains a grid of elements.  
 */
public class GridElement extends AbstractTableElement implements TableElement,
        Serializable {

    /** Storage for the cell elements. */
    private DefaultKeyedValues2D<TableElement> elements;
    
    /**
     * Creates a new empty grid.
     */
    public GridElement() {
        this.elements = new DefaultKeyedValues2D<TableElement>();  
    }
    
    /**
     * Adds (or updates) a cell in the grid.
     * 
     * @param element  the element (<code>null</code> permitted).
     * @param rowKey  the row key (<code>null</code> not permitted).
     * @param columnKey  the column key (<code>null</code> not permitted).
     */
    public void setElement(TableElement element, Comparable rowKey, 
            Comparable columnKey) {
        // defer argument checking
        this.elements.setValue(element, rowKey, columnKey);
    }
    
    /**
     * Finds the cell dimensions.
     * 
     * @param g2  the graphics target (required to calculate font sizes).
     * @param bounds  the bounds.
     * 
     * @return The cell dimensions (result[0] is the widths, result[1] is the 
     *     heights). 
     */
    private double[][] findCellDimensions(Canvas g2, Paint paint, RectF bounds) {
        int rowCount = this.elements.getXCount();
        int columnCount = this.elements.getYCount();
        double[] widths = new double[columnCount];
        double[] heights = new double[rowCount];
        // calculate the maximum width for each column
        for (int r = 0; r < elements.getXCount(); r++) {
            for (int c = 0; c < this.elements.getYCount(); c++) {
                TableElement element = this.elements.getValue(r, c);
                if (element == null) {
                    continue;
                }
                Dimension2D dim = element.preferredSize(g2, paint, bounds);
                widths[c] = Math.max(widths[c], dim.getWidth());
                heights[r] = Math.max(heights[r], dim.getHeight());
            }
        }
        return new double[][] { widths, heights };
    }
    

    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (ignored for now).
     * 
     * @return The preferred size. 
     */
    @Override
    public Dimension2D preferredSize(Canvas canvas, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        Insets insets = getInsets();
        double[][] cellDimensions = findCellDimensions(canvas, paint, bounds);
        double[] widths = cellDimensions[0];
        double[] heights = cellDimensions[1];
        float w = insets.left + insets.right;
        for (int i = 0; i < widths.length; i++) {
            w = w + (float) widths[i];
        }
        float h = insets.top + insets.bottom;
        for (int i = 0; i < heights.length; i++) {
            h = h + (float) heights[i];
        }
        return new Dimension2D(w, h);
    }

    /**
     * Performs a layout of this table element, returning a list of bounding
     * rectangles for the element and its subelements.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * @param constraints  the constraints (if any).
     * 
     * @return A list of bounding rectangles. 
     */
    @Override
    public List<RectF> layoutElements(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        double[][] cellDimensions = findCellDimensions(g2, paint, bounds);
        double[] widths = cellDimensions[0];
        double[] heights = cellDimensions[1];
        List<RectF> result = new ArrayList<RectF>(
                this.elements.getXCount() * this.elements.getYCount());
        float y = bounds.top + getInsets().top;
        for (int r = 0; r < elements.getXCount(); r++) {
            float x = bounds.left + getInsets().left;
            for (int c = 0; c < this.elements.getYCount(); c++) {
                result.add(new RectF(x, y, x + (float) widths[c], y + (float) heights[r]));
                x += widths[c];
            }
            y = y + (float) heights[r];
        }
        return result;
    }

    /**
     * Draws the element within the specified bounds.
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     */
    @Override
    public void draw(Canvas canvas, Paint paint, RectF bounds) {
        double[][] cellDimensions = findCellDimensions(canvas, paint, bounds);
        double[] widths = cellDimensions[0];
        double[] heights = cellDimensions[1];
        float y = bounds.top + getInsets().top;
        for (int r = 0; r < elements.getXCount(); r++) {
            float x = bounds.left + getInsets().left;
            for (int c = 0; c < this.elements.getYCount(); c++) {
                TableElement element = this.elements.getValue(r, c);
                if (element == null) {
                    continue;
                }
                element.draw(canvas, paint, new RectF(x, y, x + (float) widths[c], 
                        y + (float) heights[r]));
                x += widths[c];
            }
            y = y + (float) heights[r];
        }        
    }
    
    /**
     * Tests this element for equality with an arbitrary object.
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
        if (!(obj instanceof GridElement)) {
            return false;
        }
        GridElement that = (GridElement) obj;
        if (!this.elements.equals(that.elements)) {
            return false;
        }
        return true;
    }
    
}
