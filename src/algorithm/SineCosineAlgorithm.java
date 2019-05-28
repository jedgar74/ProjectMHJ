package algorithm;

import problem.Problem;
import state.Population;
import state.Solution;
import util.Randomizer;

public class SineCosineAlgorithm extends Heuristic{
	String shortTerm = "SCA";
	String initialSolution = "RANDOM"; 
	int nSearchAgents = 10;
	Population searchAgents = new Population(); 
	
	double R1 = 1;
	double R2 = 1;
	double R3 = 1;
	double R4 = 1;
	
	
	
	public SineCosineAlgorithm(Problem problem, String _fileConfig) {

		this.objProblem = problem;
		this.readParameters(_fileConfig, this.shortTerm);
		this.setParameters(problem);
		this.status.stateInitial = new Solution("PERMUTATIONAL", objProblem.nVar, initialSolution);
		problem.evaluate(this.status.stateInitial);
		problem.counter.incCount();
		this.status.stateFinal = (Solution) this.status.stateInitial.clone(); 

		/*this.status.stateInitial.print();*/
		/*
		 * this.stateFinal = new Solution(objProblem.nJobs,
		 * objProblem.typeInitialSolution); this.stateFinal.copy(_state);
		 */
		this.searchAgents.generateRandomAndSort(this.nSearchAgents, this.status.stateInitial, objProblem);

		this.status.stateInitial = (Solution) this.searchAgents.get(0).clone();
		this.status.stateFinal = (Solution) this.status.stateInitial.clone(); 

		/*System.out.print(" HC " + this.allNeighs);
		if (this.allNeighs.equals("NONE"))
			System.out.print(" FactNeigh=" + this.factorNeighs);
		System.out.println();*/
		sineCosineAlgorithm(this.status.stateInitial);
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
	public SineCosineAlgorithm(Problem problem, String _fileConfig, Solution _state) {
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

			if (nameParameters.get(i).equals("NSEARCHAGENTS"))
				this.nSearchAgents = Integer.parseInt(valueParameters
						.get(i)); 
			else if (nameParameters.get(i).equals("TYPEINITIAL")) 
				this.initialSolution = valueParameters.get(i);
			else if (nameParameters.get(i).equals("R1")) 
				this.R1 = Double.parseDouble(valueParameters.get(i));
		}
	}


	public void sineCosineAlgorithm(Solution _init) {

		double r1=1;double r2=1;double r3=1;double r4=1;
		this.searchAgents.printFitness("POP(INI)");
        //		this.status.stateFinal

		while (isStopCriteria(this.objProblem)) {
			for (int i = 0; i < this.nSearchAgents ; i++) {
				r1=Randomizer.getDouble()*R1;
				r2=Randomizer.getDouble()*R2;
				r3=Randomizer.getDouble()*R3;
				r4=Randomizer.getDouble()*R4;
				int d = op.distance(this.status.stateFinal, searchAgents.get(i));
				
				double s;
				if (r4< 0.5) {
					//sin 
					s = r1*Math.sin(r2)*d;  
				} else {
					s = r1*Math.cos(r2)*d;
				}
				/*System.out.println(" s"+s);*/
				/*searchAgents.get(i).print("..."+(int) s);*/
//				op.swappingKNeigh(searchAgents.get(i), (int) s);
				Solution nextState = (Solution) searchAgents.get(i).clone();
				op.swappingKNeigh(nextState, (int) s);
				this.objProblem.evaluate(nextState);
				this.objProblem.counter.incCount();

				/*nextState.print("..+");*/
				searchAgents.setElement(i,(Solution) nextState.clone());
				/*if (op.best(nextState, searchAgents.get(i), this.objProblem)) { 
					searchAgents.setElement(i,(Solution) nextState.clone()); 
				} */	
			}
			this.status.stateFinal = searchAgents.getBetter(objProblem);
			
			this.searchAgents.printFitness("POP(*)");
			
			/*op.swapping(nextState);
			this.objProblem.evaluate(nextState);
			this.objProblem.counter.incCount();
			nextState.swappingOne();
			nextState.calcFitnessOne();
			objProblem.evaluationCounter++;
			 
			if (op.best(nextState,this.status.stateFinal, this.objProblem)) { 
				this.status.stateFinal = (Solution) nextState.clone(); 
			} 
			int i =op.distance(_init, nextState);*/
		}
 
	}
}
