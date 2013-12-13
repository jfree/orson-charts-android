/* ========================
 * Orson Charts for Android
 * ========================
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

import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A table element that displays a list of sub-elements in a vertical flow 
 * layout.
 * 
 * @since 1.1
 */
public class VerticalFlowElement extends AbstractTableElement 
        implements ContainerElement, Serializable {

    /** The sub-elements in this flow. */
    private List<TableElement> elements;
    
    /** The vertical alignment of the contents of each column. */
    private VAlign verticalAlignment;

    /** 
     * The vertical gap between elements in the same column. 
     */
    private int vgap;
    
    /**
     * Creates a new instance (equivalent to 
     * <code>new VerticalFlowElement(VAlign.MIDDLE, 2)</code>).
     */
    public VerticalFlowElement() {
        this(VAlign.MIDDLE, 2);
    }

    /**
     * Creates a new instance.
     * 
     * @param alignment  the vertical alignment of columns (<code>null</code> 
     *         not permitted).
     * @param vgap  the gap between elements. 
     */
    public VerticalFlowElement(VAlign alignment, int vgap) {
        ArgChecks.nullNotPermitted(alignment, null);
        this.elements = new ArrayList<TableElement>();
        this.verticalAlignment = alignment;
        this.vgap = vgap;
    }
    
    /**
     * Returns the vertical alignment for the elements.
     * 
     * @return The vertical alignment (never <code>null</code>). 
     */
    public VAlign getVerticalAlignment() {
        return this.verticalAlignment;
    }
    
    /**
     * Sets the vertical alignment of elements within columns,
     * 
     * @param alignment  the alignment (<code>null</code> not permitted). 
     */
    public void setVerticalAlignment(VAlign alignment) {
        ArgChecks.nullNotPermitted(alignment, "alignment");
        this.verticalAlignment = alignment;
    }
    
    /**
     * Returns the vertical gap between elements.
     * 
     * @return The vertical gap. 
     */
    public int getVGap() {
        return this.vgap;
    }
    
    /**
     * Sets the vertical gap between elements.
     * 
     * @param vgap  the gap. 
     */
    public void setVGap(int vgap) {
        this.vgap = vgap;
    }
    
    /**
     * Returns a (new) list containing the elements in this flow layout.
     * 
     * @return A list containing the elements (possibly empty, but never 
     *     <code>null</code>). 
     */
    public List<TableElement> getElements() {
        return new ArrayList<TableElement>(this.elements);
    }
    
    /**
     * Adds a sub-element to the list.
     * 
     * @param element  the element (<code>null</code> not permitted).
     */
    @Override
    public void addElement(TableElement element) {
        ArgChecks.nullNotPermitted(element, "element");
        this.elements.add(element);
    }

    /**
     * Returns the preferred size for the element.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     * @param constraints  the layout constraints (ignored here).
     * 
     * @return The preferred size (never <code>null</code>). 
     */
    @Override
    public Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        Insets insets = getInsets();
        float width = insets.left + insets.right;
        float height = insets.top + insets.bottom;
        float maxColHeight = 0.0f;
        int elementCount = this.elements.size();
        int i = 0;
        while (i < elementCount) {
            // get one column of elements...
            List<ElementInfo> elementsInColumn = columnOfElements(i, g2, 
                    paint, bounds);
            float colWidth = calcColumnWidth(elementsInColumn);
            float colHeight = calcColumnHeight(elementsInColumn, this.vgap);
            maxColHeight = Math.max(colHeight, maxColHeight);
            width += colWidth;
            i = i + elementsInColumn.size();
        }
        height += maxColHeight;
        return new Dimension2D(width, height);
    }

    /**
     * Returns info for as many elements as we can fit into one column.
     * 
     * @param first  the index of the first element.
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return A list of elements and dimensions. 
     */
    private List<ElementInfo> columnOfElements(int first, 
            Canvas g2, Paint paint, RectF bounds) {
        List<ElementInfo> result = new ArrayList<ElementInfo>();
        int index = first;
        boolean full = false;
        double h = getInsets().top + getInsets().bottom;
        while (index < this.elements.size() && !full) {
            TableElement element = this.elements.get(index);
            Dimension2D dim = element.preferredSize(g2, paint, bounds);
            if (h + dim.getHeight() <= bounds.height() || index == first) {
                result.add(new ElementInfo(element, dim));
                h += dim.getHeight() + this.vgap;
                index++;
            } else {
                full = true;
            }
        }
        return result;
    }
    
    /**
     * Returns the width of the widest element in the list.
     * 
     * @param elementInfoList  element info list
     * 
     * @return The width. 
     */
    private float calcColumnWidth(List<ElementInfo> elementInfoList) {
        float result = 0.0f;
        for (ElementInfo elementInfo : elementInfoList) {
            result = Math.max(result, elementInfo.getDimension().getWidth());
        }
        return result;
    }
    
    /**
     * Calculates the total height of the elements that will form one column.
     * 
     * @param elementInfoList  the elements in the column.
     * @param vgap  the gap between elements.
     * 
     * @return The total height. 
     */
    private float calcColumnHeight(List<ElementInfo> elementInfoList, 
            float vgap) {
        float result = 0.0f;
        for (ElementInfo elementInfo : elementInfoList) {
            result += elementInfo.getDimension().getHeight();
        }
        int count = elementInfoList.size();
        if (count > 1) {
            result += (count - 1) * vgap;
        }
        return result;
    }
    
    @Override
    public List<RectF> layoutElements(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        int elementCount = this.elements.size();
        List<RectF> result = new ArrayList<RectF>(elementCount);
        int i = 0;
        float x = bounds.left + getInsets().left;
        float y = bounds.top + getInsets().top;
        while (i < elementCount) {
            // get one column of elements...
            List<ElementInfo> elementsInColumn = columnOfElements(i, g2, 
                    paint, bounds);
            float width = calcColumnWidth(elementsInColumn);
            float height = calcColumnHeight(elementsInColumn, this.vgap);  
            if (this.verticalAlignment == VAlign.MIDDLE) {
                y = bounds.centerY() - (height / 2.0f);
            } else if (this.verticalAlignment == VAlign.BOTTOM) {
                y = bounds.bottom - getInsets().bottom - height;
            }
            for (ElementInfo elementInfo : elementsInColumn) {
                Dimension2D dim = elementInfo.getDimension();
                RectF position = new RectF(x, y, width, dim.getHeight());
                result.add(position);
                y += position.height() + this.vgap;
            }
            i = i + elementsInColumn.size();
            x += width;
            y = bounds.top + getInsets().top;
        }
        return result;
    }

    /**
     * Draws the element and all of its subelements within the specified
     * bounds.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     */
    @Override
    public void draw(Canvas g2, Paint paint, RectF bounds) {
        //Shape savedClip = g2.getClip();
        //g2.clip(bounds);
        List<RectF> layoutInfo = layoutElements(g2, paint, bounds, null);
        for (int i = 0; i < this.elements.size(); i++) {
            RectF rect = layoutInfo.get(i);
            TableElement element = this.elements.get(i);
            element.draw(g2, paint, rect);
        }
        //g2.setClip(savedClip);
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
        if (!(obj instanceof VerticalFlowElement)) {
            return false;
        }
        VerticalFlowElement that = (VerticalFlowElement) obj;
        if (this.vgap != that.vgap) {
            return false;
        }
        if (this.verticalAlignment != that.verticalAlignment) {
            return false;
        }
        if (!this.elements.equals(that.elements)) {
            return false;
        }
        return super.equals(obj);
    }
    
}