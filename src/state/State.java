package state;

import basic.Variable;

public class State {
	public Variable[] var;
	public double fitness;
	public int nVar;
	public String typeVar;

	public State(int nVar ) {
		this.nVar=nVar; 
		this.fitness=0;
	}
	
	public State(int nVar,  String type, double _down, double _up) {
		this.nVar=nVar; 
		this.fitness=0;
		defType(type, _up, _down);
	}
	
	public void defType(String type, double _down, double _up) {
		/*if (type.equals("INTEGER")){
			this.typeVar="INTEGER";
			this.var = new IntegerN[nVar]; 
			for (int i=0; i< nVar; i++){
				var[i] = new IntegerN(0,  (int)_down,  (int) _up);
			}
		} else {
			this.typeVar="REAL";
			this.var = new RealN[nVar]; 
			for (int i=0; i< nVar; i++){
				var[i] = new RealN(0,  _down,  _up);
			}*/
		
	}
	 
	public void print( ) {
		
		/*if (this.typeVar.equals("INTEGER")){ 
			for (int i = 0; i < nVar; i++) {
				 System.out.print ( ((IntegerN) var[i]).getValue()+ " " );
			} 
		}  else { 
			for (int i = 0; i < nVar; i++) {
				 System.out.print ( ((RealN) var[i]).getValue() + " " );
			} 
		}
		 System.out.println();*/
	}
	
	public void setValue(int index, double r){
	/*	((RealN) var[index]).setValue(r);
	}*/
	}
	public void setValue(int index, int r){
		//((IntegerN) var[index]).setValue(r);
	}
	
	public void genStateRandom(String type ) {
		/*if (type.equals("INTEGER")){
			for (int i=0; i< nVar; i++){
				var[i] = new IntegerN( 0, 1);
				var[i].randomize();
			}
		}  else {
			for (int i=0; i< nVar; i++){
				var[i] = new RealN( 0, 1);
				var[i].randomize();
			}
		} */
	}
	
}
