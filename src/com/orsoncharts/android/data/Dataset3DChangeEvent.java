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

package com.orsoncharts.android.data;

import java.util.EventObject;

/**
 * A dataset change event.  Any object that implements the 
 * {@link Dataset3DChangeListener} interface can register with a dataset to
 * receive notification of change events.  By default, the plot classes in
 * Orson Charts register with their dataset to monitor dataset changes.
 */
public class Dataset3DChangeEvent extends EventObject {

    private Object dataset;
  
    /**
     * Creates a new dataset change event.  The source can be the same as the
     * dataset, but this is not required.
     * 
     * @param source  the source.
     * @param dataset  the dataset.
     */
    public Dataset3DChangeEvent(Object source, Object dataset) {
        super(source);
        this.dataset = dataset;
    }
  
    /**
     * Returns the dataset that this event is associated with.  This will
     * normally be an instance of {@link PieDataset3D}, 
     * {@link com.orsoncharts.android.data.category.CategoryDataset3D} or 
     * {@link com.orsoncharts.android.data.xyz.XYZDataset}.
     * 
     * @return The dataset. 
     */
    public Object getDataset() {
        return this.dataset;
    }
}
