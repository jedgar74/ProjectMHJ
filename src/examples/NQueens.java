package examples; 

import basic.OnlyInteger;
import problem.Problem;
import state.Solution;
import util.MatrixI;

/**
 * Define the classic problem termed N-Queens problems
 * 
 * 
 * @author Jhon Amaya
 *
 */
public class NQueens extends Problem {
	 

	public NQueens(int nVar) {
		super();
		this.nameShort = "NQs"; 
		this.nVar = nVar;
	}

	public NQueens(int nVar, int nTools, int nMagazine, MatrixI M) {
		super();
		this.nameShort = "NQs";  
		this.nVar = nVar; 
	}

	// read instance from file termed namInst
	public NQueens(String namInst) {
		super();
		this.nameShort = "NQs"; 
		readInstance(namInst);
	}
	
	
	public void readInstance(String namFile){
		nVar = Integer.parseInt(namFile);
	}
	
	public void evaluate(Solution s){ 
		int nCollitions = 0;

		for (int i = 1; i < nVar; i++) {
			int b = 0;
			
			int u = ((OnlyInteger) s.getVar().allvar[i]).getValue();
			
			int c = i;
			int k = 0;
			while (c > 0) {
				k = i - c + 1;
				b = ((OnlyInteger) s.getVar().allvar[i-1]).getValue();
		 
				//if (u - k >= 0 && u - k == b)
				//	a++;
				// diagonal superior
				if (u - k >= 0 && u - k == b)
					nCollitions++;
				if (u + 1 < nVar && u + 1 == b)
					nCollitions++;
				c--;
			}
		}
		 
		s.setFitness(nCollitions);
	}
}
