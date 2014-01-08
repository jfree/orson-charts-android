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

package com.orsoncharts.android.data.function;

import java.io.Serializable;

import com.orsoncharts.android.Range;
import com.orsoncharts.android.data.xyz.XYZDatasetUtils;

/**
 * Represents a function <code>y = f(x, z)</code>.  
 * <br><br>
 * A dataset can be created by sampling a function - see the 
 * {@link XYZDatasetUtils#sampleFunction(Function3D, String, Range, double, Range, double)} 
 * method.
 */
public interface Function3D extends Serializable {
    
    /**
     * Returns the value of the function ('y') for the specified inputs ('x' 
     * and 'z').
     *
     * @param x  the x-value.
     * @param z  the z-value.
     *
     * @return The function value.
     */
    public double getValue(double x, double z);
   
}
