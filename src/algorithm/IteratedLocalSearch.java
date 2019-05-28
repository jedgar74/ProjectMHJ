package algorithm;
 
import problem.Problem; 
import state.Solution;

public class IteratedLocalSearch extends Heuristic {
	String shortTerm = "ILS";
	String initialSolution = "RANDOM";  
    
	int factorNeighs = 0;
	int nicConstant = 0;
	String includeMethods = "HC";
	String configMethods = "";
	String perturbation = "SWAPPING"; // can be BLOCKING
	String allNeighs = "NONE"; // can be ALL or NONE
	
	
	public IteratedLocalSearch(Problem problem, String _fileConfig) {

		this.objProblem = problem;
		this.readParameters(_fileConfig, this.shortTerm);
		this.setParameters(problem);
		this.status.stateInitial = new Solution(problem.typeState, objProblem.nVar, initialSolution);
		problem.evaluate(this.status.stateInitial);
		problem.counter.incCount();
		this.status.stateFinal = (Solution) this.status.stateInitial.clone(); 
	 
		/*this.status.stateInitial.print();*/
		/*
		 * this.stateFinal = new Solution(objProblem.nJobs,
		 * objProblem.typeInitialSolution); this.stateFinal.copy(_state);
		 */
		

		/*System.out.print(" HC " + this.allNeighs);
		if (this.allNeighs.equals("NONE"))
			System.out.print(" FactNeigh=" + this.factorNeighs);
		System.out.println();*/
		iteratedLocalSearch(this.status.stateInitial);
		/*if (this.allNeighs.equals("ALL")) {
			hillClimbingAll(_state);
		} else {
			hillClimbingNoAll(_state);
		}*/
	}

	
	/**
	 * 
	 * 
	 * @param problem
	 * @param _fileConfig
	 * @param _state
	 */
	public IteratedLocalSearch(Problem problem, String _fileConfig, Solution _state) {
		// Solution _tmpSolution = new Solution();
		this.objProblem = problem;
		this.readParameters(_fileConfig, this.shortTerm);
		this.setParameters(problem);

		/*
		 * this.stateFinal = new Solution(objProblem.nJobs,
		 * objProblem.typeInitialSolution); this.stateFinal.copy(_state);
		 */
		this.status.stateFinal  = (Solution) _state.clone();

	}
	
	
	public void setParameters(Problem _problem) {
		for (int i = 0; i < nameParameters.size(); i++) {

			if (nameParameters.get(i).equals("FACTORNEIGHS")) {
				this.factorNeighs = Integer.parseInt(valueParameters
						.get(i));
			} else if (nameParameters.get(i).equals("ALLNEIGHS")) {
				this.allNeighs = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("NICCONSTANT")) {
				this.nicConstant = Integer.parseInt(valueParameters
						.get(i));
			} else if (nameParameters.get(i).equals("INCLUDEMETHODS")) {
				this.includeMethods = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("CONFIGMETHODS")) {
				this.configMethods = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("TYPEINITIAL")) {
				this.initialSolution = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("PERTURBATION")) {
				this.perturbation = valueParameters.get(i);
			}
		}
	}

