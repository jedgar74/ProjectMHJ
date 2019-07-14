package statistics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

import util.Matrix;

import graph.BoxPlotGraph;

/*import sun.awt.SunHints.Value;*/
/**
 * Tiene como entrada una matrix 
 * 
 * - Algor1 Algor2 ... AlgorK
 * 1 x x ... x
 * 2 x x ... x
 * ...
 * N x x ... x
 * 
 * Where N represent the number of experiments y K represent the algorithms
 * 
 * @author Tauger
 *
 */
public class FriedmanImanHolm {
	String nameFile;
	int nTopAlgorithms=3;
	Vector<String> labels = new Vector<String>();
    String info;

	Matrix values;
	
	boolean graph =false;

	public FriedmanImanHolm() {
		super(); 
	} 
	
	public FriedmanImanHolm(int nTopAlgorithms) {
		super();
		this.nTopAlgorithms = nTopAlgorithms;
	} 

	public void minProblem(){
		try{
			System.out.println("\n*** Jhon Edgar Amaya ***"); 

			Calendar dateAndTime = new GregorianCalendar();
			nameFile =dateAndTime.get(Calendar.DAY_OF_MONTH)+"_"
					+(dateAndTime.get(Calendar.MONTH)+1)+"_"
					+dateAndTime.get(Calendar.YEAR)+"_"
					+dateAndTime.get(Calendar.HOUR_OF_DAY)+"_"
					+dateAndTime.get(Calendar.MINUTE)+".txt";
					 
			
			/*StatisticsII stats = new StatisticsII();*/
			//Read File
			/*File auxFile = new File(".", nameFile);
			FileReader input = new FileReader(auxFile);
			char buffer[] = new char[1];

			String tmp = "";
			Vector<String> lines = new Vector<String>();            
			for (int charsRead = input.read(buffer); charsRead != -1; charsRead = input.read(buffer)) {

				char caracter = buffer[0];

				if (caracter == '\n') {
					lines.addElement(tmp);
					tmp = "";				
				} else				
					tmp = new StringBuffer(String.valueOf(tmp)).append(caracter).toString();
			}				

			input.close();*/

			/*Vector<String> labels = new Vector<String>();
			int k = 0; 
			StringTokenizer tokens=new StringTokenizer(lines.elementAt(0));
			while(tokens.hasMoreTokens()){
				if (k != 0){
					labels.addElement(tokens.nextToken());
				} else {
					String rse = tokens.nextToken();
				}	
				//System.out.println(tokens.nextToken());
				k++;
			}

			//--System.out.println("-------------------------------- "+k);

			int K = k - 1; //indicate number algorithm
			int N = lines.size() - 1; //indicate number experiments
*/
			
			int K = this.values.getCol();
			int N = this.values.getRow();
					
			System.out.println("***********************************************");
			System.out.println("******* ALG : "+K+ " --- INST : "+N+" *****");
			System.out.println("***********************************************");

			info = info + "\nAlgorithms : "+K+ " --- Instances : "+N+"\n";
			info = info + "-----------------------------------\n";
			//Create matrix values
			/*Matrix values = new Matrix(N, K);
			StringTokenizer tokens2;
			for (int j = 1; j < lines.size(); j++){
				tokens2 = new StringTokenizer(lines.elementAt(j));
				int g = 0;
				while(tokens2.hasMoreTokens()){
					String r =  tokens2.nextToken();
					if (r.indexOf(",") != -1)
						r= r.replaceAll(",", ".");
					if (g != 0){
						values.setMatrix(j-1, g-1, Double.parseDouble(r));
					}					
					g++; 
				}
			}*/

			//values.printlnAll();

			//Calculate Rank
			Matrix ranks = new Matrix(N, K);
			Vector<String> exx = new Vector<String>();
			Vector<String> ddd = new Vector<String>();
			for (int j = 0; j < values.getRow(); j++){
				//for (int j = 0; j < 1; j++){
				for (int g = 0; g < values.getCol(); g++){
					exx.addElement(""+g);
				}

				double ranking = 1;

				while(exx.size() != 0){
					//double min = 1000000.0;
					double min = 1e40;

					for (int g = 0; g < exx.size(); g++){
						/*double tmpx = values.getMatrix(j, Integer.parseInt(exx.elementAt(g)));*/
						double tmpx = values.getElement(j, Integer.parseInt(exx.elementAt(g)));
 
						if (tmpx < min){
							ddd.removeAllElements();
							min = tmpx;
							ddd.addElement(exx.elementAt(g));
						} else if (tmpx == min){
							ddd.addElement(exx.elementAt(g));
						}
					}
					double rankTemp = (ddd.size()+ 2*ranking - 1)/2;
					//System.out.println(""+ranking+"---"+ddd.size()+"..."+rankTemp);
					ranking=ranking + ddd.size();

					for (int g = 0; g < ddd.size(); g++){
						/*ranks.setMatrix(j, Integer.parseInt(ddd.elementAt(g)), rankTemp);*/
						ranks.setElement(j, Integer.parseInt(ddd.elementAt(g)), rankTemp);
					}

					for (int g = 0; g < ddd.size(); g++){
						int rs = exx.indexOf(ddd.elementAt(g));
						exx.removeElementAt(rs);						
						//ranks.setMatrix(j, Integer.parseInt(ddd.elementAt(g)), rankTemp);
					}

				}				

			}

			System.out.println();
			System.out.println("***********************************************");
			System.out.println("*** RANKS IN THE MATRIX ***********************");
			System.out.println("***********************************************");

			
			info = info + "\nRanks Matrix   \n";
			info = info + "-----------------------------------\n";
			// print matrix of ranks
			genMatrix(ranks, nameFile, "Rank_", labels);  /// Esto es nuevo!!!!!!!!!!!!!!!
			ranks.printlnAll();
			info = info + ranks.toString();

			//Calculate Friedman
			double Friedman = calculateFriedmanX( ranks, N, K);

			//Calculate Iman 
			double Iman = calculateIman(Friedman, N, K);

			Vector<String> labeee = new Vector<String>();
			for (int g = 0; g < labels.size(); g++){
				labeee.addElement(labels.elementAt(g));
			}
			//Calculate Holm
			calculateHolm(ranks, N, K, labeee);

 
			//____________________________________________________

			Vector<String> exxR = new Vector<String>();
			Vector<String> dddR = new Vector<String>();
			Vector<String> labess = new Vector<String>();

			for (int j = 0; j < labels.size(); j++){
				labess.addElement(labels.elementAt(j));
			}	

			for (int j = 0; j < ranks.getCol(); j++){
				dddR.addElement(""+j);
				double dff = 0.0;
				for (int g = 0; g < ranks.getRow(); g++){
					/*dff = dff + ranks.getMatrix(g, j);*/
					dff = dff + ranks.getElement(g, j);
				}
				exxR.addElement(""+dff);
			}

			//ordenar
			for (int j = 0; j < dddR.size() ; j++){
				double minm = Double.parseDouble(exxR.elementAt(j));
				for (int g = 0; g < dddR.size(); g++){

					if (minm < Double.parseDouble(exxR.elementAt(g))){
						String trxx = labess.elementAt(j);
						String trxy = labess.elementAt(g);
						labess.setElementAt(trxy, j);
						labess.setElementAt(trxx, g);
						String trxx2 = dddR.elementAt(j);
						String trxy2 = dddR.elementAt(g);
						dddR.setElementAt(trxy2, j);
						dddR.setElementAt(trxx2, g);
						String trxx3 = exxR.elementAt(j);
						String trxy3 = exxR.elementAt(g);
						exxR.setElementAt(trxy3, j);
						exxR.setElementAt(trxx3, g);
						minm = Double.parseDouble(exxR.elementAt(g));
					}
				}

			}

			for (int j = 0 ; j < dddR.size(); j++){
				System.out.println("alg = "+ dddR.elementAt(j)+" - "+ this.roundersII(Double.parseDouble(exxR.elementAt(j)), 6)
				     + " - "+ labess.elementAt(j));

			}

			int K2 = nTopAlgorithms; //indicate number algorithm
			/*int N2 = lines.size() - 1;*/ //indicate number experiments
			int N2 = this.values.getRow();
			//Create matrix values
			Matrix values2 = new Matrix(N2, K2);
			//values2.printlnAll();

			//llenar values2
			for (int j = 0; j < K2; j++){
				for (int g = 0; g < N2; g++){
					/*values2.setMatrix(g, j, values.getMatrix(g, Integer.parseInt(dddR.elementAt(j))));*/
					values2.setElement(g, j, values.getElement(g, Integer.parseInt(dddR.elementAt(j))));
				}
			}


			//Calculate Rank
			Matrix ranks2 = new Matrix(N2, K2);
			Vector<String> exx2 = new Vector<String>();
			Vector<String> ddd2 = new Vector<String>();
			for (int j = 0; j < values2.getRow(); j++){
				//for (int j = 0; j < 1; j++){
				for (int g = 0; g < values2.getCol(); g++){
					exx2.addElement(""+g);
				}

				double ranking = 1;

				while(exx2.size() != 0){
					double min = 1000000.0;

					for (int g = 0; g < exx2.size(); g++){
						/*double tmpx = values2.getMatrix(j, Integer.parseInt(exx2.elementAt(g)));*/
						double tmpx = values2.getElement(j, Integer.parseInt(exx2.elementAt(g))); 
						if (tmpx < min){
							ddd2.removeAllElements();
							min = tmpx;
							ddd2.addElement(exx2.elementAt(g));
						} else if (tmpx == min){
							ddd2.addElement(exx2.elementAt(g));
						}
					}
					double rankTemp = (ddd2.size()+ 2*ranking - 1)/2;
					//System.out.println(""+ranking+"---"+ddd.size()+"..."+rankTemp);
					ranking=ranking + ddd2.size();

					for (int g = 0; g < ddd2.size(); g++){
						/*ranks2.setMatrix(j, Integer.parseInt(ddd2.elementAt(g)), rankTemp);*/
						ranks2.setElement(j, Integer.parseInt(ddd2.elementAt(g)), rankTemp);
					}

					for (int g = 0; g < ddd2.size(); g++){
						int rs = exx2.indexOf(ddd2.elementAt(g));
						exx2.removeElementAt(rs);						
						//ranks.setMatrix(j, Integer.parseInt(ddd.elementAt(g)), rankTemp);
					}

				}				

			} 
			System.out.println();
			System.out.println("***********************************************");
			System.out.println("*** VALUES IN THE MATRIX **********************");
			System.out.println("***********************************************");
			
			info = info + "\nTop Algorithms "+this.nTopAlgorithms+"\n";
			info = info + "-----------------------------------";
			values2.printlnAll();
			System.out.println();
			System.out.println("***********************************************");
			System.out.println("*** RANKS IN THE MATRIX ***********************");
			System.out.println("***********************************************");
			info = info + "\nRanks Matrix   \n";
			info = info + "-----------------------------------\n";

			if (labess.size() > nTopAlgorithms){				
				while (labess.size() > nTopAlgorithms){
					labess.removeElementAt(labess.size()-1);
				}				
			} 

			genMatrix(ranks2, nameFile, "RankTop_", labess);  /// Esto es nuevo!!!!!!!!!!!!!!!
			ranks2.printlnAll();
			info = info + ranks2.toString();
			//Calculate Friedman
			double Friedman2 = calculateFriedmanX (ranks2, N2, K2);


			//Calculate Iman 

			double Iman2 = calculateIman(Friedman2, N2, K2);

			//Calculate Holm

			calculateHolm(ranks2, N2, K2, labess);

			
			if (graph){
				BoxPlotGraph demo = new BoxPlotGraph("", ranks.getMatrix(), labels);
				demo.pack();
				/* RefineryUtilities.centerFrameOnScreen(demo);*/
				demo.setVisible(true);
				
				BoxPlotGraph demf = new BoxPlotGraph("", ranks2.getMatrix(), labess);
				demf.pack();
				/* RefineryUtilities.centerFrameOnScreen(demo);*/
				demf.setVisible(true);
				
			}


			/*
			 * I include methods to generate boxplot in Matlab. First, sort columns 
			 * for median and media. Second, generate the files *.m     
			 */

			genFilesMatlab( "Rank_", ranks, labels);
			genFilesMatlab( "RankTop_", ranks2, labess);


		} catch(Exception e){
			e.printStackTrace();
			System.out.println("java FriedmanIman _file _top");
		}
	}
 

