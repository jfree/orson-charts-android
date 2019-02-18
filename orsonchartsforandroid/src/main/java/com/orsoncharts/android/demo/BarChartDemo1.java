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
 * Creates a bar chart instance for the demo.
 */
public class BarChartDemo1 {
    
    /**
     * Creates a bar chart instance.
     * 
     * @return A bar chart instance.
     */
    public static Chart3D createChart() {
        Chart3D chart = Chart3DFactory.createBarChart(
            "Average Maximum Temperature", 
            "http://www.worldclimateguide.co.uk/climateguides/", createDataset(), 
            null, null, "Temp deg C");
        chart.getViewPoint().panLeftRight(-Math.PI / 60);
        return chart;
    }

    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    static CategoryDataset3D createDataset() {    
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
                
        DefaultKeyedValues<Number> s3 = new DefaultKeyedValues<Number>();
        s3.put("Jan", 7);
        s3.put("Feb", 7);
        s3.put("Mar", 10);
        s3.put("Apr", 13);
        s3.put("May", 17);
        s3.put("Jun", 20);
        s3.put("Jul", 22);
        s3.put("Aug", 21);
        s3.put("Sep", 19);
        s3.put("Oct", 15);
        s3.put("Nov", 10);
        s3.put("Dec", 8);
        dataset.addSeriesAsRow("London", s3);

        DefaultKeyedValues<Number> s1 = new DefaultKeyedValues<Number>();
        s1.put("Jan", 3);
        s1.put("Feb", 5);
        s1.put("Mar", 9);
        s1.put("Apr", 14);
        s1.put("May", 18);
        s1.put("Jun", 22);
        s1.put("Jul", 25);
        s1.put("Aug", 24);
        s1.put("Sep", 20);
        s1.put("Oct", 14);
        s1.put("Nov", 8);
        s1.put("Dec", 4);
        dataset.addSeriesAsRow("Geneva", s1);
        
        DefaultKeyedValues<Number> s2 = new DefaultKeyedValues<Number>();
        s2.put("Jan", 9);
        s2.put("Feb", 11);
        s2.put("Mar", 13);
        s2.put("Apr", 16);
        s2.put("May", 20);
        s2.put("Jun", 23);
        s2.put("Jul", 26);
        s2.put("Aug", 26);
        s2.put("Sep", 24);
        s2.put("Oct", 19);
        s2.put("Nov", 13);
        s2.put("Dec", 9);
        dataset.addSeriesAsRow("Bergerac", s2);

        DefaultKeyedValues<Number> s4 = new DefaultKeyedValues<Number>();
        s4.put("Jan", 22);
        s4.put("Feb", 22);
        s4.put("Mar", 20);
        s4.put("Apr", 17);
        s4.put("May", 14);
        s4.put("Jun", 11);
        s4.put("Jul", 11);
        s4.put("Aug", 12);
        s4.put("Sep", 14);
        s4.put("Oct", 17);
        s4.put("Nov", 19);
        s4.put("Dec", 21);
        dataset.addSeriesAsRow("Christchurch", s4);

        DefaultKeyedValues<Number> s5 = new DefaultKeyedValues<Number>();
        s5.put("Jan", 20);
        s5.put("Feb", 20);
        s5.put("Mar", 19);
        s5.put("Apr", 17);
        s5.put("May", 14);
        s5.put("Jun", 12);
        s5.put("Jul", 11);
        s5.put("Aug", 12);
        s5.put("Sep", 13);
        s5.put("Oct", 15);
        s5.put("Nov", 17);
        s5.put("Dec", 19);
        dataset.addSeriesAsRow("Wellington", s5);

        return dataset;
    }

}
