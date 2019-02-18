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

package com.orsoncharts.android.plot;

import java.util.EventObject;
import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.data.Dataset3DChangeEvent;
import com.orsoncharts.android.util.ArgChecks;

/**
 * An event used to signal a change to a {@link Plot3D}.  Any object that
 * implements the {@link Plot3DChangeListener} interface can register with a 
 * plot to receive change notifications.  By default, the {@link Chart3D}
 * object will register with the plot it manages to monitor changes to the plot
 * and its subcomponents.
 */
public class Plot3DChangeEvent extends EventObject {

    /** The plot. */
    private Plot3D plot;
  
    /**
     * Creates a new event.  The <code>source</code> of the event can be
     * either the plot instance or another event that was received by the
     * plot (for example, a {@link Dataset3DChangeEvent}).
     * 
     * @param source  the event source ({@code null} not permitted).
     * @param plot  the plot ({@code null} not permitted).
     */
    public Plot3DChangeEvent(Object source, Plot3D plot) {
        super(source);
        ArgChecks.nullNotPermitted(plot, "plot");
        this.plot = plot;
    }
 
    /**
     * Returns the plot from which the event came.
     * 
     * @return The plot (never {@code null}).
     */
    public Plot3D getPlot() {
        return this.plot;
    }
}
