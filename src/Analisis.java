import statistics.MulticriteriaMethods;
import statistics.FriedmanImanHolm;
import util.Matrix;

public class Analisis {
	
	static int n=4;
	static int m=6; //methods
	static String type="MAX";
	
	/*
	* 
	* name: Analisis.main
	* @param
	* @return
	* 
	*/
	public static void main(String[] args) {
		//leer archivo de datos 
		/*
		double[][] matrixAC =  new double[m][n];
		String[] labels = new String[m];  
		*/ 
		//double[][] matrixAC = { { 8, 7, 2, 1  }, { 5, 3, 7, 5 }, {7, 5, 6, 4 }, {
		//	9, 9, 7, 3 }, {11, 10, 3, 7 }, {6, 9, 5, 4} }; 
		//String[] labels= { "a", "b", "c", "d" , "g", "MINn"};
		
		double[][] matrixAC = { { 150, 12, 12, 2 }, { 100, 16, 8, 4  }, {200, 32, 16,3 }, {
			175, 24, 8, 3 }, {125, 16,16, 5 } }; 
		String[] labels= { "a", "b", "c", "d" , "e"};
		
		Matrix values = new Matrix(matrixAC.length, matrixAC[0].length) ;
		//values.printlnAll(2);
		values.setMatrix(matrixAC);
		values.trasponse();
		values.printlnAll(4);
		FriedmanImanHolm f = new FriedmanImanHolm(3); 
		f.setLabels(labels);
		f.setValues(values);
		if (type.equals("MIN")){
			f.setGraph(true);
			//f.minProblem();
			String info = f.getInfo();
			//System.out.println(info); 
		} else if (type.equals("MAX")){
			f.setGraph(true);
			//f.maxProblem();
			String info = f.getInfo();
			//System.out.println(info); 
		}

		int code = 0; 
		/*double[] weights = new double[n];
	    int[] typeCriteria = new int[n]; */ 
	    //double[] weights= { 0.4, 0.3, 0.1, 0.2 }; 
	    //int[] typeCriteria= {1, 1, 1, 1};
		
		double[] weights= { 0.35, 0.25, 0.25, 0.15 }; 
	    int[] typeCriteria= {0, 1, 1, 0};
     
	    MulticriteriaMethods mc = new MulticriteriaMethods(5, 4, 
			matrixAC, weights, typeCriteria );
		mc.setVFactor(-1);
		mc.run(code);	

	}
}
