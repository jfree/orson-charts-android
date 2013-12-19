/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.data;

/**
 * A generic representation of a three dimensional grid (cube) of data values.
 * We refer to the indices in the three dimensions as the 
 * <code>seriesIndex</code>, the <code>rowIndex</code> and the 
 * <code>columnIndex</code> (to match the downstream use of this data
 * structure to represent data values in a three dimensional plot).
 * 
 * @see KeyedValues3D
 */
public interface Values3D<T> {

    /**
     * Returns the number of items in the x-dimension.
     * 
     * @return The number of items in the x-dimension. 
     */
    int getSeriesCount();
  
    /**
     * Returns the number of items in the y-dimension.
     * 
     * @return The number of items in the y-dimension. 
     */
    int getRowCount();
  
    /**
     * Returns the number of items in the z-dimension.
     * 
     * @return The number of items in the z-dimension. 
     */
    int getColumnCount();
  
    /**
     * Returns the data item at the specified position.
     * 
     * @param seriesIndex  the series-index.
     * @param rowIndex  the row-index.
     * @param columnIndex  the column-index.
     * 
     * @return The data value (possibly <code>null</code>).
     */
    T getValue(int seriesIndex, int rowIndex, int columnIndex);
   
    /**
     * Returns the data item at the specified position as a double primitive.
     * Where the {@link #getValue(int, int, int) } method returns 
     * <code>null</code>, this method returns <code>Double.NaN</code>.
     * 
     * @param seriesIndex  the series index.
     * @param rowIndex  the row index.
     * @param columnIndex  the column index.
     * 
     * @return The data value.
     */
    double getDoubleValue(int seriesIndex, int rowIndex, int columnIndex);
  
}
