/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;

import com.orsoncharts.android.graphics3d.Dimension2D;

/**
 * A table element that displays a shape.
 */
public class ShapeElement extends AbstractTableElement 
        implements TableElement {

    /** 
     * The shape (by convention, the shape should be centered on the point
     * (0, 0)). 
     */
    private Shape shape;
    
    /**
     * Creates a new shape element.
     * 
     * @param shape  the shape (<code>null</code> not permitted).
     * @param fillPaint  the fill paint.
     */
    public ShapeElement(Shape shape, int fillPaint) {
        super();
        this.shape = shape;
        setForegroundPaint(fillPaint);
        //setBackgroundPaint(new Color(0, 0, 0, 0)); // transparent
    }
    
    @Override
    public Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds) {
        return preferredSize(g2, paint, bounds, null);
    }

    @Override
    public Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        Insets insets = getInsets();
        float w = this.shape.getWidth();
        float h = this.shape.getHeight();
        return new ElementDimension(
        		Math.min(w + insets.left + insets.right, bounds.width()), 
                Math.min(h + insets.top + insets.bottom, bounds.height()));
    }

    @Override
    public List<RectF> layoutElements(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints) {
        List<RectF> result = new ArrayList<RectF>();
        Insets insets = getInsets();
        float sw = this.shape.getWidth();
        float sh = this.shape.getHeight();
        float w = (float) Math.min(sw + insets.left + insets.right,
                bounds.width());
        float h = (float) Math.min(sh + insets.top 
                + insets.bottom, bounds.height());
        float x = bounds.centerX() - w / 2f;
        float y = bounds.centerY() - h / 2f;
        RectF pos = new RectF(x, y, x + w, y + h);
        result.add(pos);
        return result;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, RectF bounds) {
//        Paint background = getBackgroundPaint();
//        if (background != null) {
//            g2.setPaint(getBackgroundPaint());
//            g2.fill(bounds);
//        }
        canvas.translate(bounds.centerX(), bounds.centerY());
        paint.setColor(getForegroundPaint());
        paint.setStyle(Style.FILL);
        this.shape.draw(canvas, paint);
        canvas.translate(-bounds.centerX(), -bounds.centerY());
    }
    
}
