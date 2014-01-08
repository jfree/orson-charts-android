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

package com.orsoncharts.android.graphics3d;

/**
 * A collection of utility methods for 2D geometry.
 */
public class Utils2D {
    
    private Utils2D() {
        // no need to instantiate
    }
    
    /**
     * Returns <code>true</code> if the specified value is spanned by the
     * two bounds, and <code>false</code> otherwise.
     * 
     * @param value  the value.
     * @param bound1  the first boundary.
     * @param bound2  the second boundary.
     * 
     * @return A boolean. 
     */
    public static boolean spans(double value, double bound1, double bound2) {
        return (bound1 < value && bound2 > value)
                || (bound1 > value && bound2 < value);
    }

    /**
     * Calculates twice the area of a triangle for points specified in 
     * counter-clockwise order (if the points are specified in clockwise order
     * the result will be negative).
     * 
     * @param a  the first point (<code>null</code> not permitted).
     * @param b  the second point (<code>null</code> not permitted).
     * @param c  the third point (<code>null</code> not permitted).
     * 
     * @return The area x 2.
     */
    public static double area2(Point2D a, Point2D b, Point2D c) {
        double ax = a.getX();
        double ay = a.getY();
        double bx = b.getX();
        double by = b.getY();
        double cx = c.getX();
        double cy = c.getY();
        return (ax - cx) * (by - cy) - (ay - cy) * (bx - cx);
    }
    
    /**
     * Returns the point in the center of the four supplied points.
     * 
     * @param pt0  point 0 (<code>null</code> not permitted).
     * @param pt1  point 1 (<code>null</code> not permitted).
     * @param pt2  point 2 (<code>null</code> not permitted).
     * @param pt3  point 3 (<code>null</code> not permitted).
     * 
     * @return  The center point (never <code>null</code>).
     */
    public static Point2D centerPoint(Point2D pt0, Point2D pt1, Point2D pt2, 
            Point2D pt3) {
        float x = (float) ((pt0.getX() + pt1.getX() + pt2.getX() + pt3.getX()) 
                / 4.0);
        float y = (float) ((pt0.getY() + pt1.getY() + pt2.getY() + pt3.getY()) 
                / 4.0);
        return new Point2D(x, y);
    }

    /**
     * Returns the dimensions of the smallest rectangle that could contain
     * the supplied points.
     * 
     * @param pts  an array of points (<code>null</code> not permitted).
     * 
     * @return The dimensions.
     */
    public static Dimension2D findDimension(Point2D[] pts) {
        double minx = Double.POSITIVE_INFINITY;
        double maxx = Double.NEGATIVE_INFINITY;
        double miny = Double.POSITIVE_INFINITY;
        double maxy = Double.NEGATIVE_INFINITY;
        for (Point2D pt : pts) {
            minx = Math.min(minx, pt.getX());
            maxx = Math.max(maxx, pt.getX());
            miny = Math.min(miny, pt.getY());
            maxy = Math.max(maxy, pt.getY());
        }
        return new Dimension2D((int) (maxx - minx), (int) (maxy - miny));
    }
  
    /**
     * Creates and returns a line that is perpendicular to the specified line.
     *
     * @param line  the reference line (<code>null</code> not permitted).
     * @param b  the base point, expressed as a percentage along the length of 
     *     the reference line.
     * @param size  the size or length of the perpendicular line.
     * @param opposingPoint  an opposing point, to define which side of the 
     *     reference line the perpendicular line will extend (<code>null</code> 
     *     not permitted).
     *
     * @return The perpendicular line.
     */
    public static Line2D createPerpendicularLine(Line2D line, double b, 
            double size, Point2D opposingPoint) {
        double dx = line.getX2() - line.getX1();
        double dy = line.getY2() - line.getY1();
        double length = Math.sqrt(dx * dx + dy * dy);
        double pdx = dy / length;
        double pdy = -dx / length;
        int ccw = line.relativeCCW(opposingPoint);
        Point2D pt1 = new Point2D((float) (line.getX1() + b * dx), 
                (float) (line.getY1() + b * dy));
        Point2D pt2 = new Point2D((float) (pt1.getX() - ccw * size * pdx), 
                (float) (pt1.getY() - ccw * size * pdy));
        return new Line2D(pt1, pt2);
    }
    
    /**
     * Creates and returns a line that is perpendicular to the specified 
     * line.
     * 
     * @param line  the reference line (<code>null</code> not permitted).
     * @param pt1  a point on the reference line (<code>null</code> not 
     *     permitted).
     * @param size  the length of the new line.
     * @param opposingPoint  an opposing point, to define which side of the 
     *     reference line the perpendicular line will extend (<code>null</code> 
     *     not permitted).
     * 
     * @return The perpendicular line. 
     */
    public static Line2D createPerpendicularLine(Line2D line, Point2D pt1, 
            double size, Point2D opposingPoint) {
        double dx = line.getX2() - line.getX1();
        double dy = line.getY2() - line.getY1();
        double length = Math.sqrt(dx * dx + dy * dy);
        double pdx = dy / length;
        double pdy = -dx / length;
        int ccw = line.relativeCCW(opposingPoint);
        Point2D pt2 = new Point2D((float) (pt1.getX() - ccw * size * pdx), 
                (float) (pt1.getY() - ccw * size * pdy));
        return new Line2D(pt1, pt2);
    }
  
    /**
     * Returns the angle of rotation (in radians) for the specified line.
     * 
     * @param line  the line (<code>null</code> not permitted).
     * 
     * @return The angle of rotation (in radians). 
     */
    public static double calculateTheta(Line2D line) {
        double dx = line.getX2() - line.getX1();
        double dy = line.getY2() - line.getY1();
        return Math.atan2(dy, dx);
    }

}
