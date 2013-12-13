/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
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
    
    /** Margin for chart resizing. */
    private float margin = 0.15f;

    /** A reusable rectangle that holds the chart bounds. */
    private RectF bounds;
    
    /** The paint object for drawing operations. */
    private Paint paint;
    
    /** A flag that is set to true while the surface exists. */
    private boolean surfaceExists;

    /** Coordinates of the last motion event ACTION_DOWN. */
    private float lastX, lastY;
    
    /** A gesture detector. */
    private ScaleGestureDetector scaleGestureDetector;
    
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
     * @param context
     */
    public ChartSurfaceView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Creates a new instance.
     * 
     * @param context
     * @param attrs
     */
    public ChartSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Creates a new instance.
     * 
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ChartSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    
    /**
     * Initialises the view.
     * 
     * @param context
     */
    private void init(Context context) {
        TextStyle.density = context.getResources().getDisplayMetrics().density;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.bounds = new RectF();
        getHolder().addCallback(this);
        this.chart = PieChartDemo1.createChart();
        this.chart.getViewPoint().setRho(16);
        this.chart.addChangeListener(this);
        this.scaleGestureDetector = new ScaleGestureDetector(context,
                new ScaleListener());        
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
     * @param chart  the chart (<code>null</code> not permitted).
     */
    public void setChart(Chart3D chart) {
        this.chart.removeChangeListener(this);
        this.chart = chart;
        this.chart.addChangeListener(this);
        Dimension2D size = new Dimension2D(getWidth(), getHeight());
        zoomToFit(size);
        submitChartRedraw();
    }
    
    @Override 
    public void onDraw(Canvas canvas) { 
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        this.bounds.set(0f, 0f, width, height);
        if (this.chart != null) {
            this.chart.draw(canvas, this.paint, this.bounds);
        }
    } 
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceExists = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        submitChartRedraw();    
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.surfaceExists = false;
    }

    @Override
    public void chartChanged(Chart3DChangeEvent event) {
        // submit chart redraw
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
        this.scaleGestureDetector.onTouchEvent(event);
        if (this.scaleGestureDetector.isInProgress()) {
            invalidate();
            return true;
        }
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
        return super.onTouchEvent(event);
    }

    private void processMovement(float x, float y) {
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
     * leave room for labels etc.
     * 
     * @param size  the target size (<code>null</code> not permitted).
     */    
    public void zoomToFit(Dimension2D size) {
        int w = (int) (size.getWidth() * (1.0 - this.margin));
        int h = (int) (size.getHeight() * (1.0 - this.margin));
        Dimension2D target = new Dimension2D(w, h);
        Dimension3D d3d = this.chart.getDimensions();
        float distance = this.chart.getViewPoint().optimalDistance(target, 
                d3d);
        this.chart.getViewPoint().setRho(distance);
        submitChartRedraw();        
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            double distance = chart.getViewPoint().getRho();
            distance = distance * 1 / detector.getScaleFactor();
            chart.getViewPoint().setRho(distance);
            submitChartRedraw();
            return true;
        }
        
    }
    
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            // TODO Auto-generated method stub
            return super.onDoubleTapEvent(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // TODO Auto-generated method stub
            super.onLongPress(e);
        }
        
    }

}
