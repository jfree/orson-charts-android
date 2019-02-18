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
import com.orsoncharts.android.Range;
import com.orsoncharts.android.axis.ValueAxis3D;
import com.orsoncharts.android.data.function.Function3D;
import com.orsoncharts.android.graphics3d.Dimension3D;
import com.orsoncharts.android.plot.XYZPlot;
import com.orsoncharts.android.renderer.RainbowScale;
import com.orsoncharts.android.renderer.xyz.SurfaceRenderer;

/**
 * Creates a surface chart for the demo.
 */
public class SurfaceChartDemo1 {
    
    /**
     * Creates a demo chart.
     * 
     * @return A demo chart.
     */
    public static Chart3D createChart() {
        
        Function3D function = new Function3D() {

            @Override
            public double getValue(double x, double z) {
                return Math.sin(x * x + z * z);
            }
            
        };
        
        Chart3D chart = Chart3DFactory.createSurfaceChart(
                "SurfaceRendererDemo2", 
                "y = sin(x^2 + z^2)", 
                function, "X", "Y", "Z");
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.setDimensions(new Dimension3D(10, 5, 10));
        ValueAxis3D xAxis = plot.getXAxis();
        xAxis.setRange(-2, 2);
        ValueAxis3D zAxis = plot.getZAxis();
        zAxis.setRange(-2, 2);
        SurfaceRenderer renderer = (SurfaceRenderer) plot.getRenderer();
        renderer.setColorScale(new RainbowScale(new Range(-1.0, 1.0)));
        renderer.setXSamples(30);
        renderer.setZSamples(30);
        renderer.setDrawFaceOutlines(false);
        //chart.setLegendPosition(LegendAnchor.TOP_RIGHT, Orientation.VERTICAL);
       // chart.setAntiAlias(false);
        return chart;
    }

}
