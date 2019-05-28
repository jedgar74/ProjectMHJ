package state;

import java.util.ArrayList; 

/**
 *  
 * 
 * ...
 * 
 * @author Jhon Edgar Amaya
 */
public class Cycles { 
	
	ArrayList<String> indexes = new ArrayList<String>();
	public int elementOfCycle = 0;

	public Cycles(int _ind) {
		this.indexes.add("" + _ind);
		this.elementOfCycle = 1; 
	}

	public void addElementToCycle(int _ind) {
		indexes.add("" + _ind);
		elementOfCycle = indexes.size();
	}

	public int getElement(int r) {
		return Integer.parseInt(this.indexes.get(r));
	}
	
	public void print (){
		for (int e = 0; e < elementOfCycle; e++) {
			 System.out.print(" "+ indexes.get(e));
		}
		System.out.println( );
	}
}
