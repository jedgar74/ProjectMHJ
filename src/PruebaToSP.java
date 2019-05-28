import examples.ToolSwitchingProblem;
import problem.Problem;
import util.MatrixI;

public class PruebaToSP {

	public static void main(String[] args) { 
		/*String typo="INTEGER";
		
		State r = new State(4, typo, 0 ,  1.0 );  
 		r.setValue(2, 4 );
		r.print( );*/
		
		MatrixI A= new MatrixI(9,10);
		int[][] r = new int[ ][ ]{  {1, 0, 1, 0, 0, 1, 0, 1, 0, 1}, 
									{1, 0, 0, 0, 0, 1, 1, 0, 0, 0}, 
									{0, 0, 1, 0, 0, 0, 1, 1, 0, 0},
									{0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
									{1, 1, 0, 0, 0, 0, 1, 0, 0, 0},
									{0, 0, 0, 0, 1, 1, 0, 0, 1, 0},
									{1, 1, 0, 0, 0, 0, 1, 0, 0, 0},
									{0, 0, 0, 1, 0, 0, 0, 0, 1, 1},
									{0, 1, 1, 1, 1, 0, 0, 0, 0, 0} };
									
        A.setMatrix(r);
		
		ToolSwitchingProblem p = new ToolSwitchingProblem(10, 9, 4, A);
		execute(p, 100, "GA");
	}

	public static void execute(Problem p, int r, String s){
		
	}
}
