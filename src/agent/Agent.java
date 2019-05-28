package agent;
import java.io.File; 
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar; 
import java.util.GregorianCalendar;

import algorithm.CuckooSearch;
import algorithm.FireworksAlgorithm;
import algorithm.GeneticAlgorithm;
import algorithm.Heuristic;
import algorithm.HillClimbing;
import algorithm.ImperialistCompetitiveAlgorithm;
import algorithm.IteratedLocalSearch;
import algorithm.RandomWalk;
import algorithm.SimulatingAnnealing;
import algorithm.SineCosineAlgorithm;
import examples.NQueens;
import examples.RehearsalSchedulingProblem;
import examples.SingleMachineTotalWeightedTardinessProblem;
import examples.ToolSwitchingProblem;
import examples.QuadraticAssignmentProblem;
import problem.Counter; 
import problem.Problem; 
import state.Solution;
import statistics.BasicStats;  


/**
 * Define the structure to implement an agent with metaheuristics
 * 
 * 
 * @author Jhon Edgar Amaya
 *
 */
public class Agent {


	/* define the directory to config file  */
	/*	private String DIRECTORY = "./";
	  define the particular problem to solve  */
	public Problem problem ;
	/*  define the internal state of agent. In this case is a candidate solution
	 * of a particular problem  */
	public Solution internal;
	/**
	 * define the name of metaheuristic applying. It is stand for as String
	 * label, e.g. CEHyb, TSA, GA, CoopLS
	 * 
	 * before named nameLocalSearch deprecated
	 */
	public String nameMetaheuristic;
	public String paramMetaheuristic;
	public int nEvals;
	public int nExperim = 1;
	/*public Internal info = new Internal();*/
	public BasicStats info = new BasicStats();
	public Heuristic algorithm; 
	
	
	public Agent(){

	}

	
	public Agent metaheuristic(String name){
		nameMetaheuristic = name;

		return this;
	}

	
	public Agent paraMetaheuristic(String name){
		paramMetaheuristic = name;

		return this;
	}


	public Agent nEvals(int nevals){
		nEvals = nevals;
		//		problem.counter = new Counter(nevals);
		return this;
	}


	public Agent nExp(int nexp){
		this.nExperim = nexp;

		return this;
	}


	public void printFile(){
		try { 
			Calendar dateAndTime = new GregorianCalendar();
			File auxFile = new File("./output", problem.nameShort+" "+dateAndTime.get(Calendar.DAY_OF_MONTH)+"_"
					+(dateAndTime.get(Calendar.MONTH)+1)+"_"
					+dateAndTime.get(Calendar.YEAR)+" "
					+dateAndTime.get(Calendar.HOUR_OF_DAY)+"_"
					+dateAndTime.get(Calendar.MINUTE)+".txt");
			FileWriter output = new FileWriter(auxFile);

			output.write(info.toString()); 
			output.close(); 
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("....");
		} 
	}

	
	public Agent problem(String name, String nameIns){
		/*nameMetaheuristic = name;*/
		if (name.equals("ToSP")){
			problem = new ToolSwitchingProblem(nameIns);			
		} else if (name.equals("RSP")){
			problem = new RehearsalSchedulingProblem(nameIns); 
		} else if (name.equals("NQs")){
			problem = new NQueens(nameIns); 
		} else if (name.equals("QAP")){
			problem = new QuadraticAssignmentProblem(nameIns); 
		} /*else if (name.equals("SMTWTP")){
			 problem = new SingleMachineTotalWeightedTardinessProblem(nameIns); 
		} else if (name.equals("CSP")){
			problem = new CarSequencingProblem(nameIns); 
		} */
		return this;
	}


	public Agent problem(String name, String nameIns, int nJobs){
		/*nameMetaheuristic = name;*/
		if (name.equals("SMTWTP")){
			problem = new SingleMachineTotalWeightedTardinessProblem(nameIns, nJobs); 
		}		
		return this;
	}
	
	
	  

