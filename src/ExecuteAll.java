 

/**
 * Main class of executing several metaheuristics on specific problem
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class ExecuteAll {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* String[] methods={"FWA","ILS","VNS","GA","MA", "HC"};
		String[] configFile={"FWA20","ILSFb","VNSFA","GA20ap","MA30HCAP", "HCF"}; */
		//String[] methods ={ "ICA",  "ICA" };
		//String[] configFile ={ "ICAc",  "ICAd"}; 
		//String[] instanc = { "25", "20" };
		//int[] nEvalsXIns = {  6500 , 4000 }; 
		String[] methods ={ "GA",  "FWA" , "SA", "ICA"};
		String[] configFile ={ "GA20o",  "FWA20", "SAR", "ICA"}; 
		String[] instanc = { "22", "24", "26" };
		int[] nEvalsXIns = {  100000, 100000, 100000 }; 
	    /* String[] instanc ={ "25", "50", "100", "20", "36", "40"};
		int[] nEvalsXIns ={  6500 , 50000 , 200000, 4000, 12960, 16000 }; */
		/* String[] instances={ "matrix_30j_25to_10C_NSS_2.txt"};
		int[] nEvalsXInst={  45000  };*/ 
		
		
/*
		double[] w = new double[2*instanc.length];
	    int[] c = new int[2*instanc.length];
	    
	    double r = 1.0/(double) instanc.length;
	    for (int k = 0; k < 2*instanc.length; k++){
	    	c[k]=0;
	    	if (k % 2 == 0)  
	    		w[k]=r*0.7;
	    	else  
	    		w[k]=r*0.3; 
	    }
		

*/
		double[] w = new double[ instanc.length];
	    int[] c = new int[ instanc.length];
	    
	    double r = 1.0/(double) instanc.length;
	    for (int k = 0; k <  instanc.length; k++){
	    	c[k]=0; 
			w[k]=r ;  
	    }
		


		Executing e = new Executing()
				.analysis()		// Analiza estadísticamente la ejecución
				.problem("NQs")
				.nExperiments(4)
				.configFiles(configFile) 
				.methods(methods)
				.instances(instanc)
				.nEvalsXInst(nEvalsXIns)  
				//.printAllSolution( );
				.analysisMCMA(w, c, 0) 
				.save();	// Guardar información en un archivo
		e.run();   
		
	}

}
