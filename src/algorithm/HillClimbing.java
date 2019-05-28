package algorithm; 

import problem.Problem; 
import state.Solution;

/**
 *   by Jhon Edgar Amaya
 * 
 * ...
 * 
 * @author Jhon Edgar Amaya
 */
public class HillClimbing extends Heuristic  {

	String shortTerm = "HC";
	double factorNeighs = 0;
	String allNeighs = "NONE"; // can be ALL or NONE
	String initialSolution = "RANDOM"; 
	/**
	 * 
	 * 
	 * @param problem
	 * @param _fileConfig
	 */ 
	public HillClimbing(Problem problem, String _fileConfig) {

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
		hillClimbing(this.status.stateInitial);
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
	public HillClimbing(Problem problem, String _fileConfig, Solution _state) {
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
			}
		}
	}

	
	/**
	 * Hill Climbing with all neighs with reinitial
	 * 
	 * @param _init
	 */
	/*public void hillClimbingAll(Solution _init) {
		try {

			
			 * Solution nextState = new Solution(objProblem.nJobs,
			 * objProblem.typeInitialSolution); nextState.copy(_init);
			 
			Solution nextState = (Solution) _init.clone();

			while (timeExecEvaluation(this.objProblem)) {
				// int numberNeighs = (this.nJobs*(this.nJobs - 1))/2;
				// ArrayList<JobsSqcs> n = getAllNeighbourhood(nextState);

				ArrayList<Solution> neighs = this.objProblem
						.getAllNeighbourhood(nextState);

				int best = 0;

				// ...Get the best of neighbourhood
				for (int d = 1; d < neighs.size(); d++) {
					if ((neighs.get(d)).best(neighs.get(best))) {
						best = d;
					}
				}

				nextState = (Solution) neighs.get(best).clone();
				// nextState.copy(neighs.elementAt(best));

				// ___System.out.println("--> "+this.objProblem.evaluationCounter);
				if (nextState.best(this.stateFinal)) {
					// ___System.out.println("CH "+nextState.fitnessOne+"---"+this.stateFinal.fitnessOne);
					this.stateFinal = (Solution) nextState.clone();

				} else {
					// System.out.print(" +++");
					nextState.random();
				}

			}

		} catch (Exception e) {

		}
	}*/

	/**
	 * 
	 * 
	 * @param _init
	 */
	public void hillClimbing(Solution _init) {
		try {

			/*
			 * Solution nextState = new Solution(objProblem.nJobs,
			 * objProblem.typeInitialSolution); nextState.copy(_init);
			 */
			Solution nextState = (Solution) _init.clone();

			while (isStopCriteria(this.objProblem)) {
				// int numberNeighs = (this.nJobs*(this.nJobs - 1))/2;
				// ArrayList<JobsSqcs> n = getAllNeighbourhood(nextState);
  
				if (objProblem.typeState.equals("PERMUTATIONAL"))
				    nextState = op.getBestNeighbour(nextState, this.factorNeighs, this.objProblem);
				else if (objProblem.typeState.equals("BINARY"))
					nextState = op.getBestNeighbourBinary(nextState, this.factorNeighs, this.objProblem);
//			    nextState.print();
				// ___System.out.println("--> "+this.objProblem.evaluationCounter);
				if (op.best(nextState, this.status.stateFinal, this.objProblem) ) {
					// ___System.out.println("CH "+nextState.fitnessOne+"---"+this.stateFinal.fitnessOne);
					this.status.stateFinal = (Solution) nextState.clone(); 
				} else {
					nextState = new Solution(objProblem.typeState, objProblem.nVar, "RANDOM"); 
				}

			}

		} catch (Exception e) {

		}
	}

	
	/**
	 * 
	 * 
	 * @param _init
	 * @param problem
	 * @return
	 */
	/*public Solution hillClimbingAllWithBreak(Solution _init, Problem problem) {
		try {

			Solution nextState = (Solution) _init.clone();

			// int control = this.objProblem.evaluationCounter;
			// nextState.printSolution("INT_"+this.objProblem.evaluationCounter);

			while (timeExecEvaluation(problem)) {

				ArrayList<Solution> neighs = problem
						.getAllNeighbourhood(nextState);

				int best = 0;

				// ...Get the best of neighbourhood
				for (int d = 1; d < neighs.size(); d++) {
					if ((neighs.get(d)).best(neighs.get(best))) {
						best = d;
					}
				}

				nextState = (Solution) neighs.get(best).clone();
				// nextState.copy(neighs.elementAt(best));

				if (nextState.best(_init)) {
					// this.stateFinal.copy(nextState);
					_init = (Solution) nextState.clone();
					// System.out.println("ooooooooooooooooooooooooooooooooooo");

				} else {
					// System.out.println(" +++"+
					// this.objProblem.evaluationCounter);
					break;
					// __nextState.random();
				}

			}

			// nextState.printSolution("***");
		} catch (Exception e) {

		}

		return _init;
	}*/

	
	/**
	 * 
	 * 
	 * @param _init
	 * @param problem
	 * @return
	 */
	/*public Solution hillClimbingWithBreak(Solution _init, Problem problem) {
		try {

			
			 * Solution nextState = new Solution(objProblem.nJobs,
			 * objProblem.typeInitialSolution); nextState.copy(_init);
			 
			Solution nextState = (Solution) _init.clone();

			while (timeExecEvaluation(problem)) {
				// int numberNeighs = (this.nJobs*(this.nJobs - 1))/2;
				// ArrayList<JobsSqcs> n = getAllNeighbourhood(nextState);

				ArrayList<Solution> neighs = problem.getNoAllNeighbourhood(
						nextState, this.factorNeighs);

				int best = 0;

				// ...Get the best of neighbourhood
				for (int d = 1; d < neighs.size(); d++) {
					if ((neighs.get(d)).best(neighs.get(best))) {
						best = d;
					}
				}

				nextState = (Solution) neighs.get(best).clone();
				// nextState.copy(neighs.elementAt(best));

				if (nextState.best(_init)) {
					// this.stateFinal.copy(nextState);
					_init = (Solution) nextState.clone();

				} else {
					// System.out.print(" +++");
					// nextState.random();
					break;
				}

			}

		} catch (Exception e) {

		}

		return _init;
	}
*/
	
	/**
	 * 
	 * 
	 * @param _init
	 * @param problem
	 * @param _evals
	 * @return
	 */
	/*public Solution hillClimbingAllLimited(Solution _init, Problem problem,
			int _evals) {
		try {

			Solution nextState = (Solution) _init.clone();

			int controlled = problem.evaluationCounter;

			while (this.timeExecEvaluation(_evals + controlled, problem)) {

				ArrayList<Solution> neighs = problem
						.getAllNeighbourhood(nextState);

				int best = 0;

				// ...Get the best of neighbourhood
				for (int d = 1; d < neighs.size(); d++) {
					if ((neighs.get(d)).best(neighs.get(best))) {
						best = d;
					}
				}

				nextState = (Solution) neighs.get(best).clone();
				// nextState.copy(neighs.elementAt(best));

				if (nextState.best(_init)) {
					// this.stateFinal.copy(nextState);
					_init = (Solution) nextState.clone();

				} else {
					// System.out.print(" +++");
					break;
					// __nextState.random();
				}

			}

		} catch (Exception e) {

		}

		return _init;
	}
*/
	
	/**
	 * 
	 * 
	 * @param _init
	 * @param problem
	 * @param _evals
	 * @return
	 */
     public Solution hillClimbing(Solution _init, Problem problem, int _evals) {
		try {
			Solution nextState = (Solution) _init.clone(); 
			int controlled = problem.counter.count;  
			
			while (isStopCriteria(_evals + controlled, problem)) {
				/*System.out.println("..."+problem.counter.count+" "+this.factorNeighs);*/
				if (objProblem.typeState.equals("PERMUTATIONAL"))
				    nextState = op.getBestNeighbour(nextState, this.factorNeighs, this.objProblem);
				else if (objProblem.typeState.equals("BINARY"))
					nextState = op.getBestNeighbourBinary(nextState, this.factorNeighs, this.objProblem);
		 
				/*System.out.println("..."+problem.counter.count);*/
				if (op.best(nextState, _init, problem) ) {
					_init = (Solution) nextState.clone(); 
				} else {
					/*nextState = new Solution("PERMUTATIONAL", objProblem.nVar, "RANDOM"); */
					break;
				}

			}

		} catch (Exception e) {

		}

		return _init;
	}
 
	 
	/*public Solution selectHC(Solution _init, Problem problem, int _evals) {
		if (this.allNeighs.equals("ALL"))
			return hillClimbingAllLimited(_init, problem, _evals);
		else
			return hillClimbingLimited(_init, problem, _evals);
	}*/
}
