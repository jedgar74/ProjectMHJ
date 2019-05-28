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
 * Problem define in http://www.csplib.org/Problems/prob039/
 * 
 * 
 * @author Jhon Amaya
 *
 */
public class RehearsalSchedulingProblem extends Problem {
	
	/*
	 * nVar is equals to n pieces
	 */
	public int nPlayers ;   
	public MatrixI matA; 
	public static ArrayList<String> D;
	
	
	public RehearsalSchedulingProblem(int nVar) {
		super();
		this.nameShort = "RSP"; 
		this.nVar = nVar;
	}

	
	public RehearsalSchedulingProblem(int nVar, int nPlayers, int nMagazine, MatrixI M) {
		super();
		this.nameShort = "RSP"; 
		this.nVar = nVar;
	    this.nPlayers = nPlayers; 
	}

	
	// read instance from file termed namInst
	public RehearsalSchedulingProblem(String namInst) {
		super();
		this.nameShort = "RSP"; 
		readInstance(namInst);
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
				// read first line
				if (j==0){
					StringTokenizer mm = new StringTokenizer(auxili, " "); 
					int h=0;
					while (mm.hasMoreTokens()) {
						h++; 
						if (h==1){ 
							nVar=Integer.parseInt(mm.nextToken().trim()); 
						} else if (h==2){
							nPlayers=Integer.parseInt(mm.nextToken().trim()); 
						}  
					}  
					matA=new MatrixI(  nPlayers, nVar); 

				} else if (j==1){
					D = new ArrayList<String>();
					StringTokenizer mm = new StringTokenizer(auxili, " "); 
					/*int h=0;*/
					while (mm.hasMoreTokens()) {
						D.add(mm.nextToken().trim()); 
					}  
				} else { 
					// fill the Matrix
					StringTokenizer mm = new StringTokenizer(auxili, " "); 
					int h=0;
					while (mm.hasMoreTokens()) {
						matA.setElement(j-2, h, Integer.parseInt(mm.nextToken().trim()));
						h++;
					}  
				} 
			}
			/*System.out.println("++++++++++");*/

			System.out.println("MUS/npla: "+nPlayers+" PIE/nvar:"+nVar );  

			matA.printlnAllInt();
			
			for (int i=0;i< D.size(); i++){
				
				System.out.print(" "+D.get(i) );  
	
			}
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
	
	
	public void evaluate(Solution s){ 

		double value = 0; 
		for (int j=0; j< this.nPlayers; j ++){ 
			value = value + wtp(s, j);
		}		
		s.setFitness(value);
	}

	
	@SuppressWarnings("static-access")
	public int wtp (Solution s, int j){
		int count=0; 
		boolean rehearse= false; 
		int np=0; 

		for (int k=0; k < this.nVar; k ++){ 
		
			if (this.matA.getElement(j,((OnlyInteger) s.getVar().allvar[k]).getValue())==1) 
				 np=np+1; 		
		}
		/*System.out.print (" "+np);*/
		int nn=0;
		for (int k=0; k< this.nVar; k ++){ 		
			int g = ((OnlyInteger) s.getVar().allvar[k]).getValue();
		 
			if (nn==np){
				rehearse=false;
			}
			
			if (this.matA.getElement(j, g)==1 ){
				if (rehearse==false){
					rehearse=true; 
				} 
				if (rehearse==true){	 
				    nn = nn + 1 ; 
				}	
			}
			
			if (this.matA.getElement(j, g)==0 && rehearse==true  ){
				count = count + Integer.parseInt(this.D.get(g));
			}
			/*System.out.print (" "+count);*/	
		}
		/*System.out.println(" * "+count);*/
		return count;
	}
}
