package statistics;

import java.util.ArrayList;

import state.Operators;
import state.Solution; 

public class BasicStats {
	ArrayList<Solution> solutions = new ArrayList<Solution>();
	String label;
	Operators op = new Operators();
	
	public void removeAllSolutions(){
	solutions.clear();	
	}
	
	public boolean contains(Solution s){
		boolean r =false;
		 
		for (int t=0;t < this.solutions.size(); t++){ 
			if (op.equals(s, this.solutions.get(t))) {
				r = true;
				break;
			}
		}

		return r;
	}
	
	public Solution getInitSolution (){
		return solutions.get(0);
	}
	
	
	public double getSolution (int i){
		return solutions.get(i).fitness;
	}
	
	public Solution getSolutionComplete (int i){
		return solutions.get(i);
	}
	
	public int nSolutions (){
		return solutions.size();
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void add(Solution s){
		solutions.add(s);
	}
	
	public double getBetter() {
		int b = this.better();
		return this.solutions.get(b).fitness;
	}
	
	public double getNBetter() {
		double q=solutions.get(0).fitness;
		int k =1;
		for(int i=1; i< solutions.size(); i++){
			if (solutions.get(i).fitness < q){
				k=1;
				q=solutions.get(i).fitness;
			} else if (solutions.get(i).fitness == q){
				k++;
			}
		}	
		return k;
	}


	public void print(){
		System.out.println(":: Metaheuristic :: "+label +"  ");
		System.out.println(":: N. Experiment.:: "+solutions.size());
		System.out.println("-----------------------------------");
		int q = better();
		System.out.println(":: Better fitness :: "+ solutions.get(q).fitness  );
		double ave = this.average();
		System.out.println(":: Average        :: "+ ave  ); 
		System.out.println(":: St. Deviation  :: "+ stDeviat(ave) );
		
		System.out.println(":: Better Solution in the experiment :: "+(q+1));
		solutions.get(q).print();
	}
	
	
	public String toString(){
		String r ="";
		
		r = r+":: Metaheuristic :: "+label+ "\n";
		r = r+":: N. Experiment.:: "+solutions.size()+"\n";
		r = r+"-----------------------------------"+"\n";
		int q = better();
		r = r+":: Better fitness :: "+ solutions.get(q).fitness +"\n";
		double ave = this.average();
		r = r+":: Average        :: "+ ave  +"\n"; 
		r = r+":: St. Deviation  :: "+ stDeviat(ave) +"\n";
		
		r = r+":: Better Solution in the experiment :: "+(q+1)+"\n";
		r = r+"-----------------------------------"+"\n";
		r = r + solutions.get(q).toString()+"\n";
		r = r+"-----------------------------------"+"\n";
		int c = 0;
		for (int i=0; i< solutions.size(); i++){
			int cc = r.length();
			r = r+ solutions.get(i).fitness+ " ";
			c= c + (r.length() -cc);
			if (c >=59){
					//i%12==11)
				r = r + "\n";
				c = 0;
			}
		}
		return r;
	}
	
	public void printAllSolution(){
		for (int i=0; i< solutions.size(); i++){
			System.out.print(solutions.get(i).fitness+ " ");
		}
		System.out.println();
	}

	public void printAllInfoSolution(){
		for (int i=0; i< solutions.size(); i++){
			solutions.get(i).print();
		}
	}
	/**
	 *  revise to max problems
	 * @return
	 */
	public int better(){
		double q=solutions.get(0).fitness;
		int k =0;
		for(int i=1; i< solutions.size(); i++){
			if (solutions.get(i).fitness < q){
				k=i;
				q=solutions.get(i).fitness;
			}		
		}	
		return k;
	}
	
	public double average(){
		double q=0;
		for(int i=0; i< solutions.size(); i++){
			q= q + solutions.get(i).fitness;
		}	
		return q/solutions.size();
	}
	
	public double stDeviat(double media){
		double q=0;
		for(int i=0; i< solutions.size(); i++){
			q= q + Math.pow(solutions.get(i).fitness - media,2);
		}	
		return Math.sqrt(q/solutions.size());
	}
	
	/*public void printAllSolutions(){
		for(int i=0; i< solutions.size(); i++){
			q= q + Math.pow(solutions.get(i).fitness - media,2);
		}
	}*/
}
