package problem;

import state.Solution;


/**
 * Problem
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class Problem {
	 
	public int nVar;
	public String typeState ="PERMUTATIONAL";  //integer, real  -- podria usarse enum
	public String typeProblem= "MIN";  // MIN o MAX
	
	public String nameShort; 
	public Counter counter;
	public void evaluate(Solution s){};
	public void readInstance(String file){};
}
	
