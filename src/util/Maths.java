package util;


/**
 * Maths
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class Maths {
	
	public static int IINFPOS = 2147483647;
    public static int IINFNEG =-2147483648;
			
	public static double logGamma(double x) {
	      double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
	      double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
	                       + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
	                       +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
	      return tmp + Math.log(ser * Math.sqrt(2 * Math.PI));
	}
	
	public static double gamma(double x) { 
		return Math.exp(logGamma(x)); 
	}
	
    
	public static double sigma(double _beta){ 
		double sigma = (gamma(1+_beta)*Math.sin(Math.PI*_beta/2)) / (gamma((1+_beta)/2)*_beta*Math.pow(2, (_beta-1)/2));

		return Math.pow(sigma, 1/_beta);
	}
	
	
	public static double roundToNDecimals(double numero, int  decimals ) {
		return Math.round(numero*Math.pow(10, decimals))/Math.pow(10, decimals);
	}
}
