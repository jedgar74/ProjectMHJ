package statistics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

import util.Matrix;
import util.Maths;

import io.Terminal;

import graph.BoxPlotGraph;

import org.apache.commons.math3.distribution.*;

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
	int nTopAlgorithms = 3;
	Vector<String> labels = new Vector<String>();
    String info;

	Matrix values;
	
	boolean graph =false;
	double alpha = 0.05;
    int decimal = 4; 
    
	public FriedmanImanHolm() {
		super(); 
	} 

	
	public FriedmanImanHolm(int nTopAlgorithms) {
		super();
		this.nTopAlgorithms = nTopAlgorithms;
	} 


	public void minProblem(){
		try{
			System.out.println("\n*******        Jhon Edgar Amaya         *******");

			Calendar dateAndTime = new GregorianCalendar();
			nameFile = dateAndTime.get(Calendar.DAY_OF_MONTH)+"_"
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
					
			//System.out.println("***********************************************");
			//System.out.println("******* ALG : "+K+ " --- INST : "+N+" *****");
			//System.out.println("***********************************************");
			//System.out.print(Terminal.title("ALG : " + K + " --- INST : " + N)); 
			System.out.print("\nAlgorithms : "+K+ "  ---  Instances : "+N+"\n" 
				+ Terminal.separator());
			 
			
			info = info + "\nAlgorithms : "+K+ " --- Instances : "+N+"\n";
			info = info + Terminal.separator();
			
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
			Vector<String> indexRanking = new Vector<String>();
			Vector<String> selectedRank = new Vector<String>();
			
			for (int j = 0; j < values.getRow(); j++){ 
				for (int g = 0; g < values.getCol(); g++){
					indexRanking.addElement("" + g);
				}

				double ranking = 1;

				while(indexRanking.size() != 0){ 
					double min = 1e40;

					for (int g = 0; g < indexRanking.size(); g++){ 
						double tmpx = values.getElement(j, Integer.parseInt(indexRanking.elementAt(g)));
 
						if (tmpx < min){
							selectedRank.removeAllElements();
							min = tmpx;
							selectedRank.addElement(indexRanking.elementAt(g));
						} else if (tmpx == min){
							selectedRank.addElement(indexRanking.elementAt(g));
						}
					}
					double rankTemp = (selectedRank.size() + 2 * ranking - 1) / 2;
					//System.out.println(""+ranking+"---"+selectedRank.size()+"..."+rankTemp);
					ranking = ranking + selectedRank.size();

					for (int g = 0; g < selectedRank.size(); g++){ 
						ranks.setElement(j, Integer.parseInt(selectedRank.elementAt(g)), rankTemp);
					}

					for (int g = 0; g < selectedRank.size(); g++){
						int rs = indexRanking.indexOf(selectedRank.elementAt(g));
						indexRanking.removeElementAt(rs);					 
					} 
				}	 
			}

			System.out.println();
			// System.out.println("***********************************************");
			// System.out.println("*** RANKS IN THE MATRIX ***********************");
			// System.out.println("***********************************************");
			System.out.print(Terminal.title("RANKS MATRIX"));
			
			info = info + "\nRanks Matrix   \n";
			info = info + Terminal.separator(); 
			 
			// print matrix of ranks
			genMatrix(ranks, nameFile, "Rank_", labels);  /// Esto es nuevo!!!!!!!!!!!!!!!
			ranks.printlnAll();
			info = info + ranks.toString();

			//Calculate Friedman
			double Friedman = calculateFriedmanX(ranks, N, K);

			//Calculate Iman 
			double Iman = calculateIman(Friedman, N, K);

			Vector<String> labelsHolm = new Vector<String>();
			for (int g = 0; g < labels.size(); g++){
				labelsHolm.addElement(labels.elementAt(g));
			}
			//Calculate Holm
			calculateHolm(ranks, N, K, labelsHolm);

 
			//____________________________________________________

			Vector<String> exxR = new Vector<String>();
			Vector<String> dddR = new Vector<String>();
			Vector<String> labelsTopAlg = new Vector<String>();

			for (int j = 0; j < labels.size(); j++){
				labelsTopAlg.addElement(labels.elementAt(j));
			}	

			for (int j = 0; j < ranks.getCol(); j++){
				dddR.addElement("" + j);
				double dff = 0.0;
				for (int g = 0; g < ranks.getRow(); g++){ 
					dff = dff + ranks.getElement(g, j);
				}
				exxR.addElement(""+dff);
			}

			//ordenar
			for (int j = 0; j < dddR.size() ; j++){
				double minm = Double.parseDouble(exxR.elementAt(j));
				for (int g = 0; g < dddR.size(); g++){

					if (minm < Double.parseDouble(exxR.elementAt(g))){
						String trxx = labelsTopAlg.elementAt(j);
						String trxy = labelsTopAlg.elementAt(g);
						labelsTopAlg.setElementAt(trxy, j);
						labelsTopAlg.setElementAt(trxx, g);
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
				System.out.println("alg = "+ dddR.elementAt(j)+" - "+ Maths.rounds(Double.parseDouble(exxR.elementAt(j)), decimal)
				     + " - "+ labelsTopAlg.elementAt(j));

			}

			int Ktop = nTopAlgorithms; //indicate number algorithm
			/*int Ntop = lines.size() - 1;*/ //indicate number experiments
			int Ntop = this.values.getRow();
			//Create matrix values
			Matrix values2 = new Matrix(Ntop, Ktop);
			//values2.printlnAll();

			//llenar values2
			for (int j = 0; j < Ktop; j++){
				for (int g = 0; g < Ntop; g++){
					/*values2.setMatrix(g, j, values.getMatrix(g, Integer.parseInt(dddR.elementAt(j))));*/
					values2.setElement(g, j, values.getElement(g, Integer.parseInt(dddR.elementAt(j))));
				}
			}


			//Calculate Rank
			Matrix ranks2 = new Matrix(Ntop, Ktop);
			Vector<String> indexRanking2 = new Vector<String>();
			Vector<String> selectedRank2 = new Vector<String>();
			
			for (int j = 0; j < values2.getRow(); j++){
				for (int g = 0; g < values2.getCol(); g++){
					indexRanking2.addElement(""+g);
				}

				double ranking = 1;

				while(indexRanking2.size() != 0){
					double min = 1e40;

					for (int g = 0; g < indexRanking2.size(); g++){
						/*double tmpx = values2.getMatrix(j, Integer.parseInt(indexRanking2.elementAt(g)));*/
						double tmpx = values2.getElement(j, Integer.parseInt(indexRanking2.elementAt(g))); 
						if (tmpx < min){
							selectedRank2.removeAllElements();
							min = tmpx;
							selectedRank2.addElement(indexRanking2.elementAt(g));
						} else if (tmpx == min){
							selectedRank2.addElement(indexRanking2.elementAt(g));
						}
					}
					double rankTemp = (selectedRank2.size()+ 2*ranking - 1)/2;
					//System.out.println(""+ranking+"---"+selectedRank.size()+"..."+rankTemp);
					ranking=ranking + selectedRank2.size();

					for (int g = 0; g < selectedRank2.size(); g++){
						/*ranks2.setMatrix(j, Integer.parseInt(selectedRank2.elementAt(g)), rankTemp);*/
						ranks2.setElement(j, Integer.parseInt(selectedRank2.elementAt(g)), rankTemp);
					}

					for (int g = 0; g < selectedRank2.size(); g++){
						int rs = indexRanking2.indexOf(selectedRank2.elementAt(g));
						indexRanking2.removeElementAt(rs);						
						//ranks.setMatrix(j, Integer.parseInt(selectedRank.elementAt(g)), rankTemp);
					}

				}				

			} 
			
			System.out.println();
			//System.out.println("***********************************************");
			//System.out.println("*** VALUES IN THE MATRIX **********************");
			//System.out.println("***********************************************");
			System.out.print(Terminal.title("MATRIX TOP ALGORITHMS"));
			
			info = info + "\nTop Algorithms "+this.nTopAlgorithms+"\n";
			info = info + Terminal.separator();  
			 
			values2.printlnAll();
			
			//System.out.println("***********************************************");
			//System.out.println("*** RANKS IN THE MATRIX ***********************");
			//System.out.println("***********************************************");
			System.out.print(Terminal.title("RANKS MATRIX"));
			
			info = info + "\nRanks Matrix   \n";
			info = info + Terminal.separator();
			 

			if (labelsTopAlg.size() > nTopAlgorithms){				
				while (labelsTopAlg.size() > nTopAlgorithms){
					labelsTopAlg.removeElementAt(labelsTopAlg.size()-1);
				}				
			} 

			genMatrix(ranks2, nameFile, "RankTop_", labelsTopAlg);  /// Esto es nuevo!!!!!!!!!!!!!!!
			ranks2.printlnAll();
			info = info + ranks2.toString();
			
			//Calculate Friedman

			double Friedman2 = calculateFriedmanX (ranks2, Ntop, Ktop);


			//Calculate Iman 

			double Iman2 = calculateIman(Friedman2, Ntop, Ktop);


			//Calculate Holm

			calculateHolm(ranks2, Ntop, Ktop, labelsTopAlg);

			
			if (graph){
				this.sortMatrix(ranks, labels);
				BoxPlotGraph demo = new BoxPlotGraph("", ranks.getMatrix(), labels);
				demo.pack(); 
				demo.setVisible(true);
				
				this.sortMatrix(ranks2, labelsTopAlg);
				BoxPlotGraph demf = new BoxPlotGraph("", ranks2.getMatrix(), labelsTopAlg);
				demf.pack(); 
				demf.setVisible(true);
				
			}


			/*
			 * I include methods to generate boxplot in Matlab. First, sort columns 
			 * for median and media. Second, generate the files *.m     
			 */

			genFilesMatlab( "Rank_", ranks, labels);
			genFilesMatlab( "RankTop_", ranks2, labelsTopAlg);


		} catch(Exception e){
			e.printStackTrace();
			System.out.println("java FriedmanIman _file _top");
		}
	}
 

	public void maxProblem(){
		try{
			System.out.println("\n*******        Jhon Edgar Amaya         *******"); 
		  			  
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
					
			//System.out.println("***********************************************");
			//System.out.println("******* ALG : "+K+ " --- INST : "+N+" *****");
			//System.out.println("***********************************************");
			//System.out.print(Terminal.title("ALG : " + K + " --- INST : " + N)); 
			System.out.print("\nAlgorithms : "+K+ "  ---  Instances : "+N+"\n" 
				+ Terminal.separator()); 
			
		  
			info = info + "\nAlgorithms : "+K+ " --- Instances : "+N+"\n";
			info = info + Terminal.separator();
			
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
			Vector<String> indexRanking = new Vector<String>();
			Vector<String> selectedRank = new Vector<String>();
			
			for (int j = 0; j < values.getRow(); j++){
				for (int g = 0; g < values.getCol(); g++){
					indexRanking.addElement(""+g);
				}

				double ranking = 1;

				while(indexRanking.size() != 0){
					//double min = 1000000.0;
 					double max = 1e-40; 

					for (int g = 0; g < indexRanking.size(); g++){
						/*double tmpx = values.getMatrix(j, Integer.parseInt(indexRanking.elementAt(g)));*/
						double tmpx = values.getElement(j, Integer.parseInt(indexRanking.elementAt(g)));
 
						if (tmpx > max){
							selectedRank.removeAllElements();
							max = tmpx;
							selectedRank.addElement(indexRanking.elementAt(g));
						} else if (tmpx == max){
							selectedRank.addElement(indexRanking.elementAt(g));
						}
					}
					double rankTemp = (selectedRank.size()+ 2*ranking - 1)/2;
					//System.out.println(""+ranking+"---"+selectedRank.size()+"..."+rankTemp);
					ranking=ranking + selectedRank.size();

					for (int g = 0; g < selectedRank.size(); g++){
						/*ranks.setMatrix(j, Integer.parseInt(selectedRank.elementAt(g)), rankTemp);*/
						ranks.setElement(j, Integer.parseInt(selectedRank.elementAt(g)), rankTemp);
					}

					for (int g = 0; g < selectedRank.size(); g++){
						int rs = indexRanking.indexOf(selectedRank.elementAt(g));
						indexRanking.removeElementAt(rs);						
						//ranks.setMatrix(j, Integer.parseInt(selectedRank.elementAt(g)), rankTemp);
					}

				}				

			}

			System.out.println();
			// System.out.println("***********************************************");
			// System.out.println("*** RANKS IN THE MATRIX ***********************");
			// System.out.println("***********************************************");
			System.out.print(Terminal.title("RANKS MATRIX"));
			
			info = info + "\nRanks Matrix   \n";
			info = info + Terminal.separator(); 
			
			// print matrix of ranks
			genMatrix(ranks, nameFile, "Rank_", labels);  /// Esto es nuevo!!!!!!!!!!!!!!!
			ranks.printlnAll();
			info = info + ranks.toString();

			//Calculate Friedman
			double Friedman = calculateFriedmanX( ranks, N, K);

			//Calculate Iman 
			double Iman = calculateIman(Friedman, N, K);

			Vector<String> labelsHolm = new Vector<String>();
			for (int g = 0; g < labels.size(); g++){
				labelsHolm.addElement(labels.elementAt(g));
			}
			//Calculate Holm
			calculateHolm(ranks, N, K, labelsHolm);

 
			//____________________________________________________

			Vector<String> exxR = new Vector<String>();
			Vector<String> dddR = new Vector<String>();
			Vector<String> labelsTopAlg = new Vector<String>();

			for (int j = 0; j < labels.size(); j++){
				labelsTopAlg.addElement(labels.elementAt(j));
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
						String trxx = labelsTopAlg.elementAt(j);
						String trxy = labelsTopAlg.elementAt(g);
						labelsTopAlg.setElementAt(trxy, j);
						labelsTopAlg.setElementAt(trxx, g);
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
				System.out.println("alg = "+ dddR.elementAt(j)+" - "+ Maths.rounds(Double.parseDouble(exxR.elementAt(j)), decimal)
				     + " - "+ labelsTopAlg.elementAt(j));

			}

			int Ktop = nTopAlgorithms; //indicate number algorithm
			/*int Ntop = lines.size() - 1;*/ //indicate number experiments
			int Ntop = this.values.getRow();
			//Create matrix values
			Matrix values2 = new Matrix(Ntop, Ktop);
			//values2.printlnAll();

			//llenar values2
			for (int j = 0; j < Ktop; j++){
				for (int g = 0; g < Ntop; g++){
					/*values2.setMatrix(g, j, values.getMatrix(g, Integer.parseInt(dddR.elementAt(j))));*/
					values2.setElement(g, j, values.getElement(g, Integer.parseInt(dddR.elementAt(j))));
				}
			}


			//Calculate Rank
			Matrix ranks2 = new Matrix(Ntop, Ktop);
			Vector<String> indexRanking2 = new Vector<String>();
			Vector<String> selectedRank2 = new Vector<String>();
			for (int j = 0; j < values2.getRow(); j++){
				//for (int j = 0; j < 1; j++){
				for (int g = 0; g < values2.getCol(); g++){
					indexRanking2.addElement(""+g);
				}

				double ranking = 1;

				while(indexRanking2.size() != 0){
					double max = 1e-40; 

					for (int g = 0; g < indexRanking2.size(); g++){
						/*double tmpx = values2.getMatrix(j, Integer.parseInt(indexRanking2.elementAt(g)));*/
						double tmpx = values2.getElement(j, Integer.parseInt(indexRanking2.elementAt(g))); 
						if (tmpx > max){
							selectedRank2.removeAllElements();
							max = tmpx;
							selectedRank2.addElement(indexRanking2.elementAt(g));
						} else if (tmpx == max){
							selectedRank2.addElement(indexRanking2.elementAt(g));
						}
					}
					double rankTemp = (selectedRank2.size()+ 2*ranking - 1)/2;
					//System.out.println(""+ranking+"---"+selectedRank.size()+"..."+rankTemp);
					ranking=ranking + selectedRank2.size();

					for (int g = 0; g < selectedRank2.size(); g++){
						/*ranks2.setMatrix(j, Integer.parseInt(selectedRank2.elementAt(g)), rankTemp);*/
						ranks2.setElement(j, Integer.parseInt(selectedRank2.elementAt(g)), rankTemp);
					}

					for (int g = 0; g < selectedRank2.size(); g++){
						int rs = indexRanking2.indexOf(selectedRank2.elementAt(g));
						indexRanking2.removeElementAt(rs);						
						//ranks.setMatrix(j, Integer.parseInt(selectedRank.elementAt(g)), rankTemp);
					}

				}				

			} 
			
			System.out.println();
			//System.out.println("***********************************************");
			//System.out.println("*** VALUES IN THE MATRIX **********************");
			//System.out.println("***********************************************");
			System.out.print(Terminal.title("MATRIX TOP ALGORITHMS"));
			
			info = info + "\nTop Algorithms "+this.nTopAlgorithms+"\n";
			info = info + Terminal.separator(); 
			values2.printlnAll(); 
			
			//System.out.println("***********************************************");
			//System.out.println("*** RANKS IN THE MATRIX ***********************");
			//System.out.println("***********************************************");
			System.out.print(Terminal.title("RANKS MATRIX"));
			
			info = info + "\nRanks Matrix   \n";
			info = info + Terminal.separator(); 

			if (labelsTopAlg.size() > nTopAlgorithms){				
				while (labelsTopAlg.size() > nTopAlgorithms){
					labelsTopAlg.removeElementAt(labelsTopAlg.size() - 1);
				}				
			} 

			genMatrix(ranks2, nameFile, "RankTop_", labelsTopAlg);  /// Esto es nuevo!!!!!!!!!!!!!!!
			ranks2.printlnAll();
			info = info + ranks2.toString();
			//Calculate Friedman
			double Friedman2 = calculateFriedmanX (ranks2, Ntop, Ktop);


			//Calculate Iman 

			double Iman2 = calculateIman(Friedman2, Ntop, Ktop);

			//Calculate Holm

			calculateHolm(ranks2, Ntop, Ktop, labelsTopAlg);

			
			if (graph){ 
				this.sortMatrix(ranks, labels);
				BoxPlotGraph demo = new BoxPlotGraph("BOX PLOT", ranks.getMatrix(), labels);
				demo.pack(); 
				demo.setVisible(true);
				
				this.sortMatrix(ranks2, labelsTopAlg);
				BoxPlotGraph demf = new BoxPlotGraph("BOX PLOT [TOP]", ranks2.getMatrix(), labelsTopAlg);
				demf.pack(); 
				demf.setVisible(true); 
			}


			/*
			 * I include methods to generate boxplot in Matlab. First, sort columns 
			 * for median and media. Second, generate the files *.m     
			 */

			genFilesMatlab( "Rank_", ranks, labels);
			genFilesMatlab( "RankTop_", ranks2, labelsTopAlg);


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
			System.out.println(labels.elementAt(i)+ "--- "+ Maths.rounds(median[i], decimal)  +
					" ::: "+ Maths.rounds(media[i], decimal)); 
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


	public void sortMatrix(Matrix ranks, Vector<String> labels){
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
 

			int middle = tmp.length/2;  // subscript of middle element
			if (tmp.length%2 == 1) { 
				median[i] =  tmp[middle];
			} else {
				median[i] = (tmp[middle-1] + tmp[middle]) / 2.0;
			}
			System.out.println(labels.elementAt(i)+ "--- "+ Maths.rounds(median[i], decimal)  +
					" ::: "+ Maths.rounds(media[i], decimal)); 
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
						double sin = ranks.getElement(j, l);
						double cos = ranks.getElement(j, k);
						ranks.setElement(j, l, cos);
						ranks.setElement(j, k, sin);
					}

				} 
			}
		} 
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
		info = info + Terminal.separator();
		
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
		//System.out.println("***********************************************");
		//System.out.println("*** FRIEDMAN TEST *****************************");
		//System.out.println("***********************************************");
		System.out.print(Terminal.title("FRIEDMAN TEST"));
		System.out.println("N = "+N+ " --- "+"K = "+K);
		System.out.println("Beta = " + Maths.rounds(beta, decimal));
		
		
		info = info + "\nFriedman Test\n";
		info = info + Terminal.separator();
		
		for (int g = 0; g < ranks2.getCol(); g++){
			double media = 0.0;
			for (int j = 0; j < ranks2.getRow(); j++){
				media=media+ ranks2.getElement(j, g);
			}
			media = media / ranks2.getRow();
			System.out.println("media [alg "+g+ "] - " + Maths.rounds(media, decimal));

			media = media - beta;
			Friedman2=Friedman2+(media*media);
		}
		//System.out.println("Friedman = "+Friedman);
		Friedman2 = Friedman2*6*N/(K*beta);
		System.out.println("***********************************************");
		System.out.println("Friedman value = " + Maths.rounds(Friedman2, decimal));
		System.out.println("prob Chi-square function = " + Maths.rounds(stats.chiSquaredProbability(Friedman2, K-1), decimal));
		System.out.println("Calculate with degree = "+(K-1)+ " and obtain critical value with one sided ");
		ChiSquaredDistribution x2 = new ChiSquaredDistribution(K-1);
	 
		System.out.println("Critical value = "+Maths.precisionAndSpaces(x2.inverseCumulativeProbability(1 - alpha), decimal, 10)+ "   alpha = "+alpha);		
		System.out.println("***********************************************");
		System.out.println(); 
		
		info = info + "Friedman value = " + Maths.rounds(Friedman2, decimal)+"\n";
		info = info + "prob Chi-square function = " + Maths.rounds(stats.chiSquaredProbability(Friedman2, K-1), decimal) +"\n";
		info = info + "Calculate with degree = "+(K-1)+ " and obtain critical value with one sided "+"\n";
		info = info + "Critical value = " + Maths.rounds(x2.inverseCumulativeProbability(1 - alpha), decimal)+ "   alpha = "+alpha +"\n"; 
		return Friedman2;
	}


	@SuppressWarnings("static-access")
	public   void calculateHolm(Matrix ranks2, int N, int K, Vector<String> labelsTopAlg){
	 
		Statistics stats = new Statistics();
		double beta = (double) K*(K+1)/(6*N);
		//System.out.println("beta "+beta);
		beta=Math.sqrt(beta);
		double alfa = 0.05;
		Vector<String> ss = new Vector<String>();
		//System.out.println();
		//System.out.println("***********************************************");
		//System.out.println("************ z Statistics *********************");
		//System.out.println("***********************************************");
		System.out.print(Terminal.title("HOLM TEST"));
		System.out.println("N = "+N+ " --- "+"K = "+K);
		System.out.println("beta = " + Maths.rounds(beta, decimal));
		info = info + "\nHolm Test\n";
		info = info + Terminal.separator();
		for (int g = 0; g < ranks2.getCol(); g++){
			double media = 0.0;
			for (int j = 0; j < ranks2.getRow(); j++){
				media=media+ ranks2.getElement(j, g);
			}
			media=media/ranks2.getRow();
			System.out.println("media [alg "+g+ "] - " + Maths.rounds(media, decimal));

			ss.addElement("" + media);
		}
		//System.out.println("Friedman = "+Friedman);


		//ordenar
		for (int j = 0; j < ss.size() ; j++){
			double minm = Double.parseDouble(ss.elementAt(j));
			for (int g = 0; g < ss.size(); g++){

				if (minm < Double.parseDouble(ss.elementAt(g))){
					String trxx = labelsTopAlg.elementAt(j);
					String trxy = labelsTopAlg.elementAt(g);
					labelsTopAlg.setElementAt(trxy, j);
					labelsTopAlg.setElementAt(trxx, g);
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
		System.out.println("*** <tenor p value> if tenor > value then there is differences ***");
		System.out.println("***********************************************");
		System.out.println("CONTROL "+ labelsTopAlg.elementAt(0));
		info = info + "Control Algorithm "+labelsTopAlg.elementAt(0)+"\n";
		info = info + "[tenor p value] if tenor > value then there is differences (see @ \n\tsymbol)\n";
		info = info + Terminal.separator();
		for (int j = 1 ; j < ss.size(); j++){
			double ttp = ((Double.parseDouble(ss.elementAt(j)) - leader)/beta);
			double ptenor = (alfa/(K-(K-j)));
			double pp = (1-stats.normalProbability(ttp)) ;
			
			
			info = info + j+ " " + labelsTopAlg.elementAt(j)+" ::: " + Maths.rounds(ttp, decimal) + " - ";  
			info = info + Maths.rounds(ptenor, decimal) + " p " + Maths.rounds(pp, decimal);
			
			if (ptenor > pp){
				System.out.println(j+ " "+ labelsTopAlg.elementAt(j)+" ::: " + Maths.rounds(ttp, decimal) + " - "+  
						 Maths.rounds(ptenor, decimal) + " p " + Maths.rounds(pp, decimal)+ " @");
				info = info +" @\n";
			} else{
				System.out.println(j+ " "+ labelsTopAlg.elementAt(j)+" ::: "+ Maths.rounds(ttp, decimal) + " - "+  
						Maths.rounds(ptenor, decimal) + " p "+ Maths.rounds(pp, decimal));
				info = info +"\n";
			}
		}
		System.out.println("***********************************************");
		System.out.println();
	}


	@SuppressWarnings("static-access")
	public  double calculateIman(double Friedman, int N, int K){
		//System.out.println("***********************************************");
		//System.out.println("*** IMAN DAVENPORT TEST ***********************");
		//System.out.println("***********************************************");
		System.out.print(Terminal.title("IMAN-DAVENPORT TEST"));
		
		info = info + "\nIman-Davenport Test\n";
		info = info + Terminal.separator();
		
		Statistics stats = new Statistics();
		double ttm = (N - 1) * Friedman;
		double ttd = N * (K - 1) - Friedman;
		int Nmin = N-1;
		int Kmin = K-1;
		System.out.println("Iman-Davenport  = " + Maths.rounds((ttm /ttd), decimal));
		
		info = info + "Iman-Davenport  = " + Maths.rounds((ttm /ttd), decimal) +"\n"; 
		
		try{
			System.out.println("prob F-Function  = " + Maths.rounds(stats.FProbability(ttm /ttd, Kmin, Kmin*Nmin), decimal));
			info = info + "prob F-Function  = " + Maths.rounds(stats.FProbability(ttm /ttd, Kmin, Kmin*Nmin), decimal) +"\n"; 
		
		} catch (Exception e) {
			System.out.println("Error in computing prob F-Function");
			info = info + "Error in computing prob F-Function\n"; 
		}
		
		System.out.println("Calculate with degree = "+(K-1)+ " X "+(K-1)*(N-1)+" and obtain critical value with one sided ");		
		FDistribution x2 = new FDistribution(K-1,(K-1)*(N-1));
	 
		System.out.println("Critical value = " + Maths.precisionAndSpaces(x2.inverseCumulativeProbability(1 - alpha), decimal, 10)+ "   alpha = "+alpha);		
		System.out.println("***********************************************");
		System.out.println();
		info = info + "Calculate with degree = "+(K-1)+ " X "+(K-1)*(N-1)+" and obtain critical value with one \n\tsided " +"\n"; 
		info = info + "Critical value = "+ Maths.rounds(x2.inverseCumulativeProbability(1 - alpha), decimal)+ "   alpha = "+alpha +"\n"; 
		
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
				r = r + " " + Maths.rounds(ranks.getElement(i, j), 4); 
			}	
			input2.write(r+"\n");
		}
		input2.close();
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
	
	public void setDecimal(int t) {
		decimal = t;
	}
	
	public boolean getGraph() {
		return graph;
	}


	public void setGraph(boolean info) {
		this.graph = info;  

	}
}
