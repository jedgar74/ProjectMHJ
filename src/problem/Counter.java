package problem;


/**
 * Counter
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class Counter {
	public int count;
	public int limit;
	
	
	public Counter(int limit) {
		super();
		this.limit = limit;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public void incCount(){
		this.count = this.count + 1;
	}
}
