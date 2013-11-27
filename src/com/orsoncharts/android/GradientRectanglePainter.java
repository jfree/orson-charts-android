/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android;

import java.io.Serializable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.orsoncharts.android.graphics3d.Point2D;
import com.orsoncharts.android.util.Anchor2D;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A {@link RectanglePainter} that can fill a rectangle with a gradient (the
 * gradient is generated using anchor points to fit any size rectangle on 
 * demand).  Instances of this class are immutable.
 */
public final class GradientRectanglePainter implements RectanglePainter, 
        Serializable {

    /** The first color for the gradient. */
    private int color1;
    
    /** The anchor point used to find the starting point for the gradient. */
    private Anchor2D anchor1;
    
    /** The first color for the gradient. */
    private int color2;
    
    /** The anchor point used to find the ending point for the gradient. */
    private Anchor2D anchor2;
    
    /**
     * Creates a new instance.  
     * <br><br>
     * NOTE:  some useful standard anchor points are defined in the 
     * {@link TitleAnchor} class.
     * 
     * @param color1  the first color for the gradient.
     * @param anchor1  the anchor point used to determine the starting point 
     *     for the gradient (<code>null</code> not permitted).
     * @param color2  the second color for the gradient.
     * @param anchor2  the anchor point used to determine the ending point for
     *     the gradient (<code>null</code> not permitted).
     */
    public GradientRectanglePainter(int color1, Anchor2D anchor1, 
            int color2, Anchor2D anchor2) {
        ArgChecks.nullNotPermitted(color1, "color1");
        ArgChecks.nullNotPermitted(anchor1, "anchor1");
        ArgChecks.nullNotPermitted(color2, "color2");
        ArgChecks.nullNotPermitted(anchor2, "anchor2");
        this.color1 = color1;
        this.anchor1 = anchor1;
        this.color2 = color2;
        this.anchor2 = anchor2;
    }
    
    /**
     * Returns the first color for the gradient (as specified via the 
     * constructor).  There is no setter method because instances of this class
     * are immutable.
     * 
     * @return The first color for the gradient. 
     */
    public int getColor1() {
        return this.color1;
    }
    
    /**
     * Returns the anchor point used to find the starting point for the 
     * gradient (as specified via the constructor).  There is no setter method 
     * because instances of this class are immutable.
     * 
     * @return The anchor point (never <code>null</code>). 
     */
    public Anchor2D getAnchor1() {
        return this.anchor1; 
    }
    
    /**
     * Returns the second color for the gradient (as specified via the 
     * constructor).  There is no setter method because instances of this class
     * are immutable.
     * 
     * @return The second color for the gradient (never <code>null</code>). 
     */
    public int getColor2() {
        return this.color2;
    }
    
    /**
     * Returns the anchor point used to find the ending point for the 
     * gradient (as specified via the constructor).  There is no setter method 
     * because instances of this class are immutable.
     * 
     * @return The anchor point (never <code>null</code>). 
     */
    public Anchor2D getAnchor2() {
        return this.anchor2; 
    }
    
//    /**
//     * Returns a <code>GradientPaint</code> instance with coordinates based 
//     * on the painter's anchor points and the supplied rectangle.
//     * 
//     * @param area  the area (<code>null</code> not permitted).
//     * 
//     * @return A gradient paint (never <code>null</code>). 
//     */
//    private GradientPaint createTransformedGradient(RectF area) {
//        // defer arg check
//        Point2D pt1 = this.anchor1.getAnchorPoint(area);
//        Point2D pt2 = this.anchor2.getAnchorPoint(area);
//        return new GradientPaint(pt1, this.color1, pt2, this.color2);
//    }
    
    /**
     * Fills the specified <code>area</code> with a gradient paint created
     * using the colors and anchor points of this painter.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param area  the area to fill (<code>null</code> not permitted).
     */
    @Override
    public void fill(Canvas g2, Paint paint, RectF area) {
//        Paint saved = g2.getPaint();
//        g2.setPaint(createTransformedGradient(area));
//        g2.fill(area);
//        g2.setPaint(saved);
    	//FIXME
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GradientRectanglePainter)) {
            return false;
        }
        GradientRectanglePainter that = (GradientRectanglePainter) obj;
        if (this.color1 != that.color1) {
            return false;
        }
        if (!this.anchor1.equals(that.anchor1)) {
            return false;
        }
        if (this.color2 != that.color2) {
            return false;
        }
        if (!this.anchor2.equals(that.anchor2)) {
            return false;
        }
        return true;
    }
    
}
