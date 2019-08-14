package statistics;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Vector;

import util.Maths;
import util.Matrix;

import io.Terminal;

import graph.PlotGraph;


public class MulticriteriaMethods {
	
	public static Matrix A;
	public static int m;
	public static int n;
	public static double[] w;
	public static int[] c; 
	
	public static String info;
	public static int decimal = 4; 
	public static boolean graph = true;
	
	public static int typeSIR = 0 ;  //1==SIR-SAW,  0=SIR-TOPSIS
	public static String typePROMETHEE = "II"; 
	public static double vFactor = 0.5;
	
	public static String[] pfs; //preference functions
	public static double[][] limpfs; 
	/*
	* 
	* MulticriteriaMethods:  this is the constructor  
	* @param
	* @return
	* 
	*/
 	public MulticriteriaMethods(int alt, int crit, double[][] t, double[] weights, int[] tCrit ) {
		this.w=weights;
		this.m=alt;
		this.n=crit;
		this.c=tCrit;
		
		A = new Matrix(m, n);
		 
		info = "\n" + "MCD Matrix\n" + Terminal.separator(); 
		
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				A.setElement(i, j, t[i][j]);
			}
		}
		 
		info = info + A.toString(4); 
		 
		info = info + "\n" + "Weights\n" + Terminal.separator();
		 
		
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
				
				
		/*if (code==0) {
			topsis(m, n, A, w, c);
			vikor(m, n, A, w, c);
			promethee(m, n, A, w, c);
			sir(m, n, A, w, c);
		} else if (code==1) {
			topsis(m, n, A, w, c);
		} else if (code==2) {
			vikor(m, n, A, w, c);
		} else if (code==3) {
			promethee(m, n, A, w, c);
		} else if (code==4) {
			sir(m, n, A, w, c);
		}
		System.out.println(info);	*/
	}


	/*
	* 
	* run:    
	* @param
	* @return
	* 
	*/
	public static void run(int code) {
		if (code==0) {
			topsis();
			//vikor(m, n, A, w, c);
			vikor();
			//promethee(m, n, A, w, c);
			promethee( );
			//sir(m, n, A, w, c);
			sir();
		} else if (code==1) {
			topsis();
		} else if (code==2) {
			//vikor(m, n, A, w, c);
			vikor();
		} else if (code==3) {
			//promethee(m, n, A, w, c);
			promethee( );
		} else if (code==4) {
			//sir(m, n, A, w, c);
			sir();
		}
		System.out.println(info);	
	}
	
	
	public static void topsis() {
		/* 
		DecimalFormatSymbols simD = new DecimalFormatSymbols();
		simD.setDecimalSeparator('.');
		DecimalFormat forD = new DecimalFormat("0.00000", simD); */
		
		//info=info+"\n***********************************************";
		//info=info+"\n*******     TOPSIS method               *******";
		//info=info+"\n***********************************************\n"; 
		info = info + Terminal.title("TOPSIS method");
		
		//step1. build the matrix
		
		//step 2. normalize matrix
		Matrix N = new Matrix(m, n);
		N = normal(A);
		/*N.printlnAll();*/
		/*N.printlnAll(8);*/
		info=info+"\n"+"Normalized matrix\n" ;
		info=info+N.toString(4);
		
		
		//step 3. Calculate the weighted normalized decision matrix.
		Matrix V = new Matrix(N.getRow(), N.getCol());
		for(int j = 0; j < n; j++) { 
			for(int i = 0; i < m; i++) {
				V.setElement(i, j, w[j] * N.getElement(i, j));
			}
		}
		/*System.out.println("");*/
		/*V.printlnAll();*/
		/*V.printlnAll(8);*/
		info=info+"\n"+"Weighted normalized decision matrix\n" ;
		info=info+V.toString(4);
		
		
		//step 4. positive ideal and negative ideal solutions
		double[] vpos = new double[N.getCol()];
		double[] vneg = new double[N.getCol()];
		for(int j=0;j<n;j++) {
			double max =-1e100;
			double min =1e100;
			for(int i = 0; i < m; i++) {
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
			info=info+Maths.precisionAndSpaces(vneg[i], decimal, decimal+7) ;
		}
		/*System.out.println("");*/
		info=info+" \n\n";
		//
		//
		//step 5. separation measures from the positive ideal solution and the negative ideal solution
		double[] dpos = new double[N.getRow()];
		double[] dneg = new double[N.getRow()];

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
		
		if (graph){
			String[] labels = {"D+","D-","R " };
			PlotGraph demo = new PlotGraph("TOPSIS", dpos, dneg  , rind , labels);
			demo.pack(); 
			demo.setVisible(true);  
		}
		

		//Step 7. Ranking 
		


		/*System.out.println("Z");
		for(int i=0;i<m;i++) {
			System.out.print(" "+(int)rnkg[i]);
		}
		System.out.println("");*/
		info = info + "\n" + Terminal.separator(); 
		double[] rnkg = new double[A.getRow()];
		rnkg = ranksMax(rind); 
		printingPlusOne("Z", rnkg, decimal); 

	}


	public static void vikor() {
		info = info + Terminal.title("VIKOR Test");
		//info=info+"\n***********************************************";
		//info=info+"\n*******     VIKOR Test                  *******";
		//info=info+"\n***********************************************\n"; 
		 
		//step1. build the matrix 

		//step  . normalize matrix
		/*Matrix N = new Matrix(m, n);
		N= normal(A);
		N.printlnAll();*/

		double[] fbest= new double[A.getCol()];
		double[] fworst= new double[A.getCol()]; 

		for(int j=0;j<n;j++) {
			double max =-1e100;
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
			if (c[j]==0) {
				fworst[j]=max;
				fbest[j]=min;
			} else {
				fbest[j]=max;
				fworst[j]=min; 				
			}
		}

		double[] R = new double[A.getRow()];
		double[] S = new double[A.getRow()];
		double[] Q = new double[A.getRow()];

		double Smin =1e100;
		double Smax =-1e100;
		double Rmin =1e100;
		double Rmax =-1e100;
		
		for(int i=0;i<m;i++) {
			double sum = 0;
			double max = -1e100;
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
		info = info + "\n";
		printing("R", R, decimal);
		printing("S", S, decimal);

		
		info=info+" vFactor "+Maths.precisionAndSpaces(vFactor, decimal, decimal+7)+"\n";	 
		for(int i=0;i<m;i++) {
			double group = vFactor*(S[i]-Smin)/(Smax-Smin);
			double individual = (1-vFactor)*(R[i]-Rmin)/(Rmax-Rmin);
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
		info = info + " DQ "+Maths.precisionAndSpaces(dq, decimal, decimal+7)+"\n";
		info = info + "\n"; 
				
		double[] Rs = new double[A.getRow()];
		double[] Ss = new double[A.getRow()];
		double[] Qs = new double[A.getRow()];
		System.out.println();
		Qs = ranks(Q);
		printingPlusOne("Qs", Qs, decimal);
		Ss = ranks(S);
		printingPlusOne("Ss", Ss, decimal);
		Rs = ranks(R);
		printingPlusOne("Rs", Rs, decimal);

		if (graph){
			String[] labels = {"R ","S ","Q " };
			PlotGraph demo = new PlotGraph("VIKOR", R , S , Q , labels);
			demo.pack();
			/* RefineryUtilities.centerFrameOnScreen(demo);*/
			demo.setVisible(true);  
		}


		// first condition Cone
		boolean Cone = false;

		// if ((Qs[1] - Qs[0]) >= dq) {
		if ((Q[(int)Qs[1]] - Q[(int)Qs[0]]) >= dq) {
			/*System.out.println("--a--");*/
			Cone=true;	
		} 

		// second condition
		boolean Ctwo = false;

		if ((Qs[0] == Ss[0]) || (Qs[0] == Rs[0])) {
			/*System.out.println("--b--");*/
			Ctwo = true;	
		} 


		double[] Aa;
		if (Cone && Ctwo) {
			Aa = new double[1];
			Aa[0]=Qs[0];
			/*System.out.println("(a) better "+ ((int)Qs[0]+1));*/
			info=info+"(a) better "+ Maths.precisionAndSpaces(((int)Qs[0]+1), decimal, decimal+7) +"\n"; 
		} else if (! Cone) {
			int f=0;
			for(int i= Qs.length-1;i>0;i--) {
				if ((Qs[i]-Qs[0]) < dq) {
				// if ((Q[(int)Qs[i] - 1] - Q[(int)Qs[0] - 1]) >= dq) {
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
 
		
		info = info + "\n" + Terminal.separator();  
		printingPlusOne("Rk", Qs, decimal);

	}


	/*superiority and inferiority ranking method*/
	public static void sir() { 
		//info=info+"\n***********************************************";
		//info=info+"\n*******     SIR Test                    *******";
		//info=info+"\n***********************************************\n";
		info = info + Terminal.title("SIR Test"); 
		 
		//double[] p = { 1, 2};
		//String pf = "li";
		
		if (pfs == null){
			setPreferenceFunctionsAsSame("li");
		} 
		
		if (limpfs == null){
			setLimPreferenceFunctionsAsSame(1, 2);
		}
		
		// calculate S matrix  
		Matrix S = sirMatrix(limpfs, c , pfs) ;
		info=info+S.toString("S", 4)+"\n";

		int[] u = new int[n];
		for(int i=0;i<n;i++) {
			if (c[i] == 1)
				u[i]=0;
			else
				u[i]=1; 
		}
		
		// calculate I matrix
		Matrix I = sirMatrix(limpfs, u, pfs);
		info=info+I.toString("I", 4)+"\n";

		double[] Sflow = new double[m];
		double[] Iflow = new double[m];
		if (typeSIR == 1){ // SIR-SAW
			// calculate S-flow
			Sflow = sirSAW(w, S);

			// calculate I-flow
			Iflow = sirSAW(w, I);
		} else { // SIR-TOPSIS
			// calculate S-flow
			// Splus, Sminus = SIRTOPSIS(w, S, 2);
			// Sflow = sirTOPSIS(Splus, Sminus);
			Sflow = sirTOPSIS(w, S, 2);
			
			// calculate I-flow
			// Iplus, Iminus = SIRTOPSIS(w, I, 2);
			// Iflow = sirTOPSIS(Iplus, Iminus);
			Iflow = sirTOPSIS(w, I, 2);
		}
		// calculate n-flow
		double[] nflow = new double[m];
		nflow =  sirFlow(Sflow, Iflow, 0);

		// calculate r-flow
		double[] rflow = new double[m];
		rflow =  sirFlow(Sflow, Iflow, 1);

		// print flows
		printing("Sflow", Sflow, decimal);
		printing("Iflow", Iflow, decimal);
		printing("nflow", nflow, decimal);
		printing("rflow", rflow, decimal); 
	
		// plot results
		// if a == 'y':
		// 	graph(around(rflow, 3), "Flow")
		// if b == 'y':
		// 	plot(around(rflow, 3), "SIR")
		
		if (graph){
			String[] labels = {"Flows" };
			PlotGraph demo = new PlotGraph("SIR", rflow , labels);
			demo.pack();
			/* RefineryUtilities.centerFrameOnScreen(demo);*/
			demo.setVisible(true);  
		}
		
		info = info + "\n" + Terminal.separator();
		double[] Qs = new double[A.getRow()];
		Qs = ranksMax(rflow);
		printingPlusOne("Z", Qs, decimal);
	}
	
	
	public static Matrix sirMatrix(double[][] p, int[] c, String[] f) {
		// Aquí  debemos tomar en cuenta el vector completo p f
		Matrix tmp = new Matrix(m, n); 
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				double k = 0;
				for (int h = 0; h < m; h++) {
					// k = k + pf(A[j, i], A[h, i], p[0, i], p[1, i], f[i], c[i]);
					k = k + pf(A.getElement(j, i), A.getElement(h, i), p[0][i], p[1][i], 
						f[i], c[i]);
					tmp.setElement(j, i, k);  
				}
			}
		}
		
		return tmp;
	}
	
	
	public static double pf(double ar, double br, double c, double d, String e, int m){
		double f =  1.0 ;
		double a = ar;
		double b = br;
		if (m == 1){ 
			double temp = a;
			a = b;
			b = temp;
		} 
		if (e.equals("u")) {  // Usual preference function
			if (b - a > 0){ 
				f = 1;
			} else 
				f = 0;
		} else if (e.equals("us")) { // U-shape preference function
			if (b - a > c){ 
				f = 1;
			} else if (b - a <= c)  
				f = 0;
		} else if (e.equals("vs")) { // V-shape preference function
			if (b - a > d){ 
				f = 1;
			} else if (b - a <= 0){ 
				f = 0;
			} else
				f = (b - a) / d;
		} else if (e.equals("le")) { // Level preference function
			if (b - a > d){ 
				f = 1;
			} else if (b - a <= c){ 
				f = 0;
			} else
				f = 0.5;
		} else if (e.equals("li")) { // Linear preference function
			if (b - a > d){ 
				f = 1;
			} else if (b - a <= c){ 
				f = 0;
			} else
				f = ((b - a) - c) / (d - c);
		} else if (e.equals("g")) {  // Gaussian preference function
			if (b - a > 0){ 
				f = 1 - Math.exp(-(Math.pow(b - a, 2)/(2 * Math.pow(d, 2))));
			} else
				f = 0;
		}
		
		return f;
	}
	
	
	public static double[] sirSAW(double[] w, Matrix T){
		double[] tflow = new double[T.getRow()];
		
		for (int i = 0; i < T.getRow(); i++) { 
			for (int j = 0; j < T.getCol(); j++) {
				tflow[i] = tflow[i] + w[j] * T.getElement(i, j);
			}
		}
		return tflow;
	}
	
	
	public static double[] sirTOPSIS(double[] w, Matrix T, int l){
		double[] tmp = new double[T.getRow()];
		
		double[] p = new double[T.getRow()];
		double[] m = new double[T.getRow()]; 
    
		double[] bb = new double[T.getCol()];
		double[] cc = new double[T.getCol()]; 
		
		for (int i = 0; i < T.getCol(); i++) { 
			bb[i] = T.maxCol(i);  
			cc[i] = T.minCol(i);
			// System.out.println(bb[i]+":::"+cc[i]);
		}
    
		for (int i = 0; i < T.getRow(); i++) { 
			for (int j = 0; j < T.getCol(); j++) {
				p[i] = p[i] + Math.pow(w[j]
					* Math.abs(T.getElement(i, j) - bb [j]), l);
				m[i] = m[i] + Math.pow(w[j]
					* Math.abs(T.getElement(i, j) - cc [j]), l);
			}
		
			p[i] = Math.pow(p[i], Math.pow(l,-1));
			m[i] = Math.pow(m[i], Math.pow(l,-1));
			// System.out.println(p[i]+"---"+m[i]);
		}
		
		
		// 
		//  
		for(int i=0;i<p.length;i++) {
			tmp[i] = m[i]/(p[i]+m[i]);  
		}
		
		return tmp;
	}
	
	
	public static double[] sirFlow(double[] Sflow, double[] Iflow, int t){
		double[] tmp = new double[Sflow.length];
		
		if (t == 0){   
			for(int i=0;i<Sflow.length;i++) {
				tmp[i] = Sflow[i]-Iflow[i];  
			}
		} else {  
			for(int i=0;i<Sflow.length;i++) {
				tmp[i] = Sflow[i]/(Sflow[i]+Iflow[i]);  
			}
		}
		return tmp;
	}
		
		
	public static void promethee() {
		//info=info+"\n***********************************************";
		//info=info+"\n*******     PROMETHEE Test              *******";
		//info=info+"\n***********************************************\n";
		info = info + Terminal.title("PROMETHEE Test"); 
		
		//double[] p = { 1, 2};
		//info=info+A.toString(4);
		//String pf = "li"; 
		
		if (pfs == null){
			setPreferenceFunctionsAsSame("li");
		} 
		
		if (limpfs == null){
			setLimPreferenceFunctionsAsSame(1, 2);
		}
		
		Matrix tmp = new Matrix(m, n);
		double[] tmpx = new double[n];
		
		for (int j = 0; j < n; j++) { 
			info = info + "\n" + "Criterion " + (j+1) + "\n";
			tmpx[0] = limpfs[0][j];
			tmpx[1] = limpfs[1][j];
			
			double[] weightedflows = unicriterion(m, A.getCols(j), tmpx, pfs[j], c[j]) ;
			
			printing("WF", weightedflows, decimal);
			for (int i = 0; i < m; i++) { 
				tmp.setElement(i, j, weightedflows[i]*w[j]);
			}
		}  
		
		double[] totalNFlows = new double[m];
		
		for (int i = 0; i < m; i++) {  
			for (int j = 0; j < n; j++) { 
				totalNFlows[i] = totalNFlows[i]+tmp.getElement(i, j);
			}
		}
		printing("FL" , totalNFlows, decimal);
		
		if (graph){
			String[] labels = {"Flows" };
			PlotGraph demo = new PlotGraph("PROMETHEE", totalNFlows , labels);
			demo.pack();
			/* RefineryUtilities.centerFrameOnScreen(demo);*/
			demo.setVisible(true);  
		}
		
		info = info+"\n"+ Terminal.separator();
		double[] Qs = new double[A.getRow()];
		Qs = ranksMax(totalNFlows);
		printingPlusOne("Z", Qs, decimal);
	}
	
	
	public static double[] unicriterion(int m, double[] x, double[] p, String f, int c) {
		Matrix uni = new Matrix(m, m); 
		
		printing("X", x, decimal);
		uni.zeros();
		for(int i = 0; i < m; i++) {
	        for(int j = 0; j < m; j++)  {
	            if (i == j) { 
	                uni.setElement(i, j, 0);
	            } else if (f.equals("u")) {  //Usual preference function
	                if (x[j] - x[i] > 0 ) {
	                    uni.setElement(i, j, 1);
	                } else
	                    uni.setElement(i, j, 0);
	            } else if (f.equals("us")) {  //U-shape preference function
	                if (x[j] - x[i] > x[0]) {
	                    uni.setElement(i, j, 1);
	                } else if (x[j] - x[i] <= p[0])  
	                    uni.setElement(i, j, 0);
	            } else if (f.equals("vs")) {  //V-shape preference function
	                if (x[j] - x[i] > p[1]) {
	                     uni.setElement(i, j, 1);
	                } else if (x[j] - x[i] <= 0) {
	                    uni.setElement(i, j, 0);
	                } else
	                    uni.setElement(i, j,  (x[j] - x[i]) / p[1]);
	            } else if (f.equals("le")) {  //Level preference function
	                if (x[j] - x[i] > p[1]) {
	                    uni.setElement(i, j, 1);
	                } else if (x[j] - x[i] <= p[0]) {
	                    uni.setElement(i, j, 0);
	                } else
	                    uni.setElement(i, j, 0.5);
	            } else if (f.equals("li")) {  //Linear preference function
	                if (x[j] - x[i] > p[1]) {
	                    uni.setElement(i, j, 1);
	                } else if (x[j] - x[i] <= p[0]) {
	                    uni.setElement(i, j, 0);
	                } else
	                    uni.setElement(i, j, ((x[j] - x[i]) -p[0]) / (p[1] - p[0]));
	            } else if (f.equals("g")) {  //Gaussian preference function
	                if (x[j] - x[i] > 0) {
	                    uni.setElement(i, j, 1 - Math.exp(-(Math.pow(x[j] - x[i], 2) / (2 * Math.pow(p[1], 2) ))));
	                } else
	                    uni.setElement(i, j, 0);
				}
			}
	    }
	    
	    info = info + uni.toString("",4);
	    
	    if (c == 1) 
	        uni.trasponse();
	        
	    // positive, negative and net flows
	    double[] pos_flows = uni.sumCols(1.0 / (m - 1));
	    double[] neg_flows = uni.sumRows(1.0 / (m - 1));
	    
	    printing("NF", neg_flows, decimal);
	    printing("PF", pos_flows, decimal);
	    double[] net_flows = new double[m];
	    for(int j=0;j<m;j++)
			net_flows[j] = pos_flows[j] - neg_flows[j];
    
		return net_flows;

	} 
	

	public static double[] ranking(double[] v) {

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

		for(int i = 0; i < v.length - 1; i++) {
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


	public static double[] ranksMax(double[] v) {

		double[]  ranks = new double[v.length];
		double[]  tmps = new double[v.length];
		
		for(int i=0;i<v.length;i++) {
			ranks[i]=i;
			tmps[i]=v[i];
		}

		for(int i=0;i<v.length-1;i++) {
			double tmp =0;
			for(int j=i+1;j<v.length;j++) {
				if (tmps[j] > tmps[i]) {
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


	public static void printingPlusOne(String name, double[] v, int dec) {
		/*DecimalFormatSymbols simD = new DecimalFormatSymbols();
		simD.setDecimalSeparator('.');
		DecimalFormat forD = new DecimalFormat("0.00000", simD);*/

		/*System.out.println(name);*/
		info=info+""+Maths.spaces(name,3)+" ";
		for(int i=0;i<v.length;i++) {
			/*System.out.print(" "+forD.format(v[i]));*/
			info=info+Maths.precisionAndSpaces(v[i]+1, dec, dec+7) +" ";
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


	public static void setVFactor(double v) {
		if (v >= 0 && v <= 1) {
			vFactor = v;
		} else
			vFactor = 0.5 * (n + 1) / n;
		
	}


	public static void setPreferenceFunctionsAsSame(String pfsr) {
		pfs = new String[n];
		for (int j = 0; j < n; j++) {  
			pfs[j] = pfsr; 
		} 
	}
	

	public static void setPreferenceFunctions(String[] pfsr) {
		pfs = pfsr; 
	}
	
	 
	public static void setDecimal(int t) {
		decimal = t;
	}
	
	public static void setLimPreferenceFunctionsAsSame(double l, double u) {
		limpfs = new double[2][n];
		for (int j = 0; j < n; j++) {  
			limpfs[0][j] = 1; 
			limpfs[1][j] = 2; 
		} 
	}
	

	public static void setLimPreferenceFunctions(double[][]  pfsr) {
		limpfs = pfsr; 
	}
	 
}
