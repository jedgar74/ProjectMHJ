package statistics;

import java.util.ArrayList;

import problem.Problem;

public class Tracker {
	ArrayList<String> evaluations = new ArrayList<String>();
	ArrayList<String> fitness = new ArrayList<String>();
	String status = "BYIMPROVEMENT";
	int interval;
	int counter;
	double better;
	
	public Tracker(){ 
		this.status =  "BYIMPROVEMENT";
		interval = -1; 
	}

	
	public Tracker( int evals){ 
		this.status =  "BYRANGE";
		interval = evals; 
	}
	
	
	public void add(double fitnessv, Problem problem){ 
		 
		if (this.status.equals("BYIMPROVEMENT")){
			if (problem.counter.count==1 || this.fitness.size() ==0 ){
				this.fitness.add(""+fitnessv);
				this.evaluations.add(""+problem.counter.count);
				better = fitnessv;
			} else if (problem.typeProblem.equals("MIN") && fitnessv < better){
				this.fitness.add(""+fitnessv);
				this.evaluations.add(""+problem.counter.count);
				better = fitnessv;
			} else if (problem.typeProblem.equals("MAX") && fitnessv > better){
				this.fitness.add(""+fitnessv);
				this.evaluations.add(""+problem.counter.count);
				better = fitnessv;
			}
		} else if (this.status.equals("BYRANGE")){
			if (problem.counter.count==1 || this.fitness.size() ==0 ){
				this.fitness.add(""+fitnessv);
				this.evaluations.add(""+problem.counter.count);
				counter = interval + problem.counter.count;
			} else if (problem.counter.count >= counter){
				this.fitness.add(""+fitnessv);
				this.evaluations.add(""+problem.counter.count);
				counter = interval + problem.counter.count;
			}

		} 
	}

	
	public void print(){
		System.out.println("STATUS: "+this.status);

		for (int i = 0; i < evaluations.size(); i++) {
			System.out.println("POINT = "+this.fitness.get(i)+ " ["+this.evaluations.get(i)+"]");
		}
	}
}
