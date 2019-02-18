/* ========================
 * Orson Charts for Android
 * ========================
 *
 * (C)opyright 2013-2019, by Object Refinery Limited.  All rights reserved.
 *
 * https://github.com/jfree/orson-charts-android
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts for Android home page:
 *
 * https://www.object-refinery.com/orsoncharts/android/index.html
 *
 */

package com.orsoncharts.android.plot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.LineStyle;
import com.orsoncharts.android.axis.Axis3DChangeEvent;
import com.orsoncharts.android.axis.Axis3DChangeListener;
import com.orsoncharts.android.axis.CategoryAxis3D;
import com.orsoncharts.android.axis.ValueAxis3D;
import com.orsoncharts.android.data.Dataset3DChangeEvent;
import com.orsoncharts.android.data.category.CategoryDataset3D;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.World;
import com.orsoncharts.android.label.CategoryLabelGenerator;
import com.orsoncharts.android.label.StandardCategoryLabelGenerator;
import com.orsoncharts.android.legend.LegendItemInfo;
import com.orsoncharts.android.legend.StandardLegendItemInfo;
import com.orsoncharts.android.renderer.Renderer3DChangeEvent;
import com.orsoncharts.android.renderer.Renderer3DChangeListener;
import com.orsoncharts.android.renderer.category.CategoryRenderer3D;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A 3D plot with two category axes (x and z) and a numerical y-axis that can
 * display data from a {@link CategoryDataset3D}.
 * <br><br>
 * The plot implements several listener interfaces so that it can receive 
 * notification of changes to its dataset, axes and renderer.  When change
 * events are received, the plot passes on a {@link Plot3DChangeEvent} to the
 * {@link Chart3D} instance that owns the plot.  This event chain is the 
 * mechanism that ensures that charts are repainted whenever the dataset 
 * changes, or when changes are made to the configuration of any chart 
 * component.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class. 
 */
