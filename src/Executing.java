import agent.Agent;
import statistics.Analyzer;
import statistics.MulticriteriaMethods;

/**
 * Define the structure to implement an agent with metaheuristics
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class Executing {

	Analyzer an = new Analyzer();
	MulticriteriaMethods mc;
	String problemname;
	int nExperiments;
	boolean analysis = false;
	boolean analysisMCMA = false;
	boolean file = false;
	boolean allSolution = false;
	String[] configFiles; 
	String[] methods;
	
	String[] instances;
	int[] nEvalsXInst;
	
	double[] weights;
	int[] typeCriteria; // 0 is cost, 1 is benefit
	double[][] matrixAC;
	int code; // 0 is all, 1 is topsis, 2 is vikor
	boolean noMatrix=false;
	
	public Executing problem(String name){
		problemname = name;
		
		return this;
	}
	
	
	public Executing nExperiments(int nexp){
		nExperiments = nexp;
		
		return this;
	}

	
	public Executing configFiles(String[] conf ){
		configFiles = conf;
		
		return this;
	}
	
	
	public Executing methods(String[] methodx ){
		if (methodx.length == this.configFiles.length){
		    methods = methodx;
		} else {
			String[] t = new String[this.configFiles.length];
			/*methods = methodx;*/
			for (int i = 0; i < this.configFiles.length;i++){
				t[i]=methodx[0];
			}
			methods = t;
		}
		
		return this;
	}
	 
	
	public Executing instances(String[] inst){
		instances = inst;
		
		return this;
	}
	
	
	public Executing nEvalsXInst(int[] evals ){
		if (evals.length == this.instances.length){
			nEvalsXInst = evals;
		} else {
			int[] t = new int[this.instances.length];
			/*methods = methodx;*/
			for (int i = 0; i < this.instances.length;i++){
				t[i]=evals[0];
			}
			nEvalsXInst = t;
		}
		
		return this;
	}
	
	
	public Executing analysis( ){
		analysis = true; 
		
		return this;
	}
	 
	
	public Executing analysisMCMA(double[] w, int[] tCri, int code){
		analysisMCMA = true; 
		weights = w; 
		typeCriteria = tCri; 
		code = code; 
		matrixAC =  new double[methods.length][weights.length];
		noMatrix = true; 
		
		return this;
	}
	
	
	public Executing save( ){
		file = true;
		
		return this;
	}
	
	
	public Executing printAllSolution( ){
		allSolution = true;
		
		return this;
	}
	
	
	public void calcMatrix( ){
		double media=0;
		
		if (weights.length == instances.length){
			for (int k=0; k< instances.length; k++){
				int control = k*methods.length;
				for(int i=0; i < methods.length; i++) { 
					media=an.getInfo(i+control).average(); 
					matrixAC[i][k]=media;   
				}
			}
	    
		} else if (weights.length == 2*instances.length){
			for (int k=0; k< instances.length; k++){
				int control = k*methods.length;
				for(int i=0; i < methods.length; i++) {
	    		
					for(int j=2*k;j < 2*(k+1);j++) {
						if (j%2==0) {
							media=an.getInfo(i+control).average(); 
							matrixAC[i][j]=media; 
						} else {
							media=an.getInfo(i+control).average(); 
							matrixAC[i][j]=an.getInfo(i+control).stDeviat(media);
						}
					}
				}
			}			
		}
	}
	
	
	public void run(){
		for (int j=0; j< instances.length; j++){

			for (int i=0; i< methods.length; i++){
				System.out.println("\n----------------------"+methods[i]+"\n");

				Agent agent = new Agent() 
						.metaheuristic(methods[i]) 
						.paraMetaheuristic(configFiles[i])
						.nEvals(nEvalsXInst[j])
						.nExp(nExperiments)      
					    .problem(problemname, instances[j]);   
 				agent.init(); 
 				if (allSolution)
					agent.info.printAllSolution();
				an.add(agent.info, instances[j]);
			} 
		}
		
		if (analysis){
			an.print();
			an.stats("MIN"); 
		}
		
		if (analysisMCMA){
			if (noMatrix)
				calcMatrix( );
			mc = new MulticriteriaMethods(methods.length, weights.length, matrixAC, 
				weights, typeCriteria, code);
			an.addInfo(mc.info);
		}
		
		if (file)
			an.printFile(this.problemname, "STATS");
	}
}
