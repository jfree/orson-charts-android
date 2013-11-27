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
	
	public ChartView(Context context) {
		super(context);
		init(context);
	}

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.bounds = new RectF();
		this.chart = PieChartDemo1.createChart();
		this.chart.getViewPoint().setRho(20);
		this.chart.addChangeListener(this);
		scaleGestureDetector = new ScaleGestureDetector(context,
		        new ScaleListener());		
	}
	
	public void setChart(Chart3D chart) {
		this.chart = chart;
		Dimension2D size = new Dimension2D(getWidth(), getHeight());
		zoomToFit(size);
		invalidate();
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
		if (chart != null) {
            chart.draw(canvas, paint, bounds);
		}
    }

	@Override
	public boolean onTouchEvent(MotionEvent event)  {
		scaleGestureDetector.onTouchEvent(event);
		if (scaleGestureDetector.isInProgress()) {
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


	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
		    double distance = chart.getViewPoint().getRho();
			distance = distance * 1 / detector.getScaleFactor();
			chart.getViewPoint().setRho(distance);
			return true;
		}
		
	}
}