public class CategoryPlot3D extends AbstractPlot3D 
        implements Axis3DChangeListener, Renderer3DChangeListener, 
        Serializable {

    private static LineStyle DEFAULT_GRIDLINE_STROKE = 
            new LineStyle(0.5f, Paint.Cap.ROUND, Paint.Join.ROUND);

    /** The dataset. */
    private CategoryDataset3D dataset;
    
    /** The renderer (never {@code null}). */
    private CategoryRenderer3D renderer;

    /** The row axis. */
    private CategoryAxis3D rowAxis;
    
    /** The column axis. */
    private CategoryAxis3D columnAxis;
    
    /** The value axis. */
    private ValueAxis3D valueAxis;

    /** Are gridlines shown for the row (z) axis? */
    private boolean gridlinesVisibleForRows;
    
    /** The paint for the row axis gridlines. */
    private int gridlinePaintForRows;
    
    /** The stroke for the row axis gridlines (never {@code null}). */
    private LineStyle gridlineStrokeForRows;

    /** Are gridlines shown for the column (x) axis? */
    private boolean gridlinesVisibleForColumns;
    
    /** The paint for the column axis gridlines. */
    private int gridlinePaintForColumns;
    
    /** The stroke for the column axis gridlines (never {@code null}). */
    private LineStyle gridlineStrokeForColumns;

    /** Are gridlines shown for the value axis? */
    private boolean gridlinesVisibleForValues;
    
    /** The paint for the value axis gridlines. */
    private int gridlinePaintForValues;

    /** The stroke for the value axis gridlines (never {@code null}). */
    private LineStyle gridlineStrokeForValues;
    
    /** The legend label generator. */
    private CategoryLabelGenerator legendLabelGenerator;

    /**
     * Creates a new plot.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     * @param renderer  the renderer ({@code null} not permitted).
     * @param rowAxis  the row axis ({@code null} not permitted).
     * @param columnAxis  the column axis ({@code null} not permitted).
     * @param valueAxis  the value axis ({@code null} not permitted).
     */
    public CategoryPlot3D(CategoryDataset3D dataset, 
            CategoryRenderer3D renderer, CategoryAxis3D rowAxis, 
            CategoryAxis3D columnAxis, ValueAxis3D valueAxis) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(renderer, "renderer");
        ArgChecks.nullNotPermitted(rowAxis, "rowAxis");
        ArgChecks.nullNotPermitted(columnAxis, "columnAxis");
        ArgChecks.nullNotPermitted(valueAxis, "valueAxis");
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        this.dimensions = calculateDimensions();
        this.renderer = renderer;
        this.renderer.setPlot(this);
        this.renderer.addChangeListener(this);
        this.rowAxis = rowAxis;
        this.rowAxis.addChangeListener(this);
        this.columnAxis = columnAxis;
        this.columnAxis.addChangeListener(this);
        this.valueAxis = valueAxis;
        this.valueAxis.addChangeListener(this);
        this.rowAxis.configureAsRowAxis(this);
        this.columnAxis.configureAsColumnAxis(this);
        this.valueAxis.configureAsValueAxis(this);
        this.gridlinesVisibleForValues = true;
        this.gridlinesVisibleForColumns = false;
        this.gridlinesVisibleForRows = false;
        this.gridlinePaintForRows = Color.WHITE;
        this.gridlinePaintForColumns = Color.WHITE;
        this.gridlinePaintForValues = Color.WHITE;
        this.gridlineStrokeForRows = DEFAULT_GRIDLINE_STROKE;
        this.gridlineStrokeForColumns = DEFAULT_GRIDLINE_STROKE;
        this.gridlineStrokeForValues = DEFAULT_GRIDLINE_STROKE;
        this.legendLabelGenerator = new StandardCategoryLabelGenerator();
    }
    
    /**
     * Sets the dimensions (in 3D space) for the plot, resets the 
     * <code>autoAdjustDimensions</code> flag to <code>false</code>, and sends
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param dimensions  the dimensions ({@code null} not permitted).
     * 
     * @see Plot3D#getDimensions() 
     */
    public void setDimensions(Dimension3D dimensions) {
        ArgChecks.nullNotPermitted(dimensions, "dimensions");
        this.dimensions = dimensions;
        this.autoAdjustDimensions = false;
        fireChangeEvent();
    }

    /**
     * Returns the dataset.
     * 
     * @return The dataset (never {@code null}).
     */
    public CategoryDataset3D getDataset() {
        return this.dataset;
    }
    
    /**
     * Sets the dataset and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param dataset  the dataset ({@code null} not permitted).
     */
    public void setDataset(CategoryDataset3D dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        this.dataset.removeChangeListener(this);
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        fireChangeEvent();
    }
    
    /**
     * Returns the renderer (very often you will need to cast this to a 
     * specific class to make customisations).
     * 
     * @return The renderer (never {@code null}).
     */
    public CategoryRenderer3D getRenderer() {
        return this.renderer;
    }
    
    /**
     * Sets the renderer and sends a change event to all registered listeners.
     * 
     * @param renderer  the renderer ({@code null} not permitted).
     */
    public void setRenderer(CategoryRenderer3D renderer) {
        ArgChecks.nullNotPermitted(renderer, "renderer");
        this.renderer.removeChangeListener(this);
        this.renderer = renderer;
        this.renderer.addChangeListener(this);
        // a new renderer might mean the axis range needs changing...
        this.valueAxis.configureAsValueAxis(this);
        fireChangeEvent();
    }
    
    /**
     * Returns the row axis.
     * 
     * @return The row axis. 
     */
    public CategoryAxis3D getRowAxis() {
        return this.rowAxis;
    }
    
    /**
     * Sets the row axis and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.  The row axis is equivalent to the z-axis.
     * 
     * @param axis  the row axis ({@code null} not permitted).
     */
    public void setRowAxis(CategoryAxis3D axis) {
        ArgChecks.nullNotPermitted(axis, "axis");
        this.rowAxis.removeChangeListener(this);
        this.rowAxis = axis;
        this.rowAxis.addChangeListener(this);
        fireChangeEvent();
    }
    
    /**
     * Returns the column axis.
     * 
     * @return The column axis (never {@code null}).
     */
    public CategoryAxis3D getColumnAxis() {
        return this.columnAxis;
    }
    
    /**
     * Sets the column axis and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param axis  the new axis ({@code null} not permitted).
     * 
     * @see #setRowAxis(com.orsoncharts.android.axis.CategoryAxis3D) 
     * @see #setValueAxis(com.orsoncharts.android.axis.ValueAxis3D) 
     * 
     */
    public void setColumnAxis(CategoryAxis3D axis) {
        ArgChecks.nullNotPermitted(axis, "axis");
        this.columnAxis.removeChangeListener(this);
        this.columnAxis = axis;
        this.columnAxis.addChangeListener(this);
        fireChangeEvent();
    }
    
    /**
     * Returns the value axis (the vertical axis in the plot).
     * 
     * @return The value axis (never {@code null}).
     */
    public ValueAxis3D getValueAxis() {
        return this.valueAxis;
    }
    
    /**
     * Sets the value axis and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param axis  the axis ({@code null} not permitted).
     */
    public void setValueAxis(ValueAxis3D axis) {
        ArgChecks.nullNotPermitted(axis, "axis");
        this.valueAxis.removeChangeListener(this);
        this.valueAxis = axis;
        this.valueAxis.addChangeListener(this);
        fireChangeEvent();
    }
    
    /**
     * Returns <code>true</code> if gridlines are shown for the row axis
     * and <code>false</code> otherwise.  The default value is 
     * <code>false</code>
     * 
     * @return A boolean. 
     */
    public boolean getGridlinesVisibleForRows() {
        return this.gridlinesVisibleForRows;
    }

    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * row axis and sends a {@link Plot3DChangeEvent} to all registered 
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleForRows(boolean visible) {
        this.gridlinesVisibleForRows = visible;
        fireChangeEvent();
    }

    /**
     * Returns the color used to draw the gridlines for the row axis.
     * 
     * @return The color.
     */
    public int getGridlinePaintForRows() {
        return this.gridlinePaintForRows;
    }

    /**
     * Sets the paint used for the row axis gridlines and sends a 
     * {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint. 
     */
    public void setGridlinePaintForRows(int paint) {
        this.gridlinePaintForRows = paint;
        fireChangeEvent();
    }

    /**
     * Returns the stroke for the gridlines associated with the row axis.
     * The default value is <code>BasicStroke(0.5f, BasicStroke.CAP_ROUND, 
     * BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f)</code>.
     * 
     * @return The stroke (never {@code null}).
     */
    public LineStyle getGridlineStrokeForRows() {
        return this.gridlineStrokeForRows;
    }

    /**
     * Sets the stroke used to draw the gridlines for the row axis, if they
     * are visible, and sends a {@link Plot3DChangeEvent} to all 
     * registered listeners.
     * 
     * @param stroke  the line style ({@code null} not permitted).
     */
    public void setGridlineStrokeForRows(LineStyle stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeForRows = stroke;
        fireChangeEvent();
    }

    /**
     * Returns <code>true</code> if gridlines are shown for the column axis
     * and <code>false</code> otherwise.  The default value is 
     * <code>false</code>
     * 
     * @return A boolean. 
     */
    public boolean getGridlinesVisibleForColumns() {
        return this.gridlinesVisibleForColumns;
    }

    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * column axis and sends a {@link Plot3DChangeEvent} to all registered 
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleForColumns(boolean visible) {
        this.gridlinesVisibleForColumns = visible;
        fireChangeEvent();
    }
    
    /**
     * Returns <code>true</code> if gridlines are shown for the value axis
     * and <code>false</code> otherwise.  The default value is <code>TRUE</code>
     * 
     * @return A boolean. 
     */
    public boolean getGridlinesVisibleForValues() {
        return this.gridlinesVisibleForValues;
    }
    
    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * value axis and sends a {@link Plot3DChangeEvent} to all registered 
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleForValues(boolean visible) {
        this.gridlinesVisibleForValues = visible;
        fireChangeEvent();
    }

    /**
     * Returns the paint for the gridlines associated with the value axis. 
     * The default value is <code>Color:WHITE</code>.
     * 
     * @return The paint for value axis gridlines. 
     */
    public int getGridlinePaintForValues() {
        return this.gridlinePaintForValues;
    }
    
    /**
     * Sets the paint used for the value axis gridlines and sends a 
     * {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint. 
     */
    public void setGridlinePaintForValues(int paint) {
        this.gridlinePaintForValues = paint;
        fireChangeEvent();
    }

    /**
     * Returns the stroke for the gridlines associated with the value axis.
     * The default value is <code>BasicStroke(0.5f, BasicStroke.CAP_ROUND, 
     * BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f)</code>.
     * 
     * @return The stroke (never {@code null}).
     */
    public LineStyle getGridlineStrokeForValues() {
        return this.gridlineStrokeForValues;
    }
    
    /**
     * Sets the stroke used to draw the grid lines for the value axis, if
     * they are visible, and sends a {@link Plot3DChangeEvent} to all
     * registered listeners.
     * 
     * @param stroke  the line style ({@code null} not permitted).
     */
    public void setGridlineStrokeForValues(LineStyle stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeForValues = stroke;
        fireChangeEvent();
    }
    
    /**
     * Returns the paint used to draw the grid lines for the column axis, if
     * they are visible.
     * 
     * @return The color.
     */
    public int getGridlinePaintForColumns() {
        return this.gridlinePaintForColumns;
    }
    
    /**
     * Sets the color for the gridlines for the column axis.
     * 
     * @param color  the color.
     */
    public void setGridlinePaintForColumns(int color) {
        this.gridlinePaintForColumns = color;
        fireChangeEvent();
    }

    /**
     * Returns the stroke for the gridlines associated with the column axis.
     * The default value is <code>BasicStroke(0.5f, BasicStroke.CAP_ROUND, 
     * BasicStroke.JOIN_ROUND, 1f, new float[] { 3f, 3f }, 0f)</code>.
     * 
     * @return The stroke (never {@code null}).
     */
    public LineStyle getGridlineStrokeForColumns() {
        return this.gridlineStrokeForColumns;
    }
    
    /**
     * Sets the stroke used to draw the grid lines for the column axis, if
     * they are visible, and sends a {@link Plot3DChangeEvent} to all
     * registered listeners.
     * 
     * @param stroke  the line style ({@code null} not permitted).
     */
    public void setGridlineStrokeForColumns(LineStyle stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeForColumns = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the legend label generator.
     * 
     * @return The legend label generator (never {@code null}).
     * 
     * @since 1.2
     */
    public CategoryLabelGenerator getLegendLabelGenerator() {
        return this.legendLabelGenerator;    
    }
    
    /**
     * Sets the legend label generator and sends a {@link Plot3DChangeEvent}
     * to all registered listeners.
     * 
     * @param generator  the generator ({@code null} not permitted).
     * 
     * @since 1.2
     */
    public void setLegendLabelGenerator(CategoryLabelGenerator generator) {
        ArgChecks.nullNotPermitted(generator, "generator");
        this.legendLabelGenerator = generator;
        fireChangeEvent();
    }
    
    /**
     * Returns a list containing legend item info, typically one item for
     * each series in the chart.  This is intended for use in the construction
     * of a chart legend.
     * 
     * @return A list containing legend item info.
     */
    @Override
    public List<LegendItemInfo> getLegendInfo() {
        List<LegendItemInfo> result = new ArrayList<LegendItemInfo>();
        List<Comparable<?>> keys = this.dataset.getSeriesKeys();
        for (Comparable<?> key : keys) {
            int series = this.dataset.getSeriesIndex(key);
            int paint = this.renderer.getColorSource().getLegendColor(series);
            String seriesLabel = this.legendLabelGenerator.generateSeriesLabel(
                    this.dataset, key);
            LegendItemInfo info = new StandardLegendItemInfo(key, 
                    seriesLabel, paint);
            result.add(info);
        }
        return result;
    }

    @Override
    public void compose(World world, double xOffset, double yOffset, 
            double zOffset) {
        for (int series = 0; series < this.dataset.getSeriesCount(); series++) {
            for (int row = 0; row < this.dataset.getRowCount(); row++) {
                for (int column = 0; column < this.dataset.getColumnCount(); 
                        column++) {
                    this.renderer.composeItem(this.dataset, series, row, column,
                            world, getDimensions(), xOffset, yOffset, zOffset);
                }
            }
        }
    }
    
    /**
     * Tests this plot for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }    
        if (!(obj instanceof CategoryPlot3D)) {
            return false;
        }
        CategoryPlot3D that = (CategoryPlot3D) obj;
        if (this.gridlinesVisibleForRows != that.gridlinesVisibleForRows) {
            return false;
        }
        if (!this.gridlineStrokeForRows.equals(that.gridlineStrokeForRows)) {
            return false;
        }
        if (this.gridlinePaintForRows != that.gridlinePaintForRows) {
            return false;
        }
        if (this.gridlinesVisibleForColumns != that.gridlinesVisibleForColumns) {
            return false;
        }
        if (!this.gridlineStrokeForColumns.equals(that.gridlineStrokeForColumns)) {
            return false;
        }
        if (this.gridlinePaintForColumns != that.gridlinePaintForColumns) {
            return false;
        }
        if (this.gridlinesVisibleForValues != that.gridlinesVisibleForValues) {
            return false;
        }
        if (!this.gridlineStrokeForValues.equals(that.gridlineStrokeForValues)) {
            return false;
        }
         if (this.gridlinePaintForValues != that.gridlinePaintForValues) {
            return false;
        }
        if (!this.legendLabelGenerator.equals(that.legendLabelGenerator)) {
            return false;
        }
       return super.equals(obj);
    }
    
    /**
     * Receives notification of a change to the dataset. 
     * 
     * @param event  the change event. 
     */
    @Override
    public void datasetChanged(Dataset3DChangeEvent event) {
        // update the category axis labels 
        // and the value axis range
        if (this.autoAdjustDimensions) {
            this.dimensions = calculateDimensions();
        }
        this.columnAxis.configureAsColumnAxis(this);
        this.rowAxis.configureAsRowAxis(this);
        this.valueAxis.configureAsValueAxis(this);
        super.datasetChanged(event);  // propogates a plot change event
    }
    
    /**
     * Returns the dimensions that best suit the current data values.
     * 
     * @return The dimensions (never {@code null}).
     */
    private Dimension3D calculateDimensions() {
        double depth = Math.max(1.0, this.dataset.getRowCount() + 1);
        double width = Math.max(1.0, this.dataset.getColumnCount() + 1);
        double height = Math.max(1.0, Math.min(width, depth));
        return new Dimension3D(width, height, depth);
    }
   
    /**
     * Receives notification that one of the axes has been changed.
     * 
     * @param event  the change event. 
     */
    @Override
    public void axisChanged(Axis3DChangeEvent event) {
        // for now we just fire a plot change event which will flow up the
        // chain and eventually trigger a chart repaint
        fireChangeEvent();
    }

    /**
     * Receives notification that the renderer has been modified in some way.
     * 
     * @param event  information about the event. 
     */
    @Override
    public void rendererChanged(Renderer3DChangeEvent event) {
        // for now we just fire a plot change event which will flow up the
        // chain and eventually trigger a chart repaint
        fireChangeEvent();
    }

}
