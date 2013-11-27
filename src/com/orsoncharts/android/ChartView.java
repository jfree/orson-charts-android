package com.orsoncharts.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;

import com.orsoncharts.android.data.DefaultKeyedValues;
import com.orsoncharts.android.data.StandardPieDataset3D;
import com.orsoncharts.android.data.category.CategoryDataset3D;
import com.orsoncharts.android.data.category.StandardCategoryDataset3D;
import com.orsoncharts.android.graphics3d.ViewPoint3D;

public class ChartView extends View implements Chart3DChangeListener {

	private Paint paint;
	
	private Chart3D chart;
	
	private RectF bounds;
	
	private ScaleGestureDetector scaleGestureDetector;
	
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
		this.chart = createChart();
		this.chart.getViewPoint().setRho(20);
		this.chart.addChangeListener(this);
		scaleGestureDetector = new ScaleGestureDetector(context,
		        new ScaleListener());		
	}
	
	private Chart3D createChart() {
		StandardPieDataset3D dataset = new StandardPieDataset3D();
		dataset.add("A", 20);
		dataset.add("B", 30);
		dataset.add("C", 40);
		return Chart3DFactory.createPieChart("title", "subtitle", dataset);
	}
	
	private Chart3D createChart2() {
		return Chart3DFactory.createBarChart("Title", "Subtitle", 
				createDataset2(), "Row Axis", "Column Axis", "Value Axis");
	}
	
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    private static CategoryDataset3D createDataset2() {    
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
                
        DefaultKeyedValues s3 = new DefaultKeyedValues();
        s3.put("Jan", 7);
        s3.put("Feb", 7);
        s3.put("Mar", 10);
        s3.put("Apr", 13);
        s3.put("May", 17);
        s3.put("Jun", 20);
        s3.put("Jul", 22);
        s3.put("Aug", 21);
        s3.put("Sep", 19);
        s3.put("Oct", 15);
        s3.put("Nov", 10);
        s3.put("Dec", 8);
        dataset.addSeriesAsRow("London", s3);

        DefaultKeyedValues s1 = new DefaultKeyedValues();
        s1.put("Jan", 3);
        s1.put("Feb", 5);
        s1.put("Mar", 9);
        s1.put("Apr", 14);
        s1.put("May", 18);
        s1.put("Jun", 22);
        s1.put("Jul", 25);
        s1.put("Aug", 24);
        s1.put("Sep", 20);
        s1.put("Oct", 14);
        s1.put("Nov", 8);
        s1.put("Dec", 4);
        dataset.addSeriesAsRow("Geneva", s1);
        
        DefaultKeyedValues s2 = new DefaultKeyedValues();
        s2.put("Jan", 9);
        s2.put("Feb", 11);
        s2.put("Mar", 13);
        s2.put("Apr", 16);
        s2.put("May", 20);
        s2.put("Jun", 23);
        s2.put("Jul", 26);
        s2.put("Aug", 26);
        s2.put("Sep", 24);
        s2.put("Oct", 19);
        s2.put("Nov", 13);
        s2.put("Dec", 9);
        dataset.addSeriesAsRow("Bergerac", s2);

        DefaultKeyedValues s4 = new DefaultKeyedValues();
        s4.put("Jan", 22);
        s4.put("Feb", 22);
        s4.put("Mar", 20);
        s4.put("Apr", 17);
        s4.put("May", 14);
        s4.put("Jun", 11);
        s4.put("Jul", 11);
        s4.put("Aug", 12);
        s4.put("Sep", 14);
        s4.put("Oct", 17);
        s4.put("Nov", 19);
        s4.put("Dec", 21);
        dataset.addSeriesAsRow("Christchurch", s4);

        DefaultKeyedValues s5 = new DefaultKeyedValues();
        s5.put("Jan", 20);
        s5.put("Feb", 20);
        s5.put("Mar", 19);
        s5.put("Apr", 17);
        s5.put("May", 14);
        s5.put("Jun", 12);
        s5.put("Jul", 11);
        s5.put("Aug", 12);
        s5.put("Sep", 13);
        s5.put("Oct", 15);
        s5.put("Nov", 17);
        s5.put("Dec", 19);
        dataset.addSeriesAsRow("Wellington", s5);

        return dataset;
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
		chart.draw(canvas, paint, bounds);
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
