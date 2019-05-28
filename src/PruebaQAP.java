import examples.QuadraticAssignmentProblem;
import problem.Problem;
import state.Solution;


/**
 * Define a class to test the QAP
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class PruebaQAP {

	public static void main(String[] args) { 
		/*String typo="INTEGER";
		
		State r = new State(4, typo, 0 ,  1.0 );  
 		r.setValue(2, 4 );
		r.print( );*/
		
		
		QuadraticAssignmentProblem p = new QuadraticAssignmentProblem ("had12.dat");
		int[] vc= new int []{2,9,10,1,11,4,5,6,7,0,3,8};
		Solution s = new Solution("PERMUTATIONAL", vc);
		p.evaluate(s);
		System.out.println("  "+s.fitness);
	}


}
