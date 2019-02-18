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

package com.orsoncharts.android;

import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.android.graphics3d.Face;
import com.orsoncharts.android.graphics3d.Object3D;
import com.orsoncharts.android.graphics3d.Point3D;
import com.orsoncharts.android.axis.TickData;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A chart box is the container within which the chart elements are drawn.
 * The six faces of the box created so that they are visible only when they 
 * do not obscure the content of the chart (generally the back three faces 
 * will be visible and the front three faces will not be visible, although from
 * some angles four faces will be visible at one time).  There is also support 
 * provided for specifying gridlines on the visible faces.
 */
public class ChartBox3D {

    private double xLength, yLength, zLength;
    private double xOffset, yOffset, zOffset;
    
    /** 
     * Tick info for the x-axis (or column axis). 
     */
    private List<TickData> xTicks;
    
    /** 
     * Tick info for the y-axis (or value axis). 
     */
    private List<TickData> yTicks;
    
    /** 
     * Tick info for the z-axis (or row axis). 
     */
    private List<TickData> zTicks;

    private int color;
    
    private Object3D object3D;

    private CBFace faceA;
    private CBFace faceB;
    private CBFace faceC;
    private CBFace faceD;
    private CBFace faceE;
    private CBFace faceF;

    /**
     * Creates a new chart box with the specified attributes.  When drawn, only
     * the faces at the rear of the box will be rendered, allowing the user to 
     * see the content of the box (typically the plot items).
     * 
     * @param xLength  the x-dimension.
     * @param yLength  the y-dimension.
     * @param zLength  the z-dimension.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * @param color  the color for the sides of the box.
     */
    public ChartBox3D(double xLength, double yLength, double zLength, 
            double xOffset, double yOffset,
            double zOffset, int color) {
        this(xLength, yLength, zLength, xOffset, yOffset, zOffset, color,
                new ArrayList<TickData>(0), new ArrayList<TickData>(0), 
                new ArrayList<TickData>(0));
    }
    
    /**
     * Creates a new chart box with the specified attributes.
     * 
     * @param xLength  the length of the box along the x-axis.
     * @param yLength  the length of the box along the y-axis.
     * @param zLength  the length of the box along the z-axis.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     * @param color  the color for the sides of the box.
     * @param xTicks  tick data for the x-axis ({@code null} not
     *     permitted).
     * @param yTicks  tick data for the y-axis ({@code null} not
     *     permitted).
     * @param zTicks  tick data for the z-axis ({@code null} not
     *     permitted).
     * 
     * @since 1.1
     */
    public ChartBox3D(double xLength, double yLength, double zLength, 
            double xOffset, double yOffset, double zOffset, int color, 
            List<TickData> xTicks, List<TickData> yTicks, 
            List<TickData> zTicks) {
        ArgChecks.nullNotPermitted(color, "color");
        ArgChecks.nullNotPermitted(xTicks, "xTicks");
        ArgChecks.nullNotPermitted(yTicks, "yTicks");
        ArgChecks.nullNotPermitted(zTicks, "zTicks");
        this.xLength = xLength;
        this.yLength = yLength;
        this.zLength = zLength;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.color = color;
        this.xTicks = xTicks;
        this.yTicks = yTicks;
        this.zTicks = zTicks;
        this.object3D = createObject3D();
    }

    /**
     * Returns the 3D object for the chart box.  This includes vertices for
     * the tick marks specified in the constructor, if any.  Individual
     * faces for the box are returned by the methods {@link #faceA()}, 
     * {@link #faceB()}, {@link #faceC()},  {@link #faceD()},  {@link #faceE()},
     * and {@link #faceF()}. 
     * 
     * @return The 3D object for the chart box. 
     */
    public Object3D getObject3D() {
        return this.object3D;
    }

    /**
     * Returns face A (the bottom face, in the XZ axis plane).
     * 
     * @return Face A. 
     */
    public CBFace faceA() {
        return this.faceA;
    }
  
    /**
     * Returns face B (the front face, in the XY axis plane).
     * 
     * @return Face B. 
     */
    public CBFace faceB() {
        return this.faceB;
    }
  
    /**
     * Returns face C (the top face, in the XZ axis plane).
     * 
     * @return Face C. 
     */
    public CBFace faceC() {
        return this.faceC;
    }
  
    /**
     * Returns face D (the rear face, in the XY axis plane).
     * 
     * @return Face D. 
     */
    public CBFace faceD() {
        return this.faceD;
    }

    /**
     * Returns face E (the left face, in the YZ axis plane).
     * 
     * @return Face E. 
     */
    public CBFace faceE() {
        return this.faceE;
    }
  
    /**
     * Returns face F (the right face, in the YZ axis plane).
     * 
     * @return Face F.
     */
    public CBFace faceF() {
        return this.faceF;
    }

