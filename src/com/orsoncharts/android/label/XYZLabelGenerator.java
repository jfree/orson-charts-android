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

package com.orsoncharts.android.label;

import com.orsoncharts.android.data.xyz.XYZDataset;

/**
 * A label generator for category charts, used to create labels for the axes 
 * and for the chart legend.  The ({@link StandardCategoryLabelGenerator}) class
 * provides the default implementation.
 * 
 * @since 1.2
 */
public interface XYZLabelGenerator {

    /**
     * Generates a label for one series in a {@link XYZDataset}.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param seriesKey  the series key (<code>null</code> not permitted).
     * 
     * @return The series label (possibly <code>null</code>).
     */
    String generateSeriesLabel(XYZDataset dataset, Comparable<?> seriesKey);

}