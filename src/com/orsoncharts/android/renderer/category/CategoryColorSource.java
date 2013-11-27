/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.renderer.category;

/**
 * A color source that can supply the colors for category plots.  This is the 
 * interface through which the renderer will obtain colors for each data item 
 * in the chart.  A default implementation 
 * ({@link StandardCategoryColorSource}) is provided and you can customise 
 * the rendering colors by providing an alternate implementation.
 */
public interface CategoryColorSource {

    /**
     * Returns the color for one data item in the chart.  We return a 
     * <code>Color</code> rather than a paint, because some manipulations
     * are done for the shading during the 3D rendering.
     * 
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * 
     * @return The color.
     */
    int getColor(int series, int row, int column);
  
    /**
     * Returns the color to be used in the legend to represent the specified
     * series.
     * 
     * @param series  the series index.
     * 
     * @return The color. 
     */
    int getLegendColor(int series);
    
}

