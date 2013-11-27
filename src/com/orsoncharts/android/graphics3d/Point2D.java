package com.orsoncharts.android.graphics3d;

public class Point2D {
	
	private float x;
	
	private float y;
	
	public Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}

	public float distance(Point2D p) {
		double dx = p.x - this.x;
		double dy = p.y - this.y;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}
}
