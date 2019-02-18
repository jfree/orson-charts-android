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
 * Orson Charts home page:
 *
 * https://github.com/jfree/orson-charts-android
 *
 */

package com.orsoncharts.orsonchartsdemoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.ChartSurfaceView;
import com.orsoncharts.android.demo.Charts;
import com.orsoncharts.android.graphics3d.ViewPoint3D;

public class MainActivity  extends AppCompatActivity {

    /**
     * A key for saving activity state.
     */
    static final String CHART_INDEX_KEY = "chartIndex";

    /**
     * A key for saving the current view point.
     */
    static final String VIEW_POINT_KEY = "viewPoint";

    /**
     * The selected chart : 0 = pie, 1 = bar, 2 = scatter
     */
    private int chartIndex;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initChart(this.chartIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CHART_INDEX_KEY, this.chartIndex);

        final ChartSurfaceView csv
                = (ChartSurfaceView) findViewById(R.id.chartView);
        ViewPoint3D vp = csv.getChart().getViewPoint();
        outState.putParcelable(VIEW_POINT_KEY, vp);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.chartIndex = savedInstanceState.getInt(CHART_INDEX_KEY);
        initChart(this.chartIndex);
        final ChartSurfaceView csv
                = (ChartSurfaceView) findViewById(R.id.chartView);
        ViewPoint3D viewPoint
                = savedInstanceState.getParcelable(VIEW_POINT_KEY);
        csv.getChart().setViewPoint(viewPoint);
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Initialises the chart and sets the correct radio button.  This is
     * called from onCreate() as well as from onRadioButtonClicked().
     *
     * @param chartIndex the chart index (0 = pie, 1 = bar, 2 = scatter).
     */
    private void initChart(int chartIndex) {
        final ChartSurfaceView csv
                = (ChartSurfaceView) findViewById(R.id.chartView);
        Chart3D chart = null;
        switch (this.chartIndex) {
            case 0:
                chart = Charts.createDemoPieChart();
                RadioButton rb1 = (RadioButton) findViewById(R.id.button1);
                rb1.setChecked(true);
                break;
            case 1:
                chart = Charts.createDemoBarChart();
                RadioButton rb2 = (RadioButton) findViewById(R.id.button2);
                rb2.setChecked(true);
                break;
            case 2:
                chart = Charts.createDemoScatterChart();
                RadioButton rb3 = (RadioButton) findViewById(R.id.button3);
                rb3.setChecked(true);
                break;
            default:
                throw new IllegalStateException("Unexpected chart index.");
        }
        csv.setChart(chart);
        // here we add a listener that will zoom-to-fit the new chart when
        // the layout changes...
        csv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight,
                                       int oldBottom) {
                csv.zoomToFit(right - left, bottom - top);
            }
        });

    }

    /**
     * Handles a click on a radio button, responds by initialising the
     * chart view with a new instance of the selected chart.
     *
     * @param view the button.
     */
    public void onRadioButtonClicked(View view) {
        // initialise a new chart according to the selection
        switch (view.getId()) {
            case R.id.button1:
                this.chartIndex = 0;
                break;
            case R.id.button2:
                this.chartIndex = 1;
                break;
            case R.id.button3:
                this.chartIndex = 2;
                break;
        }
        initChart(this.chartIndex);
    }
}