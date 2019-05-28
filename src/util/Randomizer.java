package util;

import java.util.Random;

public class Randomizer {

	static Random r = new Random();
	
	public static int getInt(){
		return r.nextInt();
	}
	
	public static Double getDouble(){
		return r.nextDouble();
	}
	
	public static int getInt(int min, int max){
		return r.nextInt(max-min+1)+min;
	}
	
	public static int getInt(int n){
		return r.nextInt(n);
	}
	
	public static Double getDouble(Double min, Double max){
		return r.nextDouble()*(max-min)+min;
	}
}
