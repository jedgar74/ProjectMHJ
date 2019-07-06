import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

/** 
 * main class of executing program. In this class we call the
 * problem and kind agent
 * 
 * @author Jhon Edgar Amaya
 */
public class ExecuteG {

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {	
		// define your data
		double[] x = { 0, 1, 2, 3, 4, 5 };
		double[] y = { 45, 89, 6, 32, 63, 12 };
    
		Plot2DPanel plot = new Plot2DPanel("SOUTH"); 
		plot.addLinePlot("my plot", x, y);
		JFrame frame = new JFrame("JMathPlot library in a swing application.");
		frame.setSize(500, 500);
		frame.setContentPane(plot); 
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); 
        frame.setVisible(true);
		 
	}
}
