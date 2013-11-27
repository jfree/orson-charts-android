/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.orsoncharts.android.util.ArgChecks;
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
    
    /** The color (never <code>null</code>). */
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
     * @param image  the image (<code>null</code> permitted).
     * @param imageFit  the fit (<code>null</code> permitted).
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
     * @return The image (possibly <code>null</code>). 
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
     * @param canvas  the graphics target (<code>null</code> not permitted).
     * @param bounds  the rectangle (<code>null</code> not permitted).
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
     * @param obj  the object (<code>null</code> permitted).
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
