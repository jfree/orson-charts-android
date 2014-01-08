/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/android/index.html
 * 
 * Redistribution of this source file is prohibited.
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
