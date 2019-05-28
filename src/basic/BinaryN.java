package basic;

import util.Randomizer;

public class BinaryN implements Variable{
	int value;
	int uLimit;
	int lLimit;
	
	public BinaryN( ){ 
		this.value =0;
	}
	
	public BinaryN(int value ){
		this.value=value; 
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int r){
		value=r;
	}
	
	public BinaryN copy() {
		return new BinaryN(value);
	}
	
	public void randomize(){
		value = Randomizer.getInt(2)*2 -1;
	}
}
