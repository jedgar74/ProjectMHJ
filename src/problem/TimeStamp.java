package problem;

import state.Solution;

public class TimeStamp {
	public Solution state ;
	public int time ;
	
	TimeStamp(Solution s, int time){
		state = s;
		this.time = time;
	}
}
