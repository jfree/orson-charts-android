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
     * @param element  the element ({@code null} permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     */
    public void setElement(TableElement element, Comparable<?> rowKey, 
            Comparable<?> columnKey) {
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
        for (int r = 0; r < this.elements.getXCount(); r++) {
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
     * @param canvas  the graphics target.
     * @param paint  the paint.
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
        for (int r = 0; r < this.elements.getXCount(); r++) {
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
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param bounds  the bounds.
     */
    @Override
    public void draw(Canvas canvas, Paint paint, RectF bounds) {
        List<RectF> positions = layoutElements(canvas, paint, bounds, null);
        for (int r = 0; r < this.elements.getXCount(); r++) {
            for (int c = 0; c < this.elements.getYCount(); c++) {
                TableElement element = this.elements.getValue(r, c);
                if (element == null) {
                    continue;
                }
                RectF pos = positions.get(r * this.elements.getYCount() + c);
                element.draw(canvas, paint, pos);
            }
        }        
    }
    
    /**
     * Tests this element for equality with an arbitrary object.
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
