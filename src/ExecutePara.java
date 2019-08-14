 

/**
 * Main class of executing several metaheuristics on specific problem
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class ExecutePara {

	public static void main(String[] args) {
		
		String[] methods ={ "GA",  "GA" , "GA", "GA", "GA",  "GA" , "GA", "GA"};
		String[] configFile ={ "GA30ap01",  "GA30ap02", "GA30ap05", "GA30ap10", "GA30pb01",  "GA30pb02", "GA30pb05", "GA30pb10"}; 
		String[] instanc = { "22", "24", "26" };
		int[] nEvalsXIns = {  110000, 120000, 130000 }; 
	  

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
				.nExperiments(10)
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
