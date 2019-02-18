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

import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.Fit2D;

/**
 * A table element that displays a list of sub-elements in a flow layout.
 */
public class FlowElement extends AbstractTableElement implements TableElement,
        ContainerElement, Serializable {

    /** The sub-elements in this flow. */
    private List<TableElement> elements;
    
    /** The horizontal alignment of each row. */
    private HAlign horizontalAlignment;

    /** 
     * The horizontal gap between elements on the same line. 
     */
    private int hgap;
    
    /**
     * Creates a new instance (equivalent to 
     * <code>new FlowElement(HAlign.CENTER, 2)</code>).
     */
    public FlowElement() {
        this(HAlign.CENTER, 2);
    }
    
    /**
     * Creates a new instance with the specified attributes.
     * 
     * @param alignment  the horizontal alignment of the elements within
     *     each row ({@code null} not permitted).
     * @param hgap  the gap between elements.
     * 
     * @since 1.1
     */
    public FlowElement(HAlign alignment, int hgap) {
        super();
        ArgChecks.nullNotPermitted(alignment, "alignment");
        this.elements = new ArrayList<TableElement>();
        this.horizontalAlignment = alignment;
        this.hgap = hgap;
    }
    
    /**
     * Returns the horizontal gap between elements.
     * The default value is <code>2</code>.
     * 
     * @return The horizontal gap. 
     */
    public int getHGap() {
        return this.hgap;
    }
    
    /**
     * Sets the horizontal gap between elements.
     * 
     * @param gap  the gap. 
     */
    public void setHGap(int gap) {
        this.hgap = gap;
    }
    
    /**
     * Returns the horizontal alignment of items within rows.  The default
     * value is {@link HAlign#CENTER}.
     * 
     * @return The horizontal alignment (never {@code null}).
     * 
     * @since 1.1
     */
    public HAlign getHorizontalAlignment() {
        return this.horizontalAlignment;
    }
    
    /**
     * Sets the horizontal alignment.
     * 
     * @param alignment  the alignment ({@code null} not permitted).
     * 
     * @since 1.1
     */
    public void setHorizontalAlignment(HAlign alignment) {
        ArgChecks.nullNotPermitted(alignment, "alignment");
        this.horizontalAlignment = alignment;
    }
    
    /**
     * Returns a (new) list containing the elements in this flow layout.
     * 
     * @return A list containing the elements (possibly empty, but never 
     *     {@code null}).
     */
    public List<TableElement> getElements() {
        return new ArrayList<TableElement>(this.elements);
    }
    
    /**
     * Adds a sub-element to the list.
     * 
     * @param element  the element ({@code null} not permitted).
     */
    @Override
    public void addElement(TableElement element) {
        ArgChecks.nullNotPermitted(element, "element");
        this.elements.add(element);
    }
    
    /**
     * Returns info for as many elements as we can fit into one row.
     * 
     * @param first  the index of the first element.
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return A list of elements and dimensions. 
     */
    private List<ElementInfo> rowOfElements(int first, 
            Canvas g2, Paint paint, RectF bounds) {
        List<ElementInfo> result = new ArrayList<ElementInfo>();
        int index = first;
        boolean full = false;
        double w = getInsets().left + getInsets().right;
        while (index < this.elements.size() && !full) {
            TableElement element = this.elements.get(index);
            Dimension2D dim = element.preferredSize(g2, paint, bounds);
            if (w + dim.getWidth() <= bounds.width() || index == first) {
                result.add(new ElementInfo(element, dim));
                w += dim.getWidth() + this.hgap;
                index++;
            } else {
                full = true;
            }
        }
        return result;
    }
    
    /**
     * Returns the height of the tallest element in the list.
     * 
     * @param elementInfoList  element info list
     * 
     * @return The height. 
     */
    private float calcRowHeight(List<ElementInfo> elementInfoList) {
        float result = 0.0f;
        for (ElementInfo elementInfo : elementInfoList) {
            result = Math.max(result, elementInfo.getDimension().getHeight());
        }
        return result;
    }
    
    /**
     * Calculates the total width of the elements that will form one row.
     * 
     * @param elementInfoList  the elements in the column.
     * @param hgap  the gap between elements.
     * 
     * @return The total height. 
     */
    private float calcRowWidth(List<ElementInfo> elementInfoList, 
            float hgap) {
        float result = 0.0f;
        for (ElementInfo elementInfo : elementInfoList) {
            result += elementInfo.getDimension().getWidth();
        }
        int count = elementInfoList.size();
        if (count > 1) {
            result += (count - 1) * hgap;
        }
        return result;
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
    public Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        Insets insets = getInsets();
        float width = insets.left + insets.right;
        float height = insets.top + insets.bottom;
        float maxRowWidth = 0.f;
        int elementCount = this.elements.size();
        int i = 0;
        while (i < elementCount) {
            // get one row of elements...
            List<ElementInfo> elementsInRow = rowOfElements(i, g2, 
                    paint, bounds);
            float rowHeight = calcRowHeight(elementsInRow);
            float rowWidth = calcRowWidth(elementsInRow, this.hgap);
            maxRowWidth = Math.max(rowWidth, maxRowWidth);
            height += rowHeight;
            i = i + elementsInRow.size();
        }
        width += maxRowWidth;
        return new Dimension2D(width, height);        
    }
    
    /**
     * Calculates the layout of the elements for the given bounds and 
     * constraints.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param constraints  the constraints (not used here).
     * 
     * @return A list of positions for the sub-elements. 
     */
    @Override
    public List<RectF> layoutElements(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        int elementCount = this.elements.size();
        List<RectF> result = new ArrayList<RectF>(elementCount);
        int i = 0;
        float x = bounds.left + getInsets().left;
        float y = bounds.top + getInsets().top;
        while (i < elementCount) {
            // get one row of elements...
            List<ElementInfo> elementsInRow = rowOfElements(i, g2, paint,
                    bounds);
            float height = calcRowHeight(elementsInRow);
            float width = calcRowWidth(elementsInRow, this.hgap);  
            if (this.horizontalAlignment == HAlign.CENTER) {
                x = bounds.centerX() - (width / 2.0f);
            } else if (this.horizontalAlignment == HAlign.RIGHT) {
                x = bounds.right - getInsets().right - width;
            }
            for (ElementInfo elementInfo : elementsInRow) {
                Dimension2D dim = elementInfo.getDimension();
                RectF position = new RectF(x, y, x + dim.getWidth(), y + height);
                result.add(position);
                x += position.width() + this.hgap;
            }
            i = i + elementsInRow.size();
            x = bounds.left + getInsets().left;
            y += height;
        }
        return result;

    }
    
    @Override
    public void draw(Canvas canvas, Paint paint, RectF bounds) {
        // find the preferred size of the flow layout
        Dimension2D prefDim = preferredSize(canvas, paint, bounds);
        
        // fit a rectangle of this dimension to the bounds according to the 
        // element anchor
        Fit2D fitter = Fit2D.getNoScalingFitter(getRefPoint());
        RectF dest = fitter.fit(prefDim, bounds);
        
        // perform layout within this bounding rectangle
        List<RectF> layoutInfo = layoutElements(canvas, paint, dest, null);
        for (int i = 0; i < this.elements.size(); i++) {
            RectF rect = layoutInfo.get(i);
            TableElement element = this.elements.get(i);
            element.draw(canvas, paint, rect);
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
        if (!(obj instanceof FlowElement)) {
            return false;
        }
        FlowElement that = (FlowElement) obj;
        if (this.hgap != that.hgap) {
            return false;
        }
        if (this.horizontalAlignment != that.horizontalAlignment) {
            return false;
        }
        if (!this.elements.equals(that.elements)) {
            return false;
        }
        return super.equals(obj);
    }
    
}
