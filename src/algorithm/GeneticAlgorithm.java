package algorithm;

import java.util.ArrayList; 
import problem.Problem; 
import state.Population;
import state.Solution;
import util.Randomizer; 


/**
 * Genetic Algorithm  
 * 
 * @author Jhon Edgar Amaya
 */
public class GeneticAlgorithm extends Heuristic {

	String shortTerm = "GA"; 
	int individuals = 10;

	String operSelection = "TOURNAMENT"; // see selection method
	int nIndividualsTournament = 1;

	String operCrossover = "U2X";	// see crossover method
	String operMutation = "SWAPPING"; // see mutation method
	String version = "STEADY"; // or GENERATIONAL --- before geneticType
	double probCrossover = 1;
	double probMutation = 0.15;
	int lambda = -1; 
	Population population = new Population();

	int nMutations = 0;
	int nCrossovers = 0;
	String initialSolution = "RANDOM"; 


	public GeneticAlgorithm(Problem problem, String _fileConfig) {

		this.objProblem = problem;
		this.readParameters(_fileConfig, this.shortTerm);
		this.setParameters(problem); 

		// Solution _state = new Solution(objProblem.nJobs, objProblem.typeInitialSolution);
		/*
		 * this.stateFinal = new Solution(objProblem.nJobs, objProblem.typeInitialSolution); 
		 * this.stateFinal.copy(_state);
		 */ 

		this.status.stateInitial = new Solution(problem.typeState, objProblem.nVar, initialSolution);

		problem.evaluate(this.status.stateInitial);
		problem.counter.incCount();  

		/*if (this.geneticType.equals("STEADY")) {
			if (problem.typeState.equals("BINARY")){
				population.generateRandom(individuals, this.status.stateInitial, objProblem);

				this.status.stateInitial = (Solution) this.population.get(0).clone();
				this.status.stateFinal = (Solution) this.status.stateInitial.clone();

				geneticBinary(this.status.stateInitial);
			} else if (problem.typeState.equals("PERMUTATIONAL")){
				population.generateRandomSortFOne(individuals, this.status.stateInitial, objProblem);

				this.status.stateInitial = (Solution) this.population.get(0).clone();
				this.status.stateFinal = (Solution) this.status.stateInitial.clone();

				genetic(this.status.stateInitial);
			}
		} if (this.geneticType.equals("GENERATIONAL")) {
			population.generateRandomSortFOne(individuals, this.status.stateInitial, objProblem);

			this.status.stateInitial = (Solution) this.population.get(0).clone();
			this.status.stateFinal = (Solution) this.status.stateInitial.clone();

			geneticGen(this.status.stateInitial);
		} */

		population.generateRandomAndSort(individuals, this.status.stateInitial, objProblem);

		this.status.stateInitial = (Solution) this.population.get(0).clone();
		this.status.stateFinal = (Solution) this.status.stateInitial.clone();

		if (this.version.equals("STEADY")) {
			if (problem.typeState.equals("BINARY")){
				geneticBinary(this.status.stateInitial);
			} else if (problem.typeState.equals("PERMUTATIONAL")){
				genetic(this.status.stateInitial);
			}
		} if (this.version.equals("GENERATIONAL")) {			
			geneticGen(this.status.stateInitial);
		}
	}


	public GeneticAlgorithm(Problem problem, String _fileConfig, String shortT) {

		this.objProblem = problem;
		this.readParameters(_fileConfig, shortT);
		this.setParameters(problem); 

		// Solution _state = new Solution(objProblem.nJobs,
		// objProblem.typeInitialSolution);
		/*
		 * this.stateFinal = new Solution(objProblem.nJobs,
		 * objProblem.typeInitialSolution); this.stateFinal.copy(_state);
		 */

		this.status.stateInitial = new Solution(problem.typeState, objProblem.nVar, initialSolution);
		problem.evaluate(this.status.stateInitial);
		problem.counter.incCount();
		// this.population = generatingPopulation(_state);
		population.generateRandomAndSort(individuals, this.status.stateInitial, objProblem);

		this.status.stateInitial = (Solution) this.population.get(0).clone();
		this.status.stateFinal = (Solution) this.status.stateInitial.clone(); 

	}


