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

package com.orsoncharts.android;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Code that is executed in a background thread to repaint a chart that is
 * displayed in a {@link ChartSurfaceView}.
 */
public class ChartRepainter implements Runnable {

    /** The surface holder. */
    private SurfaceHolder surfaceHolder;
    
    /** The chart view to be repainted. */
    private ChartSurfaceView view;
    
    /**
     * Creates a new instance.
     * 
     * @param surfaceHolder  the surface holder.
     * @param view  the view.
     */
    public ChartRepainter(SurfaceHolder surfaceHolder, 
            ChartSurfaceView view) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
    }

    /**
     * Performs the chart repaint.
     */
    @Override
    public void run() {
        Canvas c = null;
        try {
            c = this.surfaceHolder.lockCanvas();
            if (c != null) {
                synchronized (this.surfaceHolder) {
                    this.view.onDraw(c);
                    this.view.postInvalidate();
                }
            }
        } finally {
            if (c != null) {
                this.surfaceHolder.unlockCanvasAndPost(c);
            }
        }
    }
    
}
