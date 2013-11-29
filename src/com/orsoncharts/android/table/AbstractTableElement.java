/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.table;

import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A base class that can be used to implement a {@link TableElement}.
 */
public abstract class AbstractTableElement {
    
    /** The insets. */
    private Insets insets;
    
    /** The foreground paint. */
    private int foregroundPaint;
    
    /** The background paint (this can be <code>null</code>). */
    private int backgroundPaint;
    
    /**
     * Creates a new instance.
     */
    public AbstractTableElement() {
        this.insets = new Insets(2, 2, 2, 2);
        this.foregroundPaint = Color.BLACK;
        this.backgroundPaint = Color.argb(127, 255, 255, 255);
    }

    /**
     * Returns the insets.  The default value is 
     * <code>Insets(2, 2, 2, 2)</code>.
     * 
     * @return The insets (never <code>null</code>).
     */
    public Insets getInsets() {
        return this.insets;
    }
    
    /**
     * Sets the insets.
     * 
     * @param insets  the insets (<code>null</code> not permitted). 
     */
    public void setInsets(Insets insets) {
        ArgChecks.nullNotPermitted(insets, "insets");
        this.insets = insets;
    }
    
    /**
     * Returns the foreground paint.  The default value is <code>BLACK</code>.
     * 
     * @return The foreground paint. 
     */
    public int getForegroundPaint() {
        return this.foregroundPaint;
    }

    /**
     * Sets the foreground paint.
     * 
     * @param paint  the paint. 
     */
    public void setForegroundPaint(int paint) {
        this.foregroundPaint = paint;
    }
    
    /**
     * Returns the background paint.  The default value is <code>WHITE</code>.
     * 
     * @return The background paint. 
     */
    public int getBackgroundPaint() {
        return this.backgroundPaint;
    }

    /**
     * Sets the background paint.
     * 
     * @param paint  the paint. 
     */
    public void setBackgroundPaint(int paint) {
        this.backgroundPaint = paint;
    }
    
    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param g2  the graphics target.
     * @param bounds  the bounds.
     * 
     * @return The preferred size. 
     */
    public Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds) {
        return preferredSize(g2, paint, bounds, null);
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
    public abstract Dimension2D preferredSize(Canvas g2, Paint paint, RectF bounds, 
            Map<String, Object> constraints);
    
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
        if (!(obj instanceof AbstractTableElement)) {
            return false;
        }
        AbstractTableElement that = (AbstractTableElement) obj;
        if (!this.insets.equals(that.insets)) {
            return false;
        }
        if (this.backgroundPaint != that.backgroundPaint) {
            return false;
        }
        if (this.foregroundPaint != that.foregroundPaint) {
            return false;
        }
        return true;
    }
   
}
