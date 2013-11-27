package com.orsoncharts.android.demo;

import com.orsoncharts.android.Chart3D;
import com.orsoncharts.android.Chart3DFactory;
import com.orsoncharts.android.TitleAnchor;
import com.orsoncharts.android.data.PieDataset3D;
import com.orsoncharts.android.data.StandardPieDataset3D;
import com.orsoncharts.android.legend.LegendAnchor;

public class PieChartDemo1 {

	/**
	 * Creates and returns a demo chart.
	 * 
	 * @return A demo chart.
	 */
	public static Chart3D createChart() {
		Chart3D chart = Chart3DFactory.createPieChart(
                "New Zealand Exports 2012", 
                "http://www.stats.govt.nz/browse_for_stats/snapshots-of-nz/nz-in-profile-2013.aspx", createDataset());
        chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        chart.setLegendAnchor(LegendAnchor.BOTTOM_RIGHT);
        return chart;
	}
	
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    static PieDataset3D createDataset() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("Milk Products", 11625);
        dataset.add("Meat", 5114);
        dataset.add("Wood/Logs", 3060);
        dataset.add("Crude Oil", 2023);
        dataset.add("Machinery", 1865);
        dataset.add("Fruit", 1587);
        dataset.add("Fish", 1367);
        dataset.add("Wine", 1177);
        dataset.add("Other", 18870);
        return dataset; 
    }
}
