package algorithm;

import problem.Problem;
import state.Operators;
import state.Solution;
import statistics.BasicStats;

public class RandomWalk extends Heuristic  {
	String shortTerm = "RW";
	double factorNeighs = 0;
	String allNeighs = "NONE"; // can be ALL or NONE
	String initialSolution = "RANDOM";
	String typeNeigh="SWAP"; 
    BasicStats data = new BasicStats();
    
    
	public RandomWalk(Problem problem, String _fileConfig) {

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
		randomWalk(this.status.stateInitial);
		/*if (this.allNeighs.equals("ALL")) {
			hillClimbingAll(_state);
		} else {
			hillClimbingNoAll(_state);
		}*/
	}

	public RandomWalk(Problem problem, String _fileConfig, BasicStats data) {
        this.data = data;
		
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
		randomWalk(this.status.stateInitial);
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
	public RandomWalk(Problem problem, String _fileConfig, Solution _state) {
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

	/*
	 * public HillClimbing(Problem problem, String _fileConfig){
	 * 
	 * //Solution _tmpSolution = new Solution(); this.objProblem = problem;
	 * 
	 * 
	 * this.setParameters(problem);
	 * 
	 * }
	 */

	/**
	 * 
	 * 
	 * @param problem
	 */
	public void setParameters(Problem problem) {
		for (int i = 0; i < nameParameters.size(); i++) {

			if (nameParameters.get(i).equals("TYPEINITIAL")) {
				this.initialSolution = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("FACTORNEIGHS")) {
				this.factorNeighs = Double.parseDouble(valueParameters
						.get(i));
			} else if (nameParameters.get(i).equals("ALLNEIGHS")) {
				this.allNeighs = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("TYPENEIGHS")) {
				this.typeNeigh = valueParameters.get(i);
			}
		}
	}
	
	public void randomWalk(Solution _init) {
		try {

			/*
			 * Solution nextState = new Solution(objProblem.nJobs,
			 * objProblem.typeInitialSolution); nextState.copy(_init);
			 */
			Solution nextState = (Solution) _init.clone();

			while (isStopCriteria(this.objProblem)) {
				// int numberNeighs = (this.nJobs*(this.nJobs - 1))/2;
				// ArrayList<JobsSqcs> n = getAllNeighbourhood(nextState);
				 nextState = neighborhood(this.status.stateFinal); 
				 this.objProblem.counter.incCount();
				 /*System.out.println(""+data.nSolutions());*/
				 data.add((Solution) nextState.clone());
				if (op.best(nextState, this.status.stateFinal, this.objProblem) ) {
					// ___System.out.println("CH "+nextState.fitnessOne+"---"+this.stateFinal.fitnessOne);
					this.status.stateFinal = (Solution) nextState.clone(); 
				} /*else {
					nextState = new Solution(problem.typeState, objProblem.nVar, "RANDOM"); 
				}*/ 
			}

		} catch (Exception e) {

		}
	}
	
	
	public Solution neighborhood(Solution s){
		Solution r = (Solution) s.clone();
		
		if (this.typeNeigh.equals("SWAP")){
		    op.swapping(r);
		    this.objProblem.evaluate(r);
		} else if (this.typeNeigh.equals("RBI")){
		    op.swappingRBI(r);
		    this.objProblem.evaluate(r);
		}
		
		return r;
	}

	public BasicStats getData() {
		return data;
	}
	
	
}
