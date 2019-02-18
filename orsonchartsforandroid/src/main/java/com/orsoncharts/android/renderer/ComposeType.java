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

package com.orsoncharts.android.renderer;

import com.orsoncharts.android.renderer.xyz.SurfaceRenderer;

/**
 * An enumeration of the different methods used by renderers for composing the
 * items in a chart.  Most renderers work on a per-item basis, where the plot
 * iterates over the items in a dataset then asks the renderer to compose one 
 * item at a time.  An alternative approach is where the renderer does the
 * entire composition in one pass (the {@link SurfaceRenderer} does this,
 * since it plots a function rather than a dataset (performing sampling of
 * the function on-the-fly).
 */
public enum ComposeType {
    
    /** Compose individual items one at a time. */
    PER_ITEM,
    
    /** Compose all items at once. */
    ALL
    
}
