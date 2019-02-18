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
import com.orsoncharts.android.data.StandardPieDataset3D;
import com.orsoncharts.android.data.category.StandardCategoryDataset3D;
import com.orsoncharts.android.data.xyz.XYZSeries;
import com.orsoncharts.android.data.xyz.XYZSeriesCollection;

public class Charts {

    /**
     * Creates and returns a simple pie chart for demo purposes.
     *
     * @return A pie chart.
     */
    public static Chart3D createDemoPieChart() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("A", 60.0);
        dataset.add("B", 30.0);
        return Chart3DFactory.createPieChart("Pie Chart", "Subtitle...",
                dataset);
    }

    /**
     * Creates and returns a simple bar chart for demo purposes.
     *
     * @return A bar chart.
     */
    public static Chart3D createDemoBarChart() {
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
        dataset.addValue(5.0, "Series 1", "Row 1", "Column 1");
        dataset.addValue(7.0, "Series 1", "Row 1", "Column 2");
        dataset.addValue(9.0, "Series 1", "Row 1", "Column 3");
        dataset.addValue(11.0, "Series 2", "Row 2", "Column 1");
        dataset.addValue(7.0, "Series 2", "Row 2", "Column 2");
        dataset.addValue(3.0, "Series 2", "Row 2", "Column 3");
        return Chart3DFactory.createBarChart("Bar Chart", "Subtitle...",
                dataset, "Row Axis", "Column Axis", "Value Axis");
    }

    /**
     * Creates and returns a simple scatter chart for demo purposes.
     *
     * @return A scatter chart.
     */
    public static Chart3D createDemoScatterChart() {
        XYZSeries series1 = new XYZSeries("Series 1");
        XYZSeries series2 = new XYZSeries("Series 2");
        for (int i = 0; i < 100; i++) {
            series1.add(Math.random() * 5, Math.random() * 10,
                    Math.random() * 20);
            series2.add(Math.random() * 10, Math.random() * 5,
                    Math.random() * 20);
        }
        XYZSeriesCollection dataset = new XYZSeriesCollection();
        dataset.add(series1);
        dataset.add(series2);
        return Chart3DFactory.createScatterChart("Scatter Chart", "Subtitle...",
                dataset, "X", "Y", "Z");
    }
}