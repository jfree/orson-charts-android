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
     * @param shape  the shape ({@code null} not permitted).
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
