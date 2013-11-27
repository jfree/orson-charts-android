package com.orsoncharts.android;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;

public class TextStyle {

	private Typeface typeface;
	
	private int size;
	
	public TextStyle(Typeface tf, int size) {
		this.typeface = tf;
		this.size = size;
	}
	
	public void applyToPaint(Paint paint) {
		paint.setTypeface(this.typeface);
		paint.setTextSize(this.size);
		paint.setStyle(Style.FILL);
	}
	
	// TODO: implement equals()
}
