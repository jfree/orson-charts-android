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

import android.graphics.Color;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.Chart3DFactory;
import com.orsoncharts.android.data.DefaultKeyedValues;
import com.orsoncharts.android.data.category.CategoryDataset3D;
import com.orsoncharts.android.data.category.StandardCategoryDataset3D;
import com.orsoncharts.android.plot.CategoryPlot3D;
import com.orsoncharts.android.renderer.category.AreaRenderer3D;

/**
 * Creates an area chart instance for the demo.
 */
public class AreaChartDemo1 {

    /**
     * Returns an area chart instance for the demo.
     * 
     * @return An area chart instance.
     */
    public static Chart3D createChart() {
        Chart3D chart = Chart3DFactory.createAreaChart(
                "Reported Revenues By Quarter", 
                "Large companies in the IT industry", createDataset(), 
                "Company", "Quarter", "Value");
        chart.setChartBoxColor(Color.argb(128, 255, 255, 255));
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.getRowAxis().setVisible(false);
        AreaRenderer3D renderer = (AreaRenderer3D) plot.getRenderer();
        renderer.setBaseColor(Color.GRAY);
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
        s1.put("Q1/11", 8.58);
        s1.put("Q2/11", 9.03);
        s1.put("Q3/11", 9.72);
        s1.put("Q4/11", 10.58);
        s1.put("Q1/12", 10.65);
        s1.put("Q2/12", 12.214);
        s1.put("Q3/12", 14.101);
        s1.put("Q4/12", 14.419);
        s1.put("Q1/13", 13.969);
        s1.put("Q2/13", 14.105);
        dataset.addSeriesAsRow("Google", s1);
        
        DefaultKeyedValues<Number> s2 = new DefaultKeyedValues<Number>();
        s2.put("Q1/11", 16.43);
        s2.put("Q2/11", 17.37);
        s2.put("Q3/11", 17.37);
        s2.put("Q4/11", 20.89);
        s2.put("Q1/12", 17.41);
        s2.put("Q2/12", 18.06);
        s2.put("Q3/12", 16.008);
        s2.put("Q4/12", 21.456);
        s2.put("Q1/13", 20.489);
        s2.put("Q2/13", 19.896);
        dataset.addSeriesAsRow("Microsoft", s2);
        
        DefaultKeyedValues<Number> s3 = new DefaultKeyedValues<Number>();
        s3.put("Q1/11", 24.67);
        s3.put("Q2/11", 28.57);
        s3.put("Q3/11", 28.27);
        s3.put("Q4/11", 46.33);
        s3.put("Q1/12", 39.20);
        s3.put("Q2/12", 35.00);
        s3.put("Q3/12", 36.00);
        s3.put("Q4/12", 54.5);
        s3.put("Q1/13", 43.6);
        s3.put("Q2/13", 35.323);
        dataset.addSeriesAsRow("Apple", s3);

        return dataset;
    }


}