    /**
     * Creates an {@link Object3D} that contains the six faces for the 
     * chart box, plus the vertices for the tick marks along the edges of
     * each face.  This method is called from the constructor.
     * 
     * @return A 3D object. 
     */
    private Object3D createObject3D() {
        Object3D box = new Object3D();
        Point3D v0 = new Point3D(this.xOffset, this.yOffset, this.zOffset);
        Point3D v1 = new Point3D(this.xLength + this.xOffset, this.yOffset, 
                this.zOffset);
        Point3D v2 = new Point3D(this.xLength + this.xOffset, 
                this.yLength + this.yOffset, this.zOffset);
        Point3D v3 = new Point3D(this.xOffset, this.yLength + this.yOffset, 
                this.zOffset);
        Point3D v4 = new Point3D(this.xOffset, this.yLength + this.yOffset, 
                this.zLength + this.zOffset);
        Point3D v5 = new Point3D(this.xOffset, this.yOffset, 
                this.zLength + this.zOffset);
        Point3D v6 = new Point3D(this.xLength + this.xOffset, this.yOffset, 
                this.zLength + this.zOffset);
        Point3D v7 = new Point3D(this.xLength + this.xOffset, 
                this.yLength + this.yOffset, this.zLength + this.zOffset);

        box.addVertex(v0);   // 0, 0, 0
        box.addVertex(v1);   // 1, 0, 0
        box.addVertex(v2);   // 1, 1, 0
        box.addVertex(v3);   // 0, 1, 0

        box.addVertex(v4);   // 0, 1, 1
        box.addVertex(v5);   // 0, 0, 1
        box.addVertex(v6);   // 1, 0, 1
        box.addVertex(v7);   // 1, 1, 1
                
        this.faceA = new CBFace(new int[] {0, 5, 6, 1}, this.color);  // XZ
        this.faceB = new CBFace(new int[] {0, 1, 2, 3}, this.color);  // XY
        this.faceC = new CBFace(new int[] {7, 4, 3, 2}, this.color);  // XZ
        this.faceD = new CBFace(new int[] {5, 4, 7, 6}, this.color);  // XY
        this.faceE = new CBFace(new int[] {0, 3, 4, 5}, this.color);  // YZ
        this.faceF = new CBFace(new int[] {6, 7, 2, 1}, this.color);  // YZ
        box.addFace(this.faceA);
        box.addFace(this.faceB);
        box.addFace(this.faceC);
        box.addFace(this.faceD);
        box.addFace(this.faceE);
        box.addFace(this.faceF);

        // add vertices for the x-grid lines (ABCD)
        int base = 8;
        for (TickData t : this.xTicks) {
            double xx = this.xOffset + this.xLength * t.getPos();
            box.addVertex(xx, this.yOffset, this.zOffset);
            box.addVertex(xx, this.yOffset, this.zOffset + this.zLength);
            box.addVertex(xx, this.yOffset + this.yLength,  
                    this.zOffset + this.zLength);
            box.addVertex(xx, this.yOffset + this.yLength, this.zOffset);
            TickData td0 = new TickData(t, base);
            TickData td1 = new TickData(t, base + 1);
            TickData td2 = new TickData(t, base + 2);
            TickData td3 = new TickData(t, base + 3);
            this.faceA.addXTicks(td0, td1);
            this.faceB.addXTicks(td0, td3);
            this.faceC.addXTicks(td3, td2);
            this.faceD.addXTicks(td2, td1);
            base += 4;
        }
        
        // add vertices for the y-grid lines (BDEF)
        for (TickData t : this.yTicks) {
            double yy = this.yOffset + this.yLength * t.getPos();
            box.addVertex(this.xOffset, yy, this.zOffset);
            box.addVertex(this.xOffset + this.xLength, yy, this.zOffset);
            box.addVertex(this.xOffset + this.xLength, yy, 
                    this.zOffset + this.zLength);
            box.addVertex(this.xOffset, yy, this.zOffset + this.zLength);
            TickData td0 = new TickData(t, base);
            TickData td1 = new TickData(t, base + 1);
            TickData td2 = new TickData(t, base + 2);
            TickData td3 = new TickData(t, base + 3);
            this.faceB.addYTicks(td0, td1);
            this.faceD.addYTicks(td2, td3);
            this.faceE.addYTicks(td0, td3);
            this.faceF.addYTicks(td1, td2);
            base += 4;
        }

        for (TickData t : this.zTicks) {
            double zz = this.zOffset + this.zLength * t.getPos();
            box.addVertex(this.xOffset, this.yOffset, zz);
            box.addVertex(this.xOffset + this.xLength, this.yOffset, zz);
            box.addVertex(this.xOffset + this.xLength, 
                    this.yOffset + this.yLength, zz);
            box.addVertex(this.xOffset, this.yOffset + this.yLength, zz);
            TickData td0 = new TickData(t, base);
            TickData td1 = new TickData(t, base + 1);
            TickData td2 = new TickData(t, base + 2);
            TickData td3 = new TickData(t, base + 3);
            this.faceA.addZTicks(td0, td1);
            this.faceC.addZTicks(td3, td2);
            this.faceE.addZTicks(td0, td3);
            this.faceF.addZTicks(td1, td2);
            base += 4;
        }
        
        return box;
    }