	public void init(){
		this.problem.counter = new Counter(this.nEvals); 
		this.info.setLabel(this.nameMetaheuristic +"---"
				+this.paramMetaheuristic+"["+this.nEvals+"]");

		System.out.println(" Experiments to "+this.nameMetaheuristic);
		for (int e = 0; e < this.nExperim; e++) { 
			/*System.out.println("\n Name file = " + objProblem.fileConfig
					+ " Method = " + metaHeuristic + " <---> "
					+ (_counterExperiments + 1));*/ 
			/*System.out.println(" "+e+" "+this.nameMetaheuristic); */
			if (e% 15 == 14){
				System.out.println(" "+e );
			} else {
				System.out.print(" "+e );
			}
			
			if (this.nameMetaheuristic.compareToIgnoreCase("HC") == 0) {
				/*System.out.println("------- "+e);*/
				HillClimbing HC = new HillClimbing(this.problem,
						this.paramMetaheuristic);
				//this.internalState = (Solution) HC.getStateFinal().clone();
				/*HC.status.stateFinal.print();*/
				info.add(HC.status.stateFinal);
			
			} if (this.nameMetaheuristic.compareToIgnoreCase("SA") == 0) {
				/*System.out.println("------- "+e);*/
				SimulatingAnnealing SA = new SimulatingAnnealing(this.problem,
						this.paramMetaheuristic);
				//this.internalState = (Solution) HC.getStateFinal().clone();
				/*HC.status.stateFinal.print();*/
				info.add(SA.status.stateFinal);
			} if (this.nameMetaheuristic.compareToIgnoreCase("CS") == 0) {
				/*System.out.println("------- "+e);*/
				CuckooSearch CS = new CuckooSearch(this.problem,
						this.paramMetaheuristic);
				//this.internalState = (Solution) HC.getStateFinal().clone();
				/*HC.status.stateFinal.print();*/
				info.add(CS.status.stateFinal);
			} if (this.nameMetaheuristic.compareToIgnoreCase("GA") == 0) {
				/*System.out.println("------- "+e);*/
				GeneticAlgorithm GA = new GeneticAlgorithm(this.problem,
						this.paramMetaheuristic);
				//this.internalState = (Solution) HC.getStateFinal().clone();
				/*HC.status.stateFinal.print();*/
				info.add(GA.status.stateFinal);
			
			} if (this.nameMetaheuristic.compareToIgnoreCase("ILS") == 0) {
				/*System.out.println("------- "+e);*/
				IteratedLocalSearch ILS = new IteratedLocalSearch(this.problem,
						this.paramMetaheuristic);
				//this.internalState = (Solution) HC.getStateFinal().clone();
				/*HC.status.stateFinal.print();*/
				info.add(ILS.status.stateFinal);
			} if (this.nameMetaheuristic.compareToIgnoreCase("RW") == 0) {
				/*System.out.println("------- "+e);*/
				RandomWalk RW = new RandomWalk(this.problem,
						this.paramMetaheuristic, info);
				//this.internalState = (Solution) HC.getStateFinal().clone();
				/*HC.status.stateFinal.print();*/
				/*info.add(RW.status.stateFinal);*/
				/*System.out.println(RW.getData().nSolutions());
				RW.getData().printAllSolution();*/
				info = RW.getData();
			} if (this.nameMetaheuristic.compareToIgnoreCase("FWA") == 0) {
				/*System.out.println("------- "+e);*/
				FireworksAlgorithm FWA = new FireworksAlgorithm(this.problem,
						this.paramMetaheuristic);
				//this.internalState = (Solution) HC.getStateFinal().clone();
				/*HC.status.stateFinal.print();*/
				info.add(FWA.status.stateFinal);
			
			} if (this.nameMetaheuristic.compareToIgnoreCase("ICA") == 0) {
				/*System.out.println("------- "+e);*/
				ImperialistCompetitiveAlgorithm ICA = new ImperialistCompetitiveAlgorithm(this.problem,
						this.paramMetaheuristic);
				//this.internalState = (Solution) HC.getStateFinal().clone();
				/*HC.status.stateFinal.print();*/
				info.add(ICA.status.stateFinal);
			} if (this.nameMetaheuristic.compareToIgnoreCase("SCA") == 0) {
				/*System.out.println("------- "+e);*/
				SineCosineAlgorithm SCA = new SineCosineAlgorithm(this.problem,
						this.paramMetaheuristic);
				//this.internalState = (Solution) HC.getStateFinal().clone();
				/*HC.status.stateFinal.print();*/
				info.add(SCA.status.stateFinal);
			}
			problem.counter = new Counter(this.nEvals);
		}
		
		System.out.println( ); 
		info.print();
		/*System.out.println( "----"+info.nSolutions());
		info.printAllInfoSolution();*/
	}
	
	////////////////////////////////
	// revisar
	////////////////////////////////
	
