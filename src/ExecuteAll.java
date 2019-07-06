 

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
		String[] methods ={ "GA",  "FWA" , "SA"};
		String[] configFile ={ "GA20o",  "FWA20", "SAR"}; 
		String[] instanc = { "22", "24" };
		int[] nEvalsXIns = {  100000, 100000 }; 
	    /* String[] instanc ={ "25", "50", "100", "20", "36", "40"};
		int[] nEvalsXIns ={  6500 , 50000 , 200000, 4000, 12960, 16000 }; */
		/* String[] instances={ "matrix_30j_25to_10C_NSS_2.txt"};
		int[] nEvalsXInst={  45000  };*/ 

		Executing e = new Executing()
				.analysis("file")
				.problem("NQs")
				.nExperiments(4)
				.configFiles(configFile) 
				.methods(methods)
				.instances(instanc)
				.nEvalsXInst(nEvalsXIns) ; 
				//.printAllSolution( );
				//.analysisMCMA("file");
		e.run();   
		
	}

}
