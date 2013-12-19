/* ========================
 * Orson Charts for Android
 * ========================
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.android.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.orsoncharts.android.ChartSurfaceView;
import com.orsoncharts.android.R;

/**
 * The main activity for the demo.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        ChartSurfaceView view = (ChartSurfaceView) this.findViewById(R.id.chartView);
        switch (item.getItemId()) {
        case R.id.action_about : {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Orson Charts for Android")
                .setMessage("For more info please visit:\n\nhttp://www.object-refinery.com")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                   // nothing
              }
            });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
        case R.id.action_barchart1 : {
            view.setChart(BarChartDemo1.createChart());
            return true;
        }
        case R.id.action_piechart1 : {
            view.setChart(PieChartDemo1.createChart());
            return true;
        }
        case R.id.action_areachart1 : {
            view.setChart(AreaChartDemo1.createChart());
            return true;
        }
        case R.id.action_linechart1 : {
            view.setChart(LineChartDemo1.createChart());
            return true;
        }
        case R.id.action_scatterchart1 : {
            view.setChart(ScatterChartDemo1.createChart());
            return true;
        }
        case R.id.action_stackedbarchart1 : {
            view.setChart(StackedBarChartDemo1.createChart());
            return true;
        }
        case R.id.action_surfacechart1 : {
            view.setChart(SurfaceChartDemo1.createChart());
        }
        }
        return super.onOptionsItemSelected(item);
    }

}
