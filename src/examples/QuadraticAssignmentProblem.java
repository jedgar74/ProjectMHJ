package examples;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import basic.OnlyInteger;
import problem.Problem;
import state.Solution; 
import util.MatrixI;


/**
 * Define the Quadratic Assignment Problem
 * 
 * 
 * @author Jhon Edgar Amaya
 *
 */
public class QuadraticAssignmentProblem extends Problem { 
	 
	public MatrixI matD; 
	public MatrixI matW;
	//	public Variables var;

	public QuadraticAssignmentProblem(int nVar) {
		super();
		this.nameShort = "QAP"; 
		this.nVar = nVar;
	}

	public QuadraticAssignmentProblem(int nVar, MatrixI M) {
		super();
		this.nameShort = "QAP"; 
		this.nVar = nVar; 
	}

	// read instance from file termed namInst
	public QuadraticAssignmentProblem(String namInst) {
		super();
		this.nameShort = "QAP"; 
		readInstance(namInst);
	}
	/*public void genState(){ 
		int[] r = new int[this.nVar];
		for (int i=0; i<this.nVar;i++){
			r[i]=i;
		}
		var = new Permutation(nVar, r);
	}*/

	public void evaluate(Solution s){ 
		int value =0;
		//Distnce[i][j] *flow[sol[i]][sol[j]]

		for (int i=0; i< this.nVar; i++){ 
			for (int j=0; j< this.nVar; j ++){ 
				int gi = ((OnlyInteger) s.getVar().allvar[i]).getValue();
				int gj = ((OnlyInteger) s.getVar().allvar[j]).getValue(); 
			
				value = value + this.matW.getElement(i, j) * this.matD.getElement(gi,gj); 
			}
		}		
		s.setFitness(value);  
	}
	

	public void readInstance(String namFile){
		 String fileData = namFile;  
		//matrix_?j_?to_?C_NSS_?.txt
		/*String aus = fileData.replaceFirst("matrix_", "");
		aus = aus.replaceFirst("j_", "-");
		aus = aus.replaceFirst("to_", "-");
		aus = aus.replaceFirst("C_NSS_", "-");
		StringTokenizer stringT = new StringTokenizer( aus, "-"); 
		int t=0;
		System.out.println(""+fileData+" "+aus);
		while (stringT.hasMoreTokens()) {
			String g = stringT.nextToken();	
			if (t == 0)
				this.nVar = Integer.parseInt(g);
			else if (t == 1)
				this.nPlayers = Integer.parseInt(g);
		 
			t++;
		} */
		  
		/*timeLimit = Integer.parseInt(_parameters[2]);
		fileConfig = _parameters[3];
		numberExperiments = Integer.parseInt(_parameters[4]);*/ 

		ArrayList<String> lineFile = new ArrayList<String>();
		try {
			File auxFile = new File("./instances/"+this.nameShort, fileData);
			FileReader input = new FileReader(auxFile);
			char buffer[] = new char[1];
			 
			/*
			String typeLines = "";
			try {
				typeLines = _parameters[4];
			} catch (Exception e){
				typeLines = "";	
			}*/ 
	 

			String tmp = "";

			for (int charsRead = input.read(buffer); charsRead != -1; charsRead = input.read(buffer)) {

				char caracter = buffer[0];

				if (caracter == '\n') { 
					lineFile.add(tmp);  
					tmp = ""; 
				} else {			
					tmp = new StringBuffer(String.valueOf(tmp)).append(caracter).toString();
				}			
			}

			input.close(); 

			// Second step. Process line
			String auxili;  

			for (int j = 0; j < lineFile.size(); j++){
				auxili = (String) lineFile.get(j);
				System.out.println(auxili); 
				// read first line
				if (j==0){
					StringTokenizer mm = new StringTokenizer(auxili, " "); 
					int h=0;
					nVar=Integer.parseInt(mm.nextToken().trim());
					   
					matW=new MatrixI(nVar, nVar); 
					matD=new MatrixI(nVar, nVar);
				} else if (j>=1 &&  j<1+nVar){ 
					// fill the Matrix
					StringTokenizer mm = new StringTokenizer(auxili, " ");  
					int h=0;
					
					while (mm.hasMoreTokens()) {
						matW.setElement(j-1, h, Integer.parseInt(mm.nextToken().trim()));
						h++;
					} 
				} else { 
					// fill the Matrix
					StringTokenizer mm = new StringTokenizer(auxili, " "); 
					int h=0;
					while (mm.hasMoreTokens()) {
						matD.setElement(j-1-nVar, h, Integer.parseInt(mm.nextToken().trim()));
						h++;
					}  
				} 
			}
			/*System.out.println("++++++++++");*/

			System.out.println(" nvar:"+nVar );  
			matW.printlnAllInt();
			
			System.out.println( );  
			matD.printlnAllInt();
			
			System.out.println( );  

			/*
			 * Now, we must select a method and technique 
			 * 
			 * This phase include to select technique, i.e, only one agent, several agents, 
			 * cooperative agents and memetic agents  
			 */	
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("....");
		} catch (Exception e2) {
			e2.printStackTrace();
			System.out.println("....");/*System.out.println("Use ::: _magazine _fileInfo   _time _configfile _nExperiments _instance");
			System.out.println("For example 4 example100.txt 1000 BS2 10 1 "); */
		}    
	}
}
