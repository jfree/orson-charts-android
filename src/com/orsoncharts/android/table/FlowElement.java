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

import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A table element that displays a list of sub-elements in a flow layout.
 */
public class FlowElement extends AbstractTableElement implements TableElement,
        Serializable {

    /** The sub-elements in this flow. */
    private List<TableElement> elements;
    
    /** The horizontal alignment. */
    private HAlign horizontalAlignment;

    /** 
     * The horizontal gap between elements on the same line, specified in 
     * Java2D units. 
     */
    private int hgap;
    
    /**
     * Creates a new instance.
     */
    public FlowElement() {
        this.elements = new ArrayList<TableElement>();
        this.horizontalAlignment = HAlign.CENTER;
        this.hgap = 2;
    }
    
    /**
     * Returns the horizontal gap between elements, specified in Java2D units.
     * 
     * @return The horizontal gap. 
     */
    public int getHGap() {
        return this.hgap;
    }
    
    /**
     * Sets the horizontal gap between elements.
     * 
     * @param gap  the gap (in Java2D units). 
     */
    public void setHGap(int gap) {
        this.hgap = gap;
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
    public void addElement(TableElement element) {
        ArgChecks.nullNotPermitted(element, "element");
        this.elements.add(element);
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
        double maxWidth = bounds.width();
        Insets insets = getInsets();
        float width = insets.left + insets.right;
        float height = insets.top + insets.bottom;
        float rowWidth = insets.left + insets.right;
        float rowHeight = 0.0f;
        boolean first = true;
        for (TableElement e : this.elements) {
            Dimension2D dim = e.preferredSize(canvas, paint, bounds, constraints);
            if (rowWidth + dim.getWidth() <= maxWidth) {
                rowWidth += dim.getWidth();
                if (first) {
                    first = false;
                } else {
                    rowWidth += this.hgap;
                }
                rowHeight = Math.max(dim.getHeight(), rowHeight);
            } else {
                width = Math.max(width, rowWidth);
                rowWidth = insets.left + insets.right + dim.getWidth();
                height += rowHeight;
                rowHeight = dim.getHeight();
            }
            width = Math.max(width, rowWidth);
        }
        height += rowHeight;
        return new Dimension2D(width, height);
    }

    /**
     * Extracts a line of elements.
     * 
     * @param firstElement  the index of the first element.
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return A line of elements. 
     */
    private ElementLine lineOfElements(int firstElement, Canvas g2, Paint paint,
            RectF bounds) {
        List<TableElement> elementsInLine = new ArrayList<TableElement>();
        List<Float> elementWidths = new ArrayList<Float>();
        float x = getInsets().left;
        float w = 0.0f;
        float h = 0.0f;
        for (int i = firstElement; i < this.elements.size(); i++) {
            TableElement e = this.elements.get(i);
            Dimension2D dim = e.preferredSize(g2, paint, bounds);
            if (x + dim.getWidth() < bounds.width() + 10) {
                elementsInLine.add(this.elements.get(i));
                elementWidths.add(dim.getWidth());
                w = dim.getWidth();
                if (i > firstElement) {
                    w += this.hgap;
                }
                h = Math.max(h, dim.getHeight());
            }
            x += dim.getWidth() + this.hgap;
        }
        if (elementsInLine.isEmpty()) {
            elementsInLine.add(this.elements.get(firstElement));
            w = bounds.width() - getInsets().left - getInsets().right;
            elementWidths.add(w);
        }
        return new ElementLine(elementsInLine, elementWidths, w, h);
    }
    
    @Override
    public List<RectF> layoutElements(Canvas canvas, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        List<RectF> result = new ArrayList<RectF>(
                this.elements.size());
        float x = bounds.left + getInsets().left;
        float y = bounds.top + getInsets().top;
        int i = 0;
        while (i < this.elements.size()) {
            ElementLine line = lineOfElements(i, canvas, paint, bounds);
            for (int elementIndex = 0; elementIndex < line.getElements().size();
                    elementIndex++) {
                // x will depend on horizontal alignment, the gap and the 
                // element index
                // y is already known
                float dx = calculateXOffset(elementIndex, line.getWidths(), 
                        this.hgap);
                RectF rect = new RectF(x + dx, y, 
                        x + line.getWidths().get(elementIndex), 
                        y + line.getHeight());
                result.add(rect);
            }
            i += line.getElements().size();
            y += line.getHeight();
        }
        return result;
    }

    private float calculateXOffset(int elementIndex, List<Float> widths, 
            int gap) {
        float x = 0.0f;
        for (int i = 0; i < elementIndex; i++) {
            x += widths.get(i).doubleValue();
        }
        if (elementIndex > 1) {
            x += gap * (elementIndex - 1);
        }
        return x;    
    }
    
    @Override
    public void draw(Canvas canvas, Paint paint, RectF bounds) {
        List<RectF> layoutInfo = layoutElements(canvas, paint, bounds, null);
        for (int i = 0; i < this.elements.size(); i++) {
            RectF rect = layoutInfo.get(i);
            TableElement element = this.elements.get(i);
            element.draw(canvas, paint, rect);
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
        if (!(obj instanceof FlowElement)) {
            return false;
        }
        FlowElement that = (FlowElement) obj;
        if (this.hgap != that.hgap) {
            return false;
        }
        if (!this.elements.equals(that.elements)) {
            return false;
        }
        return super.equals(obj);
    }
    
    /** 
     * A line of elements in the {@link FlowElement}.
     */
    private static class ElementLine {
        
        /** The line width. */
        private float width;
        
        /** The line height. */
        private float height;
        
        /** The elements in the line. */
        private List<TableElement> elements;
        
        /** The widths of the elements in the line. */
        private List<Float> widths;
        
        /**
         * Creates a new (empty) line.
         */
        public ElementLine() {
            this(new ArrayList<TableElement>(), new ArrayList<Float>(), 0.0f, 
                    0.0f);
        }
        
        /**
         * Creates a new line with...
         * 
         * @param elements
         * @param elementWidths
         * @param width
         * @param height 
         */
        public ElementLine(List<TableElement> elements, 
                List<Float> elementWidths, float width, float height) {
            this.elements = elements;
            this.widths = elementWidths;
            this.width = width;
            this.height = height;
        }
        
        /**
         * Returns the elements in the line.
         * 
         * @return The elements. 
         */
        public List<TableElement> getElements() {
            return this.elements;
        }
        
        /**
         * Returns the widths of the elements in the line.
         * 
         * @return The widths.
         */
        public List<Float> getWidths() {
            return this.widths;
        }
        
        /**
         * Returns the width of the line.
         * 
         * @return The width. 
         */
        public float getWidth() {
            return this.width;
        }
        
        /**
         * Returns the height of the line.
         * 
         * @return The height.
         */
        public float getHeight() {
            return this.height;
        }
    }
}
