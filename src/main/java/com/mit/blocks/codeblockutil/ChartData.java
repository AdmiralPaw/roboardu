package com.mit.blocks.codeblockutil;

import org.jfree.chart.JFreeChart;

/**
 *
 * @author User
 */
public abstract class ChartData {

    /**
     *
     */
    public final String title;

    /**
     *
     * @return
     */
    public abstract JFreeChart makeChart();

    /**
     *
     * @param title
     */
    public ChartData(String title) {
        this.title = title;
    }
}
