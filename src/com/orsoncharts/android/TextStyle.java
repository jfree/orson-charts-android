/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
     * @param tf  the type face (<code>null</code> not permitted).
     * @param size  the size.
     */
    public TextStyle(Typeface tf, float size) {
        this.typeface = tf;
        this.size = size;
    }
    
    /**
     * Updates the supplied paint to use this text style.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public void applyToPaint(Paint paint) {
        paint.setTypeface(this.typeface);
        paint.setTextSize(this.size * TextStyle.density);
        paint.setStyle(Style.FILL);
    }
    
    // TODO: implement equals()
}
