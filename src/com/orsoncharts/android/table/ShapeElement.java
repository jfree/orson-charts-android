/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/android/index.html
 * 
 * Redistribution of this source file is prohibited.
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
        float w = Math.min(sw + insets.left + insets.right, bounds.width());
        float h = Math.min(sh + insets.top + insets.bottom, bounds.height());
        float x = bounds.centerX() - w / 2f;
        float y = bounds.centerY() - h / 2f;
        RectF pos = new RectF(x, y, x + w, y + h);
        result.add(pos);
        return result;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, RectF bounds) {
        paint.setStyle(Style.FILL);
        int backgroundColor = getBackgroundPaint();
        if (backgroundColor != 0) {
            paint.setColor(backgroundColor);
            canvas.drawRect(bounds, paint);
        }
        float dx = bounds.centerX() - this.shape.getWidth() / 2f;
        float dy = bounds.centerY() - this.shape.getHeight() / 2f;
        canvas.translate(dx, dy);
        paint.setColor(getForegroundPaint());
        this.shape.draw(canvas, paint);
        canvas.translate(-dx, -dy);
    }
    
}
