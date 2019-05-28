package algorithm;
 
import problem.Problem;  
import state.Population;
import state.Solution;
import util.Randomizer; 

public class FireworksAlgorithm extends Heuristic  {
	String shortTerm = "FWA";
	String initialSolution = "RANDOM"; 
    
	int individuals = 10;
	Population fireworks = new Population(); 
	double maximum= 10000;
	double mConst=1;
	
	public FireworksAlgorithm(Problem problem, String _fileConfig) {

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
		fireworks.generateRandomAndSort(individuals, this.status.stateInitial, objProblem);

		this.status.stateInitial = (Solution) this.fireworks.get(0).clone();
		this.status.stateFinal = (Solution) this.status.stateInitial.clone(); 

		/*System.out.print(" HC " + this.allNeighs);
		if (this.allNeighs.equals("NONE"))
			System.out.print(" FactNeigh=" + this.factorNeighs);
		System.out.println();*/
		fireworksAlgorithm(this.status.stateInitial);
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
	public FireworksAlgorithm(Problem problem, String _fileConfig, Solution _state) {
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

			if (nameParameters.get(i).equals("INDIVIDUALS"))
				this.individuals = Integer.parseInt(valueParameters
						.get(i)); 
			else if (nameParameters.get(i).equals("TYPEINITIAL")) 
				this.initialSolution = valueParameters.get(i);
			else if (nameParameters.get(i).equals("MCONST")) {
				this.mConst = Double.parseDouble(valueParameters.get(i));
			}
		}
	}
	
	public void fireworksAlgorithm(Solution _init) {  
		/*Tracer tr = new Tracer(50000 );*/
		this.fireworks.printFitness("POP(INI)");
		
		Population sparks = new Population( );
		while (isStopCriteria(this.objProblem)) { 
			
			for (int i = 0; i < individuals ; i++) {
				double diffv = this.diff(-1.0);
				// calculate number sparks
				int s = calculateNoSparks(i, diffv, -1.0);
//				System.out.println("..."+i+" "+s);
				for (int j=0; j< s;j++){
					Solution nextState = (Solution) fireworks.get(j).clone();
					op.swapping(nextState);
					
					if (! sparks.contains(nextState)){
						this.objProblem.evaluate(nextState);
						this.objProblem.counter.incCount();
						sparks.add(nextState);
					} else {
						j--;
					} 
				}
				// define this method
//				sparks = getNeigh(s);
			}
//			System.out.println("2 cycle " );	
			int mprima= (int) (individuals*0.5);
			for (int i = 0; i < mprima ; i++) {
				Solution nextState = (Solution) fireworks.get(i).clone();
				op.swappingRBI(nextState);
				if (! sparks.contains(nextState)){
					this.objProblem.evaluate(nextState);
					this.objProblem.counter.incCount(); 
					sparks.add(nextState);
				} else {
					i--;
				}
			}
			// review this method  
			for (int j=0;j<fireworks.size();j++){
				Solution nnState = (Solution) fireworks.get(j).clone();
				sparks.add(nnState);
			}
	 
			/*sparks.printTempoV(".s..");
			fireworks.printTempoV(". ff");*/
			
			selectFireworksRoulette(sparks);
			sparks.removeAll();
//			System.out.println(" Cycle !!!");
			/*tr.printBundle(objProblem, fireworks, true);*/
		}
		this.fireworks.printFitness("POP(FIN)");
		System.out.println( );
		this.status.stateFinal = fireworks.get(0);
	}
	
	
	public void selectFireworksRoulette(Population sparks){
		// get better
		Solution betterState = (Solution) fireworks.get(0).clone();
		Solution worseState = (Solution) fireworks.get(0).clone();
		fireworks.removeAll();
		int tt=0;
		
		for (int rr = 0; rr < sparks.size(); rr++) {
			if (sparks.get(rr).fitness > worseState.fitness) {
//				System.out.println(" "+sparks.get(rr).fitness);
				worseState =  (Solution) sparks.get(rr).clone() ;  
			} 
			
			if (sparks.get(rr).fitness < betterState.fitness) {
//				System.out.println(" "+sparks.get(rr).fitness);
				betterState =  (Solution) sparks.get(rr).clone() ; 
				tt=rr;
			} 
 
		} 
	
		sparks.removeElement(tt);
		fireworks.add((Solution) betterState.clone()) ;
//		System.out.println(fireworks.size());	
		// define roulette
		
		// to avoid division by zero
		double xi = 1;
		
		// sum all fitness from pool
		double sumf = 0;
		double temp = 0;
		 
		for (int rr = 0; rr < sparks.size(); rr++) {
			temp = (worseState.fitness - sparks.get(rr).fitness)  + xi;
//			temp = Math.pow(temp, 2);
			sumf= sumf +  temp ;
		}
//		System.out.println("sumf "+sumf);
		// get probability
		double[] prob = new double[sparks.size()];
		double sump=0;
		for (int rr = 0; rr < sparks.size(); rr++) {
			temp = (worseState.fitness - sparks.get(rr).fitness)  + xi;
//			temp = Math.pow(temp, 2);
			prob[rr]=  temp /sumf; 
			sump=sump+prob[rr];
		}
		
 
		for (int rr = 1; rr < this.individuals; rr++) {
			double sumx=0;
			double rnd= Randomizer.getDouble()*sump;
		 
			boolean c= false;
			for (int ss=0; ss < prob.length; ss++){
				if ( sumx > rnd){
					fireworks.add( (Solution) sparks.get(ss).clone());
					c=true;
					break;
				} 
				sumx=sumx+prob[ss];
			}
			
			if (! c)
				rr--;
		}
//		System.out.println(fireworks.size());
	}
	
	public double diff(double e){
		double diff=0;
		if (e!=-1.0)
			for (int u=0;u<this.individuals;u++){
				diff=diff+Math.abs(e - fireworks.get(u).fitness);		
			}
		else
			for (int u=0;u<this.individuals;u++){
				 diff=diff+Math.abs(this.maximum - fireworks.get(u).fitness);		
			}	
		return diff;
		
	}
	
	
	public int calculateNoSparks(int i, double diff, double label){
		int r=0;
		
		// this parameter avoids the division by zero
		double xi = 0.0001;
		// this parameter controls the total number of sparks gained from the n fireworks
		double m = this.mConst;
		 
		double dff;
		if (label == -1.0)
			dff=	 Math.abs(this.maximum - this.fireworks.get(i).fitness );
		else
			dff=	 Math.abs(label - this.fireworks.get(i).fitness );
				 
		double div= m*((dff +xi)/(diff+xi));		
		 
		// round solutions a < b < 1
		double a =0.3;
		double b= 0.7;
		if (div < m*a )
			r= (int) (m*a);
		else if (div > m*b)
			r= (int) (m*b);
		else 
			r= (int) (div);
		
		
		return r;
	}
}
