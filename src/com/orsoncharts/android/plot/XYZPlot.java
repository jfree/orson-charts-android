/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.plot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.orsoncharts.android.LineStyle;
import com.orsoncharts.android.axis.Axis3DChangeEvent;
import com.orsoncharts.android.axis.Axis3DChangeListener;
import com.orsoncharts.android.axis.ValueAxis3D;
import com.orsoncharts.android.data.Dataset3DChangeEvent;
import com.orsoncharts.android.data.Dataset3DChangeListener;
import com.orsoncharts.android.data.xyz.XYZDataset;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.World;
import com.orsoncharts.android.legend.LegendItemInfo;
import com.orsoncharts.android.legend.StandardLegendItemInfo;
import com.orsoncharts.android.renderer.ComposeType;
import com.orsoncharts.android.renderer.Renderer3DChangeEvent;
import com.orsoncharts.android.renderer.Renderer3DChangeListener;
import com.orsoncharts.android.renderer.xyz.XYZRenderer;
import com.orsoncharts.android.util.ArgChecks;

/**
 * A 3D plot with three numerical axes that displays data from an
 * {@link XYZDataset}.
 */
public class XYZPlot extends AbstractPlot3D implements Dataset3DChangeListener, 
        Axis3DChangeListener, Renderer3DChangeListener, Serializable {

    private static LineStyle DEFAULT_GRIDLINE_STROKE = new LineStyle(0.5f,
            Paint.Cap.ROUND, Paint.Join.ROUND);

    /** The dataset. */
    private XYZDataset dataset;

    /** The renderer. */
    private XYZRenderer renderer;
  
    /** The x-axis. */
    private ValueAxis3D xAxis;

    /** The y-axis. */
    private ValueAxis3D yAxis;
  
    /** The z-axis. */
    private ValueAxis3D zAxis;
    
    /** Are gridlines visible for the x-axis? */
    private boolean gridlinesVisibleX;
    
    /** The paint for the x-axis gridlines. */
    private int gridlinePaintX;

    /** The stroke for the x-axis gridlines. */
    private LineStyle gridlineStrokeX;
    
    /** Are gridlines visible for the y-axis? */
    private boolean gridlinesVisibleY;

    /** The paint for the y-axis gridlines. */
    private int gridlinePaintY;
    
    /** The stroke for the y-axis gridlines. */
    private LineStyle gridlineStrokeY;
    
    /** Are gridlines visible for the z-axis? */
    private boolean gridlinesVisibleZ;

    /** The paint for the z-axis gridlines. */
    private int gridlinePaintZ;

    /** The stroke for the z-axis gridlines. */
    private LineStyle gridlineStrokeZ;
    
    /**
     * Creates a new plot with the specified axes.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param renderer  the renderer (<code>null</code> not permitted).
     * @param xAxis  the x-axis (<code>null</code> not permitted).
     * @param yAxis  the y-axis (<code>null</code> not permitted).
     * @param zAxis  the z-axis (<code>null</code> not permitted).
     */
    public XYZPlot(XYZDataset dataset, XYZRenderer renderer, ValueAxis3D xAxis, 
            ValueAxis3D yAxis, ValueAxis3D zAxis) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        ArgChecks.nullNotPermitted(renderer, "renderer");
        ArgChecks.nullNotPermitted(xAxis, "xAxis");
        ArgChecks.nullNotPermitted(yAxis, "yAxis");
        ArgChecks.nullNotPermitted(zAxis, "zAxis");
        this.dimensions = new Dimension3D(10, 10, 10);
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        this.renderer = renderer;
        this.renderer.setPlot(this);
        this.renderer.addChangeListener(this);
        this.xAxis = xAxis;
        this.xAxis.addChangeListener(this);
        this.xAxis.configureAsXAxis(this);
        this.zAxis = zAxis;
        this.zAxis.addChangeListener(this);
        this.zAxis.configureAsZAxis(this);
        this.yAxis = yAxis;
        this.yAxis.addChangeListener(this);
        this.yAxis.configureAsYAxis(this);
        this.gridlinesVisibleX = true;
        this.gridlinePaintX = Color.WHITE;
        this.gridlineStrokeX = DEFAULT_GRIDLINE_STROKE;
        this.gridlinesVisibleY = true;
        this.gridlinePaintY = Color.WHITE;
        this.gridlineStrokeY = DEFAULT_GRIDLINE_STROKE;
        this.gridlinesVisibleZ = true;
        this.gridlinePaintZ = Color.WHITE;
        this.gridlineStrokeZ = DEFAULT_GRIDLINE_STROKE;
    }
  
    /**
     * Sets the dimensions for the plot and notifies registered listeners that
     * the plot dimensions have been changed.
     * 
     * @param dim  the new dimensions (<code>null</code> not permitted).
     */
    public void setDimensions(Dimension3D dim) {
        ArgChecks.nullNotPermitted(dim, "dim");
        this.dimensions = dim;
        fireChangeEvent();
    }

    /**
     * Returns the dataset for the plot.
     * 
     * @return The dataset (never <code>null</code>). 
     */
    public XYZDataset getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset and sends a change event notification to all registered
     * listeners.
     * 
     * @param dataset  the new dataset (<code>null</code> not permitted).
     */
    public void setDataset(XYZDataset dataset) {
        ArgChecks.nullNotPermitted(dataset, "dataset");
        this.dataset.removeChangeListener(this);
        this.dataset = dataset;
        this.dataset.addChangeListener(this);
        fireChangeEvent();
    }

    /**
     * Returns the x-axis.
     * 
     * @return The x-axis (never <code>null</code>). 
     */
    public ValueAxis3D getXAxis() {
        return this.xAxis;
    }

    /**
     * Sets the x-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param xAxis  the x-axis (<code>null</code> not permitted). 
     */
    public void setXAxis(ValueAxis3D xAxis) {
        ArgChecks.nullNotPermitted(xAxis, "xAxis");
        this.xAxis = xAxis;
        fireChangeEvent();
    }

    /**
     * Returns the y-axis.
     * 
     * @return The y-axis (never <code>null</code>). 
     */
    public ValueAxis3D getYAxis() {
        return this.yAxis;
    }

    /**
     * Sets the y-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param yAxis  the y-axis (<code>null</code> not permitted). 
     */
    public void setYAxis(ValueAxis3D yAxis) {
        ArgChecks.nullNotPermitted(yAxis, "yAxis");
        this.yAxis = yAxis;
        fireChangeEvent();
    }
    
    /**
     * Returns the z-axis.
     * 
     * @return The z-axis (never <code>null</code>). 
     */
    public ValueAxis3D getZAxis() {
        return this.zAxis;
    }

    /**
     * Sets the z-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param zAxis  the z-axis (<code>null</code> not permitted). 
     */
    public void setZAxis(ValueAxis3D zAxis) {
        ArgChecks.nullNotPermitted(zAxis, "zAxis");
        this.zAxis = zAxis;
        fireChangeEvent();
    }
    
    /**
     * Returns the renderer for the plot.
     * 
     * @return The renderer (possibly <code>null</code>).
     */
    public XYZRenderer getRenderer() {
        return this.renderer;
    }

    /**
     * Sets the renderer for the plot and sends a {@link Plot3DChangeEvent}
     * to all registered listeners.
     * 
     * @param renderer  the renderer (<code>null</code> not permitted). 
     */
    public void setRenderer(XYZRenderer renderer) {
        this.renderer.setPlot(null);
        this.renderer.removeChangeListener(this);
        this.renderer = renderer;
        this.renderer.setPlot(this);
        this.renderer.addChangeListener(this);
        fireChangeEvent();
    }

    /**
     * Returns the flag that controls whether or not gridlines are shown for
     * the x-axis.
     * 
     * @return A boolean. 
     */
    public boolean isGridlinesVisibleX() {
        return this.gridlinesVisibleX;
    }

    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * x-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleX(boolean visible) {
        this.gridlinesVisibleX = visible;
        fireChangeEvent();
    }
    
    /**
     * Returns the paint used to draw the gridlines for the x-axis.
     * 
     * @return The paint. 
     */
    public int getGridlinePaintX() {
        return this.gridlinePaintX;
    }
    
    /**
     * Sets the paint used to draw the gridlines for the x-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint.
     */
    public void setGridlinePaintX(int paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintX = paint;
        fireChangeEvent();
    }
    
    /**
     * Returns the stroke used to draw the gridlines for the x-axis.
     * 
     * @return The stroke (<code>null</code> not permitted). 
     */
    public LineStyle getGridlineStrokeX() {
        return this.gridlineStrokeX;
    }

    /**
     * Sets the stroke used to draw the gridlines for the x-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted).
     */
    public void setGridlineStrokeX(LineStyle stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeX = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the flag that controls whether or not gridlines are shown for
     * the y-axis.
     * 
     * @return A boolean. 
     */
    public boolean isGridlinesVisibleY() {
        return this.gridlinesVisibleY;
    }
    
    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * y-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleY(boolean visible) {
        this.gridlinesVisibleY = visible;
        fireChangeEvent();
    }

    /**
     * Returns the paint used to draw the gridlines for the y-axis.
     * 
     * @return The paint. 
     */
    public int getGridlinePaintY() {
        return this.gridlinePaintY;
    }
    
    /**
     * Sets the paint used to draw the gridlines for the y-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint.
     */
    public void setGridlinePaintY(int paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintY = paint;
        fireChangeEvent();
    }

    /**
     * Returns the stroke used to draw the gridlines for the y-axis.
     * 
     * @return The stroke (<code>null</code> not permitted). 
     */
    public LineStyle getGridlineStrokeY() {
        return this.gridlineStrokeY;
    }
    
    /**
     * Sets the stroke used to draw the gridlines for the y-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted).
     */
    public void setGridlineStrokeY(LineStyle stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeY = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the flag that controls whether or not gridlines are shown for
     * the z-axis.
     * 
     * @return A boolean. 
     */
    public boolean isGridlinesVisibleZ() {
        return this.gridlinesVisibleZ;
    }
    
    /**
     * Sets the flag that controls whether or not gridlines are shown for the
     * z-axis and sends a {@link Plot3DChangeEvent} to all registered
     * listeners.
     * 
     * @param visible  the new flag value.
     */
    public void setGridlinesVisibleZ(boolean visible) {
        this.gridlinesVisibleZ = visible;
        fireChangeEvent();
    }
    
    /**
     * Returns the paint used to draw the gridlines for the z-axis.
     * 
     * @return The paint. 
     */
    public int getGridlinePaintZ() {
        return this.gridlinePaintZ;
    }    
    
    /**
     * Sets the paint used to draw the gridlines for the z-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public void setGridlinePaintZ(int paint) {
        ArgChecks.nullNotPermitted(paint, "paint");
        this.gridlinePaintZ = paint;
        fireChangeEvent();
    }

    /**
     * Returns the stroke used to draw the gridlines for the z-axis.
     * 
     * @return The stroke (<code>null</code> not permitted). 
     */
    public LineStyle getGridlineStrokeZ() {
        return this.gridlineStrokeZ;
    }
    
    /**
     * Sets the stroke used to draw the gridlines for the z-axis, and sends 
     * a {@link Plot3DChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted).
     */
    public void setGridlineStrokeZ(LineStyle stroke) {
        ArgChecks.nullNotPermitted(stroke, "stroke");
        this.gridlineStrokeZ = stroke;
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
            int color = this.renderer.getColorSource().getLegendColor(series);
            LegendItemInfo info = new StandardLegendItemInfo(key, 
                    key.toString(), color);
            result.add(info);
        }
        return result;
    }

    /**
     * Adds 3D objects representing the current data for the plot to the 
     * specified world.  After the world has been populated (or constructed) in
     * this way, it is ready for rendering.
     * 
     * @param world  the world (<code>null</code> not permitted).
     * @param xOffset  the x-offset.
     * @param yOffset  the y-offset.
     * @param zOffset  the z-offset.
     */
    @Override
    public void compose(World world, double xOffset, double yOffset, 
            double zOffset) {
      
        if (this.renderer.getComposeType() == ComposeType.ALL) {
            this.renderer.composeAll(this, world, this.dimensions, xOffset, 
                    yOffset, zOffset);
        } else if (this.renderer.getComposeType() == ComposeType.PER_ITEM) {
            // for each data point in the dataset figure out if the composed 
            // shape intersects with the visible 
            // subset of the world, and if so add the object
            int seriesCount = this.dataset.getSeriesCount();
            for (int series = 0; series < seriesCount; series++) {
                int itemCount = this.dataset.getItemCount(series);
                for (int item = 0; item < itemCount; item++) {
                    this.renderer.composeItem(this.dataset, series, item, world, 
                    this.dimensions, xOffset, yOffset, zOffset);
                }
            }
        } else {
            // if we get here, someone changed the ComposeType enum
            throw new IllegalStateException("ComposeType not expected: " 
                    + this.renderer.getComposeType());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYZPlot)) {
            return false;
        }
        XYZPlot that = (XYZPlot) obj;
        if (!this.dimensions.equals(that.dimensions)) {
            return false;
        }
        if (this.gridlinesVisibleX != that.gridlinesVisibleX) {
            return false;
        }
        if (this.gridlinesVisibleY != that.gridlinesVisibleY) {
            return false;
        }
        if (this.gridlinesVisibleZ != that.gridlinesVisibleZ) {
            return false;
        }
        if (this.gridlinePaintX != that.gridlinePaintX) {
            return false;
        }
        if (this.gridlinePaintY != that.gridlinePaintY) {
            return false;
        }
        if (this.gridlinePaintZ != that.gridlinePaintZ) {
            return false;
        }
        if (!this.gridlineStrokeX.equals(that.gridlineStrokeX)) {
            return false;
        }
        if (!this.gridlineStrokeY.equals(that.gridlineStrokeY)) {
            return false;
        }
        if (!this.gridlineStrokeZ.equals(that.gridlineStrokeZ)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Receives notification that one of the plot's axes has changed, and 
     * responds by passing on a {@link Plot3DChangeEvent} to the plot's 
     * registered listeners (with the default set-up, this notifies the 
     * chart).
     * 
     * @param event  the event. 
     */
    @Override
    public void axisChanged(Axis3DChangeEvent event) {
        this.yAxis.configureAsYAxis(this);
        fireChangeEvent();
    }

    /**
     * Receives notification that the plot's renderer has changed, and 
     * responds by passing on a {@link Plot3DChangeEvent} to the plot's 
     * registered listeners (with the default set-up, this notifies the 
     * chart).
     * 
     * @param event  the event. 
     */
    @Override
    public void rendererChanged(Renderer3DChangeEvent event) {
        fireChangeEvent();
    }

    /**
     * Receives notification that the plot's dataset has changed, and 
     * responds by passing on a {@link Plot3DChangeEvent} to the plot's 
     * registered listeners (with the default set-up, this notifies the 
     * chart).
     * 
     * @param event  the event. 
     */
    @Override
    public void datasetChanged(Dataset3DChangeEvent event) {
        this.xAxis.configureAsXAxis(this);
        this.yAxis.configureAsYAxis(this);
        this.zAxis.configureAsZAxis(this);
        super.datasetChanged(event);
    }
    
}
