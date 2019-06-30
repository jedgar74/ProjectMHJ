package landscape;

import problem.Problem;
import state.Operators;
import state.Solution;
import statistics.BasicStats;

public class LocalMinima {

	Operators op = new Operators();
	public BasicStats data = new BasicStats();
	public BasicStats optima = new BasicStats();
	Problem objProblem = new Problem();
    int min =0;
	
	public LocalMinima(Problem problem, Solution solution, int nEvals) {
		int h =0;
		Solution nextState = (Solution) solution.clone();
		Solution updState = (Solution) solution.clone();
		this.objProblem = problem;
		int inc = (int) (0.5*(solution.nVar)*(solution.nVar-1));
		/*System.out.println(".g."+solution.nVar + " "+inc );*/
        /*double optimum = solution.fitness;*/
		
		while (h < nEvals) {
			// int numberNeighs = (this.nJobs*(this.nJobs - 1))/2;
			// ArrayList<JobsSqcs> n = getAllNeighbourhood(nextState);
			nextState = op.getBestNeighbourComplete(updState, this.objProblem); 
			
			h = h + inc;
			/*System.out.println(""+data.nSolutions());*/
			/*nextState.print(""+h);*/
			if (op.best(nextState, updState, this.objProblem) ) {
				// ___System.out.println("CH "+nextState.fitnessOne+"---"+this.stateFinal.fitnessOne);
				updState = (Solution) nextState.clone();
				/*optimum = nextState.fitness;*/
			} else {
				
				if (! data.contains(updState)){
					min++;
					data.add((Solution) updState.clone());					
				}
				
				if (optima.nSolutions()==0){
					optima.add((Solution) updState.clone());
				} else if (op.best(updState, optima.getInitSolution(), this.objProblem) ){
					/*System.out.println("...a "+optima.nSolutions());*/
					optima.removeAllSolutions();
					optima.add((Solution) updState.clone());
					/*System.out.println("...b "+optima.nSolutions());*/
				} else if (updState.fitness == optima.getInitSolution().fitness){
					/*System.out.println("...2 "+optima.nSolutions());*/
					if (! optima.contains(updState)){
						/*System.out.println("...3 "+optima.nSolutions());*/
						optima.add((Solution) updState.clone());					
					}	
				}
				updState = new Solution("PERMUTATION", objProblem.nVar, "RANDOM"); 
				this.objProblem.evaluate(updState);
			} 
		} 
		
		System.out.println("Number local minima = "+min);
		System.out.println("Number global minima = "+optima.nSolutions());
	}
} 
