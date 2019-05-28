package state; 

import java.util.ArrayList;

import problem.Problem;

/**
 * Define a population of solutions
 * 
 * @author Jhon Edgar Amaya
 */
public class Population implements Cloneable{

	public int populationSize = 0; 
	public ArrayList<Solution> population = new ArrayList<Solution>();

	
	/**
	 * constructor
	 * 
	 */
	public Population(){

	}
 

	/**
	 * return true if solution is contained in population
	 * 
	 * @param solution
	 * @return
	 */
	public boolean contains(Solution solution){

		return this.population.contains(solution);
	}


	/**
	 * return true if solution is contained in population
	 * 
	 * @param solution
	 * @param op
	 * @return
	 */
	public boolean containsStrict(Solution solution, Operators op ){
		boolean flag = false;

		for (int t = 0; t < this.populationSize; t++){

			if (op.equals(solution, this.population.get(t)) ) {
				flag = true;
				break;
			}
			/*int k=0;
			 			while ( k < solution.nVar){        
			 				if (  ((OnlyInteger) this.population.get(t).var.allvar[k]).getValue()
			 						!=  ((OnlyInteger) solution.var.allvar[k]).getValue()){ 
			 					break;
			 				}
			 				k++;
			 			}
			 			if (k == solution.nVar){
			 				r=true;
			 				break;
			 			}*/
		}

		this.population.contains(solution);
		
		return flag;
	}
	  
	
	/**
	 * Sort array by means of bubble sort
	 * 
	 * @param _typeFitness
	 */
	public void sort(){
		int size = population.size();

		while(size > 0){ 
			int newSize = 0;
			
			for (int i = 1; i < size; i++){ 
				
				if ( population.get(i - 1).fitness > population.get(i).fitness){
					Solution aux = (Solution) population.get(i).clone();
					population.set(i, (Solution) population.get(i-1).clone());
					population.set(i - 1, aux);
					newSize = i;				         
				}
			}
			
			size = newSize; 
		} 
	}
 
	
	/**
	 * get the population size
	 * 
	 * @return
	 */
	public int size(){ 
		return this.populationSize;
	}

	
	/**
	 * get i-th element in population
	 * 
	 * @param i
	 * @return
	 */
	public Solution get(int i){
		return (Solution) this.population.get(i).clone();
	}

	
	/**
	 * get first element
	 * 
	 * @return
	 */
	public Solution getFirstElement(){
		return (Solution) this.population.get(0).clone();
	}

	
	/**
	 * get better solution
	 * 
	 * @param problem
	 * @return
	 */
	public Solution getBetter(Problem problem){
		int index = 0;
		double fitness = this.population.get(0).fitness;

		for (int c = 1; c < this.populationSize; c++){
			
			if (problem.typeProblem.equals("MIN") && fitness > this.population.get(c).fitness){
				fitness = this.population.get(c).fitness;
				index = c;
			} else if (problem.typeProblem.equals("MAX") && fitness < this.population.get(c).fitness){
				fitness = this.population.get(c).fitness;
				index = c;
			}
		}

		return (Solution) this.population.get(index).clone();
	}


	/**
	 * add list of solutions
	 * 
	 * @param sol
	 */
	public void addSolutions(ArrayList<Solution> sol) {
		int pSize = this.populationSize;

		for (int s = 0; s < sol.size(); s++) {
			boolean isBetter = false;

			for (int t = 0; t < pSize; t++) {
				if (sol.get(s).fitness <  getFitness(t)) {
					this.population.add(t, sol.get(s));  
					isBetter = true;
					break;
				}
			}
			if ( isBetter ) {
				/*System.out.println(" "+pSize+" "+this.population.size());*/
				this.population.remove(pSize);
			}
		}
	}


