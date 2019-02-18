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

package com.orsoncharts.android.graphics3d;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.orsoncharts.android.Chart3D;

/**
 * A three dimensional scene that can be viewed from an arbitrary viewpoint 
 * and rendered to any {@link Canvas} instance.  The {@link Chart3D} class 
 * implements this interface.
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
     * @return The view point (never {@code null}).
     */
    ViewPoint3D getViewPoint();
    
    /**
     * Sets a new view point.  Note that the <code>ViewPoint3D</code> class is
     * implemented so that its position and orientation can be updated directly,
     * so you should use this method only when you want to set an entirely
     * new view point.
     * 
     * @param viewPoint  the view point ({@code null} not permitted).
     */
    void setViewPoint(ViewPoint3D viewPoint);
    
    /**
     * Returns the projection distance.  The default value is 1500, higher 
     * numbers flatten out the perspective and reduce distortion in the
     * projected image.
     * 
     * @return The projection distance.
     * 
     * @since 1.1
     */
    float getProjDistance();
    
    /**
     * Sets the projection distance.  
     * 
     * @param dist  the distance.
     * 
     * @since 1.1
     */
    void setProjDistance(float dist);
    
    /**
     * Returns the 2D offset for the scene.
     * 
     * @return The translation (never {@code null}).
     */
    Offset2D getTranslate2D();
    
    /**
     * Sets the translation offset.
     * 
     * @param offset  the offset ({@code null} not permitted).
     */
    void setTranslate2D(Offset2D offset);
    
    /**
     * Draws the scene to the supplied <code>Graphics2D</code> target.
     * 
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param bounds  the bounds ({@code null} not permitted).
     */
    void draw(Canvas canvas, Paint paint, RectF bounds);
    
}
