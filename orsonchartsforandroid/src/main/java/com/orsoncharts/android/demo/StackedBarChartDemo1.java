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
import com.orsoncharts.android.data.DefaultKeyedValues;
import com.orsoncharts.android.data.category.CategoryDataset3D;
import com.orsoncharts.android.data.category.StandardCategoryDataset3D;

/**
 * Creates a stacked bar chart for the demo.
 */
public class StackedBarChartDemo1 {
    
    /**
     * Creates a demo chart.
     * 
     * @return A demo chart.
     */
    public static Chart3D createChart() {
        Chart3D chart = Chart3DFactory.createStackedBarChart(
                "Stacked Bar Chart", "Put the data source here", createDataset(), null, 
                null, "Value");
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

        DefaultKeyedValues<Number> s1 = new DefaultKeyedValues<Number>();
        s1.put("A", 4);
        s1.put("B", 2);
        s1.put("C", 3);
        s1.put("D", 5);
        s1.put("E", 2);
        s1.put("F", 1);
        DefaultKeyedValues<Number> s2 = new DefaultKeyedValues<Number>();
        s2.put("A", 1);
        s2.put("B", 2);
        s2.put("C", 3);
        s2.put("D", 2);
        s2.put("E", 3);
        s2.put("F", 1);
        DefaultKeyedValues<Number> s3 = new DefaultKeyedValues<Number>();
        s3.put("A", 6);
        s3.put("B", 6);
        s3.put("C", 6);
        s3.put("D", 4);
        s3.put("E", 4);
        s3.put("F", 4);
        DefaultKeyedValues<Number> s4 = new DefaultKeyedValues<Number>();
        s4.put("A", 9);
        s4.put("B", 8);
        s4.put("C", 7);
        s4.put("D", 6);
        s4.put("D", 3);
        s4.put("E", 4);
        s4.put("F", 6);
        DefaultKeyedValues<Number> s5 = new DefaultKeyedValues<Number>();
        s5.put("A", 9);
        s5.put("B", 8);
        s5.put("C", 7);
        s5.put("D", 6);
        s5.put("E", 7);
        s5.put("F", 9);

        dataset.addSeriesAsRow("Series 1", "Row 1", s1);
        dataset.addSeriesAsRow("Series 2", "Row 2", s2);
        dataset.addSeriesAsRow("Series 3", "Row 2", s3);
        dataset.addSeriesAsRow("Series 4", "Row 3", s4);
        dataset.addSeriesAsRow("Series 5", "Row 3", s5);
        return dataset;
    }

}
