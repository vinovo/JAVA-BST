package cse12pa6student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;


public class Graph extends JFrame {
	
	private static final long serialVersionUID = 1L;
	List<Integer> x;
	Map<String, List<Integer>> map;
	int numSeries;
	String title;
	
	public Graph(final String title, List<Integer> years, DefaultMap<String, List<Integer>> data) {
		
	    super(title);
	    this.title = title;
	    List<String> seriesNames = data.keys();
	    Map<String, List<Integer>> series = new HashMap<>();
	    for(String sname: seriesNames) {
	    		series.put(sname, data.get(sname));
	    }
	    this.x = years;
	    this.map = series;
	    numSeries = this.map.values().size();
	}
	
	
	private JFreeChart createChart() {
	    
		List<XYSeries> series = new ArrayList<>(numSeries);
		
		for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
			
			String ngram = entry.getKey();
			series.add((new XYSeries(ngram)));
		}
		
		for (int i=0; i<series.size(); i++) {
			for(int j=0; j<x.size(); j++) {
				List<Integer> y = map.get(series.get(i).getName()); 	
				series.get(i).add(x.get(j), y.get(j));
			}
		}
	
	    final XYSeriesCollection dataset = new XYSeriesCollection();
	    for (XYSeries s: series)
	    	dataset.addSeries(s);
	    
	    NumberAxis yAxis = new NumberAxis("Count");
	    NumberAxis xAxis = new NumberAxis("Years");
	    xAxis.setRange(x.get(0), x.get(x.size()-1));
	    xAxis.setTickUnit(new NumberTickUnit(2));
	    
	    
	    final CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot(xAxis);
        combinedPlot.setGap(1);
	    XYPlot plot1 = new XYPlot(dataset, null, yAxis, new StandardXYItemRenderer());
	    
	    combinedPlot.add(plot1,1);
	    
	    final JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, combinedPlot, true);
	    
	    return chart;
	}
	
	public void showChart() {
		JFreeChart chart = createChart();
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
	    this.pack();
	    RefineryUtilities.centerFrameOnScreen(this);
	    this.setVisible(true);
	}
}
