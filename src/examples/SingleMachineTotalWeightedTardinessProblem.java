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

public class SingleMachineTotalWeightedTardinessProblem extends Problem {

	public int nExTardiness;
	public DataTardiness nowDataTardiness;
	public ArrayList<DataTardiness> jobsTardiness = new ArrayList<DataTardiness>();
	public int instancer;

	public SingleMachineTotalWeightedTardinessProblem(int nVar) {
		super();
		this.nameShort = "SMTWTP"; 
		this.nVar = nVar;
	}

	public SingleMachineTotalWeightedTardinessProblem(int nVar, int nExTardiness, DataTardiness nowDataTardiness, MatrixI M) {
		super();
		this.nameShort = "SMTWTP"; 
		this.nVar = nVar;
		this.nExTardiness = nExTardiness;
		/*this.nowDataTardiness = nowDataTardiness;*/
	}

	// read instance from file termed namInst
	public SingleMachineTotalWeightedTardinessProblem(String namInst, int nJobs) {
		super();
		nVar = nJobs;
		this.nameShort = "SMTWTP"; 
		readInstance(namInst);
	}

	public void evaluate(Solution s){  
		int total = 0;
		int time = 0;
		int tardiness = 0;

		
		for (int j = 0; j < nVar; j++) {
			tardiness = time
					+  nowDataTardiness.getProcessingTimeElementAt(((OnlyInteger) s.getVar().allvar[j]).getValue())
					-  nowDataTardiness.getDueDatesElementAt(((OnlyInteger) s.getVar().allvar[j]).getValue());
			if (tardiness > 0)
				// tardiness = 0
				total = total + (tardiness *  nowDataTardiness
								.getWeightsElementAt(((OnlyInteger) s.getVar().allvar[j]).getValue()));

			time = time
					+ nowDataTardiness.getProcessingTimeElementAt(((OnlyInteger) s.getVar().allvar[j]).getValue());
		}
 
		s.setFitness(total);
	}
	
	public void readInstance(String namFile){
		String fileData = namFile;  
		 
	    instancer = 1; 
		
		try {
			ArrayList<String> lineFile = new ArrayList<String>();
			
			// Remember args[2] is the name of file in this format: matrix_?j_?to_NSS_?.txt
			// where NSS means that file no contain jobs subset any others
			File auxFile = new File("./instances/"+this.nameShort, fileData);
			FileReader input = new FileReader(auxFile);
			char buffer[] = new char[1]; 
            
			String tmp = "";
                        
			for (int charsRead = input.read(buffer); charsRead != -1; charsRead = input.read(buffer)) {
			
				char caracter = buffer[0];
         
				if (caracter == '\n') { 
					lineFile.add(tmp); 
					tmp = "";
					break;
				} else if (Character.isDigit(caracter) || caracter == '-'){			
					tmp = new StringBuffer(String.valueOf(tmp)).append(caracter).toString();
				}			
			}
						
			input.close(); 
			
			// Second step. Process line
			String auxili;
			ArrayList<String> stringTemp = new ArrayList<String>();
		 
			/*System.out.println("..."+lineFile.size());*/
			// Each cycle is a instance
			for (int j = 0; j < lineFile.size(); j++){
	        	auxili = (String) lineFile.get(j);
	        	// only temporal
	        	//int tmpint = auxili.indexOf("#");
	        	//auxili = auxili.substring(0, tmpint);    	
	        	StringTokenizer mm = new StringTokenizer(auxili, "-");
	        	
	        	while (mm.hasMoreTokens()) {
	        		stringTemp.add(mm.nextToken());
	            }
	        	//stringTemp.removeAllElements();
	        }
 
			
			int contr = nVar*3;
			/*System.out.println("..."+stringTemp.size()+"--"+contr);*/
			for (int j = 0; j < stringTemp.size()/contr; j++){
				
				DataTardiness dt = new DataTardiness(nVar); 
	        	for (int g = 0; g < nVar; g++){
	        		dt.addProcessingTime(stringTemp.get(g + contr*j), g);
	        	}
	        	for (int g = nVar; g < 2*nVar; g++){
	        		dt.addWeights(stringTemp.get(g + contr*j), g - nVar);
	        	}
	        	for (int g = 2*nVar; g < 3*nVar; g++){
	        		dt.addDueDates(stringTemp.get(g + contr*j), g - (2*nVar));
	        	}
	        	       	
	        	dt.print();	
	        	
	        	jobsTardiness.add(dt);
			}
		        
			 
			/*
			 * Now, we must select a method and technique 
			 * 
			 * This phase include to select technique, i.e, only one agent, several agents, 
			 * cooperative agents and memetic agents  
			 */					
			/*System.out.println("..."+jobsTardiness.size());*/
			nExTardiness=jobsTardiness.size();
			nowDataTardiness = new  DataTardiness(nVar);
			nowDataTardiness = (DataTardiness) jobsTardiness.get(instancer-1).clone();
			
 
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("....");
		} catch (Exception e2) {
			e2.printStackTrace();
			System.out.println("...."); 
		}    
	}
}


class DataTardiness implements Cloneable{

	public int[] processingTimes;
	public int[] weights;
	public int[] dueDates;
	
	public DataTardiness(int _jobs) {
		this.processingTimes = new int[_jobs];
		this.weights = new int[_jobs];
		this.dueDates = new int[_jobs];
	}
	
	public void print(){
		System.out.println("------------------------------");
		System.out.print("p[");
		for (int g = 0; g < this.processingTimes.length; g++){
    		System.out.print(this.processingTimes[g]+",");
    	}
		System.out.println("]");
		
		System.out.print("w[");
		for (int g = 0; g < this.weights.length; g++){
    		System.out.print(this.weights[g]+",");
    	}
		System.out.println("]");
		
		System.out.print("d[");
		for (int g = 0; g < this.dueDates.length; g++){
    		System.out.print(this.dueDates[g]+",");
    	}
		System.out.println("]");
	}
	
	public void addProcessingTime(String _value, int pos){
		this.processingTimes[pos]=Integer.parseInt(_value);
	}
	
	public void addWeights(String _value, int pos){
		//System.out.println("value "+_value);
		this.weights[pos]=Integer.parseInt(_value);
	}
	
	public void addDueDates(String _value, int pos){
		this.dueDates[pos]=Integer.parseInt(_value);
	}
	
	
	public int getProcessingTimeElementAt(int _position){
		return this.processingTimes[_position];
	}
	
	public int getWeightsElementAt(int _position){
		return this.weights[_position];
	}
	
	public int getDueDatesElementAt(int _position){
		return this.dueDates[_position];
	}
	
	public Object clone() {
		Object obj = null;
		try {
			obj = (DataTardiness) super.clone();

		} catch (CloneNotSupportedException ex) {
			System.out.println("Imposible duplicate object!!!");
		}
		((DataTardiness) obj).processingTimes = (int []) ((DataTardiness) obj).processingTimes.clone();
		((DataTardiness) obj).weights = (int []) ((DataTardiness) obj).weights.clone();
		((DataTardiness) obj).dueDates = (int []) ((DataTardiness) obj).dueDates.clone();
		
		return obj;
	}
}