	public GeneticAlgorithm(Problem problem, String _fileConfig, Solution solution) {

		this.objProblem = problem;
		this.readParameters(_fileConfig, this.shortTerm);
		this.setParameters(problem); 

		// this.population = generatingPopulation(solution);
		population.generateRandomAndSort(individuals, solution, objProblem);

		solution = (Solution) this.population.get(0).clone();
		this.status.stateFinal = (Solution) solution.clone();

	}


	public GeneticAlgorithm(){

	}


	/**
	 * Select parameters of algorithm
	 * 
	 * @param problem
	 */
	public void setParameters(Problem problem) {

		for (int i = 0; i < nameParameters.size(); i++) {

			if (nameParameters.get(i).equals("INDIVIDUALS")) {
				this.individuals = Integer.parseInt(valueParameters.get(i));
			} else if (nameParameters.get(i).equals("CROSSOVER")) {
				this.operCrossover = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("MUTATION")) {
				this.operMutation = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("PROBCROSSOVER")) {
				this.probCrossover = Double.parseDouble(valueParameters.get(i));
			} else if (nameParameters.get(i).equals("PROBMUTATION")) {
				/*
				 * if -1 indicates a 1/n
				 */
				if (valueParameters.get(i).equals("-1"))
					this.probMutation = Math.pow(this.individuals, -1);
				else
					this.probMutation = Double.parseDouble(valueParameters.get(i));

			} else if (nameParameters.get(i).equals("VERSION")) {
				this.version = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("TYPEINITIAL")) {  
				this.initialSolution = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("SELECTION"))  {
				this.operSelection = valueParameters.get(i);
			} else if (nameParameters.get(i).equals("INDXTOURNAMENT")) {  
				this.nIndividualsTournament = Integer.parseInt(valueParameters.get(i));
			} else if (nameParameters.get(i).equals("LAMBDA")) {
				/*
				 * if -1 indicates equals to n solutions
				 */
				if (valueParameters.get(i).equals("-1"))
					this.lambda = this.individuals;
				else
					this.lambda = Integer.parseInt(valueParameters.get(i)); 
			}
		}
	}


	/**
	 * 
	 * 
	 * @param solution
	 */
	public void updatePopulation(Solution solution) {

		if (!population.contains(solution)) {

			boolean tempox = true;
			int p = 0;
			while (p < population.size()) {
				if (op.best(solution, population.get(p),objProblem)) {
					population.add(p, solution);
					tempox = false;
					break;
				}
				p++;
			}
			if (!tempox)
				this.population.removeElement(this.individuals);

		}
	}

	/*public void printTempoV(String solution) {

	 * System.out.print("--> "+solution + " "); for (int i = 0; i <
	 * population.size(); i++){ System.out.println(""+
	 * population.get(i).prints()+" "); }


		System.out.print("--> " + solution + " ");
		for (int i = 0; i < population.size(); i++) {
			System.out.print("" + (int) population.get(i).fitness
					+ ".");

		}
		System.out.println();
	}
	 */

	public int[] selection(){ 
		/*System.out.println(this.operSelection);*/
		if (this.operSelection.equals("TOURNAMENT")) 
			return  op.tournament(this.objProblem, population, this.nIndividualsTournament);
		/* int[] parents = op.roulette(population);
		   int[] parents = op.ranking(population);*/
		else if (this.operSelection.equals("ROULETTE")) 
			return op.roulette(population);
		else if (this.operSelection.equals("RANKING")) 
			return op.ranking(population);
		else 
			return op.tournament(this.objProblem, population, 1);
	}