    /**
     * A special subclass of {@link Face} that is used by the {@link ChartBox3D} 
     * so that when faces are sorted by z-order, the chart box sides are always 
     * drawn first (furthest in the background).  Also, these faces track 
     * tick marks, values and anchor points.
     */
    public static final class CBFace extends Face {

        /** Info about the x-axis ticks on edge A. */
        private List<TickData> xTicksA;
        
        /** Info about the x-axis ticks on edge B. */
        private List<TickData> xTicksB;
        
        /** Info about the y-axis ticks on edge A. */
        private List<TickData> yTicksA;
        
        /** Info about the y-axis ticks on edge B. */
        private List<TickData> yTicksB;
        
        /** Info about the z-axis ticks on edge A. */
        private List<TickData> zTicksA;
        
        /** Info about the z-axis ticks on edge B. */
        private List<TickData> zTicksB;
        
        /**
         * Creates a new face for a {@link ChartBox3D}.
         * 
         * @param vertices  the indices of the vertices.
         * @param color  the color.
         */
        public CBFace(int[] vertices, int color) {
            super(vertices, color, false);
            this.xTicksA = new ArrayList<TickData>();
            this.xTicksB = new ArrayList<TickData>();
            this.yTicksA = new ArrayList<TickData>();
            this.yTicksB = new ArrayList<TickData>();
            this.zTicksA = new ArrayList<TickData>();
            this.zTicksB = new ArrayList<TickData>();
        }
        
        /**
         * Clears the ticks for the x-axis.
         */
        public void clearXTicks() {
            this.xTicksA.clear();
            this.xTicksB.clear();
        }
        
        /**
         * Returns the x-axis tick data for edge A.
         * 
         * @return The x-axis tick data for edge A.
         */
        public List<TickData> getXTicksA() {
            return this.xTicksA;
        }
        
        /**
         * Adds tick data for edges A and B.
         * 
         * @param a  data for a tick on edge A.
         * @param b  data for a tick on edge B.
         */
        public void addXTicks(TickData a, TickData b) {
            this.xTicksA.add(a);
            this.xTicksB.add(b);
        }
        
        /**
         * Returns the x-axis tick data for the edge B.
         * 
         * @return The x-axis tick data for the edge B. 
         */
        public List<TickData> getXTicksB() {
            return this.xTicksB;
        }
        
        /**
         * Adds tick data items for the y-axis.
         * 
         * @param a  data for a tick on edge A.
         * @param b  data for a tick on edge B.
         */
        public void addYTicks(TickData a, TickData b) {
            this.yTicksA.add(a);
            this.yTicksB.add(b);
        }
        
        /**
         * Returns the y-axis tick data for the edge A.
         * 
         * @return The y-axis tick data for the edge A.
         */
        public List<TickData> getYTicksA() {
            return this.yTicksA;
        }
        
        /**
         * Returns the y-axis tick data for the edge B.
         * 
         * @return The y-axis tick data for the edge B.
         */
        public List<TickData> getYTicksB() {
            return this.yTicksB;
        }

        /**
         * Adds tick data items for the z-axis.
         * 
         * @param a  data for a tick on edge A.
         * @param b  data for a tick on edge B.
         */
        public void addZTicks(TickData a, TickData b) {
            this.zTicksA.add(a);
            this.zTicksB.add(b);
        }
        
        /**
         * Returns the z-axis tick data for the edge A.
         * 
         * @return The z-axis tick data for the edge A.
         */
        public List<TickData> getZTicksA() {
            return this.zTicksA;
        }
        
        /**
         * Returns the z-axis tick data for the edge B.
         * 
         * @return The z-axis tick data for the edge B. 
         */
        public List<TickData> getZTicksB() {
            return this.zTicksB;
        }
        
        /**
         * Returns <code>-123456f</code> which ensures that the chart box face 
         * is always drawn first (before any data items).
         * 
         * @param points  the points (ignored here).
         * 
         * @return <code>-123456f</code>. 
         */
        @Override
        public float calculateAverageZValue(Point3D[] points) {
            return -123456f;
        }
    }

}
