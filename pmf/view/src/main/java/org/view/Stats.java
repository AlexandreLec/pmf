package org.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.contract.Imodel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Stats extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	
	private Imodel model;

	private JFreeChart chart;
	
	private ChartPanel chartPanel;
	
	private XYDataset dataset;
	
	private int SizeTemp;
	
	private List<Double> test = new ArrayList<Double>();
	
	final XYSeries series1 = new XYSeries("Température extérieur");
	

	
	
	
	int i=0;
	
	public Stats(Imodel model) {
		this.model = model;
		this.model.observerAdd(this);
		
		this.test.add(1.0);
		this.test.add(2.0);
		this.test.add(3.0);
		this.test.add(4.0);
		
		this.paintComponent(); 
        
		this.dataset = createDataset();
        this.chart = createChart(dataset);
        this.chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(750, 500));
        this.add(chartPanel);
        SizeTemp=this.model.getTempInt().size();
        
    }
	
	public void paintComponent() {

			dataset = createDataset();
	        chart = createChart(dataset);
	        chartPanel = new ChartPanel(chart);
		
	        chartPanel.setPreferredSize(new java.awt.Dimension(750, 500));
	        this.add(chartPanel);  
	}
    
    /**
     * Creates a sample dataset.
     * 
     * @return a sample dataset.
     */
	
	
    private XYDataset createDataset() {
        final XYSeries series1 = new XYSeries("Température extérieur");
        SizeTemp=this.model.getTempInt().size();

        
        for(double i = 2.0 ; i < this.model.getTempInt().size() ; i++){
	        double ValueX = this.model.getTempInt().get((int) i);
	        series1.add(i, ValueX);
        }
        

        
        XYSeries series2 = new XYSeries("Température intérieur");
        series2.add(1.0, 5.0);
        series2.add(2.0, 7.0);
        series2.add(3.0, 6.0);
        series2.add(4.0, 8.0);
        series2.add(5.0, 4.0);
        series2.add(6.0, 4.0);
        series2.add(7.0, 2.0);
        series2.add(8.0, 1.0);
        series2.add(8.0, this.model.getTempInt().get(this.model.getTempInt().size()-1));

        XYSeries series3 = new XYSeries("Température du module");
        series3.add(3.0, 4.0);
        series3.add(4.0, 3.0);
        series3.add(5.0, 2.0);
        series3.add(6.0, 3.0);
        series3.add(7.0, 6.0);
        series3.add(8.0, 3.0);
        series3.add(9.0, 4.0);
        series3.add(10.0, 9.0);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        return dataset;
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
    	
    	
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Graphique des températures",      // chart title
            "Temps (secondes)",                      // x axis label
            "Température (C°)",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, true);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
        return chart;
        
    }

	public void update(Observable o, Object arg) {
		series1.add(7.0, 2.0);
		createChart(createDataset());
		this.removeAll();
		this.paintComponent(); 
	}

}
