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

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A point in 3D space (this class is also used to represent vectors in 3D
 * space).  Instances of this class are immutable.
 */
public final class Point3D implements Parcelable, Serializable {

    /** The origin <code>(0, 0, 0)</code>. */
    public static final Point3D ORIGIN = new Point3D(0, 0, 0);
    
    /** The point <code>(1, 0, 0)</code>. */
    public static final Point3D UNIT_X = new Point3D(1, 0, 0);
    
    /** The point <code>(0, 1, 0)</code>. */
    public static final Point3D UNIT_Y = new Point3D(0, 1, 0);
    
    /** The point <code>(0, 0, 1)</code>. */
    public static final Point3D UNIT_Z = new Point3D(0, 0, 1);
    
    /** The x-coordinate. */
    public double x;
    
    /** The y-coordinate. */
    public double y;
    
    /** The z-coordinate. */
    public double z;
    
    /**
     * Creates a new <code>Point3D</code> instance from spherical coordinates.
     * 
     * @param theta  theta (in radians).
     * @param phi  phi (in radians).
     * @param rho  the distance from the origin.
     * 
     * @return The point (never {@code null}).
     */
    public static Point3D createPoint3D(double theta, double phi, double rho) {
        double x = rho * Math.sin(phi) * Math.cos(theta);
        double y = rho * Math.sin(phi) * Math.sin(theta);
        double z = rho * Math.cos(phi);
        return new Point3D(x, y, z);
    }

    /**
     * Creates a new point in 3D space.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param z  the z-coordinate.
     */
    public Point3D(double x, double y, double z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }
    
    /**
     * Returns the x-coordinate specified in the constructor.
     * 
     * @return The x-coordinate. 
     */
    public double getX() {
        return this.x;
    }
    
    /**
     * Returns the y-coordinate specified in the constructor.
     * 
     * @return The y-coordinate.
     */
    public double getY() {
        return this.y;
    }
    
    /**
     * Returns the z-coordinate specified in the constructor.
     * 
     * @return The z-coordinate. 
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Returns theta (calculated from the cartesian coordinates).
     * 
     * @return Theta.
     */
    public double getTheta() {
        return Math.atan2(this.y, this.x);
    }
    
    /**
     * Returns phi (calculated from the cartesian coordinates).
     * 
     * @return phi.
     */
    public double getPhi() {
        return Math.acos(this.z / getRho());        
    }
    
    /**
     * Returns rho (calculated from the cartesian coordinates).
     * 
     * @return rho.
     */
    public double getRho() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    /**
     * Tests this instance for equality to an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point3D)) {
            return false;
        }
        Point3D that = (Point3D) obj;
        if (this.x != that.x) {
            return false;
        }
        if (this.y != that.y) {
            return false;
        }
        if (this.z != that.z) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this instance, primarily for 
     * debugging purposes.
     * 
     * @return A string (never {@code null}).
     */
    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
        dest.writeDouble(this.z);
    }
    
    /**
     * Provides support for parcelling.
     * 
     * @since 1.1
     */
    public static final Parcelable.Creator<Point3D> CREATOR 
            = new Parcelable.Creator<Point3D>() {

        @Override
        public Point3D createFromParcel(Parcel source) {
            double x = source.readDouble();
            double y = source.readDouble();
            double z = source.readDouble();
            return new Point3D(x, y, z);
        }

        @Override
        public Point3D[] newArray(int size) {
            return new Point3D[size];
        }

    };
}