	public void maxProblem(){
		try{
			System.out.println("\n*** Jhon Edgar Amaya ***"); 

			Calendar dateAndTime = new GregorianCalendar();
			nameFile =dateAndTime.get(Calendar.DAY_OF_MONTH)+"_"
					+(dateAndTime.get(Calendar.MONTH)+1)+"_"
					+dateAndTime.get(Calendar.YEAR)+"_"
					+dateAndTime.get(Calendar.HOUR_OF_DAY)+"_"
					+dateAndTime.get(Calendar.MINUTE)+".txt";
					 
			
			/*StatisticsII stats = new StatisticsII();*/
			//Read File
			/*File auxFile = new File(".", nameFile);
			FileReader input = new FileReader(auxFile);
			char buffer[] = new char[1];

			String tmp = "";
			Vector<String> lines = new Vector<String>();            
			for (int charsRead = input.read(buffer); charsRead != -1; charsRead = input.read(buffer)) {

				char caracter = buffer[0];

				if (caracter == '\n') {
					lines.addElement(tmp);
					tmp = "";				
				} else				
					tmp = new StringBuffer(String.valueOf(tmp)).append(caracter).toString();
			}				

			input.close();*/

			/*Vector<String> labels = new Vector<String>();
			int k = 0; 
			StringTokenizer tokens=new StringTokenizer(lines.elementAt(0));
			while(tokens.hasMoreTokens()){
				if (k != 0){
					labels.addElement(tokens.nextToken());
				} else {
					String rse = tokens.nextToken();
				}	
				//System.out.println(tokens.nextToken());
				k++;
			}

			//--System.out.println("-------------------------------- "+k);

			int K = k - 1; //indicate number algorithm
			int N = lines.size() - 1; //indicate number experiments
*/
			
			int K = this.values.getCol();
			int N = this.values.getRow();
					
			System.out.println("***********************************************");
			System.out.println("******* ALG : "+K+ " --- INST : "+N+" *****");
			System.out.println("***********************************************");

			info = info + "\nAlgorithms : "+K+ " --- Instances : "+N+"\n";
			info = info + "-----------------------------------\n";
			//Create matrix values
			/*Matrix values = new Matrix(N, K);
			StringTokenizer tokens2;
			for (int j = 1; j < lines.size(); j++){
				tokens2 = new StringTokenizer(lines.elementAt(j));
				int g = 0;
				while(tokens2.hasMoreTokens()){
					String r =  tokens2.nextToken();
					if (r.indexOf(",") != -1)
						r= r.replaceAll(",", ".");
					if (g != 0){
						values.setMatrix(j-1, g-1, Double.parseDouble(r));
					}					
					g++; 
				}
			}*/

			//values.printlnAll();

			//Calculate Rank
			Matrix ranks = new Matrix(N, K);
			Vector<String> exx = new Vector<String>();
			Vector<String> ddd = new Vector<String>();
			for (int j = 0; j < values.getRow(); j++){
				//for (int j = 0; j < 1; j++){
				for (int g = 0; g < values.getCol(); g++){
					exx.addElement(""+g);
				}

				double ranking = 1;

				while(exx.size() != 0){
					//double min = 1000000.0;
 					double max = 1e-40; 

					for (int g = 0; g < exx.size(); g++){
						/*double tmpx = values.getMatrix(j, Integer.parseInt(exx.elementAt(g)));*/
						double tmpx = values.getElement(j, Integer.parseInt(exx.elementAt(g)));
 
						if (tmpx > max){
							ddd.removeAllElements();
							max = tmpx;
							ddd.addElement(exx.elementAt(g));
						} else if (tmpx == max){
							ddd.addElement(exx.elementAt(g));
						}
					}
					double rankTemp = (ddd.size()+ 2*ranking - 1)/2;
					//System.out.println(""+ranking+"---"+ddd.size()+"..."+rankTemp);
					ranking=ranking + ddd.size();

					for (int g = 0; g < ddd.size(); g++){
						/*ranks.setMatrix(j, Integer.parseInt(ddd.elementAt(g)), rankTemp);*/
						ranks.setElement(j, Integer.parseInt(ddd.elementAt(g)), rankTemp);
					}

					for (int g = 0; g < ddd.size(); g++){
						int rs = exx.indexOf(ddd.elementAt(g));
						exx.removeElementAt(rs);						
						//ranks.setMatrix(j, Integer.parseInt(ddd.elementAt(g)), rankTemp);
					}

				}				

			}

			System.out.println();
			System.out.println("***********************************************");
			System.out.println("*** RANKS IN THE MATRIX ***********************");
			System.out.println("***********************************************");

			
			info = info + "\nRanks Matrix   \n";
			info = info + "-----------------------------------\n";
			// print matrix of ranks
			genMatrix(ranks, nameFile, "Rank_", labels);  /// Esto es nuevo!!!!!!!!!!!!!!!
			ranks.printlnAll();
			info = info + ranks.toString();

			//Calculate Friedman
			double Friedman = calculateFriedmanX( ranks, N, K);

			//Calculate Iman 
			double Iman = calculateIman(Friedman, N, K);

			Vector<String> labeee = new Vector<String>();
			for (int g = 0; g < labels.size(); g++){
				labeee.addElement(labels.elementAt(g));
			}
			//Calculate Holm
			calculateHolm(ranks, N, K, labeee);

 
			//____________________________________________________

			Vector<String> exxR = new Vector<String>();
			Vector<String> dddR = new Vector<String>();
			Vector<String> labess = new Vector<String>();

			for (int j = 0; j < labels.size(); j++){
				labess.addElement(labels.elementAt(j));
			}	

			for (int j = 0; j < ranks.getCol(); j++){
				dddR.addElement(""+j);
				double dff = 0.0;
				for (int g = 0; g < ranks.getRow(); g++){
					/*dff = dff + ranks.getMatrix(g, j);*/
					dff = dff + ranks.getElement(g, j);
				}
				exxR.addElement(""+dff);
			}

			//ordenar
			for (int j = 0; j < dddR.size() ; j++){
				double minm = Double.parseDouble(exxR.elementAt(j));
				for (int g = 0; g < dddR.size(); g++){

					if (minm < Double.parseDouble(exxR.elementAt(g))){
						String trxx = labess.elementAt(j);
						String trxy = labess.elementAt(g);
						labess.setElementAt(trxy, j);
						labess.setElementAt(trxx, g);
						String trxx2 = dddR.elementAt(j);
						String trxy2 = dddR.elementAt(g);
						dddR.setElementAt(trxy2, j);
						dddR.setElementAt(trxx2, g);
						String trxx3 = exxR.elementAt(j);
						String trxy3 = exxR.elementAt(g);
						exxR.setElementAt(trxy3, j);
						exxR.setElementAt(trxx3, g);
						minm = Double.parseDouble(exxR.elementAt(g));
					}
				}

			}

			for (int j = 0 ; j < dddR.size(); j++){
				System.out.println("alg = "+ dddR.elementAt(j)+" - "+ this.roundersII(Double.parseDouble(exxR.elementAt(j)), 6)
				     + " - "+ labess.elementAt(j));

			}

			int K2 = nTopAlgorithms; //indicate number algorithm
			/*int N2 = lines.size() - 1;*/ //indicate number experiments
			int N2 = this.values.getRow();
			//Create matrix values
			Matrix values2 = new Matrix(N2, K2);
			//values2.printlnAll();

			//llenar values2
			for (int j = 0; j < K2; j++){
				for (int g = 0; g < N2; g++){
					/*values2.setMatrix(g, j, values.getMatrix(g, Integer.parseInt(dddR.elementAt(j))));*/
					values2.setElement(g, j, values.getElement(g, Integer.parseInt(dddR.elementAt(j))));
				}
			}


			//Calculate Rank
			Matrix ranks2 = new Matrix(N2, K2);
			Vector<String> exx2 = new Vector<String>();
			Vector<String> ddd2 = new Vector<String>();
			for (int j = 0; j < values2.getRow(); j++){
				//for (int j = 0; j < 1; j++){
				for (int g = 0; g < values2.getCol(); g++){
					exx2.addElement(""+g);
				}

				double ranking = 1;

				while(exx2.size() != 0){
					double max = 1e-40; 

					for (int g = 0; g < exx2.size(); g++){
						/*double tmpx = values2.getMatrix(j, Integer.parseInt(exx2.elementAt(g)));*/
						double tmpx = values2.getElement(j, Integer.parseInt(exx2.elementAt(g))); 
						if (tmpx > max){
							ddd2.removeAllElements();
							max = tmpx;
							ddd2.addElement(exx2.elementAt(g));
						} else if (tmpx == max){
							ddd2.addElement(exx2.elementAt(g));
						}
					}
					double rankTemp = (ddd2.size()+ 2*ranking - 1)/2;
					//System.out.println(""+ranking+"---"+ddd.size()+"..."+rankTemp);
					ranking=ranking + ddd2.size();

					for (int g = 0; g < ddd2.size(); g++){
						/*ranks2.setMatrix(j, Integer.parseInt(ddd2.elementAt(g)), rankTemp);*/
						ranks2.setElement(j, Integer.parseInt(ddd2.elementAt(g)), rankTemp);
					}

					for (int g = 0; g < ddd2.size(); g++){
						int rs = exx2.indexOf(ddd2.elementAt(g));
						exx2.removeElementAt(rs);						
						//ranks.setMatrix(j, Integer.parseInt(ddd.elementAt(g)), rankTemp);
					}

				}				

			} 
			System.out.println();
			System.out.println("***********************************************");
			System.out.println("*** VALUES IN THE MATRIX **********************");
			System.out.println("***********************************************");
			
			info = info + "\nTop Algorithms "+this.nTopAlgorithms+"\n";
			info = info + "-----------------------------------";
			values2.printlnAll();
			System.out.println();
			System.out.println("***********************************************");
			System.out.println("*** RANKS IN THE MATRIX ***********************");
			System.out.println("***********************************************");
			info = info + "\nRanks Matrix   \n";
			info = info + "-----------------------------------\n";

			if (labess.size() > nTopAlgorithms){				
				while (labess.size() > nTopAlgorithms){
					labess.removeElementAt(labess.size()-1);
				}				
			} 

			genMatrix(ranks2, nameFile, "RankTop_", labess);  /// Esto es nuevo!!!!!!!!!!!!!!!
			ranks2.printlnAll();
			info = info + ranks2.toString();
			//Calculate Friedman
			double Friedman2 = calculateFriedmanX (ranks2, N2, K2);


			//Calculate Iman 

			double Iman2 = calculateIman(Friedman2, N2, K2);

			//Calculate Holm

			calculateHolm(ranks2, N2, K2, labess);

			
			if (graph){
				BoxPlotGraph demo = new BoxPlotGraph("BOX PLOT", ranks.getMatrix(), labels);
				demo.pack();
				/* RefineryUtilities.centerFrameOnScreen(demo);*/
				demo.setVisible(true);
				
				BoxPlotGraph demf = new BoxPlotGraph("BOX PLOT [TOP]", ranks2.getMatrix(), labess);
				demf.pack();
				/* RefineryUtilities.centerFrameOnScreen(demo);*/
				demf.setVisible(true);
				
			}


			/*
			 * I include methods to generate boxplot in Matlab. First, sort columns 
			 * for median and media. Second, generate the files *.m     
			 */

			genFilesMatlab( "Rank_", ranks, labels);
			genFilesMatlab( "RankTop_", ranks2, labess);


		} catch(Exception e){
			e.printStackTrace();
			System.out.println("java FriedmanIman _file _top");
		}
	}

