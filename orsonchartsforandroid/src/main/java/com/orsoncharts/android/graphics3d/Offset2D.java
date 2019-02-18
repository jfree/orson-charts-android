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

import java.io.Serializable;

/**
 * An offset <code>(dx, dy)</code> in two dimensional space.  Instances of 
 * this class are immutable.
 */
public final class Offset2D implements Serializable {
    
    /** Zero offset. */
    public static final Offset2D ZERO_OFFSET = new Offset2D(0, 0);
    
    /** The x-offset. */
    private float dx;
    
    /** The y-offset. */
    private float dy;
    
    /**
     * Default constructor (<code>(0, 0)</code>).
     */
    public Offset2D() {
        this(0.0f, 0.0f);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param dx  the x-offset.
     * @param dy  the y-offset.
     */
    public Offset2D(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }
    
    /**
     * Returns the x-offset.
     * 
     * @return The x-offset. 
     */
    public float getDX() {
        return this.dx;
    }
    
    /**
     * Returns the y-offset.
     * 
     * @return The y-offset. 
     */
    public float getDY() {
        return this.dy;
    }
    
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
        if (!(obj instanceof Offset2D)) {
            return false;
        }
        Offset2D that = (Offset2D) obj;
        if (this.dx != that.dx) {
            return false;
        }
        if (this.dy != that.dy) {
            return false;
        }
        return true;
    }

}
