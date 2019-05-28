package algorithm;

import problem.Problem;  
import state.Solution;
import util.Randomizer;


/**
 * Simulating Annealing
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class SimulatingAnnealing extends Heuristic  {
	String shortTerm = "SA";
	double initTemperature = 0;
	double finalTemperature = 0;
	String coolingScheme = "GEOMETRIC"; // can be GEOMETRIC or ARITHMETIC
	double omega = -1;
	String initialSolution = "RANDOM"; 
    
    public SimulatingAnnealing(Problem problem, String _fileConfig) {

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
		

		/*System.out.print(" HC " + this.allNeighs);
		if (this.allNeighs.equals("NONE"))
			System.out.print(" FactNeigh=" + this.factorNeighs);
		System.out.println();*/
		simulatingAnnealing(this.status.stateInitial);
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
	public SimulatingAnnealing(Problem problem, String _fileConfig, Solution _state) {
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

			if (nameParameters.get(i).equals("INITTEMPERATURE"))
				this.initTemperature = Double.parseDouble(valueParameters
						.get(i));
			else if (nameParameters.get(i).equals("FINALTEMPERATURE"))
				this.finalTemperature = Double.parseDouble(valueParameters
						.get(i));
			else if (nameParameters.get(i).equals("COOLINGSCHEME"))
				this.coolingScheme = valueParameters.get(i);
			else if (nameParameters.get(i).equals("OMEGA"))
				this.omega = Double.parseDouble(valueParameters.get(i));
			else if (nameParameters.get(i).equals("TYPEINITIAL")) {
				this.initialSolution = valueParameters.get(i);
			}
		}
	}

	public void simulatingAnnealing(Solution _init) {

		Solution nextState = (Solution) _init.clone();
		double temperature = this.calculateInitialTemperatureKirkpatrick();

		/*System.out.println(" SA CoolingScheme=" + this.coolingScheme
				+ " Omega=" + omega + " Tf=" + this.finalTemperature + "  To="
				+ this.initTemperature);*/

		while (isStopCriteria(this.objProblem)) {
 
			op.swapping(nextState);
			this.objProblem.evaluate(nextState);
			this.objProblem.counter.incCount();
			/*nextState.swappingOne();
			nextState.calcFitnessOne();
			objProblem.evaluationCounter++;
*/
			if (op.best(nextState,this.status.stateFinal, this.objProblem)) { 
				this.status.stateFinal = (Solution) nextState.clone(); 
			} else if (isAccept(temperature, nextState, this.status.stateFinal)) { 
				this.status.stateFinal = (Solution) nextState.clone();
			}

			temperature = updateTemperature(temperature);
		}

		// System.out.println(" : "+this.objProblem.evaluationCounter);
		// this.internalState.setLabel("Final");
	}
    
	/*
	 * Revise the objProblem.counter.limit in omega calculating
	 * 
	 */
	public double calculateInitialTemperatureKirkpatrick() {
		double temperature = this.initTemperature;

		if (this.omega == -1) {
			if (this.coolingScheme.equals("ARITHMETIC")) {
				this.omega = (double) (temperature / objProblem.counter.limit);
				this.finalTemperature = 0;
			} else {
				this.finalTemperature = (double) (1e-6 / temperature);
				double timer = (1 / (double) objProblem.counter.limit);
				this.omega = Math.pow(finalTemperature, timer);
			}
		}
		return temperature;
	}
	
	public double updateTemperature(double temperature) {

		if (this.coolingScheme.equals("ARITHMETIC"))
			return omega - temperature;
		else
			return omega * temperature;
	}

	
	public boolean isAccept(double _temp, Solution _nextState,
			Solution _internalState) {
		double ic = 50;

		ic *= Randomizer.getDouble();

		if (ic < this.probabilityFunction(_temp, _nextState.fitness,
				_internalState.fitness)) {
			return true;
		} else {
			return false;
		}
	}

	
	public double probabilityFunction(double _t, double _nf, double _af) {
		return Math.exp((_af - _nf) / _t);
	}
}
