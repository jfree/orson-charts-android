/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.graphics3d;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.orsoncharts.android.Chart3D;

/**
 * A three dimensional scene that can be viewed from an arbitrary viewpoint 
 * and rendered to any {@link Graphics2D} instance.  The {@link Chart3D} class 
 * implements this interface.
 * 
 * @see Panel3D
 */
public interface Drawable3D {

    /**
     * Returns the dimensions of the 3D object.
     * 
     * @return The dimensions. 
     */
    Dimension3D getDimensions();
    
    /**
     * Returns the view point.
     * 
     * @return The view point (never <code>null</code>).
     */
    ViewPoint3D getViewPoint();
    
    /**
     * Sets a new view point.  Note that the <code>ViewPoint3D</code> class is
     * implemented so that its position and orientation can be updated directly,
     * so you should use this method only when you want to set an entirely
     * new view point.
     * 
     * @param viewPoint  the view point (<code>null</code> not permitted).
     */
    void setViewPoint(ViewPoint3D viewPoint);
    
    /**
     * Returns the 2D offset for the scene.
     * 
     * @return The translation (never <code>null</code>). 
     */
    Offset2D getTranslate2D();
    
    /**
     * Sets the translation offset.
     * 
     * @param offset  the offset (<code>null</code> not permitted). 
     */
    void setTranslate2D(Offset2D offset);
    
    /**
     * Draws the scene to the supplied <code>Graphics2D</code> target.
     * 
     * @param canvas  the graphics target (<code>null</code> not permitted).
     * @param paint  the paint (<code>null</code> not permitted).
     * @param bounds  the bounds (<code>null</code> not permitted).
     */
    void draw(Canvas canvas, Paint paint, RectF bounds);
    
}
