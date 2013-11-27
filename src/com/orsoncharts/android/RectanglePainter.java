/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
     * @param canvas  the graphics target (<code>null</code> not permitted).
     * @param bounds  the rectangle (<code>null</code> not permitted).
     */
    public void fill(Canvas canvas, Paint paint, RectF bounds);

}
