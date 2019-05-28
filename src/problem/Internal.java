package problem;

import java.util.ArrayList;

import state.Solution;


/**
 * Internal
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class Internal {
	
	public boolean isPopulation = false;
	public Solution stateFinal;
	public Solution stateInitial; 
	ArrayList<TimeStamp> timestamps = new ArrayList<TimeStamp>();
	
	public boolean isPopulation() {
		return isPopulation;
	}

	public void setPopulation(boolean isPopulation) {
		this.isPopulation = isPopulation;
	}

	public Solution getStateFinal() {
		return stateFinal;
	}

	public void setStateFinal(Solution stateFinal) {
		this.stateFinal = stateFinal;
	}

	public Solution getStateInitial() {
		return stateInitial;
	}

	public void setStateInitial(Solution stateInitial) {
		this.stateInitial = stateInitial;
	}

	public ArrayList<TimeStamp> getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(ArrayList<TimeStamp> timestamps) {
		this.timestamps = timestamps;
	}
	
	public void addTimestamps(Solution s, int time) {
		this.timestamps.add(new TimeStamp(s,time));
	}

	public void print(){
		stateFinal.print();
	}
}
