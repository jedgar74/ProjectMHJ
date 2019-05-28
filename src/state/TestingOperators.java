package state;

import examples.NQueens;
import problem.Problem;


/**
 * Testing Operators
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class TestingOperators {

	public static void main(String[] args) { 
		/*String typo="INTEGER";
		Solution s = new Solution("PERMUTATION", 10);
		s.var.initRandomize( );
		s.var.print();
		Operators o = new Operators();
		
		o.swappping(s.var);
		s.var.print();
		o.swappping(s.var);
		s.var.print();
		o.swappingRBI(s.var);
		s.var.print();
		
		Solution g = new Solution("PERMUTATION", 10);
		g.var.initRandomize( );
		g.var.print();
		o.crossoverOrder(s, g);
		s.var.print();
		g.var.print();
		State r = new State(4, typo, 0 ,  1.0 );  
 		r.genStateRandom(typo);
		r.setValue(2, 4 );
		r.print( );*/
		
		Problem problem = new NQueens(7);
		
		int[] vv ={ 7, 6, 5, 4, 3, 2, 1};
		Solution s = new Solution("PERMUTATIONAL", vv);
		 problem.evaluate(s); 
		s.print();
		
		int[] vt ={ 1, 6, 4, 2, 7, 5, 3};
		Solution t = new Solution("PERMUTATIONAL", vt);
		 problem.evaluate(t);
		t.print(); 
		
		Operators o = new Operators();
		o.crossoverCycle(s, t); 
		problem.evaluate(s);
		s.print();
	}

}
