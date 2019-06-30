 
import agent.Agent; 

/** 
 * main class of executing program. In this class we call the
 * problem and kind agent
 * 
 * @author Jhon Edgar Amaya
 */
public class ExecuteOne {

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/*Problem _problem = new Problem(args); 
		Agent _agent = new Agent(_problem); 
		_agent.init();*/
		// only one run
		/*Agent agent = new Agent() 
				.metaheuristic("CBO") 
				.paraMetaheuristic("CBO")
				.nEvals(75000 )
				.nExp(10)
				.problem("ToSP", "matrix_50j_40to_25C_NSS_1.txt");*/
		
		/*Agent agent = new Agent() 
				.metaheuristic("GA") 
				.paraMetaheuristic("GA20o")
				.nEvals(10000)
				.nExp(2)
				.problem("RSP", "n9m5.txt");*/
				
		/*Agent agent = new Agent() 
				//.metaheuristic("GA") 
				//.paraMetaheuristic("GA20o")
				.metaheuristic("ILS") 
				.paraMetaheuristic("ILSFb")
				.nEvals(100000)
				.nExp(32)
				.problem("QAP", "had12.dat");*/

		 Agent agent = new Agent() 
				.metaheuristic("SA") 
				.paraMetaheuristic("SAR")
				.nEvals( 12500 )
				.nExp(10)
				.problem("NQs", "5");  
		
		/*Agent agent = new Agent() 
				.metaheuristic("GA") 
				.paraMetaheuristic("GA20o")
				.nEvals(100000 )
				.nExp(12)
				.problem("SMTWTP", "wt40jax.txt", 40); */
		
		/*Agent agent = new Agent() 
				.metaheuristic("GA") 
				.paraMetaheuristic("GA20o")
				.nEvals(15000   )
				.nExp( 2)
				.problem("CSP", "Problem90-10.txt" );  */		
		 
		/*Agent agent = new Agent() 
				.metaheuristic("MA") 
				.paraMetaheuristic("MA20HC 2P")
				.nEvals( 14400   )
				.nExp(200)
				.problem("UCP", 12, "BINARY" );   */
		
		/*Agent agent = new Agent() 
				.metaheuristic("MA") 
				.paraMetaheuristic("MA20HC 2P")
				.nEvals( 240000  )
				.nExp(20 )
				.problem("LABS", 20, "BINARY" ); */ 
		 
		agent.init();
		System.out.println("-----------------------------------");
		agent.info.printAllInfoSolution();
        /* agent.printFile();*/
		/* agent.info.print();*/
	}
}
