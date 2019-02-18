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

package com.orsoncharts.android;

import com.orsoncharts.android.axis.CategoryAxis3D;
import com.orsoncharts.android.axis.NumberAxis3D;
import com.orsoncharts.android.axis.StandardCategoryAxis3D;
import com.orsoncharts.android.axis.ValueAxis3D;
import com.orsoncharts.android.data.PieDataset3D;
import com.orsoncharts.android.data.category.CategoryDataset3D;
import com.orsoncharts.android.data.function.Function3D;
import com.orsoncharts.android.data.xyz.XYZDataset;
import com.orsoncharts.android.data.xyz.XYZSeriesCollection;
import com.orsoncharts.android.legend.ColorScaleLegendBuilder;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.plot.PiePlot3D;
import com.orsoncharts.android.plot.XYZPlot;
import com.orsoncharts.android.renderer.category.AreaRenderer3D;
import com.orsoncharts.android.renderer.category.BarRenderer3D;
import com.orsoncharts.android.renderer.category.CategoryRenderer3D;
import com.orsoncharts.android.renderer.category.LineRenderer3D;
import com.orsoncharts.android.renderer.category.StackedBarRenderer3D;
import com.orsoncharts.android.renderer.xyz.BarXYZRenderer;
import com.orsoncharts.android.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.android.renderer.xyz.SurfaceRenderer;
import com.orsoncharts.android.renderer.xyz.XYZRenderer;

/**
 * Utility methods for constructing common chart types.  Charts can be 
 * assembled piece-wise, but usually it is simpler to use the methods in this
 * class then customise the resulting chart as necessary.
 */
public class Chart3DFactory {
    
    /**
     * Private constructor prevents instantiation which is unnecessary.
     */
    private Chart3DFactory() {
        // no need to instantiate this ever
    }
    