	public Agent(Problem problem, String fileConfig, String method, Solution s) {
		this.internal = (Solution) s;
		this.problem = problem;
		this.nameMetaheuristic = method;
				
		if (method.compareToIgnoreCase("HC") == 0) { 
			HillClimbing HC = new HillClimbing(problem,
					fileConfig, s);
			this.algorithm = HC; 
		 
		} if (method.compareToIgnoreCase("SA") == 0) { 
			SimulatingAnnealing SA = new SimulatingAnnealing(problem,
					fileConfig, s);
			this.algorithm = SA; 
		} if (method.compareToIgnoreCase("CS") == 0) { 
			CuckooSearch CS = new CuckooSearch(problem,
					fileConfig, s);
			this.algorithm = CS; 
		} if (method.compareToIgnoreCase("GA") == 0) { 
			GeneticAlgorithm GA = new GeneticAlgorithm(problem,
					fileConfig, s);
			this.algorithm = GA; 
		
		} if (method.compareToIgnoreCase("ILS") == 0) { 
			IteratedLocalSearch ILS = new IteratedLocalSearch(problem,
					fileConfig, s);
			this.algorithm = ILS; 
		} if (method.compareToIgnoreCase("RW") == 0) { 
			 
		} if (method.compareToIgnoreCase("FWA") == 0) { 
			FireworksAlgorithm FWA = new FireworksAlgorithm(problem,
					fileConfig, s);
			this.algorithm = FWA; 
		
			
		} if (method.compareToIgnoreCase("ICA") == 0) { 
			ImperialistCompetitiveAlgorithm ICA = new ImperialistCompetitiveAlgorithm(problem,
					fileConfig, s);
			this.algorithm = ICA; 
			
		} if (method.compareToIgnoreCase("SCA") == 0) { 
			SineCosineAlgorithm SCA = new SineCosineAlgorithm(problem,
					fileConfig, s);
			this.algorithm = SCA; 	
			
		}
	 
	}
	
	
	public void init(int nEvals){
		this.problem.counter = new Counter( nEvals); 
		this.info.setLabel(this.nameMetaheuristic +"---"
				+this.paramMetaheuristic+"["+ nEvals+"]"); 

		if (this.nameMetaheuristic.compareToIgnoreCase("HC") == 0) { 
			this.internal  = (Solution) ((HillClimbing) algorithm).
					hillClimbing(this.internal, this.problem, nEvals).clone();
		     
		} if (this.nameMetaheuristic.compareToIgnoreCase("LF") == 0) {
			/*this.internal  = (Solution) ((LevyFlights) algorithm).
					levyFlights(this.internal, this.problem, nEvals).clone();*/ 
			
		} if (this.nameMetaheuristic.compareToIgnoreCase("SA") == 0) {
			/*this.internal  = (Solution) ((SimulatingAnnealing) algorithm).
					simulatingAnnealing(this.internal, this.problem, nEvals).clone(); */
			 
		} if (this.nameMetaheuristic.compareToIgnoreCase("CS") == 0) {
			/*this.internal  = (Solution) ((CuckooSearch) algorithm).
					cuckooSearch(this.internal, this.problem, nEvals).clone(); */ 
			
		} if (this.nameMetaheuristic.compareToIgnoreCase("GA") == 0) {
			/*this.internal  = (Solution) ((GeneticAlgorithm) algorithm).
					geneticAlgorithm(this.internal, this.problem, nEvals).clone(); */ 
		 
		
		} if (this.nameMetaheuristic.compareToIgnoreCase("ILS") == 0) {
			/*this.internal  = (Solution) ((IteratedLocalSearch) algorithm).
					iteratedLocalSearch(this.internal, this.problem, nEvals).clone(); */ 
			 
			 
		} if (this.nameMetaheuristic.compareToIgnoreCase("RW") == 0) {
			 
		} if (this.nameMetaheuristic.compareToIgnoreCase("FWA") == 0) {
			/*this.internal  = (Solution) ((FireworksAlgorithm) algorithm).
					fireworksAlgorithm(this.internal, this.problem, nEvals).clone(); */ 
		 
		} if (this.nameMetaheuristic.compareToIgnoreCase("CE") == 0) {
			/*this.internal  = (Solution) ((CrossEntropy) algorithm).
					crossEntropy(this.internal, this.problem, nEvals).clone();*/  
			 
			 
		} if (this.nameMetaheuristic.compareToIgnoreCase("ICA") == 0) {
			/*this.internal  = (Solution) ((ImperialistCompetitiveAlgorithm) algorithm).
					imperialistCompetitiveAlgorithm(this.internal, this.problem, nEvals).clone(); */ 
			 
		} if (this.nameMetaheuristic.compareToIgnoreCase("SCA") == 0) {	 	 	 	 	 	 
		} 
	}

}
