package algorithm;

import java.util.ArrayList;

import problem.Problem;
import state.Operators;
import state.Population;
import state.Solution;
import util.Randomizer;

/**
 * Imperialist Competitive Algorithm for the Flowshop Problem
Gabriela Minetti  and Carolina Salto
 * 
 * @author Jhon Amaya
 *
 */
public class ImperialistCompetitiveAlgorithm extends Heuristic {

	String shortTerm = "ICA";
	String initialSolution = "RANDOM"; 

	int nCountries = 0;
	int nImperials = 6;
	int nColonies = 0;
	double revolRate=0.15;
	double beta =2;
	double gamma=0.02;
	double zeta=0.02;
	
	
	String movement = "SWAPPING";
	String moveRev = "SWAPPING";
	
	Population countries = new Population();

	public ImperialistCompetitiveAlgorithm(Problem problem, String _fileConfig) {

		this.objProblem = problem;
		this.readParameters(_fileConfig, this.shortTerm);
		this.setParameters(problem); 

		// Solution _state = new Solution(objProblem.nVar,
		// objProblem.typeInitialSolution);
		/*
		 * this.stateFinal = new Solution(objProblem.nVar,
		 * objProblem.typeInitialSolution); this.stateFinal.copy(_state);
		 */

		this.status.stateInitial = new Solution(problem.typeState, objProblem.nVar, initialSolution);
		problem.evaluate(this.status.stateInitial);
		problem.counter.incCount();
		//		this.population = generatingPopulation(_state);
		/*	population.generateRandomSortFOne(individuals, this.status.stateInitial, objProblem);

		this.status.stateInitial = (Solution) this.population.get(0).clone();*/
		this.status.stateFinal = (Solution) this.status.stateInitial.clone(); 

		imperialistCompetitiveAlgorithm (this.status.stateInitial); 
	}


	public ImperialistCompetitiveAlgorithm(Problem problem, String _fileConfig, String shortT) {

		this.objProblem = problem;
		this.readParameters(_fileConfig, shortT);
		this.setParameters(problem); 

		// Solution _state = new Solution(objProblem.nVar,
		// objProblem.typeInitialSolution);
		/*
		 * this.stateFinal = new Solution(objProblem.nVar,
		 * objProblem.typeInitialSolution); this.stateFinal.copy(_state);
		 */

		this.status.stateInitial = new Solution(problem.typeState, objProblem.nVar, initialSolution);
		problem.evaluate(this.status.stateInitial);
		problem.counter.incCount();
		//		this.population = generatingPopulation(_state);
		/*population.generateRandomSortFOne(individuals, this.status.stateInitial, objProblem);

		this.status.stateInitial = (Solution) this.population.get(0).clone();*/
		this.status.stateFinal = (Solution) this.status.stateInitial.clone(); 

	}


	public ImperialistCompetitiveAlgorithm(Problem problem, String _fileConfig, Solution solution) {

		this.objProblem = problem;
		this.readParameters(_fileConfig, this.shortTerm);
		this.setParameters(problem); 

		//		this.population = generatingPopulation(solution);
		/*population.generateRandomSortFOne(individuals, solution, objProblem);

		solution = (Solution) this.population.get(0).clone();*/
		this.status.stateFinal = (Solution) solution.clone();

	}

	public ImperialistCompetitiveAlgorithm(){

	}


	public void setParameters(Problem _problem) {
		for (int i = 0; i < nameParameters.size(); i++) {

			if (nameParameters.get(i).equals("NCOUNTRIES")) {
				this.nCountries = Integer.parseInt(valueParameters
						.get(i));
			} else if (nameParameters.get(i).equals("NIMPERIAL")) {
				this.nImperials = Integer.parseInt(valueParameters
						.get(i));
			}else if (nameParameters.get(i).equals("REVRATE")) {
				this.revolRate = Double.parseDouble(valueParameters
						.get(i));
			} else if (nameParameters.get(i).equals("BETA")) {
				this.beta = Double.parseDouble(valueParameters
						.get(i));
			} else if (nameParameters.get(i).equals("GAMMA")) {
				this.gamma = Double.parseDouble(valueParameters
						.get(i));
			} else if (nameParameters.get(i).equals("ZETA")) {
				this.zeta = Double.parseDouble(valueParameters
						.get(i));
			} else if (nameParameters.get(i).equals("MOVEMENT")) {
				this.movement =  valueParameters.get(i) ;
			} else if (nameParameters.get(i).equals("REVMOVEMENT")) {
			    this.moveRev =  valueParameters.get(i) ;
			} 
		}
	}

