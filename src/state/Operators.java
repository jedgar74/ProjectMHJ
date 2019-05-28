package state;

import java.util.ArrayList;
import java.util.Random;

import basic.OnlyInteger;
import basic.BinaryN;
import problem.Problem;
import util.Maths;
import util.Randomizer;


/**
 * Operators
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class Operators { 
	
	/*public void swappping(Variables v, int t1, int t2){
		if (v.type.equals("PERMUTATIONAL")){ 
			int aux = ((OnlyInteger) v.allvar[t1]).getValue();
			((OnlyInteger) v.allvar[t1]).setValue(((OnlyInteger) v.allvar[t2]).getValue());
			((OnlyInteger) v.allvar[t2]).setValue(aux);
		}
	}*/
	
	
	/*public void swappping(Variables v){
		if (v.type.equals("PERMUTATIONAL")){ 
			int t1 = Randomizer.getInt(v.allvar.length);
			int t2 = Randomizer.getInt(v.allvar.length);
			
			while (t1 == t2){
				t2 = Randomizer.getInt(v.allvar.length);
			}
			swappping(v, t1, t2);
		} 
	}*/
	
	
	public void flipping(Solution s1){
		
		if (s1.var.type.equals("BINARY")){  
			int t1 = Randomizer.getInt(s1.nVar); 
			 
			((BinaryN) s1.var.allvar[t1]).setValue(((BinaryN) s1.var.allvar[t1]).getValue()*-1);
		}  
	}
	
	/**
	 * Revise this op Must be implemented with random shifts instead of block shifting
	 * 
	 *  
	 * @param bits
	 */
	/*public void swappingLevyFlight(Solution s1, int nflips, int positions){*/
     public void swappingLevyFlight(Solution s1, Solution sb, double[] param){
        ArrayList<String> pos = new ArrayList<String>();
		int x = 0; 
		double nPos = 0;
		/**
		 * Calculating the steps to swap
		 * 
		 * param[0] = beta
		 * param[1] = theta
		 */
        /*double _sizeFitness = _theta;*/
        double _sizeFitness = param[1] +1; //add one to avoid a division by zero
        double u=Math.random()*_sizeFitness* Maths.sigma(param[0]); 
        double v=Math.random()*_sizeFitness;
        /*System.out.println("s "+u + " " + v);*/
        double uv = u/Math.pow(Math.abs(v),(1/param[0]));

        double stepsize = 0.5*uv*Math.abs(s1.fitness - sb.fitness);  

        //      System.out.println("u "+u+"  v "+v+"  step "+step+"  stepsize "+stepsize);
        nPos = stepsize* Randomizer.getDouble()*_sizeFitness; 
        nPos = Math.floor(nPos)+0;
        //  System.out.println("r "+nPos);
        if (nPos > s1.nVar)
        	nPos = s1.nVar -1 ;
        else if (nPos < 2)
        	nPos = 2;
        
        int nSwaps = (int) nPos;
        /* System.out.println("r "+nSwaps + " " + nPos); */
		/**
		 * swapping this steps
		 */
		while (x < nSwaps){
			int uw = Randomizer.getInt(s1.nVar);
			if (! pos.contains(""+uw)){
				pos.add(""+uw);
				x++;
			}
		} 
		/*System.out.println("r "+nSwaps + " " + pos.size());*/ 
		//sort positions
		
		
		for(int i = 0; i < pos.size(); i++){
			for(int f = i+1; f < pos.size(); f++){
				if (Integer.parseInt(pos.get(i)) >  Integer.parseInt(pos.get(f))){	
					String aux = pos.get(i);
					pos.set(i, pos.get(f));
					pos.set(f, aux);
				}				 
			}
		}

		// blocking 
		/*int j = Randomizer.getInt(s1.nVar - nSwaps +1 );*/
		/*System.out.println("r "+nSwaps + " " + pos.size()+ " " + s1.nVar + " "+j);*/
		int h = ((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(0)) ]).getValue();
		 
		for(int i = 0; i < nSwaps - 1; i++){ 
			((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(i)) ]).setValue(
					((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(i+1)) ]).getValue() ); 
		}		
		((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(nSwaps - 1 )) ]).setValue(h); 
	} 
	
     
     public void swappingLevyFlight(Solution s1, Solution sb, int nSwaps ){
         ArrayList<String> pos = new ArrayList<String>();
 		int x = 0;  
 		 
 		  
 		/**
 		 * swapping this steps
 		 */
 		while (x < nSwaps){
 			int uw = Randomizer.getInt(s1.nVar);
 			if (! pos.contains(""+uw)){
 				pos.add(""+uw);
 				x++;
 			}
 		} 
 		/*System.out.println("r "+nSwaps + " " + pos.size());*/ 
 		//sort positions
 		
 		
 		for(int i = 0; i < pos.size(); i++){
 			for(int f = i+1; f < pos.size(); f++){
 				if (Integer.parseInt(pos.get(i)) >  Integer.parseInt(pos.get(f))){	
 					String aux = pos.get(i);
 					pos.set(i, pos.get(f));
 					pos.set(f, aux);
 				}				 
 			}
 		}

 		// blocking 
 		/*int j = Randomizer.getInt(s1.nVar - nSwaps +1 );*/
 		/*System.out.println("r "+nSwaps + " " + pos.size()+ " " + s1.nVar + " "+j);*/
 		int h = ((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(0)) ]).getValue();
 		 
 		for(int i = 0; i < nSwaps - 1; i++){ 
 			((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(i)) ]).setValue(
 					((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(i+1)) ]).getValue() ); 
 		}		
 		((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(nSwaps - 1 )) ]).setValue(h); 
 	} 
     
    /**
     * 
     *  
     * @param s1
     * @param k
     */
	public void swappingKNeigh(Solution s1, int k){
		if (s1.var.type.equals("PERMUTATIONAL")){  
			int nElementsXFlips = k+1; 
			if (nElementsXFlips < s1.nVar && nElementsXFlips >= 2){
				ArrayList<String> pos = new ArrayList<String>();
				int control = 0;
				while (control < nElementsXFlips){
					int t2 = Randomizer.getInt(s1.nVar);
				 
					if (! pos.contains(""+t2)){
					      pos.add(""+t2);
					      control++;
					}
				}
				
				for (int i = 0; i < nElementsXFlips - 1; i++) { 
					int aux = ((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(i))]).getValue();
					((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(i))]).setValue(((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(i+1))]).getValue());
					((OnlyInteger) s1.var.allvar[Integer.parseInt(pos.get(i+1))]).setValue(aux);
				} 
			}
		} 
	}
	
	public void swapping(Solution s1){
		if (s1.var.type.equals("PERMUTATIONAL")){  
			int t1 = Randomizer.getInt(s1.nVar);
			int t2 = Randomizer.getInt(s1.nVar);
			
			while (t1 == t2){
				t2 = Randomizer.getInt(s1.nVar);
			}
			int aux = ((OnlyInteger) s1.var.allvar[t1]).getValue();
			((OnlyInteger) s1.var.allvar[t1]).setValue(((OnlyInteger) s1.var.allvar[t2]).getValue());
			((OnlyInteger) s1.var.allvar[t2]).setValue(aux);
		} 
	}
	
	
	public void swappingDiff(Solution s1, double gamma, Solution s2){
		if (s1.var.type.equals("PERMUTATIONAL")){  
			int n = distance (s1, s2);
			int[][] d = new int[n][2];
			int j=0;
			for (int i = 0; i < s1.nVar; i++) {
				if (((OnlyInteger) s1.var.allvar[i]).getValue() != ((OnlyInteger) s2.var.allvar[i]).getValue() ){
					d[j][0]=((OnlyInteger) s1.var.allvar[i]).getValue();
					d[j][1]=i;
					j++;
				} 
			} 
			
			int ch=(int)(gamma*d.length); 
			
			for (int i = 0; i < ch; i++) {
				int init = Randomizer.getInt(d.length);
				int fins = Randomizer.getInt(d.length);

				while (init == fins)
					fins = Randomizer.getInt(d.length);
				
				int aux = d[init][0];
				d[init][0] = d[fins][0];
				d[fins][0] = aux;
			}
			
			for (int i = 0; i < d.length; i++) {
				((OnlyInteger) s1.var.allvar[d[i][1]]).setValue(d[i][0]); 
			}
		} 
	}
	
	public void swapping(Solution s1, int t1, int t2){
		if (s1.var.type.equals("PERMUTATIONAL")){  
			int aux = ((OnlyInteger) s1.var.allvar[t1]).getValue();
			((OnlyInteger) s1.var.allvar[t1]).setValue(((OnlyInteger) s1.var.allvar[t2]).getValue());
			((OnlyInteger) s1.var.allvar[t2]).setValue(aux);
		} 
	}
	
	public void swappingRBI(Solution s1){
		if (s1.var.type.equals("PERMUTATIONAL")){  
			int pinit = Randomizer.getInt(s1.nVar - 1);
			int tmp = (int) ((s1.nVar - pinit) / 2);
			int sizeBlock = Randomizer.getInt(tmp) + 1;
			/*System.out.println(""+sizeBlock);*/
			int pend = s1.var.allvar.length + 1;

			while (pend + sizeBlock > s1.nVar)
				pend = (pinit + sizeBlock) + Randomizer.getInt( s1.nVar - pinit - 1) ;

			for (int i = 0; i < sizeBlock; i++) { 
				int aux = ((OnlyInteger) s1.var.allvar[pinit + i]).getValue();
				((OnlyInteger) s1.var.allvar[pinit + i]).setValue(((OnlyInteger) s1.var.allvar[pend + i]).getValue());
				((OnlyInteger) s1.var.allvar[pend + i]).setValue(aux);
			} 
		} 
	}
	/**
	 * method proposed in ...
	 * 
	 * @param v
	 */
	/*public void swappingRBI(Variables v) {
		if (v.type.equals("PERMUTATIONAL")){ 
			int pinit = Randomizer.getInt( v.allvar.length -1 );
			int tmp = (int) ((v.allvar.length - pinit) / 2);
			int sizeBlock = Randomizer.getInt( tmp) + 1;
			System.out.println(""+sizeBlock);
			int pend = v.allvar.length + 1;

			while (pend + sizeBlock > v.allvar.length)
				pend = (pinit + sizeBlock) + Randomizer.getInt(  v.allvar.length - pinit - 1) ;

			for (int i = 0; i < sizeBlock; i++) { 
				int aux = ((OnlyInteger) v.allvar[pinit + i]).getValue();
				((OnlyInteger) v.allvar[pinit + i]).setValue(((OnlyInteger) v.allvar[pend + i]).getValue());
				((OnlyInteger) v.allvar[pend + i]).setValue(aux);
			}
		}
	}*/

	/**
	 * crossover One Point
	 * 
	 * @param v
	 */
	public void crossoverOnePoint(Solution s1, Solution s2) {
		 if (s1.var.type.equals("BINARY")){ 
			//we avoid the extremes, i.e, 1 and n
			int p = Randomizer.getInt(1, s1.var.allvar.length -1 ); 
 
			for (int i = p; i < s1.var.allvar.length; i++) { 
				int aux = ((BinaryN) s1.var.allvar[ i]).getValue();
				((BinaryN) s1.var.allvar[ i]).setValue(((BinaryN) s2.var.allvar[ i]).getValue());
				((BinaryN) s2.var.allvar[ i]).setValue(aux);
			}
		} else if (s1.var.type.equals("PERMUTATIONAL")){ 
			//we avoid the extremes, i.e, 1 and n
			int p = Randomizer.getInt(1, s1.var.allvar.length -1 ); 
			ArrayList<String> elements = new ArrayList<String>(); 
			  
			for (int i = 0; i < p; i++) { 
				elements.add(""+((OnlyInteger) s1.var.allvar[i]).getValue());
			}
			
			int temp = p;
			for (int i = 0; i < s2.var.allvar.length; i++) { 
				int aux = ((OnlyInteger) s2.var.allvar[ i]).getValue();
				if (! elements.contains(""+aux)) {
					((OnlyInteger) s1.var.allvar[temp]).setValue(((OnlyInteger) s2.var.allvar[ i]).getValue());
					temp++;
					/*if (temp == s2.var.allvar.length)
						break;*/
				}
			}
		}
	}
	
	
	/**
	 * crossover Two Points
	 * 
	 * @param v
	 */
	public void crossoverTwoPoints(Solution s1, Solution s2) {
		if (s1.var.type.equals("BINARY")){
			//we avoid the extremes, i.e, 1 and n
			int p1 = Randomizer.getInt(1, s1.var.allvar.length - 2 ); 
			int p2 = Randomizer.getInt(p1, s1.var.allvar.length - 1 );
			
			for (int i = p1; i < p2; i++) { 
				int aux = ((BinaryN) s1.var.allvar[ i]).getValue();
				((BinaryN) s1.var.allvar[ i]).setValue(((BinaryN) s2.var.allvar[ i]).getValue());
				((BinaryN) s2.var.allvar[ i]).setValue(aux);
			}
		} else if (s1.var.type.equals("PERMUTATIONAL")){ 
			//we avoid the extremes, i.e, 1 and n
			int p1 = Randomizer.getInt(1, s1.var.allvar.length -2 ); 
			int p2 = Randomizer.getInt(p1 , s1.var.allvar.length -1 ); 
			ArrayList<String> elements = new ArrayList<String>(); 
			
			 
			for (int i = 0; i < s2.var.allvar.length; i++) { 
				if (i >= p1 && i <= p2)
					elements.add(""+((OnlyInteger) s2.var.allvar[ i]).getValue());
				else
					elements.add("-1");
			}

			for (int i = 0; i < p1; i++) { 
				int aux = ((OnlyInteger) s1.var.allvar[ i]).getValue();
				if (! elements.contains(""+aux)) {
					elements.set(i, ""+aux);
				}
			} 

			for (int i = p2+1; i < s1.var.allvar.length; i++) { 
				int aux = ((OnlyInteger) s1.var.allvar[ i]).getValue();
				if (! elements.contains(""+aux)) {
					elements.set(i, ""+aux);
				}
			} 
			
			int control = p1;
			for (int i = 0; i < s2.var.allvar.length; i++) {
				if (elements.get(i).equals("-1")) {  
					for (int g = control; g < p2+1; g++) { 
						int aux = ((OnlyInteger) s1.var.allvar[g]).getValue(); 
						if (! elements.contains(""+aux)) {
							elements.set(i, ""+aux);
							control=g+1;
							break;
						}
					}
				}
			}

			for (int i = 0; i < s1.var.allvar.length; i++) { 
				((OnlyInteger) s1.var.allvar[i]).setValue(Integer.parseInt(elements.get(i)));		  
			}
		}
	}

	/**
	 * uniform crossover scheme (UX)
	 * 
	 * @param v
	 */
	public void crossoverUniform(Solution s1, Solution s2) {
		if (s1.var.type.equals("BINARY")){  
			
			for (int i = 0; i < s1.var.allvar.length; i++) { 
				int p = Randomizer.getInt(100);
				if (p < 50){
					int aux = ((BinaryN) s1.var.allvar[ i]).getValue();
					((BinaryN) s1.var.allvar[ i]).setValue(((BinaryN) s2.var.allvar[ i]).getValue());
					((BinaryN) s2.var.allvar[ i]).setValue(aux);
				}
			}
		} 
	}
	
	/**
	 * alternating position scheme (APX)
	 * 
	 * This method generates only one offspring
	 * 
	 * @param  
	 */
	public void crossoverAlternatingPosition(Solution s1, Solution s2) {
 
		ArrayList<String> elements = new ArrayList<String>(); 
		if (s1.var.type.equals("PERMUTATIONAL")){   
			int j=0;
			while (elements.size() < s1.var.allvar.length) {
				int elemSol1 = ((OnlyInteger) s1.var.allvar[j]).getValue();
				int elemSol2 = ((OnlyInteger) s2.var.allvar[j]).getValue();
				if (! elements.contains(""+elemSol1)) {
					elements.add("" + elemSol1 ); 
				} 
				if (! elements.contains(""+elemSol2) ) {
					elements.add("" +  elemSol2 ); 
				} 
				j++;
			} 
		 
			for (int i = 0; i < s1.var.allvar.length; i++) {  
				((OnlyInteger) s1.var.allvar[ i]).setValue(Integer.parseInt(elements.get(i)));  
			}
		}  
	}
	
	/**
	 *  position-based scheme (PBX)
	 * 
	 * This method generates only one offspring
	 * 
	 * @param   
	 */
	public void crossoverPositionBased(Solution s1, Solution s2) {
		
		ArrayList<String> elements1 = new ArrayList<String>();
		ArrayList<String> elements2 = new ArrayList<String>();
	 
		if (s1.var.type.equals("PERMUTATIONAL")){
			int i = 0; 
			while (elements2.size() < s1.var.allvar.length) {
				elements2.add("" + ((OnlyInteger) s2.var.allvar[i]).getValue());
				i++;
			} 
			i=0;
			int j=0;
			while (elements1.size() < s1.var.allvar.length) {
				int r =  Randomizer.getInt(2);  
				if (r == 1){
					elements1.add("" + ((OnlyInteger) s1.var.allvar[i]).getValue()); 
					int tmp = elements2.indexOf("" + ((OnlyInteger) s1.var.allvar[i]).getValue());
					elements2.remove(tmp);
					j++;
				} else 
					elements1.add("-1"); 
				i++;
			} 

			i = 0;
			j = elements2.size(); 
			while ( j > 0) { 
				if (elements1.get(i).equals("-1")) {
					elements1.set(i, elements2.get(0));
					elements2.remove(0);
					j = elements2.size();
				}  
				i++;
			}  
			 
			for ( i = 0; i < s1.var.allvar.length; i++) {  
				((OnlyInteger) s1.var.allvar[ i]).setValue(Integer.parseInt(elements1.get(i)));  
			}
		}  
	}
	
	/**
	 * partially matched scheme (PMX)
	 * 
	 * This method generates only one offspring
	 * 
	 * @param v
	 */
	/*public void crossoverPartiallyMatched(Solution s1, Solution s2) {
 
		ArrayList<String> elements1 = new ArrayList<String>();
		ArrayList<String> elements2 = new ArrayList<String>();
		 
		if (s1.var.type.equals("PERMUTATIONAL")){   
			int bl = (s1.var.allvar.length)/2;
			int dl = Randomizer.getInt( s1.var.allvar.length - bl);
			int ul = Randomizer.getInt( s1.var.allvar.length - dl - bl) + dl;
	 
			for (int t=0; t < s1.var.allvar.length; t++){
				elements1.add("-1");
			}
		System.out.println(" ...   "+ dl + " "+ul);
			// Copy seleted positiond from parent 1 
			for (int t=dl; t < ul+1; t++) {
				int elemSol1=((OnlyInteger) s1.var.allvar[t]).getValue();
				elements1.set(t,  ""+ elemSol1 );
				elements2.add(""+ elemSol1 );
			}
			
			// check selected swap
			for (int t = dl; t < ul + 1; t++) {
				int elemSol2 = ((OnlyInteger) s2.var.allvar[t]).getValue();
				boolean condition=true;
				for (int r=0; r < elements2.size();r++){
					if (elements2.get(r).equals(""+elemSol2 )){
						condition=false;
						break;
					}
				} 
				if (condition){
					int value1 = ((OnlyInteger) s2.var.allvar[t]).getValue();
					int index1 = t;
					int value2 = ((OnlyInteger) s2.var.allvar[t]).getValue();
					
					while (true){
						int valueT = ((OnlyInteger) s1.var.allvar[index1]).getValue();
						int index2 = 0;
						for (int j=0; j< s1.var.allvar.length; j++){
							if (((OnlyInteger) s2.var.allvar[j]).getValue()==valueT){
								index2 = j;
							}
						}
						if (index2 >= dl && index2<=ul){ 
							value2 = valueT;
							index1 = index2;
						} else {
							elements1.set(index2, ""+value1);
							break;
						}
					}
				}
			}
			
 
			for (int j=0; j< s1.var.allvar.length; j++){
				if (elements1.get(j).equals("-1")){
					elements1.set(j, ""+((OnlyInteger) s2.var.allvar[j]).getValue());
				} 
			} 
			 
			for (int i = 0; i < s1.var.allvar.length; i++) {
				((OnlyInteger) s1.var.allvar[ i]).setValue(Integer.parseInt(elements1.get(i)));  
			} 
		}  
	}*/
	public void crossoverPartiallyMatched(Solution s1, Solution s2) {
		 
		ArrayList<String> elements1 = new ArrayList<String>();
		ArrayList<String> relation1 = new ArrayList<String>();
		ArrayList<String> relation2 = new ArrayList<String>();
		
		if (s1.var.type.equals("PERMUTATIONAL")){   
			int bl = (s1.var.allvar.length)/2;
			int dl = Randomizer.getInt( s1.var.allvar.length - bl);
			int ul = Randomizer.getInt( s1.var.allvar.length - dl - bl) + dl;
	 
			for (int t=0; t < s1.var.allvar.length; t++){
				elements1.add("-1");
			}
		
			/*System.out.println(" ...   "+ dl + " "+ul);*/
			// Copy seleted positiond from parent 1 
			for (int t=dl; t < ul+1; t++) {
				int elemSol2=((OnlyInteger) s2.var.allvar[t]).getValue();
				elements1.set(t,  ""+ elemSol2 );
				relation2.add(""+ elemSol2 );
				relation1.add(""+ ((OnlyInteger) s1.var.allvar[t]).getValue() );
			}
 
			for (int j=0; j< s1.var.allvar.length; j++){
				int aux = ((OnlyInteger) s1.var.allvar[j]).getValue();
				if (elements1.get(j).equals("-1") && ! elements1.contains(""+aux) ){
					elements1.set(j,  ""+ aux );					
				} else if (elements1.get(j).equals("-1") && ! relation1.isEmpty()){
					int k = relation2.indexOf(""+aux);
					elements1.set(j,  ""+ relation1.get(k));	
				}
			} 
			 
			for (int i = 0; i < s1.var.allvar.length; i++) {
				((OnlyInteger) s1.var.allvar[ i]).setValue(Integer.parseInt(elements1.get(i)));  
			} 
		}  
	}
	
	
	/**
	 * cycle scheme (CX)
	 * 
	 * This method generates only one offspring
	 * 
	 * @param v
	 */
	public void crossoverCycle(Solution s1, Solution s2) {

		ArrayList<String> elements1 = new ArrayList<String>(); 
		if (s1.var.type.equals("PERMUTATIONAL")){   
			int i = 0;
			int nCycles = 0;
			int count = 0;
			int temporal = 0;
			int uno = 0;
			int dos = 0;

			ArrayList<String> labels = new ArrayList<String>();
			ArrayList<Cycles> cycles = new ArrayList<Cycles>();
			for (int k = 0; k < s1.var.allvar.length; k++) {
				labels.add("N");
			}

			while (temporal < s1.var.allvar.length && count < s1.var.allvar.length) { 
				uno =  ((OnlyInteger) s1.var.allvar[count]).getValue() ;
				dos =  ((OnlyInteger) s2.var.allvar[count]).getValue() ;
			 
				if (dos != uno) {
					if (labels.get(count).equals("N")) {
						Cycles cycle = new Cycles(count); 
					    labels.set(count, "Y");  
						while (dos != uno) { 
							int index = 0;
							for (int k = 0; k < s1.var.allvar.length; k++) {
								if (((OnlyInteger) s1.var.allvar[k]).getValue() == dos && 
										labels.get(k).equals("N")) {
									index = k;
									break;
								}
							}  
							labels.set(index, "Y"); 
							cycle.addElementToCycle(index);
							dos = ((OnlyInteger) s2.var.allvar[index]).getValue() ; 
						} 
						cycles.add(cycle); 
					}
				} else {
					labels.set(count, "Y");
				}

				int rx = 0;
				for (int ex = 0; ex < labels.size(); ex++) {
					if (labels.get(ex).equals("Y")) {
						rx++;
					}
				}
				temporal = rx; 
				count++;
			}

			for (int e = 0; e < s1.var.allvar.length; e++) {
				elements1.add("" + ((OnlyInteger) s1.var.allvar[e]).getValue() );
			}

			 
			nCycles = cycles.size();
			int r = 0;
			while (r < nCycles) {
				/*cycles.get(r).print();*/
				/*int g = Randomizer.getInt( 2); */ 
				int g = r%2;
				if (g == 1) {
					int w = 0;
					while (w < ((Cycles) cycles.get(r)).elementOfCycle) {
						int m = ((Cycles) cycles.get(r)).getElement(w);
						elements1.set(m, "" + ((OnlyInteger) s2.var.allvar[m]).getValue() ); 
						w++;
					}
				}
				r++; 
			}

			for (i = 0; i < s1.var.allvar.length; i++) {
				((OnlyInteger) s1.var.allvar[ i]).setValue(Integer.parseInt(elements1.get(i)));  
			} 
		}  
	}

	/**
	 * uniform modifies scheme (U2X)
	 *  
	 * This method was used by Amaya in ...
	 * 
	 * This method generates only one offspring
	 * 
	 * @param v
	 */
	public void crossoverUniformModified(Solution s1, Solution s2) {
 
		ArrayList<String> elements = new ArrayList<String>(); 
		if (s1.var.type.equals("PERMUTATIONAL")){   
			int i = 0;  

			int count=0;
			for(i = 0; i < s1.var.allvar.length; i++) {
				 
				int uno = ((OnlyInteger) s1.var.allvar[i]).getValue();
				int dos = ((OnlyInteger) s2.var.allvar[i]).getValue(); 

				if (Randomizer.getInt( 2) == 0) {
					if (! elements.contains("" + ((OnlyInteger) s1.var.allvar[i]).getValue() )){
						elements.add("" + ((OnlyInteger) s1.var.allvar[i]).getValue()); 
					} else {
						elements.add("-1");
					}
				} else {
					if (! elements.contains("" + ((OnlyInteger) s2.var.allvar[i]).getValue() )){
						elements.add("" + ((OnlyInteger) s2.var.allvar[i]).getValue() ); 
					} else {
						elements.add("-1");
					}
				}
			}
			
			while (count < s1.var.allvar.length) {
				if (elements.get(count).equals("-1")){
					i = Randomizer.getInt( s1.var.allvar.length);
					int uno = ((OnlyInteger) s1.var.allvar[i]).getValue();
					int dos = ((OnlyInteger) s2.var.allvar[i]).getValue();  
					 
					if (Randomizer.getInt( 2) == 0) {
						if (! elements.contains("" + ((OnlyInteger) s1.var.allvar[i]).getValue() )){
							elements.set(count, "" + ((OnlyInteger) s1.var.allvar[i]).getValue() ); 
						} 
					} else {
						if (! elements.contains("" + ((OnlyInteger) s2.var.allvar[i]).getValue() )){
							elements.set(count, "" + ((OnlyInteger) s2.var.allvar[i]).getValue() ); 
						} 
					} 
				} else {
					count++;
				} 
			} 
		 
			for ( i = 0; i < s1.var.allvar.length; i++) {  
				((OnlyInteger) s1.var.allvar[ i]).setValue(Integer.parseInt(elements.get(i)));  
			}
		}  
	}

	/**
	 * order scheme (OX)
	 *  http://www.cs.colostate.edu/~genitor/1995/permutations.pdf
	 * 
	 * This method generates only one offspring
	 * 
	 * @param v
	 */
	/*public void crossoverOrder(Solution s1, Solution s2) {
 
		ArrayList<String> elements = new ArrayList<String>(); 
		if (s1.var.type.equals("PERMUTATIONAL")){   
			  
			int bl = s1.var.allvar.length/2;
			int dl = Randomizer.getInt(s1.var.allvar.length - bl);
			int ul = Randomizer.getInt(s1.var.allvar.length -dl - bl) +dl;
	 
			System.out.println(""+dl+" "+ul);
			for (int t=0; t < s1.var.allvar.length; t++){
				elements.add("-1");
			}
			
			// Copy seleted positiond from parent 1 
			for (int t=dl; t < ul+1; t++) {
				int uno = ((OnlyInteger) s1.var.allvar[t]).getValue();
				elements.set(t,  ""+uno );	 
			}
			
			int i = 0; //other parent
			int j = 0;
			while (j < s1.var.allvar.length) { 
				int dos = ((OnlyInteger) s2.var.allvar[i]).getValue();  
				if (elements.get(j).equals("-1")) {
					if (! elements.contains(""+dos)){
						elements.set(j, ""+ dos);
						j++;
					} else	{
						i++; 
					}
				} else {
					j++;
				}  
			}
			 
			for ( i = 0; i < s1.var.allvar.length; i++) {  
				((OnlyInteger) s1.var.allvar[ i]).setValue(Integer.parseInt(elements.get(i)));  
			}
		}  
	}*/
	public void crossoverOrder(Solution s1, Solution s2) {
		 
		ArrayList<String> elements = new ArrayList<String>(); 
		ArrayList<String> fillerBlock = new ArrayList<String>();
		if (s1.var.type.equals("PERMUTATIONAL")){   
			  
			int bl = s1.var.allvar.length/2;
			int dl = Randomizer.getInt(s1.var.allvar.length - bl);
			int ul = Randomizer.getInt(s1.var.allvar.length -dl - bl) +dl;
	  
			for (int t=0; t < s1.var.allvar.length; t++){
				elements.add("-1");
			}
			
			// Copy seleted positiond from parent 1 
			for (int t=dl; t < ul+1; t++) {
				int uno = ((OnlyInteger) s1.var.allvar[t]).getValue();
				elements.set(t,  ""+uno );	 
			}
			
			for (int t=0; t < s1.var.allvar.length; t++){
				int dos = ((OnlyInteger) s2.var.allvar[t]).getValue();
				if (! elements.contains(""+dos))
					fillerBlock.add(""+dos);
			}

			for (int i = ul+1; i < s1.var.allvar.length; i++) {  
				if ( elements.get(i).equals("-1")) {
					elements.set(i, fillerBlock.get(0));
					fillerBlock.remove(0);
				}
			} 
			for (int i = 0; i < dl; i++) {  
				if ( elements.get(i).equals("-1")) {
					elements.set(i, fillerBlock.get(0));
					fillerBlock.remove(0);
				}
			} 

			 
			for (int i = 0; i < s1.var.allvar.length; i++) {  
				((OnlyInteger) s1.var.allvar[ i]).setValue(Integer.parseInt(elements.get(i)));  
			}
		}  
	}
	 
	
	
	public ArrayList<Solution> neighbourhood(Solution _initial, double _factor, Problem problem){
		ArrayList<Solution> neighbors = new ArrayList<Solution>(); 

		int _factor2 = (int)(_factor * problem.nVar);

		for (int i = 0; i < _factor2; i++){
			Solution _tmp = (Solution) _initial.clone(); 
 
			swapping(_tmp);  
			problem.evaluate(_tmp);
			problem.counter.incCount(); 

			if (! neighbors.contains(_tmp))
				neighbors.add(_tmp);
			else 
				i--;
		} 
		return neighbors;
	}
	
	public Solution getBestNeighbour(Solution _initial, double _factor, Problem problem){
		ArrayList<Solution> neighbors = new ArrayList<Solution>(); 

		int _factor2 = (int)(_factor * problem.nVar);

		for (int i = 0; i < _factor2; i++){
			Solution _tmp = (Solution) _initial.clone(); 
 
			swapping(_tmp);  
			problem.evaluate(_tmp);
			problem.counter.incCount(); 

			if (! neighbors.contains(_tmp))
				neighbors.add(_tmp);
			else 
				i--;
		} 
		
		int best = 0;

		// ...Get the best of neighbourhood
		for (int d = 1; d < neighbors.size(); d++) {
			if ( best(neighbors.get(d), neighbors.get(best), problem)  ) {
			/*if ((neighbors.get(d)).best(neighbors.get(best))) {*/
				best = d;
			}
		} 
		
		return (Solution) neighbors.get(best).clone();
	}

	
	public Solution getBestNeighbourBinary(Solution _initial, double _factor, Problem problem){
		ArrayList<Solution> neighbors = new ArrayList<Solution>(); 

		int _factor2 = (int)(_factor * problem.nVar);

		for (int i = 0; i < _factor2; i++){
			Solution _tmp = (Solution) _initial.clone(); 
 
			flipping(_tmp);  
			problem.evaluate(_tmp);
			problem.counter.incCount(); 

			if (! neighbors.contains(_tmp))
				neighbors.add(_tmp);
			else 
				i--;
		} 
		
		int best = 0;

		// ...Get the best of neighbourhood
		for (int d = 1; d < neighbors.size(); d++) {
			if ( best(neighbors.get(d), neighbors.get(best), problem)  ) {
			/*if ((neighbors.get(d)).best(neighbors.get(best))) {*/
				best = d;
			}
		} 
		
		return (Solution) neighbors.get(best).clone();
	}
	
	public Solution getBestNeighbourKNeighs(Solution _initial, double _factor, int kneighs, Problem problem){
		ArrayList<Solution> neighbors = new ArrayList<Solution>(); 

		int _factor2 = (int)(_factor * problem.nVar);

		for (int i = 0; i < _factor2; i++){
			Solution _tmp = (Solution) _initial.clone(); 
 
			this.swappingKNeigh(_tmp, kneighs);  
			problem.evaluate(_tmp);
			problem.counter.incCount(); 

			if (! neighbors.contains(_tmp))
				neighbors.add(_tmp);
			else 
				i--;
		} 
		
		int best = 0;

		// ...Get the best of neighbourhood
		for (int d = 1; d < neighbors.size(); d++) {
			if ( best(neighbors.get(d), neighbors.get(best), problem)  ) {
			/*if ((neighbors.get(d)).best(neighbors.get(best))) {*/
				best = d;
			}
		} 
		
		return (Solution) neighbors.get(best).clone();
	}
	
	

	public Solution getBestNeighbourComplete(Solution initial, Problem problem){
		Solution better = (Solution) initial.clone(); 
	     
		for (int i = 0; i < initial.nVar -1; i++){
			for (int j = i+1; j < initial.nVar; j++){
			 
				Solution _tmp = null;
				_tmp = (Solution) initial.clone();  

				swapping(_tmp, i, j);  
				problem.evaluate(_tmp);
				problem.counter.incCount(); 
                
				if ( best( _tmp, better , problem) ) {
					 better = (Solution) _tmp.clone();
					 /*_tmp.print("@@@");*/
				}
			} 
		} 
		/* better.print("ooo");
		System.out.println();*/
		return better;
	}
	
	
	public boolean best(Solution s1, Solution s2, Problem problem){
		boolean b = false;
		if  ((s1.fitness < s2.fitness) && problem.typeProblem.equals("MIN"))
			b = true;
		else if  ((s1.fitness > s2.fitness) && problem.typeProblem.equals("MAX"))
			b = true;
		return b;
	} 
	
	
	public int[] roulette(Population p){
		int[] indexSols = new int[2];
		double sum = p.totalFitness(); 
		
		for (int i = 0; i < 2; i++){
			double acc = 0;
			double r = Randomizer.getDouble()*sum; 
			
			for (int j = 0; j < p.size(); j++){
				acc = acc + p.get(j).fitness; 
				if (acc >= r){
					indexSols[i] = j;
					break;
				}
			}
		}
		
		return indexSols;
	}
	
	
	public int[] ranking(Population p){
		int[] indexSols = new int[2];
		double sum = p.sumInverseRankings();
		
		for (int i=0; i<2;i++){
			double c=0;
			double r = Randomizer.getDouble()*sum;
			for (int j=0; j<p.size();j++){
				c =c+ (1/(j+1));
				if (c>= r){
					indexSols[i]=j;
					break;
				}
			}
		}
		return indexSols;
	}
	
	public int[] tournament(Problem problem, Population p, int size){
		int[] s = new int[2];
		double sum = p.totalFitness();
		
		for (int i=0; i<2;i++){
			int c = Randomizer.getInt(p.size());
	
			for (int j=1; j< size;j++){
				int c1 = Randomizer.getInt(p.size());
				if (this.best(p.get(c1), p.get(c), problem)){
					c = c1;
				}
			}
			s[i]=c;
		}
		return s;
	}
	
	public boolean equals(Solution s1, Solution s2 ){
		boolean b = false;
		if (s1.typeVar.equals("PERMUTATIONAL")){
			if  (s1.fitness == s2.fitness){
				int k=0;
				while ( k < s1.nVar){        
					if (((OnlyInteger) s1.var.allvar[k]).getValue()
							!= ((OnlyInteger) s2.var.allvar[k]).getValue()){ 
						break;
					}
					k++;
				}
				if (k == s1.nVar)
					b = true;
			}
		} else if (s1.typeVar.equals("BINARY")){
			if  (s1.fitness == s2.fitness){
				int k=0;
				while ( k < s1.nVar){        
					if (((BinaryN) s1.var.allvar[k]).getValue()
							!= ((BinaryN) s2.var.allvar[k]).getValue()){ 
						break;
					}
					k++;
				}
				if (k == s1.nVar)
					b = true;
			}
		}
		return b;
	}
	
	public int distance (Solution s1, Solution s2){
		int c =0;
		if (s1.var.type.equals("PERMUTATIONAL")){ 
			for (int i = 0; i < s1.var.allvar.length; i++) {  
				if (((OnlyInteger) s1.var.allvar[ i]).getValue() != 
						((OnlyInteger) s2.var.allvar[ i]).getValue() ){
					c++;
				} 
			}

		} 
		return c;
	}

}