	/**
	 * Select the crossover method
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public Solution crossover(Solution s1, Solution s2) {
		Solution _state = (Solution) s1.clone();
		// s1.printSolution("s1");
		// s2.printSolution("s2");
		// System.out.println("*** "+this.operCrossover);

		if (s1.typeVar.equals("PERMUTATIONAL")){
			if (this.operCrossover.equals("APX"))
				op.crossoverAlternatingPosition(s1, s2); 
			else if (this.operCrossover.equals("U2X"))
				op.crossoverUniformModified(s1, s2);
			else if (this.operCrossover.equals("PBX"))
				op.crossoverPositionBased(s1, s2);
			else if (this.operCrossover.equals("PMX"))
				op.crossoverPartiallyMatched(s1, s2); 
			else if (this.operCrossover.equals("OX")) 
				op.crossoverOrder(s1, s2);
			else if (this.operCrossover.equals("CX"))
				op.crossoverCycle(s1, s2);

		} else if (s1.typeVar.equals("BINARY")){
			if (this.operCrossover.equals("1POINT"))
				op.crossoverOnePoint(s1, s2); 
			else if (this.operCrossover.equals("2POINT"))
				op.crossoverOnePoint(s1, s2);
			else if (this.operCrossover.equals("UX"))
				op.crossoverUniform(s1, s2);
		}
		/*else if (this.operCrossover.equals("NCPX"))
			_state.crossoverNCPX(s1, s2);*/ 

