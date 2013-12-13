/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.renderer.category;

import java.io.Serializable;

import android.graphics.Color;

import com.orsoncharts.android.Chart3DFactory;
import com.orsoncharts.android.Range;
import com.orsoncharts.android.axis.CategoryAxis3D;
import com.orsoncharts.android.axis.ValueAxis3D;
import com.orsoncharts.android.data.DataUtils;
import com.orsoncharts.android.data.Values3D;
import com.orsoncharts.android.data.category.CategoryDataset3D;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.Object3D;
import com.orsoncharts.android.graphics3d.World;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.renderer.Renderer3DChangeEvent;
import com.orsoncharts.android.util.ObjectUtils;

/**
 * A renderer for creating 3D bar charts from a {@link CategoryDataset3D} (for 
 * use with a {@link CategoryPlot3D}).  For example:
 * <div>
 * <object id="ABC" data="../../../../doc-files/BarChart3DDemo1.svg"  type="image/svg+xml" 
 * width="500" height="359"> 
 * </object>
 * </div>
 * (refer to <code>BarChart3DDemo1.java</code> for the code to generate the
 * above chart).
 * <br><br>
 * There is a factory method to create a chart using this renderer - see
 * {@link Chart3DFactory#createBarChart(String, String, CategoryDataset3D, String, String, String)}.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public class BarRenderer3D extends AbstractCategoryRenderer3D 
                implements Serializable {

    /** The base of the bars - defaults to 0.0. */
    private double base;
    
    /** The bar width as a percentage of the column width. */
    private double barXWidth;
    
    /** The bar width as a percentage of the row width. */
    private double barZWidth;
    
    /** 
     * The color source used to fetch the color for the base of bars where
     * the actual base of the bar is *outside* of the current axis range 
     * (that is, the bar is "cropped").  If this is <code>null</code>, then 
     * the regular bar color is used.
     */
    private CategoryColorSource baseColorSource;
    
    /**
     * The paint source used to fetch the color for the top of bars where
     * the actual top of the bar is *outside* of the current axis range 
     * (that is, the bar is "cropped"). If this is <code>null</code> then the 
     * bar top is always drawn using the series paint.
     */
    private CategoryColorSource topColorSource;
        
    /**
     * Creates a new renderer with default attribute values.
     */
    public BarRenderer3D() {
        this.base = 0.0;
        this.barXWidth = 0.8;
        this.barZWidth = 0.5;
        this.baseColorSource = new StandardCategoryColorSource(Color.WHITE);
        this.topColorSource = new StandardCategoryColorSource(Color.BLACK);
    }
    
    /**
     * Returns the base value for the bars.  The default value 
     * is <code>0.0</code>.
     * 
     * @return The base value for the bars.
     * 
     * @see #setBase(double) 
     */
    public double getBase() {
        return this.base;
    }
    
    /**
     * Sets the base value for the bars and fires a 
     * {@link com.orsoncharts.android.renderer.Renderer3DChangeEvent}.
     * 
     * @param base  the new base value.
     * 
     * @see #getBase() 
     */
    public void setBase(double base) {
        this.base = base;
        fireChangeEvent();
    }
    
    /**
     * Returns the bar width as a percentage of the column width.
     * The default value is <code>0.8</code> (the total width of each column
     * in world units is <code>1.0</code>, so the default leaves a small gap
     * between each bar).
     * 
     * @return The bar width (in world units). 
     */
    public double getBarXWidth() {
        return this.barXWidth;
    }
    
    /**
     * Sets the the bar width as a percentage of the column width and
     * fires a {@link Renderer3DChangeEvent}.
     * 
     * @param barXWidth  the new width.
     */
    public void setBarXWidth(double barXWidth) {
        this.barXWidth = barXWidth;
        fireChangeEvent();
    }

    /**
     * Returns the bar width as a percentage of the row width.
     * The default value is <code>0.8</code>.
     * 
     * @return The bar width. 
     */
    public double getBarZWidth() {
        return this.barZWidth;
    }
    
    /**
     * Sets the the bar width as a percentage of the row width and
     * fires a {@link com.orsoncharts.android.renderer.Renderer3DChangeEvent}.
     * 
     * @param barZWidth  the new width.
     */
    public void setBarZWidth(double barZWidth) {
        this.barZWidth = barZWidth;
        fireChangeEvent();
    }
    
    /**
     * Returns the object used to fetch the color for the base of bars
     * where the base of the bar is "cropped" (on account of the base value
     * falling outside of the bounds of the y-axis).  This is used to give a
     * visual indication to the end-user that the bar on display is cropped.
     * If this paint source is <code>null</code>, the regular series color
     * will be used for the top of the bars.
     * 
     * @return A paint source (possibly <code>null</code>).
     */
    public CategoryColorSource getBaseColorSource() {
        return this.baseColorSource;
    }
    
    /**
     * Sets the object that determines the color to use for the base of bars
     * where the base value falls outside the axis range, and sends a
     * {@link Renderer3DChangeEvent} to all registered listeners.  If you set 
     * this to <code>null</code>, the regular series color will be used to draw
     * the base of the bar, but it will be harder for the end-user to know that 
     * only a section of the bar is visible in the chart.  Note that the 
     * default base paint source returns <code>Color.WHITE</code> always.
     * 
     * @param source  the source (<code>null</code> permitted).
     * 
     * @see #getBaseColorSource() 
     * @see #getTopColorSource()
     */
    public void setBaseColorSource(CategoryColorSource source) {
        this.baseColorSource = source;
        fireChangeEvent();
    }
    
    /**
     * Returns the object used to fetch the color for the top of bars
     * where the top of the bar is "cropped" (on account of the data value
     * falling outside of the bounds of the y-axis).  This is used to give a
     * visual indication to the end-user that the bar on display is cropped.
     * If this paint source is <code>null</code>, the regular series color
     * will be used for the top of the bars.
     * 
     * @return A paint source (possibly <code>null</code>).
     */
    public CategoryColorSource getTopColorSource() {
        return this.topColorSource;
    }
    
    /**
     * Sets the object used to fetch the color for the top of bars where the 
     * top of the bar is "cropped", and sends a {@link Renderer3DChangeEvent}
     * to all registered listeners.
     * 
     * @param source  the source (<code>null</code> permitted).
     * 
     * @see #getTopColorSource() 
     * @see #getBaseColorSource() 
     */
    public void setTopColorSource(CategoryColorSource source) {
        this.topColorSource = source;
        fireChangeEvent();
    }

    /**
     * Returns the range of values that will be required on the value axis
     * to see all the data from the dataset.  We override the method to 
     * include in the range the base value for the bars.
     * 
     * @param data  the data (<code>null</code> not permitted).
     * 
     * @return The range (possibly <code>null</code>) 
     */
    @Override
    public Range findValueRange(Values3D<? extends Number> data) {
        return DataUtils.findValueRange(data, this.base);
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
        // delegate to a separate method that is reused by the 
        // StackedBarRenderer3D subclass...
        composeItem(value, this.base, dataset, series, row, column, world, 
                dimensions, xOffset, yOffset, zOffset);
    }
    
    /**
     * Performs the actual work of composing a bar to represent one item in the
     * dataset.  This method is reused by the {@link StackedBarRenderer3D}
     * subclass.
     * 
     * @param value  the data value (top of the bar).
     * @param barBase  the base value for the bar.
     * @param dataset  the dataset.
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * @param world  the world.
     * @param dimensions  the plot dimensions.
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    protected void composeItem(double value, double barBase, 
            CategoryDataset3D dataset, int series, int row, int column,
            World world, Dimension3D dimensions, double xOffset, 
            double yOffset, double zOffset) {

        double vlow = Math.min(barBase, value);
        double vhigh = Math.max(barBase, value);

        CategoryPlot3D plot = getPlot();
        CategoryAxis3D rowAxis = plot.getRowAxis();
        CategoryAxis3D columnAxis = plot.getColumnAxis();
        ValueAxis3D valueAxis = plot.getValueAxis();
        Range range = valueAxis.getRange();
        if (!range.intersects(vlow, vhigh)) {
            return; // the bar is not visible for the given axis range
        }
        
        double vbase = range.peggedValue(vlow);
        double vtop = range.peggedValue(vhigh);
        boolean inverted = barBase > value;
        
        Comparable<?> rowKey = dataset.getRowKey(row);
        Comparable<?> columnKey = dataset.getColumnKey(column);
        double rowValue = rowAxis.getCategoryValue(rowKey);
        double columnValue = columnAxis.getCategoryValue(columnKey);

        double width = dimensions.getWidth();
        double height = dimensions.getHeight();
        double depth = dimensions.getDepth();
        double xx = columnAxis.translateToWorld(columnValue, width);
        double yy = valueAxis.translateToWorld(vtop, height);
        double zz = rowAxis.translateToWorld(rowValue, depth);

        double xw = this.barXWidth * columnAxis.getCategoryWidth();
        double zw = this.barZWidth * rowAxis.getCategoryWidth();
        double xxw = columnAxis.translateToWorld(xw, width);
        double xzw = rowAxis.translateToWorld(zw, depth);
        double basew = valueAxis.translateToWorld(vbase, height);
    
        int color = getColorSource().getColor(series, row, column);
        Integer baseColor = null;
        if (this.baseColorSource != null && !range.contains(this.base)) {
            baseColor = this.baseColorSource.getColor(series, row, column);
        }
        if (baseColor == null) {
            baseColor = color;
        }

        Integer topColor = null;
        if (this.topColorSource != null && !range.contains(value)) {
            topColor = this.topColorSource.getColor(series, row, column);
        }
        if (topColor == null) {
            topColor = color;
        }
        Object3D bar = Object3D.createBar(xxw, xzw, xx + xOffset, 
                yy + yOffset, zz + zOffset, basew + yOffset, color, baseColor, 
                topColor, inverted);
        world.add(bar);
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
        if (!(obj instanceof BarRenderer3D)) {
            return false;
        }
        BarRenderer3D that = (BarRenderer3D) obj;
        if (this.base != that.base) {
            return false;
        }
        if (this.barXWidth != that.barXWidth) {
            return false;
        }
        if (this.barZWidth != that.barZWidth) {
            return false;
        }
        if (!ObjectUtils.equals(this.baseColorSource, that.baseColorSource)) {
            return false;
        }
        if (!ObjectUtils.equals(this.topColorSource, that.topColorSource)) {
            return false;
        }
        return super.equals(obj);
    }
}
