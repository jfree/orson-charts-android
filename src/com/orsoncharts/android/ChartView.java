/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.orsoncharts.android.demo.PieChartDemo1;
import com.orsoncharts.android.graphics3d.Dimension2D;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.ViewPoint3D;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A custom view for displaying a chart.
 */
public class ChartView extends View implements Chart3DChangeListener {

    /** The chart. */
    private Chart3D chart;

    /** The paint-brush to use while drawing the chart. */
    private Paint paint;
    
    /** The chart bounds (reusable object). */
    private RectF bounds;
    
    private ScaleGestureDetector scaleGestureDetector;
    
    private float margin = 0.15f;
    
    /**
     * Creates a new instance.
     * 
     * @param context  the context.
     */
    public ChartView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Creates a new instance.
     * 
     * @param context  the context.
     * @param attrs 
     */
    public ChartView(Context context, AttributeSet attrs) {
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
    public ChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * Initialises the view (this method is called from each of the 
     * constructors).
     * 
     * @param context  the context.
     */
    private void init(Context context) {
        TextStyle.density = context.getResources().getDisplayMetrics().density;
        //this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint = new Paint();
        this.bounds = new RectF();
        this.chart = PieChartDemo1.createChart();
        this.chart.getViewPoint().setRho(16);
        this.chart.addChangeListener(this);
        this.scaleGestureDetector = new ScaleGestureDetector(context,
                new ScaleListener());        
    }
    
    /**
     * Returns the chart.
     * 
     * @return The chart (never <code>null</code>).
     */
    public Chart3D getChart() {
        return this.chart;
    }
    
    /**
     * Sets the chart for display in the panel.
     * 
     * @param chart  the chart (<code>null</code> not permitted).
     */
    public void setChart(Chart3D chart) {
        ArgChecks.nullNotPermitted(chart, "chart");
        this.chart = chart;
        Dimension2D size = new Dimension2D(getWidth(), getHeight());
        zoomToFit(size);
        invalidate();
    }
    
    /**
     * Returns the margin (a percentage) to leave around the edges of the
     * chart for labels when using the zoomToFit() method.  The default
     * value is <code>0.15</code> (15 percent).
     * 
     * @return The margin.
     */
    public float getMargin() {
        return this.margin;
    }
    
    /**
     * Sets the margin.
     * 
     * @param margin  the margin.
     */
    public void setMargin(float margin) {
        this.margin = margin;
    }
    
    /**
     * Adjusts the viewing distance so that the chart fits the specified
     * size.  A margin is left (see {@link #getMargin()} around the edges to 
     * leave room for labels etc).
     * 
     * @param size  the target size (<code>null</code> not permitted).
     */    
    public void zoomToFit(Dimension2D size) {
        int w = (int) (size.getWidth() * (1.0f - this.margin));
        int h = (int) (size.getHeight() * (1.0f - this.margin));
        Dimension2D target = new Dimension2D(w, h);
        Dimension3D d3d = this.chart.getDimensions();
        float distance = this.chart.getViewPoint().optimalDistance(target, 
                d3d);
        this.chart.getViewPoint().setRho(distance);
        invalidate();        
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }
    
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        
        return specSize;
    }
    
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        return specSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        this.bounds.set(0f, 0f, width, height);
        if (this.chart != null) {
            this.chart.draw(canvas, this.paint, this.bounds);
        }
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
            invalidate();
            return true;
        }
        case MotionEvent.ACTION_UP: {
            invalidate();
            return true;
        }
        }
        return super.onTouchEvent(event);
    }

    private float lastX, lastY;
    
    private void processMovement(float x, float y) {
        float dx = x - this.lastX;
        float dy = y - this.lastY;
        this.lastX = x;
        this.lastY = y;
        ViewPoint3D vp = this.chart.getViewPoint();
        vp.panLeftRight(-dx / 100f);    
        vp.moveUpDown(-dy / 100f);
        
    }
    
    @Override
    public void chartChanged(Chart3DChangeEvent event) {
        invalidate();
    }

    private class ScaleListener 
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            double distance = chart.getViewPoint().getRho();
            distance = distance * 1 / detector.getScaleFactor();
            chart.getViewPoint().setRho(distance);
            return true;
        }
        
    }
}
