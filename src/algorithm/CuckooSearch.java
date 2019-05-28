package algorithm; 
 
import problem.Problem;  
import state.Population;
import state.Solution;
import util.Maths;
import util.Randomizer; 


/**
 * Cuckoo Search
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class CuckooSearch extends Heuristic  {
	String shortTerm = "CS";
	String initialSolution = "RANDOM"; 
    
    int populationSize = 0;
	double fracWorstNest = 0.0;
	Population population = new Population();

    
    public CuckooSearch(Problem problem, String _fileConfig) {

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
		cuckooSearch(this.status.stateInitial);
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
	public CuckooSearch(Problem problem, String _fileConfig, Solution _state) {
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

			if (nameParameters.get(i).equals("NEST")) {
				this.populationSize = Integer.parseInt(valueParameters
						.get(i));
			} else if (nameParameters.get(i).equals("PA")) {
				this.fracWorstNest = Double.parseDouble(valueParameters
						.get(i));
			} 
		}
	}
	
	public void cuckooSearch(Solution _init) {
        // System.out.println("? "+this.fracWorstNest);
		int PAf = (int) (this.populationSize * this.fracWorstNest);
		/*Tracer tr = new Tracer(50 );*/
		population.generateRandomAndSort(populationSize, _init, objProblem);
 
		/*int invNest = 1;
		int invNestBef = 1;
		double alpha =  147.0e+12;
		double  miu = 2;*/
		double beta =3/2;
		double sigma = Maths.sigma(beta);
		// System.out.println("..."+this.objProblem.evaluationCounter);
		while (isStopCriteria(this.objProblem)) {
			int pointer = 0;

			for (int k = 0; k < this.populationSize; k++) {
				int nesty = Randomizer.getInt(this.populationSize);
				Solution cuckoo = this.population.get(nesty) ;
				double r = this.calcuteStep(sigma, beta,  cuckoo.fitness ,  ((Solution) this.population.get(0)).fitness);
				
				/*invNest = calcLevyFlight(alpha, miu, this.objProblem.evaluationCounter, cuckoo.size);
				if (invNest != invNestBef){
					System.out.println(""+invNest+"..."+this.objProblem.evaluationCounter);
					invNestBef = invNest ;
				}*/	
				/*if (invNest==2)
					cuckoo.swappingOneAux();
				else
					cuckoo.swappingLevyFlight(invNest);*/
				
				/** Define method to generate n mutations
				 * 
				 
				cuckoo = (Solution) Operators.nmutations(cuckoo, (int) r).clone();
				 */
				cuckoo = new Solution("PERMUTATIONAL", objProblem.nVar, initialSolution);
				this.objProblem.evaluate(cuckoo);
				this.objProblem.counter.incCount(); 

				pointer = Randomizer.getInt(this.populationSize);
				Solution nest = this.population.get(pointer) ;

				if (cuckoo.fitness  < nest.fitness  && population.contains(cuckoo)) {
					/* recorremos para ver */
					this.population.addSort(cuckoo) ; 
				}
			}
			 
			// replace the worst solution based on PA
			// System.out.println("--"+this.populationSize+":::"+PAf);
			for (int j = 0; j < PAf; j++) {
				this.population.removeLastElement();
			}

			int t = 0;
			while (t < PAf) {
				Solution cuckoo = new Solution("PERMUTATIONAL", objProblem.nVar, initialSolution);
				this.objProblem.evaluate(cuckoo);
				this.objProblem.counter.incCount(); 
				this.population.addSort(cuckoo) ;
				t++;
			}

			// get better solution
			this.status.stateFinal = this.population.get(0);
			/*tr.printBundle(objProblem, population, true);*/ 
//			tr.printPopulationLarge(objProblem, population, 3000000);
		} 
	} 
	
	
	public double calcuteStep(double _sigma, double _beta,  
			double _fitness, double _better){
	 
		double _sizeFitness = 2;
		
		double u=Math.random()*_sizeFitness*_sigma;
		double v=Math.random()*_sizeFitness;
		double step = Math.pow(u/Math.abs(v),(1/_beta));
		
		double stepsize = 0.01*step *(_fitness-_better);  
		
//		System.out.println("u "+u+"  v "+v+"  step "+step+"  stepsize "+stepsize);
	    double ret = stepsize*Math.random()*_sizeFitness; 
	    ret = Math.floor(ret)+2;
//	    System.out.println("r "+ret);
	    if (ret > this.objProblem.nVar)
	    	ret = this.objProblem.nVar;
	    
	    return ret;
	}
	
	 
}