	public void imperialistCompetitiveAlgorithm(Solution _init) { 
		/*Tracer tr = new Tracer(500000 );*/

		countries.generateRandomAndSort(this.nCountries, _init, objProblem);

		/*countries.printTempoV("");*/
		Empires empires = new Empires(this.nImperials);
		empires.setEmpires(countries);


		//		double sigma = Maths.sigma();

		while (isStopCriteria(this.objProblem) || this.nImperials==1) {
			//			System.out.println(".......IN");
			empires.moveColonies(countries, this.objProblem, this.movement, this.gamma); 
			empires.revColonies(countries, this.objProblem, this.moveRev, this.gamma, this.revolRate); 
			empires.convertColEmp(countries);
			//			System.out.println(".......I2");
			empires.costAll(countries, zeta);
			empires.competition(countries, this.objProblem.nVar);

			empires.collapse();
			// get better solution
			this.status.stateFinal = this.countries.get(empires.getBetter(countries));
			/*tr.printBundle(objProblem, countries, true); */
			//			tr.printPopulationLarge(objProblem, countries, 3000000);
			/*System.out.println("\n......."+this.objProblem.counter.count);
			countries.printTempoV("---");
			empires.print();*/
		} 
	} 

	
}




/**
 * Class Empire 
 * 
 * @author Jhon Amaya
 *
 */
class Empires { 

	ArrayList<String> empires;
	ArrayList<String> cost ;
	ArrayList<Colonies> ecolonies =  new ArrayList<Colonies>();
	int nIm = 0;  
	Operators op = new Operators();
	double uv = 0; // for levy flights
	
	Empires(int nImp){
		nIm=nImp;
		empires=new ArrayList<String> (nImp);  
		cost=new ArrayList<String> (nImp);
	}

	void setEmpires(Population countries){
		for (int i=0; i<nIm;i++){
			empires.add(""+i);
			Colonies c = new Colonies();
			ecolonies.add(c);
		}
		for (int i=nIm; i< countries.populationSize;i++){
			int a = Randomizer.getInt(nIm);
			ecolonies.get(a) .addValue(""+i);
		}

	}

	void costAll( Population countries, double zeta){
		for (int i=0; i<nIm;i++){
			cost.add(""+cost(i, countries, zeta));
		}
	}

	double cost(int e, Population countries, double zeta){
		double cost = 0;
		double prom = aveCol(e, countries);
		cost = countries.getFitness(Integer.parseInt(empires.get(e))) + zeta*prom;

		return cost;
	}

	double aveCol(int e, Population countries ){
		int nc = ecolonies.get(e).ncolonies;
		double cost =0;

		for (int i=0; i< nc ;i++){
			cost = cost + countries.getFitness( ecolonies.get(e).getValue( i) );
		}

		return cost;
	}

	int isColonyBetter(int e, Population countries){
		int val = -1;
		int nc = ecolonies.get(e).ncolonies;
		double imp =countries.getFitness(Integer.parseInt(empires.get(e)));

		for (int i=0; i< nc ;i++){
			if (imp >countries.getFitness(ecolonies.get(e).getValue(i))){
				val= ecolonies.get(e).getValue(i);
				imp =countries.getFitness(ecolonies.get(e).getValue(i));
			} 
		}	
		return val;
	}

	void convertColEmp(Population countries){
		for (int i=0; i < nIm;i++){
			int j=isColonyBetter(i , countries);
			if (j != -1){
				ecolonies.get(i).change(j, Integer.parseInt(empires.get(i)) );
				empires.set(i, ""+j);  
			}
		}
	}

	double getWorstCol(int e, Population countries ){
		double val = 0;
		int nc = ecolonies.get(e).ncolonies; 

		for (int i=0; i< nc ;i++){
			if (val < countries.getFitness(ecolonies.get(e).getValue(i))){
				val= countries.getFitness(ecolonies.get(e).getValue(i)); 
			} 
		}	
		return val;
	}

	int getBetter ( Population countries ){
		int val = Integer.parseInt(empires.get(0)); 
		double imp = countries.getFitness(Integer.parseInt(empires.get(0)));

		for (int i=1; i< nIm ;i++){
			if (imp >countries.getFitness(Integer.parseInt(empires.get(i)))){
				val= Integer.parseInt(empires.get(i));
				imp =countries.getFitness(Integer.parseInt(empires.get(i)));
			} 
		}
		return val;
	}

