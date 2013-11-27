package com.orsoncharts.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.orsoncharts.android.ChartView;
import com.orsoncharts.android.R;

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
	    ChartView view = (ChartView) this.findViewById(R.id.chartView);
	    switch (item.getItemId()) {
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
	    case R.id.action_scatterchart1 : {
	    	view.setChart(ScatterChartDemo1.createChart());
	    	return true;
	    }
	    }
		return super.onOptionsItemSelected(item);
	}

}
