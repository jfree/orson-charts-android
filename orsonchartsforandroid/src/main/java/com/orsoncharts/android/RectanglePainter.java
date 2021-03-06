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

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * An object that can paint a rectangular region with a color, gradient, image
 * or anything.  
 * <br><br>
 * NOTE: It is recommended that classes that implement this interface are 
 * designed to be <code>Serializable</code> and immutable.  Immutability is 
 * desirable because painters are assigned to {@link Chart3D} instances, and 
 * there is no change notification if the painter can be modified directly.
 */
public interface RectanglePainter {
    
    /**
     * Fills the specified rectangle (where "fill" is some arbitrary drawing
     * operation defined by the class that implements this interface).
     * 
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted)
     * @param bounds  the rectangle ({@code null} not permitted).
     */
    public void fill(Canvas canvas, Paint paint, RectF bounds);

}
