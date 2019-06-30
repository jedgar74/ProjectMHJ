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

		for (int i = 1; i < s.nVar; i++) {
			int qbef = 0; 
			int qupd = ((OnlyInteger) s.getVar().allvar[i]).getValue();			
			int col = i;
			int k = 0;
			
			while (col > 0) {
				k = i - col + 1;
				qbef = ((OnlyInteger) s.getVar().allvar[i-k]).getValue();				
				// diagonal inferior
				if (qupd - k >= 0 && qupd == qbef + k) {
					nCollitions++;
				}
				// diagonal superior
				if (qupd + k < s.nVar && qupd == qbef - k) {
					nCollitions++;
				}
				/*
				if (qupd - k >= 0 && qupd - k == qbef ) {
					nCollitions++;
					System.out.println("I qupd="+qupd+" k="+k+" qbef="+qbef+" col="+col);
				}
				
				if (qupd + k < nVar && qupd + k == qbef) {
					nCollitions++;
					System.out.println("S qupd="+qupd+" k="+k+" qbef="+qbef+" col="+col);
				}*/
				col--;
			}
		}
		 
		s.setFitness(nCollitions);
	}
 
}
