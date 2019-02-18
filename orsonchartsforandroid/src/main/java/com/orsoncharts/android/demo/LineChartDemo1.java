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

package com.orsoncharts.android.demo;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.Chart3DFactory;
import com.orsoncharts.android.Colors;
import com.orsoncharts.android.axis.NumberAxis3D;
import com.orsoncharts.android.axis.NumberTickSelector;
import com.orsoncharts.android.data.DefaultKeyedValues;
import com.orsoncharts.android.data.KeyedValues;
import com.orsoncharts.android.data.category.CategoryDataset3D;
import com.orsoncharts.android.data.category.StandardCategoryDataset3D;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.graphics3d.ViewPoint3D;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.renderer.category.StandardCategoryColorSource;

/**
 * Creates a line chart instance for the demo.
 */
public class LineChartDemo1 {

    /**
     * Creates a demo chart.
     * 
     * @return A demo chart.
     */
    public static Chart3D createChart() {
        Chart3D chart = Chart3DFactory.createLineChart(
                "Web Browser Market Share", 
                "Source: http://gs.statcounter.com", createDataset(), null, 
                null, "Market Share (%)");
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setDimensions(new Dimension3D(18, 8, 4));
        plot.getRowAxis().setVisible(false);
        NumberAxis3D valueAxis = (NumberAxis3D) plot.getValueAxis();
        valueAxis.setTickSelector(new NumberTickSelector(true));
        plot.getRenderer().setColorSource(new StandardCategoryColorSource(
                Colors.getColors1()));
        chart.setViewPoint(ViewPoint3D.createAboveViewPoint(30)); 
        return chart;
    }
    
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    private static CategoryDataset3D createDataset() {
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
        dataset.addSeriesAsRow("Safari", createSafariData());
        dataset.addSeriesAsRow("Firefox", createFirefoxData());
        dataset.addSeriesAsRow("Internet Explorer", 
                createInternetExplorerData());
        dataset.addSeriesAsRow("Chrome", createChromeData());
        return dataset;
    }

    private static KeyedValues<Number> createChromeData() {
        DefaultKeyedValues<Number> series = new DefaultKeyedValues<Number>();
        series.put("Jan-12", 0.2840);
        series.put("Feb-12", 0.2984);
        series.put("Mar-12", 0.3087);
        series.put("Apr-12", 0.3123);
        series.put("May-12", 0.3243);
        series.put("Jun-12", 0.3276);
        series.put("Jul-12", 0.3381);
        series.put("Aug-12", 0.3359);
        series.put("Sep-12", 0.3421);
        series.put("Oct-12", 0.3477);
        series.put("Nov-12", 0.3572);
        series.put("Dec-12", 0.3642);

        series.put("Jan-13", 0.3652);
        series.put("Feb-13", 0.3709);
        series.put("Mar-13", 0.3807);
        series.put("Apr-13", 0.3915);
        series.put("May-13", 0.4138);
        series.put("Jun-13", 0.4268);
        return series;
    }

    private static KeyedValues<Number> createFirefoxData() {
        DefaultKeyedValues<Number> series = new DefaultKeyedValues<Number>();
        series.put("Jan-12", 0.2478);
        series.put("Feb-12", 0.2488);
        series.put("Mar-12", 0.2498);
        series.put("Apr-12", 0.2487);
        series.put("May-12", 0.2555);
        series.put("Jun-12", 0.2456);
        series.put("Jul-12", 0.2373);
        series.put("Aug-12", 0.2285);
        series.put("Sep-12", 0.2240);
        series.put("Oct-12", 0.2232);
        series.put("Nov-12", 0.2237);
        series.put("Dec-12", 0.2189);
        series.put("Jan-13", 0.2142);
        series.put("Feb-13", 0.2134);
        series.put("Mar-13", 0.2087);
        series.put("Apr-13", 0.2006);
        series.put("May-13", 0.1976);
        series.put("Jun-13", 0.2001);
        return series;
    }

    private static KeyedValues<Number> createInternetExplorerData() {
        DefaultKeyedValues<Number> series = new DefaultKeyedValues<Number>();
        series.put("Jan-12", 0.3745);
        series.put("Feb-12", 0.3575);
        series.put("Mar-12", 0.3481);
        series.put("Apr-12", 0.3407);
        series.put("May-12", 0.3212);
        series.put("Jun-12", 0.3231);
        series.put("Jul-12", 0.3204);
        series.put("Aug-12", 0.3285);
        series.put("Sep-12", 0.3270);
        series.put("Oct-12", 0.3208);
        series.put("Nov-12", 0.3123);
        series.put("Dec-12", 0.3078);
        series.put("Jan-13", 0.3069);
        series.put("Feb-13", 0.2982);
        series.put("Mar-13", 0.2930);
        series.put("Jun-13", 0.2544);
        series.put("May-13", 0.2772);
        series.put("Apr-13", 0.2971);
        return series;
    }

    private static KeyedValues<Number> createSafariData() {
        DefaultKeyedValues<Number> series = new DefaultKeyedValues<Number>();
        series.put("Jan-12", 0.0662);
        series.put("Feb-12", 0.0677);
        series.put("Mar-12", 0.0672);
        series.put("Apr-12", 0.0713);
        series.put("May-12", 0.0709);
        series.put("Jun-12", 0.0700);
        series.put("Jul-12", 0.0712);
        series.put("Aug-12", 0.0739);
        series.put("Sep-12", 0.0770);
        series.put("Oct-12", 0.0781);
        series.put("Nov-12", 0.0783);
        series.put("Dec-12", 0.0792);
        series.put("Jan-13", 0.0830);
        series.put("Feb-13", 0.0860);
        series.put("Mar-13", 0.0850);
        series.put("Apr-13", 0.0800);
        series.put("May-13", 0.0796);
        series.put("Jun-13", 0.0839);
        return series;
    }
    

}
