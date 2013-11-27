package com.orsoncharts.android.graphics3d;

public class Line2D {
	
	private float x1;
	
    private float y1;
    
    private float x2;
    
    private float y2;
    
    public Line2D(Point2D pt1, Point2D pt2) {
    	this(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
    }
    
    public Line2D(float x1, float y1, float x2, float y2) {
    	this.x1 = x1;
    	this.y1 = y1;
    	this.x2 = x2;
    	this.y2 = y2;
    }

    public float getX1() {
    	return this.x1;
    }
    public float getY1() {
    	return this.y1;
    }
    public float getX2() {
    	return this.x2;
    }
    public float getY2() {
    	return this.y2;
    }
    
    public int relativeCCW(Point2D pt) {
    	return relativeCCW(pt.getX(), pt.getY());
    }
    
    public int relativeCCW(float px, float py) {
        float xx2 = x2 - x1;
        float yy2 = y2 - y1;
        px -= x1;
        py -= y1;
        double ccw = px * yy2 - py * xx2;
        if (ccw == 0.0) {
            ccw = px * xx2 + py * yy2;
            if (ccw > 0.0) {
                px -= xx2;
                py -= yy2;
                ccw = px * xx2 + py * yy2;
                if (ccw < 0.0) {
                    ccw = 0.0;
                }
            }
        }
        return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }
}
