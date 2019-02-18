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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.orsoncharts.android.demo.PieChartDemo1;
import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.ViewPoint3D;

/**
 * A <code>SurfaceView</code> for displaying a chart.
 */
public class ChartSurfaceView extends SurfaceView 
        implements Chart3DChangeListener, SurfaceHolder.Callback {
    
    /** The chart displayed in the view. */
    private Chart3D chart;
    
    
    /** 
     * The minimum viewing distance (zooming in will not go closer than this).
     */
    private float minViewingDistance;
    
    /** Margin for chart resizing. */
    private float margin = 0.25f;

    /** A reusable rectangle that holds the chart bounds. */
    private RectF bounds;
    
    /** The paint object for drawing operations. */
    private Paint paint;
    
    /** A flag that is set to true while the surface exists. */
    private boolean surfaceExists;

    /** Coordinates of the last motion event ACTION_DOWN. */
    private float lastX, lastY;
    
    private ScaleAndRotateHandler scaleAndRotateHandler;
    
    /** An executor service that executes chart repaints. */
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    
    /** 
     * The last repaint submitted to the executor...when the next repaint is
     * triggered, we can try to cancel this one (if it hasn't started yet, 
     * there's no point running it).
     */
    private Future<?> lastFuture;

    /**
     * Creates a new instance.
     * 
     * @param context  the app context.
     */
    public ChartSurfaceView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Creates a new instance.
     * 
     * @param context  the context.
     * @param attrs  the attributes.
     */
    public ChartSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Creates a new instance.
     * 
     * @param context  the context.
     * @param attrs  the attributes.
     * @param defStyle  style.
     */
    public ChartSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    
    /**
     * Initialises the view (this method is called by each of the constructors).
     * 
     * @param context  the context.
     */
    private void init(Context context) {
        TextStyle.density = context.getResources().getDisplayMetrics().density;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.bounds = new RectF();
        getHolder().addCallback(this);
        this.chart = PieChartDemo1.createChart();
        this.minViewingDistance 
            = (float) (this.chart.getDimensions().getDiagonalLength() * 0.75);
        this.chart.getViewPoint().setRho(16);
        this.chart.addChangeListener(this);
        this.scaleAndRotateHandler = new ScaleAndRotateHandler();
    }
    
    /**
     * Returns the chart.
     * 
     * @return The chart.
     */
    public Chart3D getChart() {
        return this.chart;
    }
    
    /**
     * Sets the chart.
     * 
     * @param chart  the chart ({@code null} not permitted).
     */
    public void setChart(Chart3D chart) {
        this.chart.removeChangeListener(this);
        this.chart = chart;
        this.chart.addChangeListener(this);
        this.minViewingDistance 
            = (float) (chart.getDimensions().getDiagonalLength() * 0.75);        
        Dimension2D size = new Dimension2D(getWidth(), getHeight());
        zoomToFit(size);
        submitChartRedraw();
    }
    
    /**
     * Returns the minimum viewing distance.
     * 
     * @return The minimum viewing distance.
     */
    public float getMinViewingDistance() {
        return this.minViewingDistance;    
    }
    
    /**
     * Returns the margin.  The default value is <code>0.25f</code>.
     * 
     * @return The margin.
     * 
     * @see #setMargin(float)
     */
    public float getMargin() {
        return this.margin;
    }
    
    /**
     * Sets the margin.  This is the percentage that is subtracted from the
     * width and height to determine the target bounds
     * for the zoom-to-fit operation.
     * 
     * @param margin  the margin.
     * 
     * @see #setMargin(float)
     * @see #zoomToFit(Dimension2D)
     */
    public void setMargin(float margin) {
        this.margin = margin;
    }
    
    /**
     * Draws the chart to the supplied canvas.
     * 
     * @param canvas  the canvas.
     */
    @Override 
    public void onDraw(Canvas canvas) { 
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        this.bounds.set(0f, 0f, width, height);
        if (this.chart != null) {
            this.chart.draw(canvas, this.paint, this.bounds);
        }
    } 
    
    /**
     * Sets the <code>surfaceExists</code> flag.
     * 
     * @param holder  ignored.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceExists = true;
    }

    /**
     * Receives notification of a surface change and submits a redraw.
     * 
     * @param holder  the surface holder.
     * @param format  the format.
     * @param width  the width.
     * @param height  the height.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        submitChartRedraw();    
    }

    /**
     * Receives notification that the surface has been destroyed.
     * 
     * @param holder  ignored.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.surfaceExists = false;
    }

    /**
     * Receives notification of a chart change event and responds by scheduling
     * a repaint.
     * 
     * @param event  the change event.
     */
    @Override
    public void chartChanged(Chart3DChangeEvent event) {
        submitChartRedraw();
    }
    
    /**
     * Submits a chart repaint to the executor service.  If the previous
     * repaint is still pending, it is cancelled.
     */
    private void submitChartRedraw() {
        if (!this.surfaceExists) {
            return;
        }
        ChartRepainter t = new ChartRepainter(getHolder(), this);
        if (this.lastFuture != null) {
            this.lastFuture.cancel(false);
        }
        this.lastFuture = this.executor.submit(t);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        this.scaleAndRotateHandler.update(event);
        if (this.scaleAndRotateHandler.getTrackingCount() == 2) {
            double distance = this.chart.getViewPoint().getRho();
            distance = distance 
                    * 1 / this.scaleAndRotateHandler.getPinchScaleFactor();
            this.chart.getViewPoint().setRho(
                    Math.max(distance, this.minViewingDistance));
            this.chart.getViewPoint().roll(
                    -this.scaleAndRotateHandler.getRotation());
            this.lastX = Float.NaN;
            this.lastY = Float.NaN;
            submitChartRedraw();
            return true;
 
        } else {
            int action = event.getActionMasked();
        
            switch (action) {
            case MotionEvent.ACTION_DOWN: {
                this.lastX = event.getX();
                this.lastY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                int historySize = event.getHistorySize();
                this.chart.setNotify(false);
                for (int i = 0; i < historySize; i++) {
                    float x = event.getHistoricalX(i);
                    float y = event.getHistoricalY(i);
                    processMovement(x, y);
                }
                this.chart.setNotify(true);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                invalidate();
                return true;
            }
            }
        }
        return super.onTouchEvent(event);
    }

    private void processMovement(float x, float y) {
        if (Float.isNaN(this.lastX) || Float.isNaN(this.lastY)) {
            return;
        }
        float dx = x - this.lastX;
        float dy = y - this.lastY;
        this.lastX = x;
        this.lastY = y;
        ViewPoint3D vp = this.chart.getViewPoint();
        vp.panLeftRight(-dx / 100f);    
        vp.moveUpDown(-dy / 100f);
        
    }
    
    /**
     * Adjusts the viewing distance so that the chart fits the specified
     * size.  A margin is left (see {@link #getMargin()} around the edges to 
     * leave room for labels etc.)
     * 
     * @param size  the target size ({@code null} not permitted).
     */    
    public void zoomToFit(Dimension2D size) {
        zoomToFit(size.getWidth(), size.getHeight());
    }
    
    /**
     * Adjusts the viewing distance so that the chart fits the specified
     * size.  A margin is left (see {@link #getMargin()} around the edges to 
     * leave room for labels etc.)
     * 
     * @param width  the target width.
     * @param height  the target height.
     * 
     * @since 1.1
     */    
    public void zoomToFit(double width, double height) {
        int w = (int) (width * (1.0 - this.margin));
        int h = (int) (height * (1.0 - this.margin));
        Dimension2D target = new Dimension2D(w, h);
        Dimension3D d3d = this.chart.getDimensions();
        float distance = this.chart.getViewPoint().optimalDistance(target, 
                d3d, this.chart.getProjDistance());
        this.chart.getViewPoint().setRho(distance);
        submitChartRedraw();        
    }

}
