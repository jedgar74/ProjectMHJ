import examples.QuadraticAssignmentProblem;
import problem.Problem;
import state.Solution;

public class Prueba3 {

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
