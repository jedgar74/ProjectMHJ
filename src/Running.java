
import java.util.ArrayList;

import java.util.StringTokenizer;

public class Running {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//  args[0] problem name
		//  args[1] evals

		//  args[2] configFile
		//  args[3] methods
		//  args[4] instanc
		//  args[5] nEvalsXIns

		String[] methods; /*={ "ICA", "ICA", "ICA" , "ICA", "ICA" , "ICA"};*/
		String[] configFile; /*={ "ICAc", "ICA2", "ICAb", "ICA4", "ICA5" , "ICAd"}; */

		String[] instanc; /*={ "25", "50", "100", "20", "36", "40"};*/
		int[] nEvalsXIns; /*={  6250 , 25000 , 100000, 4000, 12960, 16000 }; */
		/*int t = 0;*/
		
		try{

			StringTokenizer stringT = new StringTokenizer(args[2], ","); 
			
			ArrayList<String> a = new ArrayList<String>();
			/*System.out.println(""+fileData+" "+aus);*/
			while (stringT.hasMoreTokens()) {
				a.add(stringT.nextToken()); 
				/*t++;*/
			}
			configFile = new String[a.size()];
			for (int i=0; i< a.size(); i++){
				configFile[i] = a.get(i) ;
			}

			stringT = new StringTokenizer(args[3], ","); 
			/*t=0;*/
			a = new ArrayList<String>();
			/*System.out.println(""+fileData+" "+aus);*/
			while (stringT.hasMoreTokens()) {
				a.add(stringT.nextToken()); 
				/*t++;*/
			} 
			methods = new String[a.size()];
			for (int i = 0; i < a.size(); i++){
				methods[i] = a.get(i) ;
			}
			
			stringT = new StringTokenizer(args[4], ","); 
			/*t=0;*/
			a = new ArrayList<String>();
			/*System.out.println(""+fileData+" "+aus);*/
			while (stringT.hasMoreTokens()) {
				a.add(stringT.nextToken()); 
				/*t++;*/
			} 
			instanc = new String[a.size()];
			for (int i=0; i< a.size(); i++){
				instanc[i] = a.get(i) ;
			}

			stringT = new StringTokenizer(args[5], ","); 
			/*t=0;*/
			a = new ArrayList<String>();
			/*System.out.println(""+fileData+" "+aus);*/
			while (stringT.hasMoreTokens()) {
				a.add(stringT.nextToken()); 
				/*t++;*/
			}
			nEvalsXIns = new int[a.size()];
			for (int i=0; i< a.size(); i++){
				nEvalsXIns[i] = Integer.parseInt(a.get(i));
			}

			System.out.println(""+configFile.length+" "+methods.length+" "+
					instanc.length+" "+nEvalsXIns.length+" ");
			//-----------------------------
			//-----------------------------
			Executing e = new Executing()
					.analysis("file")
					.problem(args[0])
					.nExperiments(Integer.parseInt(args[1]))
					.configFiles(configFile)
					.methods(methods)
					.instances(instanc)
					.nEvalsXInst(nEvalsXIns) ; 

			e.run();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
