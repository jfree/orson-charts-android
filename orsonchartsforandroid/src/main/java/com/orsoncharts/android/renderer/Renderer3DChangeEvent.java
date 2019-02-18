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

import java.util.EventObject;

/**
 * An event containing information about a change to a {@link Renderer3D}.
 * Any object that implements the {@link Renderer3DChangeListener} interface
 * can register with a renderer to receive change event notifications.  By 
 * default, the plot classes register with the renderer they manage in order
 * to monitor changes to the renderer.
 */
public class Renderer3DChangeEvent extends EventObject {

    private Renderer3D renderer;
  
    /**
     * Creates a new change event.
     * 
     * @param renderer  the renderer that changed ({@code null} not
     *         permitted). 
     */
    public Renderer3DChangeEvent(Renderer3D renderer) {
        this(renderer, renderer);
    }
  
    /**
     * Creates a new change event.
     * 
     * @param source  the source.
     * @param renderer  the renderer.
     */
    public Renderer3DChangeEvent(Object source, Renderer3D renderer) {
        super(source);
        this.renderer = renderer;
    }
 
    /**
     * Returns the renderer that the event relates to.
     * 
     * @return The renderer. 
     */
    public Renderer3D getRenderer() {
        return this.renderer;
    }
}
