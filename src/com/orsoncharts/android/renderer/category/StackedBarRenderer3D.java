/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/android/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.android.renderer.category;

import com.orsoncharts.android.Range;
import com.orsoncharts.android.data.category.CategoryDataset3D;
import com.orsoncharts.android.data.DataUtils;
import com.orsoncharts.android.data.Values3D;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.World;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.Chart3DFactory;

/**
 * A renderer that can be used with the {@link CategoryPlot3D} class to create
 * 3D stacked bar charts from data in a {@link CategoryDataset3D}.  The 
 * <code>createStackedBarChart()</code> method in the {@link Chart3DFactory} 
 * class will construct a chart that uses this renderer.  Here is a sample:
 * <div>
 * <object id="StackedBarChart3DDemo1" data="../../../../doc-files/StackedBarChart3DDemo1.svg"  
 * type="image/svg+xml" width="500" height="359"></object>
 * </div>
 * (refer to <code>StackedBarChart3DDemo1.java</code> for the code to generate 
 * the above chart).
 * <br><br> 
 * There is a factory method to create a chart using this renderer - see
 * {@link Chart3DFactory#createStackedBarChart(String, String, CategoryDataset3D, String, String, String)}.
 */
public class StackedBarRenderer3D extends BarRenderer3D {

    /**
     * Creates a default constructor.
     */
    public StackedBarRenderer3D() {
        super();
    }
    
    /**
     * Returns the range of values that will be required on the value axis
     * to see all the data from the dataset.  We override the method to 
     * account for the bars from each series being stacked on top of one 
     * another.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * 
     * @return The range (possibly <code>null</code>) 
     */
    @Override
    public Range findValueRange(Values3D<? extends Number> data) {
        return DataUtils.findStackedValueRange(data);
    }
    
    /**
     * Constructs and places one item from the specified dataset into the given 
     * world.  This method will be called by the {@link CategoryPlot3D} class
     * while iterating over the items in the dataset.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * @param world  the world (<code>null</code> not permitted).
     * @param dimensions  the plot dimensions (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    public void composeItem(CategoryDataset3D dataset, int series, int row, 
            int column, World world, Dimension3D dimensions,  
            double xOffset, double yOffset, double zOffset) {
        
        double value = dataset.getDoubleValue(series, row, column);
        if (Double.isNaN(value)) {
            return;
        }        
        double[] stack = DataUtils.stackSubTotal(dataset, getBase(), series,
                row, column);
        double lower = stack[1];
        if (value < 0.0) {
            lower = stack[0];
        }
        double upper = lower + value;
        composeItem(upper, lower, dataset, series, row, column, world, 
                dimensions, xOffset, yOffset, zOffset);
        
    }
    
    /**
     * Tests this renderer for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StackedBarRenderer3D)) {
            return false;
        }
        return super.equals(obj);
    }
    
}