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

package com.orsoncharts.android.data.category;

import com.orsoncharts.android.data.Dataset3D;
import com.orsoncharts.android.data.KeyedValues3D;
import com.orsoncharts.android.plot.CategoryPlot3D;

/**
 * An interface for a dataset with multiple series of data in the form of
 * <code>(rowKey, columnKey, value)</code>.  This is the standard data 
 * interface used by the {@link CategoryPlot3D} class. 
 */
public interface CategoryDataset3D extends KeyedValues3D<Number>, Dataset3D {

}
