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

/**
 * The dimensions for a 2D shape.  Instances of this class are immutable.
 */
public class Dimension2D {

    /** The width. */
    private float width;
    
    /** The height. */
    private float height;
    
    /**
     * Creates a new instance.
     * 
     * @param width  the width.
     * @param height  the height.
     */
    public Dimension2D(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Returns the width.
     * 
     * @return The width.
     */
    public float getWidth() {
        return this.width;
    }
    
    /**
     * Returns the height.
     * 
     * @return The height.
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * Returns a string representation of this dimension, primarily for
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return "Dimension2D(" + this.width + ", " + this.height + ")";
    }

}