	public  void genFilesMatlab( String prefix, Matrix ranks, Vector<String> labels){

		try { 
					
			File auxFile = new File("./matlab", prefix+""+nameFile);
			FileReader input = new FileReader(auxFile);
			char buffer[] = new char[1];

			String tmp = "";
			Vector<String> lines = new Vector<String>();            
			for (int charsRead = input.read(buffer); charsRead != -1; charsRead = input.read(buffer)) {

				char caracter = buffer[0];

				if (caracter == '\n') {
					lines.addElement(tmp);
					tmp = "";				
				} else				
					tmp = new StringBuffer(String.valueOf(tmp)).append(caracter).toString();
			}				

			input.close(); 

			genFilesM( prefix, sortMatrix(nameFile, prefix, ranks, labels));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	public  String sortMatrix(String nameFile, String prefix, Matrix ranks, Vector<String> labels){
		String s = "n ";

		double[] media = new double[ranks.getCol()];
		double[] median = new double[ranks.getCol()];
		System.out.println("******** "+ranks.getRow() +" <---> "+ranks.getCol());
		for(int i= 0; i < ranks.getCol(); i++){

			double[] tmp = new double[ranks.getRow()];
			Vector<String> v = new Vector<String>();
			double son = 0;
			for(int j= 0; j < ranks.getRow(); j++){
				/*tmp[j]=ranks.getMatrix(j, i);
				v.addElement(""+ranks.getMatrix(j, i));*/
				tmp[j]=ranks.getElement(j, i);
				v.addElement(""+ranks.getElement(j, i));
				son = son + tmp[j];
			}
			media[i] =	son / ranks.getRow(); 

			//sort ascent-order  
			for(int j= 0; j < ranks.getRow()-1; j++){
				for(int f = j+1; f < ranks.getRow(); f++){
					if (tmp[f] < tmp[j]){
						double tms = tmp[j];
						double tm2 = tmp[f];
						tmp[f] = tms;
						tmp[j] = tm2;
					}
				}	
			}


			//Comparator comparator = Collections.reverseOrder();
			//Collections.sort(v,comparator);
			//for(int j= 0; j < ranks.getRow(); j++){
			//	tmp[j]=Double.parseDouble((String) v.elementAt(ranks.getRow() - 1 - j));
			//}

			int middle = tmp.length/2;  // subscript of middle element
			if (tmp.length%2 == 1) { 
				median[i] =  tmp[middle];
			} else {
				median[i] = (tmp[middle-1] + tmp[middle]) / 2.0;
			}
			System.out.println(labels.elementAt(i)+ "--- "+ this.roundersII(median[i],6)  +
					" ::: "+ this.roundersII(media[i],6)); 
		}

		/*
		 * Sort columns
		 */
		for(int k = 0; k < ranks.getCol()-1; k++){
			for(int l = k+1; l < ranks.getCol(); l++){
				if (median[l] < median[k]){
					String one = (String) labels.elementAt(k);
					String two = (String) labels.elementAt(l);
					labels.setElementAt(one, l);
					labels.setElementAt(two, k);

					double tms = median[k];
					double tm2 = median[l];
					median[k] = tm2;
					median[l] = tms;
					tms = media[k];
					tm2 = media[l];
					media[k] = tm2;
					media[l] = tms;

					for(int j= 0; j < ranks.getRow(); j++){
						/*double sin = ranks.getMatrix(j, l);
						double cos = ranks.getMatrix(j, k);
						ranks.setMatrix(j, l, cos);
						ranks.setMatrix(j, k, sin);	*/
						double sin = ranks.getElement(j, l);
						double cos = ranks.getElement(j, k);
						ranks.setElement(j, l, cos);
						ranks.setElement(j, k, sin);						
					}
				} else if (median[k] == median[l] && media[l] < media[k] ){
					String one = (String) labels.elementAt(k);
					String two = (String) labels.elementAt(l);
					labels.setElementAt(one, l);
					labels.setElementAt(two, k);

					double tms = median[k];
					double tm2 = median[l];
					median[k] = tm2;
					median[l] = tms;
					tms = media[k];
					tm2 = media[l];
					media[k] = tm2;
					media[l] = tms;

					for(int j= 0; j < ranks.getRow(); j++){
						/*double sin = ranks.getMatrix(j, l);
						double cos = ranks.getMatrix(j, k);
						ranks.setMatrix(j, l, cos);
						ranks.setMatrix(j, k, sin);*/		
						double sin = ranks.getElement(j, l);
						double cos = ranks.getElement(j, k);
						ranks.setElement(j, l, cos);
						ranks.setElement(j, k, sin);
					}

				} 
			}
		}


		try {
			genMatrix(ranks, nameFile, prefix, labels);
			for(int i = 0; i < labels.size(); i++){
				s = s + " " + labels.elementAt(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s; 
	}


	public  void genFilesM( String prefix, String names){
		String auxiliar = prefix+""+nameFile;
		auxiliar = auxiliar.substring(0, auxiliar.length()-4)+ ".m";

		File auxFile2 = new File("./matlab", auxiliar);
		FileWriter input2;
		try {
			/*String tmp = "";*/
			Vector<String> lines2 = new Vector<String>(); 
			Scanner sc = new Scanner (names);
			while (sc.hasNext()) {
				lines2.addElement(sc.next());
			}

			input2 = new FileWriter(auxFile2);
			String lineOne = "[labels,x,y] = readColData('"+prefix+""+nameFile+"', "+lines2.size()+", 0, 1)";
			input2.write(lineOne+"\n");

			String namess = ""; //de la forma '4 ciclos','5 ciclos','6 ciclos'

			for (int g = 0; g < lines2.size(); g++){
				if (g != 0){
					/*if (g == 1){
						namess = "'"+lines2.elementAt(g)+"',"; 	
					} else */
					if (g == lines2.size()-1){
						namess = namess + "'"+lines2.elementAt(g)+"'";
					} else {
						namess = namess + "'"+lines2.elementAt(g)+"',";
					}
				}	
			}

			String lineTwo = "boxplot(y, 'orientation', 'horizontal', 'labels',{" + namess + "})";
			input2.write(lineTwo+"\n");
			String lineThr = "xlabel('rank')";
			input2.write(lineThr+"\n");
			input2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	public  double calculateFriedman(Matrix values2, Matrix ranks2, int N, int K){
		double Friedman2 = 0.0;
		System.out.println("N = "+N+ " --- "+"K = "+K);
		info = info + "\nAlgorithms : "+K+ " --- Instances : "+N+"\n";
		info = info + "-----------------------------------\n";
		
		for (int g = 0; g < values2.getCol(); g++){
			double media = 0.0;
			for (int j = 0; j < values2.getRow(); j++){
				/*media=media+ ranks2.getMatrix(j, g);*/
				media=media+ ranks2.getElement(j, g);
			}
			media=media/values2.getRow();
			Friedman2=Friedman2+(media*media);
		}
		//System.out.println("Friedman = "+Friedman);
		Friedman2 = Friedman2 - (K*(K+1)*(K+1)/4);//revisar
		//System.out.println("Friedman = "+Friedman);
		Friedman2 = Friedman2*12*N/(K*(K+1));
		return Friedman2;
	}

	
	@SuppressWarnings("static-access")
	public  double calculateFriedmanX(Matrix ranks2, int N, int K){
		double Friedman2 = 0.0;
		Statistics stats = new Statistics();
		double beta = (K+1)/2;
		System.out.println("***********************************************");
		System.out.println("*** FRIEDMAN TEST *****************************");
		System.out.println("***********************************************");
		System.out.println("N = "+N+ " --- "+"K = "+K);
		System.out.println("Beta = "+this.roundersII(beta, 6));
		info = info + "\nFriedman Test\n";
		info = info + "-----------------------------------\n";
		
		for (int g = 0; g < ranks2.getCol(); g++){
			double media = 0.0;
			for (int j = 0; j < ranks2.getRow(); j++){
				media=media+ ranks2.getElement(j, g);
			}
			media=media/ranks2.getRow();
			System.out.println("media [alg "+g+ "] - "+ this.roundersII(media,6));

			media=media - beta;
			Friedman2=Friedman2+(media*media);
		}
		//System.out.println("Friedman = "+Friedman);
		Friedman2 = Friedman2*6*N/(K*beta);
		System.out.println("***********************************************");
		System.out.println("Friedman value = "+this.roundersII(Friedman2 , 6));
		System.out.println("prob Chi-square function = "+this.roundersII(stats.chiSquaredProbability(Friedman2, K-1), 6));
		System.out.println("Calculate with degree = "+(K-1)+ " and obtain critical value with one sided ");
		System.out.println("***********************************************");
		System.out.println();
		
		info = info + "Friedman value = "+ this.roundersII(Friedman2 , 6)+"\n";
		info = info + "prob Chi-square function = "+ this.roundersII(stats.chiSquaredProbability(Friedman2, K-1), 6) +"\n";
		info = info + "Calculate with degree = "+(K-1)+ " and obtain critical value with one sided "+"\n";
		return Friedman2;
	}


	@SuppressWarnings("static-access")
	public   void calculateHolm(Matrix ranks2, int N, int K, Vector<String> labess){
	 
		Statistics stats = new Statistics();
		double beta = (double) K*(K+1)/(6*N);
		//System.out.println("beta "+beta);
		beta=Math.sqrt(beta);
		double alfa = 0.05;
		Vector<String> ss = new Vector<String>();
		System.out.println();
		System.out.println("***********************************************");
		System.out.println("************ z Statistics *********************");
		System.out.println("***********************************************");
		System.out.println("N = "+N+ " --- "+"K = "+K);
		System.out.println("beta = "+this.roundersII(beta, 6));
		info = info + "\nHolm Test\n";
		info = info + "-----------------------------------\n";
		for (int g = 0; g < ranks2.getCol(); g++){
			double media = 0.0;
			for (int j = 0; j < ranks2.getRow(); j++){
				media=media+ ranks2.getElement(j, g);
			}
			media=media/ranks2.getRow();
			System.out.println("media [alg "+g+ "] - "+this.roundersII(media, 6));

			ss.addElement(""+media);
		}
		//System.out.println("Friedman = "+Friedman);


		//ordenar
		for (int j = 0; j < ss.size() ; j++){
			double minm = Double.parseDouble(ss.elementAt(j));
			for (int g = 0; g < ss.size(); g++){

				if (minm < Double.parseDouble(ss.elementAt(g))){
					String trxx = labess.elementAt(j);
					String trxy = labess.elementAt(g);
					labess.setElementAt(trxy, j);
					labess.setElementAt(trxx, g);
					String trxx2 = ss.elementAt(j);
					String trxy2 = ss.elementAt(g);
					ss.setElementAt(trxy2, j);
					ss.setElementAt(trxx2, g);
					minm = Double.parseDouble(ss.elementAt(g));
				}
			}

		}
		double leader = Double.parseDouble(ss.elementAt(0));
		System.out.println();
		System.out.println("***********************************************");
		System.out.println("*** <tenor p value> if tenor > value then there is different ***");
		System.out.println("***********************************************");
		System.out.println("CONTROL "+ labess.elementAt(0));
		info = info + "Control Algorithm "+labess.elementAt(0)+"\n";
		info = info + "[tenor p value] if tenor > value then there is different (see @ \n\tsymbol)\n";
		info = info + "-----------------------------------\n";
		for (int j = 1 ; j < ss.size(); j++){
			double ttp = ((Double.parseDouble(ss.elementAt(j)) - leader)/beta);
			double ptenor = (alfa/(K-(K-j)));
			double pp = (1-stats.normalProbability(ttp)) ;
			
			
			info = info + j+ " " + labess.elementAt(j)+" ::: "+ this.roundersII(ttp, 6) + " - ";  
			info = info + this.roundersII(ptenor, 6) + " p " + this.roundersII(pp, 6);
			
			if (ptenor > pp){
				System.out.println(j+ " "+ labess.elementAt(j)+" ::: "+ this.roundersII(ttp, 6) + " - "+  
						this.roundersII(ptenor, 6) + " p "+ this.roundersII(pp, 6)+ " @");
				info = info +" @\n";
			} else{
				System.out.println(j+ " "+ labess.elementAt(j)+" ::: "+ this.roundersII(ttp, 6) + " - "+  
						this.roundersII(ptenor, 6) + " p "+ this.roundersII(pp, 6));
				info = info +"\n";
			}
		}
		System.out.println("***********************************************");
		System.out.println();
	}


	@SuppressWarnings("static-access")
	public  double calculateIman(double Friedman, int N, int K){
		System.out.println("***********************************************");
		System.out.println("*** IMAN DAVENPORT TEST ***********************");
		System.out.println("***********************************************");
		
		info = info + "\nIman Davenport Test\n";
		info = info + "-----------------------------------\n";
		
		Statistics stats = new Statistics();
		double ttm = (N-1)*Friedman;
		double ttd = N*(K-1) - Friedman;
		int Nmin = N-1;
		int Kmin = K-1;
		System.out.println("Iman-Davenport  = "+this.roundersII((ttm /ttd), 6));
		info = info + "Iman-Davenport  = "+ this.roundersII((ttm /ttd), 6) +"\n"; 
		try{
			System.out.println("prob F-Function  = "+this.roundersII(stats.FProbability(ttm /ttd, Kmin, Kmin*Nmin),6));
			info = info + "prob F-Function  = "+this.roundersII(stats.FProbability(ttm /ttd, Kmin, Kmin*Nmin),6) +"\n"; 
		} catch (Exception e) {
			System.out.println("Error in computing prob F-Function");
			info = info + "Error in computing prob F-Function\n"; 
		}
		System.out.println("Calculate with degree = "+(K-1)+ " X "+(K-1)*(N-1)+" and obtain critical value with one sided ");		
		System.out.println("***********************************************");
		System.out.println();
		info = info + "Calculate with degree = "+(K-1)+ " X "+(K-1)*(N-1)+" and obtain critical value with one \n\tsided " +"\n"; 
		return ttm /ttd;
	}


	public  void genMatrix(Matrix ranks, String nameFile2, String prefix, Vector<String> labels) throws IOException{
		File auxFile2 = new File("./matlab", prefix+""+nameFile2);
		FileWriter input2 = new FileWriter(auxFile2);

		String s = "n ";
		for(int i = 0; i < labels.size(); i++){
			s = s + " " + labels.elementAt(i);
		}
		input2.write(s+"\n");

		for(int i = 0; i < ranks.getRow(); i++){
			String r = ""+(i+1)+" ";
			for(int j = 0; j < ranks.getCol(); j++){
				r = r + " " + roundersII(ranks.getElement(i, j), 4);
				//System.out.print(" "+this.matrix[i][j]);
			}	
			input2.write(r+"\n");
		}
		input2.close();
	}


	public  String roundersII(double _d, int decimal){

		double presc = Math.pow(10, decimal);
		//System.out.println();
		double tmp = _d * presc;
		tmp = (double) Math.round(tmp) / presc;

		//System.out.println(""+ _d+ " "+ presc + " :: "+tmp);
		return ""+tmp;
	}
	
	
	public Vector<String> getLabels() {
		return labels;
	}


	public void setLabels(Vector<String> labels) {
		this.labels = labels;
	}


	public void setLabels(String[] labels) {
		for(int i = 0; i < labels.length; i++){
			 this.labels.addElement(labels[i]);
		}
	}

	public Matrix getValues() {
		return values;
	}

	public void setValues(Matrix values) {
		this.values = values;
	}

	public void setValues(double[][] dvalues) {
		this.values = new Matrix(dvalues.length, dvalues[0].length) ;
		this.values.setMatrix(dvalues);
 	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public boolean getGraph() {
		return graph;
	}

	public void setGraph(boolean info) {
		this.graph = info;  

	}
}
