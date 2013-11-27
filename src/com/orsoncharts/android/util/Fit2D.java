/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.util;

import java.io.Serializable;

import android.graphics.RectF;

import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.graphics3d.Point2D;

/**
 * A specification for the alignment and fitting of one rectangle (the source 
 * rectangle) with reference to another (the target rectangle).  Instances of
 * this class are immutable.
 * <br><br>
 * One application for this is to specify how the background image for a chart 
 * should be aligned and scaled.
 */
public class Fit2D implements Serializable {
    
    /** The anchor point for alignment. */
    private Anchor2D anchor;
    
    /** The scaling to apply. */
    private Scale2D scale;
    
    /**
     * Creates a new instance.
     * 
     * @param anchor  the anchor point (<code>null</code> not permitted).
     * @param scale  the scaling (<code>null</code> not permitted).
     */
    public Fit2D(Anchor2D anchor, Scale2D scale) {
        this.anchor = anchor;
        this.scale = scale;
    }
    
    /**
     * Fits a rectangle of the specified dimension to the target rectangle,
     * aligning and scaling according to the attributes of this instance.
     * 
     * @param srcDim  the dimensions of the source rectangle (<code>null</code>
     *     not permitted).
     * @param target  the target rectangle (<code>null</code> not permitted).
     * 
     * @return The bounds of the fitted rectangle (never <code>null</code>). 
     */
    public RectF fit(Dimension2D srcDim, RectF target) {
        RectF result = new RectF();
        if (this.scale == Scale2D.SCALE_BOTH) {
        	result.set(target);
            return result;
        }
        double width = srcDim.getWidth();
        if (this.scale == Scale2D.SCALE_HORIZONTAL) {
            width = target.width();
            if (!this.anchor.getRefPt().isHorizontalCenter()) {
                width -= 2 * this.anchor.getOffset().getDX();
            }
        }
        double height = srcDim.getHeight();
        if (this.scale == Scale2D.SCALE_VERTICAL) {
            height = target.height();
            if (!this.anchor.getRefPt().isVerticalCenter()) {
                height -= 2 * this.anchor.getOffset().getDY();
            }
        }
        Point2D pt = this.anchor.getAnchorPoint(target);
        double x = Double.NaN; 
        if (this.anchor.getRefPt().isLeft()) {
            x = pt.getX();
        } else if (this.anchor.getRefPt().isHorizontalCenter()) {
            x = target.centerX() - width / 2;
        } else if (this.anchor.getRefPt().isRight()) {
            x = pt.getX() - width;
        }
        double y = Double.NaN;
        if (this.anchor.getRefPt().isTop()) {
            y = pt.getY();
        } else if (this.anchor.getRefPt().isVerticalCenter()) {
            y = target.centerY() - height / 2;
        } else if (this.anchor.getRefPt().isBottom()) {
            y = pt.getY() - height;
        }
        result.set((float) x, (float) y, (float) (x + width), 
        		(float) (y + height));
        return result;
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
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
        if (!(obj instanceof Fit2D)) {
            return false;
        }
        Fit2D that = (Fit2D) obj;
        if (!this.anchor.equals(that.anchor)) {
            return false;
        }
        if (!this.scale.equals(that.scale)) {
            return false;
        }
        return true;
    }
}
