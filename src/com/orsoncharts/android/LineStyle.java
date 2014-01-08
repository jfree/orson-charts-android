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

package com.orsoncharts.android;

import com.orsoncharts.android.util.ArgChecks;

import android.graphics.Paint;

/**
 * Represents a line style (similar to Java2D's BasicStroke).
 */
public class LineStyle {

    /**
     * A standard line style (solid line, 1 unit width).
     */
    public static final LineStyle STANDARD = new LineStyle(1.0f, 
            Paint.Cap.BUTT, Paint.Join.ROUND);

    /** The line width. */
    private float width;

    /** The cap style. */
    private Paint.Cap cap;

    /** The join style. */
    private Paint.Join join;

    /**
     * Creates a new line style with the specified width.
     * 
     * @param width  the width.
     */
    public LineStyle(float width) {
        this(width, Paint.Cap.BUTT, Paint.Join.ROUND);
    }

    /**
     * Creates a new line style with the specified attributes.
     * 
     * @param width  the width.
     * @param cap  the line cap style (<code>null</code> not permitted).
     * @param join  the join style (<code>null</code> not permitted).
     */
    public LineStyle(float width, Paint.Cap cap, Paint.Join join) {
        ArgChecks.nullNotPermitted(cap, "cap");
        ArgChecks.nullNotPermitted(join, "join");
        this.width = width;
        this.cap = cap;
        this.join = join;
    }

    /**
     * Updates the supplied paint to use this line style.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public void applyToPaint(Paint paint) {
        paint.setStrokeWidth(this.width);
        paint.setStrokeCap(this.cap);
        paint.setStrokeJoin(this.join);
    }
}
