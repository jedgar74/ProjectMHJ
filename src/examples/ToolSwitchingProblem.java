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
 * Tool Switching Problem
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class ToolSwitchingProblem extends Problem {

	public int nTools ; 
	public int nMagazine ; 
	public MatrixI matA; 
	//	public Variables var;

	public ToolSwitchingProblem(int nVar) {
		super();
		this.nameShort = "ToSP"; 
		this.nVar = nVar;
	}

	public ToolSwitchingProblem(int nVar, int nTools, int nMagazine, MatrixI M) {
		super();
		this.nameShort = "ToSP"; 
		this.nVar = nVar;
		this.nTools = nTools;
		this.nMagazine = nMagazine;
	}

	// read instance from file termed namInst
	public ToolSwitchingProblem(String namInst) {
		super();
		this.nameShort = "ToSP"; 
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

		// computing fitness by means KTNS 
		int nSeq = 0;

		// copy original matrix 
		MatrixI matrixA = new MatrixI(nTools , nVar);
		matrixA.copy(matA);

		//__int control = 1;
		// In Zhou J(i,n), here jin
		ArrayList<String> jin = new ArrayList<String>();
		jin = minJXToools(matA, nSeq, s);

		while (nSeq < nVar) { 
			//int tools = toolsForJob(v[n], matrixA, nTools);
			int tools =  toolsForJob(((OnlyInteger) s.getVar().allvar[nSeq]).getValue(), matrixA, nTools); 
			if (tools == nMagazine) {
				nSeq = nSeq + 1;	
				jin = minJXToools(matrixA, nSeq, s);				
			} else {			
				int maxNS = 1000, minNS = 0; 
				if (nSeq != 0){
					for (int i = 0; i < nTools ; i++){
						int tmp = Integer.parseInt((String) jin.get(i));  
						//if ((matrixA.getElement(i, v[n-1]) > 0) && (tmp < maxNS) ){
						if ((matrixA.getElement(i, ((OnlyInteger) s.getVar().allvar[nSeq-1]).getValue()) > 0) 
								&& (tmp < maxNS) ){ 
							minNS = i;
							maxNS = tmp;
						}						
					}	
				} else {
					for (int i = 0; i < nTools ; i++){
						int tmp = Integer.parseInt((String) jin.get(i));  
						if (tmp < maxNS) {
							minNS = i;
							maxNS = tmp;
						}						
					}
				}
				//matrixA.setElement(minNS, v[n], 1); 
				matrixA.setElement(minNS, ((OnlyInteger) s.getVar().allvar[nSeq]).getValue(), 1);  
				jin.set(minNS, ""+(nVar+2));			
			}	
		} 
		// printMat(A, SP, N, M);
		// this.fitnessOne =  getNumberSwitches(AA);	
		s.setFitness(getNumberSwitches(matrixA, s));
	}

	public int getNumberSwitches(MatrixI matA, Solution s ){
		int nSwitches = 0;

		for (int k = 0; k < (nVar - 1); k++){
			for (int l = 0; l < nTools ; l++){
				//if (_mat.getElement(l, v[k]) == 0 && _mat.getElement(l, v[k+1]) == 1){
				if (matA.getElement(l, ((OnlyInteger) s.getVar().allvar[k]).getValue()) == 0 && 
						matA.getElement(l, ((OnlyInteger) s.getVar().allvar[k+1]).getValue()) == 1){

					nSwitches += 1;
				}
			}
		}
		return nSwitches;
	} 

	public ArrayList<String> minJXToools(MatrixI matA, int nSeq, Solution s){
		ArrayList<String> minTools = new ArrayList<String>();

		for(int t = 0; t < nTools ; t++){
			int sumTools = 0;
			for(int i = nSeq + 1; i < nVar; i++){
				//int rr = (int) A.getElement(t, v[i] ); 
				int toolXJob = (int) matA.getElement(t, ((OnlyInteger) s.getVar().allvar[i]).getValue());
				if (toolXJob == 0){
					sumTools = sumTools+ 1;
				} else {
					break;
				}
			}
			minTools.add(""+sumTools);
		}
		return minTools;
	}

	public int toolsForJob(int job, MatrixI matA, int nTools) {
		double toolsXJob = 0;

		for (int i = 0; i < nTools; i++) {
			toolsXJob = toolsXJob + matA.getElement(i, job);
		}

		return (int) toolsXJob;
	}

	public void readInstance(String namFile){
		String fileData = namFile;  
		//matrix_?j_?to_?C_NSS_?.txt
		String aus = fileData.replaceFirst("matrix_", "");
		aus = aus.replaceFirst("j_", "-");
		aus = aus.replaceFirst("to_", "-");
		aus = aus.replaceFirst("C_NSS_", "-");
		StringTokenizer stringT = new StringTokenizer( aus, "-"); 
		int t=0;
		/*System.out.println(""+fileData+" "+aus);*/
		while (stringT.hasMoreTokens()) {
			String g = stringT.nextToken();	
			if (t == 0)
				this.nVar = Integer.parseInt(g);
			else if (t == 1)
				this.nTools = Integer.parseInt(g);
			else if (t == 2)
				this.nMagazine = Integer.parseInt(g);
			t++;
		} 
		  
		/*timeLimit = Integer.parseInt(_parameters[2]);
		fileConfig = _parameters[3];
		numberExperiments = Integer.parseInt(_parameters[4]);*/ 

		ArrayList<String> lineFile = new ArrayList<String>();
		try {
			// Remember args[2] is the name of file in this format: matrix_?j_?to_NSS_?.txt
			// where NSS means that file no contain jobs subset any others
			File auxFile = new File("./instances/"+this.nameShort, fileData);
			FileReader input = new FileReader(auxFile);
			char buffer[] = new char[1];

			String tmp = "";

			int nJobs=0;
			for (int charsRead = input.read(buffer); charsRead != -1; charsRead = input.read(buffer)) {

				char caracter = buffer[0];

				if (caracter == '\n') { 

					if (tmp.length() >= 2){
						int ltmp = tmp.indexOf(":");
						lineFile.add(tmp.substring(ltmp+1, tmp.length()-1));
						nJobs++;
					}
					// System.out.println(tmp);
					tmp = "";
					//					break;
				} else {			
					tmp = new StringBuffer(String.valueOf(tmp)).append(caracter).toString();
				}			
			}

			input.close(); 

			// Second step. Process line
			String auxiliar; 
			ArrayList<String> _tools = new ArrayList<String>();

			// Determine the number of tools
			for (int j = 0; j < lineFile.size(); j++){
				auxiliar = (String) lineFile.get(j);  
				 stringT = new StringTokenizer(auxiliar, ","); 
				while (stringT.hasMoreTokens()) {
					String g = stringT.nextToken().trim();	
					if (! _tools.contains(g))
						_tools.add(g); 
				} 
			}
			nTools=_tools.size(); 

		    matA =new MatrixI( nTools, nJobs);
			// Determine the number of tools
			for (int j = 0; j < lineFile.size(); j++){
				auxiliar = (String) lineFile.get(j); 
				stringT = new StringTokenizer(auxiliar, ","); 

				while (stringT.hasMoreTokens()) {
					String g = stringT.nextToken().trim();  
					matA.setElement(Integer.parseInt(g), j, 1) ;
				}   
			}

			/*System.out.println(" "+nJobs+" "+nTools+" "+nMagazine);  

			matA.printlnAllInt(); */


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
