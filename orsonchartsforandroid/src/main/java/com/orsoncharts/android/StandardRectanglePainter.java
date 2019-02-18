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

package com.orsoncharts.android;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.orsoncharts.android.util.Fit2D;
import com.orsoncharts.android.util.Scale2D;

/**
 * A {@link RectanglePainter} that fills the rectangle with a 
 * color or image.  Instances of this class are immutable.
 * <br><br>
 * Note that it is possible to use gradient paint with this painter, but it is
 * usually better to use {@link GradientRectanglePainter} since it provides
 * options to transform the gradient to fit the chart background size.
 */
public class StandardRectanglePainter implements RectanglePainter, 
        Serializable {
    
    /** The color (never {@code null}). */
    private int color;
    
    /** A background image for the chart, if any. */
    private transient Bitmap image;
    
    private Fit2D fit;
    
    /**
     * Creates a new painter that will fill a rectangle with the specified
     * paint.
     * 
     * @param paint  the fill paint.
     */
    public StandardRectanglePainter(int paint) {
        this(paint, null, null);
    }
    
    /**
     * Creates a new painter that will draw an image within the specified
     * rectangle.
     * 
     * @param color  the background color.
     * @param image  the image ({@code null} permitted).
     * @param imageFit  the fit ({@code null} permitted).
     */
    public StandardRectanglePainter(int color, Bitmap image, Fit2D imageFit) {
        this.color = color;
        this.image = image;
        this.fit = new Fit2D(TitleAnchor.TOP_CENTER, Scale2D.SCALE_BOTH);
        if (imageFit != null) {
            this.fit = imageFit;
        }
    }

    /**
     * Returns the color that will be used to fill rectangles.
     * 
     * @return The color.
     */
    public int getColor() {
        return this.color;
    }
    
    /**
     * Returns the image.
     * 
     * @return The image (possibly {@code null}).
     */
    public Bitmap getImage() {
        return this.image;
    }
    
    /**
     * Returns the image fit specification.
     * 
     * @return The image fit specification. 
     */
    public Fit2D getImageFit() {
        return this.fit;
    }
    
    /**
     * Fills the rectangle with the paint specified in the constructor.
     * 
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param bounds  the rectangle ({@code null} not permitted).
     */
    @Override
    public void fill(Canvas canvas, Paint paint, RectF bounds) {
        paint.setColor(this.color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(bounds, paint);
//        if (this.image != null) {
//            int w = this.image.getWidth(null);
//            int h = this.image.getHeight(null);
//            Rectangle2D imageBounds = this.fit.fit(new Dimension2D(w, h), bounds);
//            g2.drawImage(this.image, (int) imageBounds.getX(), 
//                    (int) imageBounds.getY(), (int) imageBounds.getWidth(),
//                    (int) imageBounds.getHeight(), null);
//        }
    }

    /**
     * Tests this painter for equality with an arbitrary object.
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
        if (!(obj instanceof StandardRectanglePainter)) {
            return false;
        }
        StandardRectanglePainter that = (StandardRectanglePainter) obj;
        if (this.color != that.color) {
            return false;
        }
        return true;
    }

}