    /**
     * Creates and returns a pie chart based on the supplied dataset.  The 
     * chart returned by this method will be constructed using a  
     * {@link PiePlot3D} instance (so it is safe to cast the result of
     * <code>chart.getPlot()</code>).
     * <br><br>
     * For reference, here is a sample pie chart:
     * <div>
     * <object id="PieChart3DDemo1" data="../../doc-files/PieChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return A pie chart (never {@code null}).
     */
    public static Chart3D createPieChart(String title, String subtitle, 
            PieDataset3D dataset) {
        PiePlot3D plot = new PiePlot3D(dataset);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns a bar chart based on the supplied dataset. The chart
     * returned by this method will be constructed with a 
     * {@link CategoryPlot3D} using a {@link BarRenderer3D} (so it is
     * safe to cast the plot and/or renderer to customise attributes that are
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample bar chart:
     * <div>
     * <object id="BarChart3DDemo1" data="../../doc-files/BarChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param dataset  the dataset ({@code null} not permitted).
     * @param rowAxisLabel  the row axis label ({@code null} permitted).
     * @param columnAxisLabel  the column axis label ({@code null}
     *     permitted).
     * @param valueAxisLabel  the value axis label ({@code null}
     *     permitted).
     * 
     * @return A bar chart (never {@code null}).
     */
    public static Chart3D createBarChart(String title, String subtitle,
            CategoryDataset3D dataset, String rowAxisLabel, 
            String columnAxisLabel, String valueAxisLabel) {
        CategoryAxis3D rowAxis = new StandardCategoryAxis3D(rowAxisLabel);
        CategoryAxis3D columnAxis = new StandardCategoryAxis3D(columnAxisLabel);
        ValueAxis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new BarRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, 
                rowAxis, columnAxis, valueAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns a stacked bar chart based on the supplied dataset.
     * The chart returned by this method will be constructed with a 
     * {@link CategoryPlot3D} using a {@link StackedBarRenderer3D} (so it is
     * safe to cast the plot and/or renderer to customise attributes that
     * are specific to those subclasses).
     * <br><br>
     * For reference, here is a sample stacked bar chart:
     * <div>
     * <object id="StackedBarChart3DDemo1" data="../../doc-files/StackedBarChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param dataset  the dataset ({@code null} not permitted).
     * @param rowAxisLabel  the row axis label ({@code null} permitted).
     * @param columnAxisLabel  the column axis label ({@code null}
     *     permitted).
     * @param valueAxisLabel  the value axis label ({@code null}
     *     permitted).
     * 
     * @return A stacked bar chart (never {@code null}).
     */
    public static Chart3D createStackedBarChart(String title, String subtitle,
            CategoryDataset3D dataset, String rowAxisLabel, 
            String columnAxisLabel, String valueAxisLabel) {
        CategoryAxis3D rowAxis = new StandardCategoryAxis3D(rowAxisLabel);
        CategoryAxis3D columnAxis = new StandardCategoryAxis3D(columnAxisLabel);
        ValueAxis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new StackedBarRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns an area chart based on the supplied dataset.  The 
     * chart returned by this method will be constructed with a 
     * {@link CategoryPlot3D} using an {@link AreaRenderer3D} (so it is safe 
     * to cast the plot and/or renderer to customise attributes that are 
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample area chart:
     * <div>
     * <object id="AreaChart3DDemo1" data="../../doc-files/AreaChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param dataset  the dataset ({@code null} not permitted).
     * @param rowAxisLabel  the row axis label ({@code null} permitted).
     * @param columnAxisLabel  the column axis label ({@code null}
     *     permitted).
     * @param valueAxisLabel  the value axis label ({@code null}
     *     permitted).
     * 
     * @return An area chart (never {@code null}).
     */
    public static Chart3D createAreaChart(String title, String subtitle,
            CategoryDataset3D dataset, String rowAxisLabel, 
            String columnAxisLabel, String valueAxisLabel) {
        
        CategoryAxis3D rowAxis = new StandardCategoryAxis3D(rowAxisLabel);
        StandardCategoryAxis3D columnAxis 
                = new StandardCategoryAxis3D(columnAxisLabel);
        columnAxis.setFirstCategoryHalfWidth(true);
        columnAxis.setLastCategoryHalfWidth(true);
        ValueAxis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new AreaRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns a line chart based on the supplied dataset.  The 
     * chart returned by this method will be constructed with a 
     * {@link CategoryPlot3D} using a {@link LineRenderer3D} (so it is safe
     * to cast the plot and/or renderer to customise attributes that are
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample line chart:
     * <div>
     * <object id="LineChart3DDemo1" data="../../doc-files/LineChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param dataset  the dataset ({@code null} not permitted).
     * @param rowAxisLabel  the row axis label ({@code null} permitted).
     * @param columnAxisLabel  the column axis label ({@code null}
     *     permitted).
     * @param valueAxisLabel  the value axis label ({@code null}
     *     permitted).
     * 
     * @return A line chart (never {@code null}).
     */
    public static Chart3D createLineChart(String title, String subtitle,
            CategoryDataset3D dataset, String rowAxisLabel, 
            String columnAxisLabel, String valueAxisLabel) {
        CategoryAxis3D rowAxis = new StandardCategoryAxis3D(rowAxisLabel);
        StandardCategoryAxis3D columnAxis 
                = new StandardCategoryAxis3D(columnAxisLabel);
        columnAxis.setFirstCategoryHalfWidth(true);
        columnAxis.setLastCategoryHalfWidth(true);
        ValueAxis3D valueAxis = new NumberAxis3D(valueAxisLabel, 
                new Range(0.0, 1.0));
        CategoryRenderer3D renderer = new LineRenderer3D();
        CategoryPlot3D plot = new CategoryPlot3D(dataset, renderer, rowAxis, 
                columnAxis, valueAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates and returns a scatter plot based on the supplied dataset 
     * (containing one or more series of <code>(x, y, z)</code> values).  The 
     * chart returned by this method will be constructed with an 
     * {@link XYZPlot} using a {@link ScatterXYZRenderer}  (so it is safe
     * to cast the plot and/or renderer to customise attributes that are
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample scatter chart:
     * <div>
     * <object id="ScatterPlot3DDemo1" data="../../doc-files/ScatterPlot3DDemo1.svg"  
     * type="image/svg+xml" width="564" height="351"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param dataset  the dataset ({@code null} not permitted).
     * @param xAxisLabel  the x-axis label ({@code null} permitted).
     * @param yAxisLabel  the y-axis label ({@code null} permitted).
     * @param zAxisLabel  the z-axis label ({@code null} permitted).
     * 
     * @return The chart. 
     */
    public static Chart3D createScatterChart(String title, String subtitle, 
            XYZDataset dataset, String xAxisLabel, String yAxisLabel, 
            String zAxisLabel) {
        ValueAxis3D xAxis = new NumberAxis3D(xAxisLabel);
        ValueAxis3D yAxis = new NumberAxis3D(yAxisLabel);
        ValueAxis3D zAxis = new NumberAxis3D(zAxisLabel);
        XYZRenderer renderer = new ScatterXYZRenderer();
        XYZPlot plot = new XYZPlot(dataset, renderer, xAxis, yAxis, zAxis);
        return new Chart3D(title, subtitle, plot);
    }
    
    /**
     * Creates a surface chart for the specified function.
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param function  the function ({@code null} not permitted).
     * @param xAxisLabel  the x-axis label ({@code null} permitted).
     * @param yAxisLabel  the y-axis label ({@code null} permitted).
     * @param zAxisLabel  the z-axis label ({@code null} permitted).
     * 
     * @return The chart.
     * 
     * @since 1.1
     */
    public static Chart3D createSurfaceChart(String title, String subtitle, 
            Function3D function, String xAxisLabel, String yAxisLabel, 
            String zAxisLabel) {
        ValueAxis3D xAxis = new NumberAxis3D(xAxisLabel);
        ValueAxis3D yAxis = new NumberAxis3D(yAxisLabel);
        ValueAxis3D zAxis = new NumberAxis3D(zAxisLabel);
        XYZRenderer renderer = new SurfaceRenderer(function);
        // we pass an empty dataset because the plot must have a non-null
        // dataset, but the renderer never looks at it...
        XYZPlot plot = new XYZPlot(new XYZSeriesCollection(), renderer, xAxis, 
                yAxis, zAxis);
        
        Chart3D chart = new Chart3D(title, subtitle, plot);
        chart.setLegendBuilder(new ColorScaleLegendBuilder());
        return chart;
    }
    
    /**
     * Creates and returns a bar chart based on the supplied dataset (this is 
     * for special cases, most general cases will be covered by the 
     * {@link #createBarChart(String, String, CategoryDataset3D, String, String, String) }
     * method). The chart returned by this method will be constructed with an 
     * {@link XYZPlot} using a {@link BarXYZRenderer}  (so it is safe
     * to cast the plot and/or renderer to customise attributes that are
     * specific to those subclasses).
     * <br><br>
     * For reference, here is a sample XYZ bar chart:
     * <div>
     * <object id="XYZBarChart3DDemo1" data="../../doc-files/XYZBarChart3DDemo1.svg"  
     * type="image/svg+xml" width="500" height="359"> 
     * </object>
     * </div>
     * 
     * @param title  the chart title ({@code null} permitted).
     * @param subtitle  the chart subtitle ({@code null} permitted).
     * @param dataset  the dataset ({@code null} not permitted).
     * @param xAxisLabel  the x-axis label ({@code null} permitted).
     * @param yAxisLabel  the y-axis label ({@code null} permitted).
     * @param zAxisLabel  the z-axis label ({@code null} permitted).
     * 
     * @return The chart. 
     */
    public static Chart3D createXYZBarChart(String title, String subtitle, 
            XYZDataset dataset, String xAxisLabel, String yAxisLabel, 
            String zAxisLabel) {
        ValueAxis3D xAxis = new NumberAxis3D(xAxisLabel);
        ValueAxis3D yAxis = new NumberAxis3D(yAxisLabel);
        ValueAxis3D zAxis = new NumberAxis3D(zAxisLabel);
        XYZRenderer renderer = new BarXYZRenderer();
        XYZPlot plot = new XYZPlot(dataset, renderer, xAxis, yAxis, zAxis);
        return new Chart3D(title, subtitle, plot);
    }

}
