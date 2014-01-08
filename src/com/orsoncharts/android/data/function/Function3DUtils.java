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

import com.orsoncharts.android.Range;
import com.orsoncharts.android.util.ArgChecks;

/**
 * Utility methods related to {@link Function3D}.
 * 
 * @since 1.1
 */
public class Function3DUtils {
    
    private Function3DUtils() {
        // no need to instantiate this
    }
    
    /**
     * Returns the range of y-values in the function by sampling.
     * 
     * @param f  the function (<code>null</code> not permitted).
     * @param xRange  the x-range to sample (<code>null</code> not permitted).
     * @param zRange  the z-range to sample (<code>null</code> not permitted).
     * @param xSamples  the number of x-samples (must be at least 2).
     * @param zSamples  the number of z-samples (must be at least 2).
     * @param ignoreNaN  ignore NaN values?
     * 
     * @return The range (<code>null</code> in the case that the function 
     *     returns no valid values). 
     */
    public static Range findYRange(Function3D f, Range xRange, Range zRange, 
            int xSamples, int zSamples, boolean ignoreNaN) {
        ArgChecks.nullNotPermitted(f, "f");
        ArgChecks.nullNotPermitted(xRange, "xRange");
        ArgChecks.nullNotPermitted(zRange, "zRange");
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int xIndex = 0; xIndex <= xSamples - 1; xIndex++) {
            double fracX = xIndex / (xSamples - 1.0);
            double x = xRange.value(fracX);
            for (int zIndex = 0; zIndex <= zSamples - 1; zIndex++) {
                double fracZ = zIndex / (zSamples - 1.0);
                double z = zRange.value(fracZ);
                double y = f.getValue(x, z);
                if (Double.isNaN(y) && ignoreNaN) {
                    continue;
                }
                min = Math.min(y, min);
                max = Math.max(y, max);
            }
        }
        if (min <= max) {
            return new Range(min, max);
        }
        return null;
    }
}
