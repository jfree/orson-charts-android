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

import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.util.ArgChecks;
import com.orsoncharts.android.util.RefPt2D;

/**
 * A base class that can be used to implement a {@link TableElement}.
 */
public abstract class AbstractTableElement {
    
    /** The reference point used to align the element when rendering. */
    private RefPt2D refPt;
    
    /** The insets. */
    private Insets insets;
    
    /** The foreground paint. */
    private int foregroundPaint;
    
    /** The background paint (this can be {@code null}). */
    private int backgroundPaint;
    
    /**
     * Creates a new instance.
     */
    public AbstractTableElement() {
        this.refPt = RefPt2D.CENTER;
        this.insets = new Insets(2, 2, 2, 2);
        this.foregroundPaint = Color.BLACK;
        this.backgroundPaint = Color.argb(127, 255, 255, 255);
    }

    /**
     * Returns the anchor point used to align the element with the bounding
     * rectangle within which it is drawn.  The default value is 
     * {@link RefPt2D#CENTER}.
     * 
     * @return The anchor point (never {@code null}).
     * 
     * @since 1.1
     */
    public RefPt2D getRefPoint() {
        return this.refPt;
    }
    
    /**
     * Sets the reference point.
     * 
     * @param refPt  the reference point ({@code null} not permitted).
     * 
     * @since 1.1
     */
    public void setRefPoint(RefPt2D refPt) {
        ArgChecks.nullNotPermitted(refPt, "refPt");
        this.refPt = refPt;
    }

    /**
     * Returns the insets.  The default value is 
     * <code>Insets(2, 2, 2, 2)</code>.
     * 
     * @return The insets (never {@code null}).
     */
    public Insets getInsets() {
        return this.insets;
    }
    
    /**
     * Sets the insets.
     * 
     * @param insets  the insets ({@code null} not permitted).
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
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * 
     * @return The preferred size. 
     */
    public Dimension2D preferredSize(Canvas canvas, Paint paint, RectF bounds) {
        return preferredSize(canvas, paint, bounds, null);
    }

    /**
     * Returns the preferred size of the element (including insets).
     * 
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     * @param constraints  the constraints (ignored for now).
     * 
     * @return The preferred size. 
     */
    public abstract Dimension2D preferredSize(Canvas canvas, Paint paint, 
            RectF bounds, Map<String, Object> constraints);
    
    /**
     * Tests this instance for equality with an arbitrary object.
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
