package statistics;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Vector;

import util.Maths;
import util.Matrix;

public class MulticriteriaMethods {
	
	public static String info;
	public static int decimal =4;

	public MulticriteriaMethods(int m, int n, double[][] t, double[] w, int[] c, int code) {
		Matrix A = new Matrix(m, n);
		/*System.out.println(" ");
		System.out.println("MCD Matrix");
		System.out.println("-----------------------------------");*/
		info="\n"+"MCD Matrix\n"+ "-----------------------------------\n";
		
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				A.setElement(i, j, t[i][j]);
			}
		}
		/*A.printlnAll(8);*/
		info=info+A.toString(4);
		
		/*System.out.println(" ");
		System.out.println("Weights");
		System.out.println("-----------------------------------");*/
		info=info+"\n"+"Weights\n"+ "-----------------------------------\n";
		
		for(int i=0;i<n;i++) {
			/*System.out.print(w[i]);*/
			String infor="";
			infor=infor+w[i];
			if (c[i]==0) {
				/*System.out.print("-D ");*/
				infor=infor+"- ";
			} else {
				/*System.out.print("-U ");*/
				infor=infor+"+ ";
			}
			info=info+Maths.spaces(infor, 8);
		}
		/*System.out.println("\n");*/
		info=info+"\n";
				
				
		if (code==0) {
			topsis(m, n, A, w, c);
			vikor(m, n, A, w, c);
		} else if (code==1) {
			topsis(m, n, A, w, c);
		} else if (code==2) {
			vikor(m, n, A, w, c);
		}
		System.out.println(info);	
	}

	public static void topsis(int m, int n, Matrix A, double[] w, int[] c) {
		/*System.out.println(" ");
		System.out.println("TOPSIS Test");
		System.out.println("-----------------------------------");
		DecimalFormatSymbols simD = new DecimalFormatSymbols();
		simD.setDecimalSeparator('.');
		DecimalFormat forD = new DecimalFormat("0.00000", simD); */
		info=info+"\n"+"TOPSIS method\n"+ "-----------------------------------\n";
		//step1. build the matrix
		
		//step 2. normalize matrix
		Matrix N = new Matrix(m, n);
		N= normal(A);
		/*N.printlnAll();*/
		/*N.printlnAll(8);*/
		info=info+"\n"+"Normalized matrix\n" ;
		info=info+N.toString(4);
		
		
		//step 3. Calculate the weighted normalized decision matrix.
		Matrix V = new Matrix(N.getRow(), N.getCol());
		for(int j=0;j<n;j++) { 
			for(int i=0;i<m;i++) {
				V.setElement(i, j, w[j]*N.getElement(i, j));
			}
		}
		/*System.out.println("");*/
		/*V.printlnAll();*/
		/*V.printlnAll(8);*/
		info=info+"\n"+"Weighted normalized decision matrix\n" ;
		info=info+N.toString(4);
		
		
		//step 4. positive ideal and negative ideal solutions
		double[] vpos= new double[N.getCol()];
		double[] vneg= new double[N.getCol()];
		for(int j=0;j<n;j++) {
			double max =0;
			double min =1e100;
			for(int i=0;i<m;i++) {
				double pos = V.getElement(i, j);
				if (pos > max ) {
					max = pos;
				}
				if (pos < min) {
					min = pos;
				}
			}
			if (c[j]==1) {
				vpos[j]=max;
				vneg[j]=min;
			} else {
				vpos[j]=min;
				vneg[j]=max; 				
			}
		}

		info=info+"\nV+/V-\n";
		/*System.out.println("V+/V-");*/
		for(int i=0;i<n;i++) {
			/*System.out.print(" "+forD.format(vpos[i]));*/
			info=info+Maths.precisionAndSpaces(vpos[i], decimal, decimal+7) ;
		}
		/*System.out.println("");*/
		info=info+"\n";
		
		for(int i=0;i<n;i++) {
			/*System.out.print(" "+forD.format(vneg[i]));*/
			info=info+Maths.precisionAndSpaces(vpos[i], decimal, decimal+7) ;
		}
		/*System.out.println("");*/
		info=info+" \n\n";
		//
		//
		//step 5. separation measures from the positive ideal solution and the negative ideal solution
		double[] dpos= new double[N.getRow()];
		double[] dneg= new double[N.getRow()];

		for(int i=0;i<m;i++) {
			double tmp =0;
			double tm2 =0;
			for(int j=0;j<n;j++) {
				tmp = tmp+(V.getElement(i, j) - vpos[j])*(V.getElement(i, j) - vpos[j]);
				tm2 = tm2+(V.getElement(i, j) - vneg[j])*(V.getElement(i, j) - vneg[j]);
			}
			dpos[i]=Math.sqrt(tmp);
			dneg[i]=Math.sqrt(tm2);
		}
		/*System.out.println("D+");
		for(int i=0;i<m;i++) {
			System.out.print(" "+forD.format(dpos[i]));
		}
		System.out.println("");
		System.out.println("D-");
		for(int i=0;i<m;i++) {
			System.out.print(" "+forD.format(dneg[i]));
		}
		System.out.println("");*/
		printing("D+", dpos, decimal);
		printing("D-", dneg, decimal);

		//Step 6. Calculate the relative closeness to the positive ideal solution.
		double[] rind= new double[N.getRow()];
		for(int i=0;i<m;i++) {
			rind[i]=dneg[i]/(dneg[i]+dpos[i]);
		}
		/*System.out.println("R");
		for(int i=0;i<m;i++) {
			System.out.print(" "+forD.format(rind[i]));
		}
		System.out.println("");*/
		printing("R", rind, decimal);

		//Step 7. Ranking 
		double[] rnkg= new double[N.getRow()];
		for(int i=0;i<m;i++) {
			rnkg[i]=i+1;
		}

		for(int i=0;i<m-1;i++) {
			for(int g=1;g<m;g++) {
				if (rind[g]>rind[i]) {
					double tt = rind[g];
					rind[g]=rind[i];
					rind[i]=tt;
					tt = rnkg[g];
					rnkg[g]=rnkg[i];
					rnkg[i]=tt;
				}
			}
		}


		/*System.out.println("Z");
		for(int i=0;i<m;i++) {
			System.out.print(" "+(int)rnkg[i]);
		}
		System.out.println("");*/
		info=info+"\n"+"-----------------------------------\n";
		printing("Z", rnkg, 0);

	}


	public static void vikor(int m, int n, Matrix A, double[] w, int[] c) {
		/*System.out.println(" ");
		System.out.println("VIKOR Test");
		System.out.println("-----------------------------------");
		DecimalFormatSymbols simD = new DecimalFormatSymbols();
		simD.setDecimalSeparator('.');
		DecimalFormat forD = new DecimalFormat("0.00000", simD); */
		info=info+"\n"+"VIKOR Test\n"+ "-----------------------------------\n";
		//step1. build the matrix
		 

		//step  . normalize matrix
		/*Matrix N = new Matrix(m, n);
		N= normal(A);
		N.printlnAll();*/

		double[] fbest= new double[A.getCol()];
		double[] fworst= new double[A.getCol()]; 

		for(int j=0;j<n;j++) {
			double max =0;
			double min =1e100;
			for(int i=0;i<m;i++) {
				double pos = A.getElement(i, j);
				if (pos > max ) {
					max = pos;
				}
				if (pos < min) {
					min = pos;
				}
			}
			if (c[j]==1) {
				fworst[j]=max;
				fbest[j]=min;
			} else {
				fbest[j]=min;
				fworst[j]=max; 				
			}
		}

		double[] R = new double[A.getRow()];
		double[] S = new double[A.getRow()];
		double[] Q = new double[A.getRow()];

		double Smin =1e100;
		double Smax =0;
		double Rmin =1e100;
		double Rmax =0;
		for(int i=0;i<m;i++) {
			double sum =0;
			double max =0;
			for(int j=0;j<n;j++) {
				double value = w[j]*(fbest[j]-A.getElement(i, j))/(fbest[j]-fworst[j]);
				sum=sum+value;
				if (value > max) {
					max = value;
				}
			}
			S[i]=sum;
			R[i]=max;
			if (S[i] > Smax ) {
				Smax = S[i];
			}
			if (S[i] < Smin) {
				Smin = S[i];
			}
			if (R[i] > Rmax ) {
				Rmax = R[i];
			}
			if (R[i] < Rmin) {
				Rmin = R[i];
			}
		}

		/*System.out.println("S");
		for(int i=0;i<m;i++) {
			System.out.print(" "+forD.format(S[i]));
		}
		System.out.println("\nR");
		for(int i=0;i<m;i++) {
			System.out.print(" "+forD.format(R[i]));
		}*/
		printing("R", R, decimal);
		printing("S", S, decimal);

		double v = 0.5;
		for(int i=0;i<m;i++) {
			double group = v*(S[i]-Smin)/(Smax-Smin);
			double individual = (1-v)*(R[i]-Rmin)/(Rmax-Rmin);
			Q[i] = group +  individual;

		}

		printing("Q", Q, decimal);

		double[] Rrk = new double[A.getRow()];
		double[] Srk = new double[A.getRow()];
		double[] Qrk = new double[A.getRow()];

		Rrk = ranking(R);
		Srk = ranking(S);
		Qrk = ranking(Q);

		/*System.out.println();
		printing("Rrk",Rrk,forD);
		printing("Srk",Srk,forD);
		printing("Qrk",Qrk,forD);*/

		double dq = 1/((double)A.getRow()-1);
		/*System.out.println("DQ="+dq);*/
		info=info+" DQ "+Maths.precisionAndSpaces(dq, decimal, decimal+7)+"\n";
		 
				
		double[] Rs = new double[A.getRow()];
		double[] Ss = new double[A.getRow()];
		double[] Qs = new double[A.getRow()];
		System.out.println();
		Qs = ranks(Q);
		printing("Qs", Qs, decimal);
		Ss = ranks(S);
		printing("Ss", Ss, decimal);
		Rs = ranks(R);
		printing("Rs", Rs, decimal);


		// first condition Cone
		boolean Cone=false;

		if ((Qs[1]-Qs[0]) >= dq) {
			/*System.out.println("--a--");*/
			Cone=true;	
		} 

		// second condition
		boolean Ctwo=false;

		if ((Qs[0]==Ss[0]) || (Qs[0]==Rs[0])) {
			/*System.out.println("--b--");*/
			Ctwo=true;	
		} 


		double[] Aa;
		if (Cone && Ctwo) {
			Aa = new double[1];
			Aa[0]=Qs[0];
			/*System.out.println("(a) better "+ ((int)Qs[0]+1));*/
			info=info+"(a) better "+ Maths.precisionAndSpaces(((int)Qs[0]+1), decimal, decimal+7) +"\n"; 
		} else if (! Cone) {
			int f=0;
			for(int i=Qs.length-1;i>0;i--) {
				if ((Qs[i]-Qs[0]) < dq) {
					/*System.out.println("--c-- "+i);*/
					f=i;
					break;
				} 
			}

			Aa = new double[f+1];
			/*System.out.print("(b) better ");*/
			info=info+"(b) better " ;
			for(int i=0;i<f+1;i++) {
				Aa[i]=Qs[i];
				/*System.out.print(((int)Qs[i]+1));
				if (i< f)
					System.out.print ( " " );*/
				info=info+ Maths.precisionAndSpaces(((int)Qs[i]+1), decimal, decimal+7) ;
			}
			/*System.out.println( );*/
			info=info+ "\n";
		} else if (! Ctwo) {
			Aa = new double[2];
			Aa[0]=Qs[0];
			Aa[1]=Qs[1];
			/*System.out.println("(c) better "+ ((int)Qs[0]+1) + " " +((int)Qs[1]+1));*/
			info=info+"(c) better "+ Maths.precisionAndSpaces(((int)Qs[0]+1), decimal, decimal+7) +
					Maths.precisionAndSpaces(((int)Qs[1]+1), decimal, decimal+7)+"\n";
			
			/*info=info+"\n";*/
		}

		info=info+"\n"+"-----------------------------------\n";
		info=info+""+Maths.spaces("Rk",3)+" ";
		/*System.out.println("Ranking ");
		System.out.print(" ");*/
		for(int i=0;i<Qs.length;i++) {
			info=info+Maths.precisionAndSpaces(((int)Qs[i]+1), decimal, decimal+7) +" ";
			/*System.out.print(((int)Qs[i]+1));*/
			/*if (i< Qs.length-1)
				System.out.print ( " " );*/
		}
		info=info+"\n";

	}


	public static double[] ranks2(double[] v) {

		double[]  ranks = new double[v.length];
		double[]  tmps = new double[v.length];

		for(int i=0;i<v.length;i++) {
			ranks[i]=i;
			tmps[i]=v[i];
		}
		for(int i=0;i<v.length-1;i++) {
			double tmp =0;
			for(int j=i+1;j<v.length;j++) {
				if (tmps[j] < tmps[i]) {
					tmp = ranks[j];
					ranks[j]=ranks[i];
					ranks[i]=tmp;
					tmp = tmps[j];
					tmps[j]=tmps[i];
					tmps[i]=tmp;
				}
			}	
		}

		return ranks;
	}


	public static  double[] ranking(double[] v) {

		double[]  ranks = new double[v.length];

		Vector<String> exx = new Vector<String>();
		Vector<String> ddd = new Vector<String>();

		for (int g = 0; g < v.length; g++){
			exx.addElement(""+g);  
		} 
		double ranking = 1;

		while(exx.size() != 0){
			//double min = 1000000.0;
			double min = 1e40;
			for (int g = 0; g < exx.size(); g++){
				double tmpx = v[Integer.parseInt(exx.elementAt(g))]; 
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
				ranks[Integer.parseInt(ddd.elementAt(g))] = rankTemp;
			}

			for (int g = 0; g < ddd.size(); g++){
				int rs = exx.indexOf(ddd.elementAt(g));
				exx.removeElementAt(rs);						
				//ranks.setMatrix(j, Integer.parseInt(ddd.elementAt(g)), rankTemp);
			}

		}				

		return ranks;
	} 


	public static double[] ranks(double[] v) {

		double[]  ranks = new double[v.length];
		double[]  tmps = new double[v.length];
		for(int i=0;i<v.length;i++) {
			ranks[i]=i;
			tmps[i]=v[i];
		}

		for(int i=0;i<v.length-1;i++) {
			double tmp =0;
			for(int j=i+1;j<v.length;j++) {
				if (tmps[j] < tmps[i]) {
					tmp = ranks[j];
					ranks[j]=ranks[i];
					ranks[i]=tmp;
					tmp = tmps[j];
					tmps[j]=tmps[i];
					tmps[i]=tmp;
				}
			}	
		}

		return ranks;
	}


	/*public static void printing(String name, double[] v) {
		DecimalFormatSymbols simD = new DecimalFormatSymbols();
		simD.setDecimalSeparator('.');
		DecimalFormat forD = new DecimalFormat("0.00000", simD);
		info=info+""+name+" --> ";
		System.out.println(name);
		for(int i=0;i<v.length;i++) {
			System.out.print(" "+(int) v[i] );
			info=info+""+(int) v[i]+" ";	
		}
		info=info+"\n";
		System.out.println();
	}*/


	public static void printing(String name, double[] v, int dec) {
		/*DecimalFormatSymbols simD = new DecimalFormatSymbols();
		simD.setDecimalSeparator('.');
		DecimalFormat forD = new DecimalFormat("0.00000", simD);*/

		/*System.out.println(name);*/
		info=info+""+Maths.spaces(name,3)+" ";
		for(int i=0;i<v.length;i++) {
			/*System.out.print(" "+forD.format(v[i]));*/
			info=info+Maths.precisionAndSpaces(v[i], dec, dec+7) +" ";
			/*info=info+""+forD.format(v[i])+" ";*/
		}
		info=info+"\n";
		/*System.out.println();*/
	}


	public static void printingMatrix(String name, double[] v, DecimalFormat forD) {
		/*DecimalFormatSymbols simD = new DecimalFormatSymbols();
		simD.setDecimalSeparator('.');
		DecimalFormat forD = new DecimalFormat("0.00000", simD);*/

		System.out.println(name);
		for(int i=0;i<v.length;i++) {
			System.out.print(" "+forD.format(v[i]));
		}
		System.out.println();
	}


	public static Matrix normal(Matrix G) {
		int m = G.getRow();
		int n = G.getCol();
		Matrix N = new Matrix(G.getRow(), G.getCol());

		double[] medi = new double[n];
		for(int j=0;j<n;j++) {
			double tmp =0;
			for(int i=0;i<m;i++) {
				tmp = tmp + G.getElement(i, j)*G.getElement(i, j);
			}
			medi[j]=Math.sqrt(tmp);
		}

		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				N.setElement(i, j, G.getElement(i, j)/medi[j]);
			}
		}

		return N;
	}
}