	public void iteratedLocalSearch (Solution _init) {
		int nic = 0;
        /*System.out.println("____________");*/
		Solution nextState = (Solution) _init.clone(); 
		/*nextState.print();*/
		
		// Local Search process
		try {

			/*HillClimbing HC = new HillClimbing(this.objProblem,
					this.configMethods, nextState);
			HC.setParameters(this.objProblem);

			// System.out.println("..."); 
			if (this.includeMethods.equals("HC")) {
				nextState = (Solution) HC.status.stateFinal.clone(); 

				if (this.allNeighs.equals("ALL"))
					nextState = HC.hillClimbingAllWithBreak(nextState, this.objProblem);
				else
					nextState = HC.hillClimbingWithBreak(nextState, this.objProblem);
			}*/
			nextState = localSearcher(nextState);
		    /*nextState.print();*/
			
			while (isStopCriteria(this.objProblem)) {
				 
			    nextState = perturbation(this.status.stateFinal, false); 
				 
				/*if (HC.allNeighs.equals("ALL")) {
					nextState = HC.hillClimbingAllWithBreak(nextState, this.objProblem);
				} else {
					nextState = HC.hillClimbingWithBreak(nextState, this.objProblem);
				} */
                /*System.out.println("ooo "+this.nicConstant);
                nextState.print();*/
                nextState = localSearcher(nextState);
			    /*nextState.print();*/ 
				
				if (op.best(nextState, this.status.stateFinal, this.objProblem) ) {
					this.status.stateFinal = (Solution) nextState.clone(); 
				}  
			} 
	   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this method gets many good solutions
	 * 
	 * @param _tmp
	 * @param _aux
	 * @return
	 */
	/*public void iteratedLocalSearch (Solution _init) {
		int nic = 0;
        System.out.println("____________");
		Solution nextState = (Solution) _init.clone(); 
		nextState.print();
		
		// Local Search process
		try {

			HillClimbing HC = new HillClimbing(this.objProblem,
					this.configMethods, nextState);
			HC.setParameters(this.objProblem);

			// System.out.println("..."); 
			if (this.includeMethods.equals("HC")) {
				nextState = (Solution) HC.status.stateFinal.clone(); 

				if (this.allNeighs.equals("ALL"))
					nextState = HC.hillClimbingAllWithBreak(nextState, this.objProblem);
				else
					nextState = HC.hillClimbingWithBreak(nextState, this.objProblem);
			}
			nextState = localSearcher(nextState);
		    nextState.print();
			
			while (isStopCriteria(this.objProblem)) {
				if (nic < this.nicConstant)
					nextState = perturbation(this.status.stateFinal, false); 
				 
				if (HC.allNeighs.equals("ALL")) {
					nextState = HC.hillClimbingAllWithBreak(nextState, this.objProblem);
				} else {
					nextState = HC.hillClimbingWithBreak(nextState, this.objProblem);
				} 
                System.out.println("ooo "+this.nicConstant);
                nextState.print();
                nextState = localSearcher(nextState);
			    nextState.print(); 
				
				if (op.best(nextState, this.status.stateFinal, this.objProblem)  
					&& (nic < this.nicConstant)) {
					this.status.stateFinal = (Solution) nextState.clone(); 
				} else if (nic == this.nicConstant) {
					this.status.stateFinal = (Solution) nextState.clone();
					nic = 0;
				} else { 
					nic++;
				}  
			} 
	   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public Solution perturbation(Solution _tmp, boolean _aux) {
		// String format = "";
		Solution nextState = (Solution) _tmp.clone();

		if (this.perturbation.equals("SWAPPING")) {
			if (!_aux) { 
				op.swapping(nextState);
				this.objProblem.evaluate(nextState);
				this.objProblem.counter.incCount(); 
			}
			return nextState;

		} else {
			if (!_aux) {
				op.swappingRBI(nextState);
				this.objProblem.evaluate(nextState);
				this.objProblem.counter.incCount(); 
			}
			return nextState;
		}
	}
	
	public Solution localSearcher(Solution solution ) {
		Solution solutionLS = (Solution) solution.clone();
	 
		if (this.includeMethods.equals("HC")) {
			HillClimbing HC = new HillClimbing(this.objProblem,
					this.configMethods, solution);
			HC.setParameters(this.objProblem); 
			/* doing local search until improvement
			/*if (this.lslevels !=-1)
				this.nEvalsLS = (int)(this.lslevels*HC.factorNeighs*this.individuals);
			*/
			solutionLS = HC.hillClimbing(solution, this.objProblem, 1000000);
		 
		} else if (this.includeMethods.equals("SA")) {
			/*SimulatingAnnealing SA = new SimulatingAnnealing(this.objProblem,
					this.configMethods, solution);
			SA.setParameters(this.objProblem); 
			solutionLS = SA.selectSA(solution, this.objProblem, this.nEvalsLS);*/
		 
		} else if (this.includeMethods.equals("ILS")) {
			/*IteratedLocalSearch ILS = new IteratedLocalSearch(this.objProblem,
					this.configMethods, solution);
			ILS.setParameters(this.objProblem);
			solutionLS = ILS.selectILS(solution, this.objProblem,
					this.nEvalsLS);*/
		} else if (this.includeMethods.equals("VNS")) {
			/*VariableNeighbourhoodSearch VNS = new VariableNeighbourhoodSearch(
					this.objProblem, this.configMethods, solution);
			VNS.setParameters(this.objProblem);
			solutionLS = VNS.selectVNS(solution, this.objProblem, this.nEvalsLS);*/
 		} else if (this.includeMethods.equals("LF")) { 
			/*LevyFlights LF = new LevyFlights(this.objProblem,
					this.configMethods, solution, population.get(0));
			LF.setParameters(this.objProblem); 
			solutionLS = LF.selectLF(solution, this.objProblem, this.nEvalsLS); */
		}

		return solutionLS;
	}
}
