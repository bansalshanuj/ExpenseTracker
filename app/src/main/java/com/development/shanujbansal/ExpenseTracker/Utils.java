package com.development.shanujbansal.ExpenseTracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * Created by shanuj.bansal on 10/14/2015.
 */
public class Utils {
    private int actualValidCount = 0;

    public Intent openChart(String[] code, double[] distribution, int[] colors, Context context) {
        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries("Pictorial Distribution");
        int distributionLength = distribution.length;
        for (int i = 0; i < distributionLength; i++) {
            // Adding a slice with its values and name to the Pie Chart
            if (distribution[i] > 0.0) {
                distributionSeries.add(code[i], distribution[i]);
                actualValidCount++;
            }
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < actualValidCount; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("Pictorial Distribution");
        defaultRenderer.setChartTitleTextSize(20);
        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setBackgroundColor(Color.BLACK);
        defaultRenderer.setLabelsColor(Color.WHITE);
        defaultRenderer.setLabelsTextSize(35);
        defaultRenderer.setShowLabels(true);
        defaultRenderer.setDisplayValues(true);
        defaultRenderer.setZoomButtonsVisible(true);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent pieChartIntent = ChartFactory.getPieChartIntent(context, distributionSeries, defaultRenderer, "Categorization");
        pieChartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // startActivity(pieChartIntent);
        actualValidCount = 0;
        return pieChartIntent;
    }
}
