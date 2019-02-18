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

package com.orsoncharts.android.graphics3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.util.ArgChecks;

/**
 * Provides a default implementation of the {@link Drawable3D} interface.
 * This is not used directly in Orson Charts, since the {@link Chart3D} class
 * implements the {@link Drawable3D} interface itself.  However, it is used
 * in testing to ensure that the <code>com.orsoncharts.graphics3d.*</code>
 * package can function on a stand-alone basis.
 */
public class DefaultDrawable3D implements Drawable3D {

    /** 
     * The default projection distance. 
     * 
     * @since 1.1
     */
    public static final float DEFAULT_PROJ_DIST = 1500f;

    /** The viewing point. */
    private ViewPoint3D viewPoint;
    
    /** The projection distance. */
    private float projDist;    

    /** The 3D world being drawn. */
    private World world;

    private Offset2D offset;

    /**
     * Creates a new instance to display the content of the specified
     * <code>world</code>.
     * 
     * @param world  the world to view ({@code null} not permitted).
     */
    public DefaultDrawable3D(World world) {
        ArgChecks.nullNotPermitted(world, "world");
        this.viewPoint = new ViewPoint3D((float) (3 * Math.PI / 2.0), 
                (float) Math.PI / 6, 40.0f, 0.0);
        this.world = world;
        this.projDist = DEFAULT_PROJ_DIST;
        this.offset = new Offset2D();
    }
    
    /**
     * Returns the dimensions of the 3D object.
     * 
     * @return The dimensions. 
     */
    @Override
    public Dimension3D getDimensions() {
        return new Dimension3D(1.0, 1.0, 1.0);  // FIXME
    }
    
    /**
     * Returns the view point.
     * 
     * @return The view point (never {@code null}).
     */
    @Override
    public ViewPoint3D getViewPoint() {
        return this.viewPoint;
    }

    /**
     * Sets the view point.
     * 
     * @param viewPoint  the view point ({@code null} not permitted).
     */
    @Override
    public void setViewPoint(ViewPoint3D viewPoint) {
        ArgChecks.nullNotPermitted(viewPoint, "viewPoint");
        this.viewPoint = viewPoint;
    }

    /** 
     * Returns the projection distance.  The default value is 
     * {@link #DEFAULT_PROJ_DIST}, higher numbers flatten out the perspective 
     * and reduce distortion in the projected image.
     * 
     * @return The projection distance.
     * 
     * @since 1.1
     */
    @Override
    public float getProjDistance() {
        return this.projDist;
    }
    
    /**
     * Sets the projection distance.  
     * 
     * @param dist  the distance.
     * 
     * @since 1.1
     */
    @Override
    public void setProjDistance(float dist) {
        this.projDist = dist;
    }

    @Override
    public Offset2D getTranslate2D() {
        return this.offset;
    }

    @Override
    public void setTranslate2D(Offset2D offset) {
        ArgChecks.nullNotPermitted(offset, "offset");
        this.offset = offset;
    }
    
    /**
     * Draws the current view to a <code>Graphics2D</code> instance.
     * 
     * @param canvas  the graphics target ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     * @param bounds  the bounds.
     */
    @Override
    public void draw(Canvas canvas, Paint paint, RectF bounds) {
        ArgChecks.nullNotPermitted(canvas, "canvas");
        paint.setColor(Color.WHITE);
        paint.setStyle(Style.FILL);
        canvas.drawRect(bounds, paint);
        canvas.translate(bounds.width() / 2, bounds.height() / 2);

        Point3D[] eyePts = this.world.calculateEyeCoordinates(this.viewPoint);

        Point2D[] pts = this.world.calculateProjectedPoints(this.viewPoint,
                    this.projDist);
        List<Face> facesInPaintOrder = new ArrayList<Face>(
                this.world.getFaces());

        // sort faces by z-order
        Collections.sort(facesInPaintOrder, new ZOrderComparator(eyePts));

        for (Face f : facesInPaintOrder) {
            double[] plane = f.calculateNormal(eyePts);
            double inprod = plane[0] * this.world.getSunX() + plane[1]
                    * this.world.getSunY() + plane[2] * this.world.getSunZ();
            double shade = (inprod + 1) / 2.0;
            if (Utils2D.area2(pts[f.getVertexIndex(0)],
                    pts[f.getVertexIndex(1)], pts[f.getVertexIndex(2)]) > 0) {
                int c = f.getColor();
                if (c != 0) {
                    Path p = new Path();
                    for (int v = 0; v < f.getVertexCount(); v++) {
                        if (v == 0) {
                            p.moveTo(pts[f.getVertexIndex(v)].getX(),
                                    pts[f.getVertexIndex(v)].getY());
                        }
                        else {
                            p.lineTo(pts[f.getVertexIndex(v)].getX(),
                                    pts[f.getVertexIndex(v)].getY());
                        }
                    }
                    p.close();
                    int sc = Color.argb(
                            Color.alpha(c),
                            (int) (Color.red(c) * shade), 
                            (int) (Color.green(c) * shade), 
                            (int) (Color.blue(c) * shade));
                    paint.setColor(sc);
                    paint.setStyle(Style.FILL_AND_STROKE);
                    canvas.drawPath(p, paint);
                }
            } 
        }
        canvas.translate(-bounds.width() / 2, -bounds.height() / 2);
    }
    
}
