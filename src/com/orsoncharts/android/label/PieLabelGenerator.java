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

import com.orsoncharts.android.data.PieDataset3D;

/**
 * A label generator for pie charts.  A default implementation
 * ({@link StandardPieLabelGenerator}) is provided.
 */
public interface PieLabelGenerator {

    /**
     * Returns a label for one data item in a pie chart.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The label (possibly <code>null</code>).
     */
    String generateLabel(PieDataset3D dataset, Comparable<?> key);
    
}