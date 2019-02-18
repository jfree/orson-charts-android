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
     * @param cap  the line cap style ({@code null} not permitted).
     * @param join  the join style ({@code null} not permitted).
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
     * @param paint  the paint ({@code null} not permitted).
     */
    public void applyToPaint(Paint paint) {
        paint.setStrokeWidth(this.width);
        paint.setStrokeCap(this.cap);
        paint.setStrokeJoin(this.join);
    }
}
