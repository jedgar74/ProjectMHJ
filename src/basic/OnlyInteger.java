package basic;

import util.Randomizer;

public class OnlyInteger implements Variable{
	int value; 
	
	public OnlyInteger( ){ 
	}
	
	public OnlyInteger(int value ){
		this.value=value; 
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int r){
		value=r;
	}
	
	public OnlyInteger copy() {
		return new OnlyInteger(value );
	}
	
	public void randomize(){
		value = -1;
	}
}
