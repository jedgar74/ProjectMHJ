package landscape;
import statistics.BasicStats;

public class Landscape {
	public double corrR1;
	public double corrLength;
	BasicStats data = new BasicStats();
		
	
	public void calcCorrR1(){
		int m = data.nSolutions();
		double media = data.average();
		double sigma = data.stDeviat(media);
	
		double sum=0;
		for (int i=0; i<m-1;i++){
			sum=sum+((data.getSolution(i+1)-media)*(data.getSolution(i)-media));
		}
		
		corrR1=(1/(sigma*sigma*(m-1)))*sum;
	}
	
	/**
	 * The lower the correlation length the more rugged the landscape.
	 */
	public void calcLength(){
	   if (corrR1==0){
		   this.corrLength=0;
	   } else {
		   this.corrLength = -1/(Math.log(Math.abs(this.corrR1)));
	   } 
	}
	
	public BasicStats getData() {
		return data;
	}

	public void setData(BasicStats data) {
		this.data = data;
	}


}