	void competition(Population countries, int nVar){
		int emp = -1;
		int posEmp = -1;
		int col = -1;
		double val = 0;
		for (int i=0; i < nIm; i++){
			for (int d=0; d < ecolonies.get(i).ncolonies; d++){
				double imp = countries.getFitness(ecolonies.get(i).getValue(d));
				if (imp > val){
					val = imp;
					emp = Integer.parseInt(empires.get(i));
					col = ecolonies.get(i).getValue(d);
					posEmp=i;
				}
			}
		}

		// compute the normalized costs
		double max = 0; 
		double sum =0;
		double[] ncost = new double[nIm];
		for (int i=0; i < nIm; i++){
			ncost[i] = Double.parseDouble(cost.get(i));
			//			ncost[i] = getWorstCol( i,  countries )- ncost[i];
			if (ncost[i]>max){
				max=ncost[i];
			}
			sum=sum+ncost[i];
		}

		double[] probIm = new double[nIm];
		int better = -1;
		double suf = 0;
		for (int i=0; i < nIm; i++){
			probIm[i]= Randomizer.getDouble()*(max - ncost[i])/sum;
			if (probIm[i] > suf){
				suf = probIm[i];
				better = i; 
			}
		}

		if (posEmp != better && nIm > 1){
			ecolonies.get(better).addValue(""+col);
			ecolonies.get(posEmp).delValue(""+col);
		}
		
		
	}

	public void revColonies(Population countries, Problem objProblem, String move, double gamma, double pRev){
		for (int k = 0; k < nIm ; k++) {
			Solution z = (Solution) countries.get(Integer.parseInt(empires.get(k))).clone() ;
			int nc = ecolonies.get(k).ncolonies;
			//			System.out.println(""+k+" "+nc);
			for (int c = 0; c < nc ; c++) {

				if (Randomizer.getDouble() <= pRev){
					/*Solution m = new Solution(problem.typeState, objProblem.nVar, "RANDOM");*/
					//new Solution(objProblem.nVar, "RANDOM");

					Solution m = (Solution) countries.get(ecolonies.get(k).getValue(c)).clone() ;
/*m.print("rev b"); 
System.out.println(""+m.check());*/
					/*m.swappingLevyFlight((int) x, objProblem.nVar);*/ 
					/*op.swappingLevyFlight(m, (int) x, objProblem.nVar);*/
 
					
					/*if (move.equals("SWAPPING")){
						op.swapping(m);
					} else if (move.equals("DIFFERENTIAL")){
						double x = op.distance(m, z);
						x= x* gamma;
						if (x <2) 
							x=2; 
						op.swappingKNeigh(m, (int) x);
					} else if (move.equals("LEVY")){
						double[] param = new double[]{3/2, m.fitness};
					    op.swappingLevyFlight(m, z, param);
					} else if (move.equals("BLOCKING")){
						op.swappingRBI(m);
					} else if (move.equals("APX"))
						op.crossoverAlternatingPosition(m, z); 
					else if (move.equals("U2X"))
						op.crossoverUniformModified(m, z);
					else if (move.equals("PBX"))
						op.crossoverPositionBased(m, z);
					else if (move.equals("PMX"))
						op.crossoverPartiallyMatched(m, z); 
					else if (move.equals("OX")) 
						op.crossoverOrder(m, z);
					else if (move.equals("CX"))
						op.crossoverCycle(m, z);*/
					movement( m, z, move, gamma);
					/*m.print("rev a");		
					System.out.println(""+m.check());*/
					
					objProblem.evaluate(m);
					objProblem.counter.incCount();
					if (op.best(m, countries.get(ecolonies.get(k).getValue(c)), objProblem))
					      countries.setElement(ecolonies.get(k).getValue(c), m);

				}
			}
		}
	}
	
	public void collapse(){
	 
		int i=0;
		while ( i < this.empires.size()){
			
			int nc = ecolonies.get(i).ncolonies;
			if (nc ==0){
				String em = this.empires.get(i);
				/*System.out.println("..."+this.empires.size());*/
				this.empires.remove(i);
				this.cost.remove(i);
				this.ecolonies.remove(i);
				nIm--;
				double m = 10000000;

				/*System.out.println("..."+this.empires.size()+" "+this.cost.size());*/
				int jr=0;
				for (int j=0; j < this.empires.size(); j++ ){
					double tm = Double.parseDouble(cost.get(j));
					if (tm < m){
						jr =j;
						m= tm;
					}
				} 
 
				ecolonies.get(jr).addValue(""+em);

			} else {
				i++;
			}
		}
	}

