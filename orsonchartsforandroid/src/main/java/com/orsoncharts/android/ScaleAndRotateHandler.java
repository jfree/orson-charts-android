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

import android.view.MotionEvent;

import com.orsoncharts.android.graphics3d.Point2D;

/**
 * A class that handles tracking the motion events and detecting pinch scale
 * and rotate gestures.
 */
public class ScaleAndRotateHandler {

    // for this code, the following tutorial was helpful:
    // http://www.codeproject.com/Articles/319401/Simple-Gestures-on-Android
    
    private int pointerId1 = -1;
    
    private Point2D prev1;
    private Point2D curr1;
    
    private int pointerId2 = -1;

    private Point2D prev2;
    private Point2D curr2;
    
    /**
     * Creates a new instance.
     */
    public ScaleAndRotateHandler() {
        super();
    }
    
    /**
     * Updates the state.
     * 
     * @param event  the latest motion event.
     */
    public void update(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerCount = event.getPointerCount();
        
        if (action == MotionEvent.ACTION_UP || action 
                == MotionEvent.ACTION_POINTER_UP) {
            int pointerId = event.getPointerId(event.getActionIndex());
            if (pointerId == this.pointerId1) {
                this.pointerId1 = -1;
                this.curr1 = null;
                this.prev1 = null;
            } else if (pointerId == this.pointerId2) {
                this.pointerId2 = -1;
                this.curr2 = null;
                this.prev2 = null;
            }
            // if it is a third or higher pointer, we can ignore
            
        } else if (action == MotionEvent.ACTION_DOWN 
                || action == MotionEvent.ACTION_POINTER_DOWN) {
            // ACTION_DOWN or ACTION_POINTER_DOWN
            int pointerIndex = event.getActionIndex();
            int pointerId = event.getPointerId(pointerIndex);
            if (this.pointerId1 == -1) {
                this.pointerId1 = pointerId;
                this.curr1 = new Point2D(event.getX(pointerIndex), 
                        event.getY(pointerIndex));
                this.prev1 = this.curr1;
            } else if (this.pointerId2 == -1) {
                this.pointerId2 = pointerId;
                this.curr2 = new Point2D(event.getX(pointerIndex), 
                        event.getY(pointerIndex));
                this.prev2 = this.curr2;
            }
            // ignore if the pointer is the third or greater
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (this.pointerId1 != -1) {
                int index1 = event.findPointerIndex(this.pointerId1);
                this.prev1 = this.curr1;
                this.curr1 = new Point2D(event.getX(index1), event.getY(index1));
            }
            if (this.pointerId2 != -1) {
                int index2 = event.findPointerIndex(this.pointerId2);
                this.prev2 = this.curr2;
                this.curr2 = new Point2D(event.getX(index2), event.getY(index2));
            }
            int trackingCount = getTrackingCount();
            if (trackingCount < 2 && pointerCount > trackingCount) {
                // there will be one or many pointers
                // if there are more pointers than we are currently tracking
                // then we should move the untracked ones in                
            }
            
        }
    }
    
    /**
     * Returns the number of pointers we are currently tracking.
     * 
     * @return The number of pointers.
     */
    public int getTrackingCount() {
        int result = 0;
        if (this.pointerId1 != -1) {
            result++;
        }
        if (this.pointerId2 != -1) {
            result++;
        }
        return result;
    }
    
    /**
     * Returns the pinch scale factor resulting from the last motion event.
     * 
     * @return The pinch scale factor.
     */
    public float getPinchScaleFactor() {
        if (getTrackingCount() < 2) {
            return 1.0f;
        }
        float currLen = this.curr1.distance(this.curr2);
        float prevLen = this.prev1.distance(this.prev2);
        if (prevLen > 0.0) {
            return currLen / prevLen;
        }
        return 1.0f;
    }
    
    public float getRotation() {
        if (getTrackingCount() < 2) {
            return 0.0f;
        }
        double currdx = this.curr2.getX() - this.curr1.getX();
        double currdy = this.curr2.getY() - this.curr1.getY();
        double prevdx = this.prev2.getX() - this.prev1.getX();
        double prevdy = this.prev2.getY() - this.prev1.getY();
        return (float) angle(currdx, currdy, prevdx, prevdy);
    }
    
    private double angle(double ax, double ay, double bx, double by) {
        double lena = Math.sqrt(ax * ax + ay * ay);
        double nax = ax / lena;
        double nay = ay / lena;
        double lenb = Math.sqrt(bx * bx + by * by);
        double nbx = bx / lenb;
        double nby = by / lenb;
        return Math.atan2(nby, nbx) - Math.atan2(nay, nax);
    }
}
