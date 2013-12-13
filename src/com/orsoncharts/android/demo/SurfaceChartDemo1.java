/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
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
