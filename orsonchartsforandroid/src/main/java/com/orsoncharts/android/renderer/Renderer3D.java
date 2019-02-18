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

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.plot.XYZPlot;

/**
 * A renderer is an object responsible for constructing objects in a 3D model
 * that correspond to data items in a dataset.  The renderer's methods will be 
 * called by the plot ({@link CategoryPlot3D} or {@link XYZPlot}) that it is 
 * assigned to.  
 * <p>
 * All renderers support a change listener mechanism so that registered 
 * listeners can be notified whenever any attribute of the renderer is modified.
 * Typically the plot that the renderer is assigned to will listen for 
 * change events, and pass these events along to the {@link Chart3D} object.
 * <p>
 * Renderers should implement the <code>java.io.Serializable</code> interface,
 * so that charts can be serialized and deserialized, but this is not a forced
 * requirement (if you never use serialization, it won't matter if you 
 * implement a renderer that does not support it).
 */
public interface Renderer3D {
    
    /**
     * Registers a listener to receive notification of changes to the
     * renderer.
     * 
     * @param listener  the listener ({@code null} not permitted).
     */
    void addChangeListener(Renderer3DChangeListener listener);
    
    /**
     * Deregisters a listener so that it no longer receives notification of
     * changes to the renderer.
     * 
     * @param listener  the listener ({@code null} not permitted).
     */
    void removeChangeListener(Renderer3DChangeListener listener);

}