		return _state;
	}


	/**
	 * Select the mutation scheme
	 * 
	 * @param solution
	 */
	public void mutation(Solution solution) {
		if (solution.typeVar.equals("PERMUTATIONAL")){
			if (this.operMutation.equals("BLOCKING")) { 
				op.swappingRBI(solution);
				this.nMutations++;
			} else if (this.operMutation.equals("SWAPPING")) {
				op.swapping(solution);  // before swappingOne();
				this.nMutations++;
			}
		} else if (solution.typeVar.equals("BINARY")){
			if (this.operMutation.equals("FLIPPING")){
				op.flipping(solution ); 
				this.nMutations++;
			}
		}
	}


	public void genetic(Solution solution) {

		//		Solution nextState = (Solution) solution.clone();
		//		boolean control = true;
		this.population.printFitness("POP(INI)"); // before printTempoV(String)

		/*int contador = 0;*/
		int samples = 1;
		/*Tracer tr = new Tracer(50000 );*/

		while (isStopCriteria(this.objProblem)) { 
			int i = 0;  

			while (i < samples) {
//System.out.println("..."+this.objProblem.counter.count);
				int notCrossover = 0;

				int[] parents = selection();
				Solution tempSol = (Solution) this.population.get(parents[0]) .clone(); 

				if (Randomizer.getDouble() <= this.probCrossover){
//System.out.println("...1");
					crossover(tempSol, this.population.get(parents[1]));
//System.out.println("...2"); 
				} else {
					notCrossover = 1;
				}
//System.out.println("...3");
				if (notCrossover == 0 && Randomizer.getDouble() <= this.probMutation)
					mutation(tempSol);
//System.out.println("...4");
				// if (notCrossover == 0 & ! this.population.contains(tempSol)){
				if (notCrossover == 0 && ! containElement(tempSol)) {
//System.out.println("...5");			
					/*System.out.println("..."+tempSol.fitness);*/
					objProblem.evaluate(tempSol);
					objProblem.counter.incCount();
					/*System.out.println(".a."+tempSol.fitness);*/
					boolean better = false;
//System.out.println("...6");
					/*this.printTempoV("B");*/
					/* recorremos para ver */
					for (int rr = 0; rr < this.individuals; rr++) {
						if (tempSol.fitness < this.population.get(rr).fitness
								&& ! this.population.containsStrict(tempSol, op )) {
							/*System.out.println("***"+tempSol.fitness);*/
							this.population.add(rr, tempSol);
							// --states2.print();
							better = true;
							break;
						}
					}
					/*this.printTempoV("A");*/
//System.out.println("...7");
					if (better)
						this.population.removeElement(this.individuals);
//System.out.println("...8");
					i++;
				}
			}

			/*contador++;*/ 
			/*tr.printBundle(objProblem, population, true); */ 
		}

		this.status.stateFinal  = (Solution) this.population.get(0).clone();
		/*this.stateFinal = this.population.get(0);*/
		// --this.printTempoS(states, "---");
		this.population.printFitness("POP(FIN)");
		/*System.out.println("CONDITIONS " + contador + " - Mut:" + this.nMutations + " --> "
				+ this.objProblem.counter.getCount()+"\n");*/
		System.out.println();
	} 


	/**
	 *  Implement a GA (\miu, \lambda) where \miu is the population size and \lambda is next offspring
	 *  
	 *  see Thomas Jansen Analyzing Evolutionary Algorithms pag 21
	 * 
	 * @param solution
	 */
	public void geneticGen(Solution solution) {  
		this.population.printFitness("POP(INI)"); 
		/*int contador = 0;*/ 
		ArrayList<Solution> populationTemp = new ArrayList<Solution>(); 

		while (isStopCriteria(this.objProblem)) { 
			int i = 0;  
			/*int nc=0, nm=0;*/

			while (i < this.lambda) { 
				int notCrossover = 0; 
				int[] parents = selection();
				Solution tempSol = (Solution) this.population.get(parents[0]) .clone(); 
				/*tempSol.print();*/
				if (Randomizer.getDouble() <= this.probCrossover){
					crossover(tempSol, this.population.get(parents[1]));
					/*nc++;*/
				} else {
					notCrossover = 1;
				}

				if (notCrossover == 0 && Randomizer.getDouble() <= this.probMutation){
					mutation(tempSol);
					/*nm++;*/
				}

				populationTemp.add(tempSol);
				objProblem.evaluate(tempSol);
				objProblem.counter.incCount();
				i++;
			}

			// An Overview of methods maintaining Diversity in Genetic Algorithms
			// Deepti Gupta, Shabina Ghafir 

			/*System.out.println(populationTemp.size());*/
			this.population.addSolutions(populationTemp);
			/*population.printTempoV("POP(***)"); */
			/* this.population = (Population) populationTemp.clone();
		    this.population.sort();
			populationTemp.removeAll(); 

			if (op.best(this.population.getBetter2(objProblem), this.status.stateFinal, objProblem))
				this.status.stateFinal  =   this.population.getBetter2(objProblem);*/
			/*this.status.stateFinal.print();*/
			/*contador++;*/ 
			/*tr.printBundle(objProblem, population, true); */ 
		} 

		this.status.stateFinal = this.population.get(0); 
		// --this.printTempoS(states, "---");
		this.population.printFitness("POP(FIN)");
		/*System.out.println("CONDITIONS " + contador + " - Mut:" + this.nMutations + " --> "
				+ this.objProblem.counter.getCount()+"\n");*/
		System.out.println();
	}


	public void geneticBinary(Solution solution) {

		this.population.printFitness("POP(INI)");

		/*int contador = 0;*/
		int samples = 1; 

		while (isStopCriteria(this.objProblem)) {

			int i = 0;  

			while (i < samples) {
				int notCrossover = 0; 
				int[] parents = selection();
				Solution tempSol = (Solution) this.population.get(parents[0]) .clone(); 

				if (Randomizer.getDouble() <= this.probCrossover){
					crossover(tempSol, this.population.get(parents[1])); 
				} else {
					notCrossover = 1;
				}

				if (notCrossover == 0 && Randomizer.getDouble() <= this.probMutation)
					mutation(tempSol);

				// if (notCrossover == 0 & ! this.population.contains(tempSol)){
				if (notCrossover == 0 && ! containElement(tempSol)) {
					/*System.out.println("..."+tempSol.fitness);*/
					objProblem.evaluate(tempSol);
					objProblem.counter.incCount();
					/*System.out.println(".a."+tempSol.fitness);*/
					boolean better = false;

					/*this.printTempoV("B");*/
					/* recorremos para ver */
					for (int rr = 0; rr < this.individuals; rr++) {
						if (tempSol.fitness < this.population.get(rr).fitness
								&& ! this.population.containsStrict(tempSol, op )) {
							/*System.out.println("***"+tempSol.fitness);*/
							this.population.add(rr, tempSol);
							// --states2.print();
							better = true;
							break;
						}
					}
					/*this.printTempoV("A");*/

					if (better)
						this.population.removeElement(this.individuals);

					i++;
				}
			} 
			/*contador++;*/ 
			/*tr.printBundle(objProblem, population, true); */ 
		} 

		this.status.stateFinal  = (Solution) this.population.get(0).clone();
		/*this.stateFinal = this.population.get(0);*/
		// --this.printTempoS(states, "---");
		this.population.printFitness("POP(FIN)");
		/*System.out.println("CONDITIONS " + contador + " - Mut:" + this.nMutations + " --> "
				+ this.objProblem.counter.getCount()+"\n");*/
		System.out.println();
	}

	/*public Solution geneticLimited(Solution solution, Problem problem, int _evals) {

		// System.out.println("HERE.................. "+problem.evaluationCounter);

		// Solution nextState = (Solution) solution.clone();
		// boolean control = true;
		this.printTempoV("");

		int contador = 0;
		int _samples = 1;
		Tracer tr = new Tracer(500000);

		updatePopulation(solution);

		// System.out.println("EVALS "+_evals);
		// System.out.println("COUNT "+problem.evaluationCounter);
		// System.out.println("FINIS "+problem.timeLimit);
		int controlled = problem.evaluationCounter;

		// System.out.println(" "+this.timeExecEvaluation(_evals+controlled,
		// problem));

		while (this.timeExecEvaluation(_evals + controlled, problem)) {
			// System.out.println("......INPUT");
			contador++;
			int i = 0;

			while (i < _samples) {
				int notCrossover = 0;
				int a = this.random.nextInt(this.individuals);
				int b = this.random.nextInt(this.individuals);

				Solution tempSol = (Solution) this.population.get(a)
						.clone();
				if (this.random.nextDouble() <= this.probCrossover)
					tempSol = crossover(this.population.get(a),
							this.population.get(b));
				else
					notCrossover = 1;

				if (notCrossover == 0
						& this.random.nextDouble() <= this.probMutation)
					mutation(tempSol);

				// if (notCrossover == 0 & ! this.population.contains(tempSol)){
				if (notCrossover == 0 && !containElement(tempSol)) {

					tempSol.calcFitnessOne();
					problem.evaluationCounter++;

					boolean better = false;
                    int rd = 0;
					 recorremos para ver 
					for (int rr = 0; rr < this.individuals; rr++) {
						if (tempSol.fitness < this.population
								.get(rr).fitness) {
							// System.out.println("1. "+this.population.size());
							this.population.add(rr, tempSol);
							// --states2.print();
							better = true;
							rd = rr;
							break;
						}
					}

					if (better) {
						// System.out.println(":::2. "+this.population.size()+
						// " " +rd);
						this.population.remove(this.individuals);
					}
					i++;
				}
			}


	 * if (this.nUpdates > _time){ for(int t= 0; t < this.individuals;
	 * t++) { if (! this.internalState.best((JobsSqcs)
	 * states.get(t))) this.internalState.copy((JobsSqcs)
	 * states.get(t)); } control = false;
	 * this.internalState.printAllDone("Over");
	 * 
	 * }

			contador++;
			tr.printBundle(objProblem, population, true); 

		}
		solution = this.population.get(0);
		// --this.printTempoS(states, "---");
		System.out.println("Cycles " + contador + " " + this.nMutations + " : "
				+ problem.evaluationCounter);

		return solution;
	}*/


	public boolean containElement(Solution solution) {
		// & ! this.population.contains(solution)){
		boolean flag = false;
		
		for (int i = 0; i < this.population.size(); i++) {
			if (this.population.get(i).equals(solution)) {
				flag = true;
				break;
			}
		}

		return flag;
	}


	public String getShortTerm() {
		return shortTerm;
	}


	public void setShortTerm(String shortTerm) {
		this.shortTerm = shortTerm;
	}

	/*public Solution selectGA(Solution solution, Problem problem, int _evals) {
		return this.geneticLimited(solution, problem, _evals);
	}*/ 

}
