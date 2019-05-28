import agent.Agent;
import statistics.Analyzer;

public class Executing {

	Analyzer an = new Analyzer();
	String problem;
	int nExperiments;
	boolean analysis = false;
	boolean file = false;
	String[] configFiles; 
	String[] methods;
	
	String[] instances;
	int[] nEvalsXInst;
	
	public Executing problem(String name){
		problem = name;
		
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
	
	public Executing analysis(String filex){
		analysis = true;
		
		if (! filex.equals(""))
			file = true;
		
		return this;
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
					    .problem(problem, instances[j]);   
 				agent.init(); 
				an.add(agent.info, instances[j]);
			} 
		}
		if (analysis){
		   an.print();
		   an.stats("MIN");
		   if (file)
		      an.printFile("TEMP", "STATS");
		}
	}
}
