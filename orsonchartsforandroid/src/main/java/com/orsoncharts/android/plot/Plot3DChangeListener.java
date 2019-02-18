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

import java.util.EventListener;
import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.Chart3DChangeEvent;

/**
 * An interface used to receive change events from {@link Plot3D} instances.
 * The {@link Chart3D} class will register with its plot to receive change
 * notifications - and upon receiving a change notification, it will pass it
 * on as a {@link Chart3DChangeEvent}.
 */
public interface Plot3DChangeListener extends EventListener {

    /**
     * Receives notification that a plot has changed.
     * 
     * @param event  event info. 
     */
    void plotChanged(Plot3DChangeEvent event);

}
