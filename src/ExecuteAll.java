/*import statistics.Analyzer;*/

public class ExecuteAll {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* String[] methods={"FWA","ILS","VNS","GA","MA", "HC"};
		String[] configFile={"FWA20","ILSFb","VNSFA","GA20ap","MA30HCAP", "HCF"}; */
		//String[] methods ={ "ICA",  "ICA" };
		//String[] configFile ={ "ICAc",  "ICAd"}; 
		//String[] instanc = { "25", "20" };
		//int[] nEvalsXIns = {  6500 , 4000 }; 
		String[] methods ={ "GA",  "ILS", "SA" };
		String[] configFile ={ "GA20o",  "ILSFa", "SAG"}; 
		String[] instanc = { "had12.dat" };
		int[] nEvalsXIns = {  100000 }; 
	    /* String[] instanc ={ "25", "50", "100", "20", "36", "40"};
		int[] nEvalsXIns ={  6500 , 50000 , 200000, 4000, 12960, 16000 }; */
		/* String[] instances={ "matrix_30j_25to_10C_NSS_2.txt"};
		int[] nEvalsXInst={  45000  };*/ 

		Executing e = new Executing()
				.analysis("file")
				.problem("QAP")
				.nExperiments(32)
				.configFiles(configFile) 
				.methods(methods)
				.instances(instanc)
				.nEvalsXInst(nEvalsXIns) ; 
		
		e.run();
		/*Analyzer an = new Analyzer();

		for (int j=0; j< instances.length; j++){

			for (int i=0; i< methods.length; i++){
				System.out.println("\n----------------------"+methods[i]+"\n");

				Agent agent = new Agent() 
						.metaheuristic(methods[i]) 
						.paraMetaheuristic(configFile[i])
						.nEvals(nEvalsXInst[j])
						.nExp( 25)      
					    .problem("NQs", instances[j]);// only one run   	
						.problem("ToSP", instances[j]);// only one run
				agent.init();
				agent.info.printAllSolution();
				an.add(agent.info, instances[j]);
			} 
		}
		an.print();
		an.stats("MIN");
		 an.printFile("ICA", "STATS"); */
	}

}