	public void moveColonies(Population countries, Problem objProblem, String move, double gamma){
		for (int k = 0; k < nIm ; k++) {
			Solution z = (Solution) countries.get(Integer.parseInt(empires.get(k))).clone() ;
			int nc = ecolonies.get(k).ncolonies;
			//			System.out.println(""+k+" "+nc);
			for (int c = 0; c < nc ; c++) {
				/*Solution m = new Solution(problem.typeState, objProblem.nVar, "RANDOM");*/

				Solution m = (Solution) countries.get(ecolonies.get(k).getValue(c)).clone() ;
				/*m.print("mov b");
				System.out.println(""+m.check());*/
				// review this method
				//___ double x = m.distance(z);
				//___ x= x* gamma;
				//___ if (x <2) 
				//___ 	x=2; 
				/*System.out.println(" "+gamma+" "+op.distance(m, z));*/
				/*m.swapDifferancial(gamma, z);*/
				/*op.swappingDiff(m, gamma, z);*/
			    /* */ 
				
				movement( m, z, move, gamma);
				/*m.print("mov a");
				System.out.println(""+m.check());*/
				//___m.swappingLevyFlight((int) x, objProblem.nVar);
				objProblem.evaluate(m);
				objProblem.counter.incCount();
				if (op.best(m, countries.get(ecolonies.get(k).getValue(c)), objProblem)) 
				     countries.setElement(ecolonies.get(k).getValue(c), m); 
				 
			}
		}
	}
	
	public void print(){
		for (int e = 0; e < empires.size() ; e++) {
			System.out.print (""+empires.get(e)+"[");
			for (int c = 0; c < ecolonies.get(e).ncolonies ; c++) {
				System.out.print(""+ecolonies.get(e).getValue(c) );
				if (c != ecolonies.get(e).ncolonies -1)
					System.out.print (" ");
			}
			System.out.print ("]  ");
		}
		System.out.println();
	}
	
	public void movement(Solution m, Solution z, String move, double gamma){
		
		 /*if (move.equals("DIFFERENTIAL")){
			int dis = Randomizer.getInt(op.distance(m, z));
			if (dis> m.nVar -1)
				dis= m.nVar -1; 
			op.swappingKNeigh(m, dis);
	    else if (move.equals("DIFFERENTIAL")){
			double x = op.distance(m, z);
			/*x= x* gamma;
			if (x <2) 
				x=2; 
			else if (x > m.nVar)
				x=m.nVar; 
			op.swappingKNeigh(m, (int) x);
		} */ 
		
		if (move.equals("SWAPPING")) {
			op.swapping(m);
		} else if (move.equals("DIFFERENTIAL")) { 
			int x = Randomizer.getInt(op.distance(m, z)-1); 
			x=x+2;
			if (x > m.nVar)
				x=m.nVar; 
			op.swappingKNeigh(m, (int) x);
		} else if (move.equals("LEVY")) {
			double[] param = new double[]{3/2, m.fitness};
		    op.swappingLevyFlight(m, z, param);
		} else if (move.equals("BLOCKING")) {
			op.swappingRBI(m);
		} else if (move.equals("APX")) {
			op.crossoverAlternatingPosition(m, z); 
		} else if (move.equals("U2X")) {
			op.crossoverUniformModified(m, z);
		} else if (move.equals("PBX")) {
			op.crossoverPositionBased(m, z);
		} else if (move.equals("PMX")) {
			op.crossoverPartiallyMatched(m, z); 
		} else if (move.equals("OX")) {
			op.crossoverOrder(m, z);
		} else if (move.equals("CX")) {
			op.crossoverCycle(m, z);
		}
	}
	
}


/**
 * Class colonies
 * 
 * @author Jhon Amaya
 *
 */
class Colonies{
	ArrayList<String>  colonies;
	int ncolonies=0;

	Colonies( ){
		colonies=new ArrayList<String> ( );  
	}

	void addValue(String a){
		colonies.add(a);
		ncolonies=colonies.size();
	}

	void delValue(String a){
		for (int i=0; i < this.ncolonies; i++){
			if (colonies.get(i).equals(a)){
				colonies.remove(i);
				break;
			}
		}
		ncolonies=colonies.size();
	}

	int getValue(int i){
		return Integer.parseInt( colonies.get(i));
	}

	void change (int orig, int nue){
		for (int i=0; i < ncolonies;i++){
			if (Integer.parseInt(colonies.get(i))==orig){
				colonies.set(i, ""+nue);
			}
		}
	}

}

