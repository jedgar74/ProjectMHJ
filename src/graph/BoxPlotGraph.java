package graph; 
 
import java.awt.Color; 
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Log;
import org.jfree.util.LogContext;

 
public class BoxPlotGraph extends ApplicationFrame {

    /** Access to logging facilities. */
    private static final LogContext LOGGER = Log.createContext(BoxPlotGraph.class);

    /**
     *  
     *
     * @param title  the frame title.
     */
    public BoxPlotGraph(final String title, double[][] matrixAC, String[] labels) {

        super(title);
        
        final BoxAndWhiskerCategoryDataset dataset = createSampleDataset(matrixAC, labels);

        final CategoryAxis xAxis = new CategoryAxis("Method");
        final NumberAxis yAxis = new NumberAxis("Rank");
        yAxis.setAutoRangeIncludesZero(false);
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart(
            " ",
            new Font("SansSerif", Font.BOLD, 14),
            plot,
            true
        );
        
        chart.setBackgroundPaint(Color.white);
        final ChartPanel chartPanel = new ChartPanel(chart);
       
        chartPanel.setPreferredSize(new java.awt.Dimension(480, 640));
        setContentPane(chartPanel);

    }
    
    
    public BoxPlotGraph(final String title, double[][] matrixAC, Vector<String> labels) {
		super(title);
	  
		String[] labelv= new String[labels.size()];
		for (int g = 0; g < labels.size(); g++){
			labelv[g] = labels.elementAt(g) ;
		}
		
        
        final BoxAndWhiskerCategoryDataset dataset = createSampleDataset(matrixAC, labelv);

        final CategoryAxis xAxis = new CategoryAxis("Method");
        final NumberAxis yAxis = new NumberAxis("Rank");
        yAxis.setAutoRangeIncludesZero(false);
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart(
            " ",
            new Font("SansSerif", Font.BOLD, 14),
            plot,
            true
        );
        
        chart.setBackgroundPaint(Color.white); 
        final ChartPanel chartPanel = new ChartPanel(chart);
         
        chartPanel.setPreferredSize(new java.awt.Dimension(480, 640));
        setContentPane(chartPanel);
    }

    /**
     * Creates a sample dataset.
     * 
     * @return A sample dataset.
     */
    private BoxAndWhiskerCategoryDataset createSampleDataset(double[][] matrixAC, String[] labels) {
      
        final int categoryCount = labels.length;
        final int entityCount = matrixAC.length;
        
        DefaultBoxAndWhiskerCategoryDataset dataset 
            = new DefaultBoxAndWhiskerCategoryDataset(); 
		
	
		for (int j = 0; j < categoryCount; j++) {
			List list = new ArrayList(); 
			for (int k = 0; k < entityCount; k++) {
				
				list.add(matrixAC[k][j]); 
			}
			 
			LOGGER.debug(list.toString());
			dataset.add(list, " "    , labels[j] );
		} 

        return dataset;
    }
 
    

}
