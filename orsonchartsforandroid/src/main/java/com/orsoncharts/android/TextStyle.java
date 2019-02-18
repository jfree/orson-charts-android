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

import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;

/**
 * A font specification.
 */
public class TextStyle {

    /** 
     * The screen density.  
     */
    public static float density = 1.0f;
    
    /** The typeface. */
    private Typeface typeface;
    
    /** The font size. */
    private float size;
    
    /**
     * Creates a new instance.
     * 
     * @param tf  the type face ({@code null} not permitted).
     * @param size  the size.
     */
    public TextStyle(Typeface tf, float size) {
        this.typeface = tf;
        this.size = size;
    }
    
    /**
     * Updates the supplied paint to use this text style.
     * 
     * @param paint  the paint ({@code null} not permitted).
     */
    public void applyToPaint(Paint paint) {
        paint.setTypeface(this.typeface);
        paint.setTextSize(this.size * TextStyle.density);
        paint.setStyle(Style.FILL);
    }
    
    // TODO: implement equals()
}