	/**
	 * add solution as sort way
	 * 
	 * @param solution
	 */
	public void addSort(Solution solution){
		boolean isBetter = false;
		
		for (int t = 0; t < population.size(); t++) {
			if (solution.fitness <  getFitness(t)) {
				this.population.add(t, solution);  
				isBetter = true;
				break;
			}
		}
		
		if (! isBetter)
			this.add(solution); 
	}

	
	/**
	 * add solution as sort way if it is only better solution
	 * 
	 * @param solution
	 * @return
	 */
	public boolean addSortBetter(Solution solution){
		boolean isBetter = false;
		
		if (population.size() == 0){
			this.population.add(solution);
			isBetter = true;
		} else {
			for (int t = 0; t < population.size(); t++) {
				if (solution.fitness <  getFitness(t)) {
					this.population.add(t, solution);   
					isBetter = true;
					break;
				}
			}
		}
		
		return isBetter;
	}
 
	
	/**
	 * add solution in the position index
	 * 
	 * @param index
	 * @param solution
	 */
	public void add(int index, Solution solution){
		this.population.add(index,solution);
		this.populationSize = this.population.size();
	}

	
	/**
	 * add solution in last position
	 * 
	 * @param solution
	 */
	public void add(Solution solution){
		this.population.add(solution);
		this.populationSize = this.population.size();
	}

	
	/**
	 * set i-th element     
	 * 
	 * @param index
	 * @param solution
	 */
	public void setElement(int index, Solution solution){
		this.population.add(index, solution);
		this.population.remove(index + 1);
	}

	
	/**
	 * remove the i-th element
	 * 
	 * @param i
	 */
	public void removeElement(int i){
		this.population.remove(i);
		this.populationSize = this.population.size();
	}

	
	/**
	 * remove all elements
	 */
	public void removeAll(){
		this.population.clear();
		this.populationSize = this.population.size();
	}

	
	/**
	 * remove last element
	 */
	public void removeLastElement(){
		this.population.remove(this.population.size() - 1);
		this.populationSize = this.population.size();
	}

	
	/**
	 * replace all elements
	 * 
	 * @param _population
	 */
	public void replace(ArrayList<Solution> _population){
		this.population = _population;
		this.populationSize = this.population.size();
	}

	
	/**
	 * get fitness of i-th individual
	 * 
	 * @param _i
	 * @return
	 */
	public double getFitness(int i){
		return population.get(i).fitness;
	}

	
	/**
	 * add solution if it is better that the worst solution and remove worst solution
	 * 
	 * @param solution
	 * @param op
	 * @param problem
	 */
	public void update(Solution solution, Operators op, Problem problem) {

		if (! population.contains(solution)) {
			boolean flag = true;
			int i = 0;
			
			while (i < population.size()) {
				if (op.best(solution, population.get(i), problem)) {
					population.add(i, solution);
					flag = false;
					break;
				}
				i++;
			}
			
			if (! flag)
				this.removeLastElement();
		}
	}

	
	/**
	 * Generate a population sorted
	 * 
	 * @param nIndividuals
	 * @param solution
	 * @param problem
	 */
	public void generateRandomAndSort(int nIndividuals, Solution solution, Problem problem) {
		int k;

		if (solution != null) {
			k = 1;
			population.add((Solution) solution.clone());
		} else {
			k = 0;
		}

		for (int i = k; i < nIndividuals; i++) {
			Solution tempSol = new Solution(problem.typeState, problem.nVar, "RANDOM");

			if (! population.contains(tempSol)) {
				problem.evaluate(tempSol); 
				problem.counter.incCount();

				boolean flag = true;
				int p = 0;
				while (p < population.size()) {
					if (problem.typeProblem.equals("MIN") && tempSol.fitness < getFitness(p)) {
						population.add(p, tempSol);
						flag = false;
						break;
					} else if (problem.typeProblem.equals("MAX") && tempSol.fitness > getFitness(p)) {
						population.add(p, tempSol);
						flag = false;
						break;
					}
					p++;
				}
				if (flag)
					population.add(tempSol); 
			}
		}

		this.populationSize = this.population.size(); 
	}


	/**
	 * Generate a population sorted
	 * 
	 */  
	public void generateRandom(int nIndividuals, Solution solution, Problem problem) {
		int k;

		if (solution != null) {
			k = 1;
			population.add((Solution) solution.clone());
		} else {
			k = 0;
		}

		for (int i = k; i < nIndividuals; i++) {
			Solution tempSol = new Solution(problem.typeState, problem.nVar, "RANDOM");

			if (! population.contains(tempSol)) {
				problem.evaluate(tempSol); 
				problem.counter.incCount();

				boolean flag = true;
				int p = 0;
				while (p < population.size()) {
					if (tempSol.fitness < getFitness(p)) {
						population.add(p, tempSol);
						flag = false;
						break;
					}
					p++;
				}
				if (flag)
					population.add(tempSol); 
			}
		}

		this.populationSize = this.population.size(); 
	}


	/**
	 * swap elements 
	 * 
	 * @param i
	 * @param j
	 */
	public void swapElements(int i, int j){
		Solution aux = (Solution) population.get(i).clone();
		population.set(i, (Solution) population.get(j).clone());
		population.set(j, aux); 
	}

	
	/**
	 * print all elements in populations
	 */
	public void printAllElements(){
		
		for (int i = 0; i < population.size(); i++) {
			this.population.get(i).print(); 
		}
	}

   
	/**
	 * Print the fitness of all invididuals (without decimals) as a only String
	 * 
	 * @param label
	 */
	public void printFitness(String label) { 
		System.out.print("--> " + label + " ");
		
		for (int i = 0; i < population.size(); i++) {
			System.out.print("" + (int) population.get(i).fitness + ".");

		}
		System.out.println();
	}


	/**
	 * sum of all fitness of population
	 * 
	 * @return
	 */
	public double totalFitness(){
		double sumF = 0;

		for (int i = 0; i < this.population.size(); i++){
			sumF = sumF + this.population.get(i).fitness; 
		}

		return sumF;
	}
	 

	/**
	 * sum of all inverse fitness of population
	 * 
	 * @return
	 */
	public double totalInverseFitness(){
		double sumF = 0;

		for (int i = 0; i < this.population.size(); i++){
			sumF = sumF + 1/this.population.get(i).fitness; 
		}

		return sumF; 
	}
	

	/**
	 * sum of all inverse fitness of population
	 * 
	 * @return
	 */
	public double totalInverseFitness(double d){
		double sumF = 0;

		for (int i = 0; i < this.population.size(); i++){
			sumF = sumF + 1/(this.population.get(i).fitness*d); 
		}

		return sumF; 
	}
	
	
	/**
	 * sum of inverse rankings
	 * 
	 * @return
	 */
	public double sumInverseRankings(){
		double sumR = 0;
		
		for (int i = 0; i < this.population.size(); i++){
			sumR = sumR + (1/(i+1)); 
		}
		
		return sumR;
	}

	
	/**
	 * Clone a object
	 */
	@SuppressWarnings("unchecked")
	public Object clone() {
		Object obj = null;
		
		try {
			obj = (Population) super.clone();

		} catch (CloneNotSupportedException ex) {
			System.out.println("Imposible duplicate object!!!");
		}

		((Population) obj).populationSize = ((Population) obj).size();
		((Population) obj).population = (ArrayList<Solution>) ((Population) obj).population.clone();

		return obj;
	}


	/* public void printOneLine(){
		System.out.print("");
		for (int i = 0; i < population.size(); i++) {
			System.out.print(":"+this.population.get(i).fitnessOne);
		}
		System.out.println("");
	}*/
}