package com.orsoncharts.android;

import android.graphics.Paint;

public class LineStyle {

	private float width;
	
	private Paint.Cap cap;
	
	private Paint.Join join;
	
	public LineStyle(float width) {
		this(width, Paint.Cap.BUTT, Paint.Join.ROUND);
	}
	
	public LineStyle(float width, Paint.Cap cap, Paint.Join join) {
		this.width = width;
		this.cap = cap;
		this.join = join;
	}
	
	public void applyToPaint(Paint paint) {
		paint.setStrokeWidth(this.width);
		paint.setStrokeCap(this.cap);
		paint.setStrokeJoin(this.join);
	}
}
