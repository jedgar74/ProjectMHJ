package util;

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
	
	public static String rounds(double d, int decimal){
		double presc = Math.pow(10, decimal);
		
		double tmp = d * presc;
		tmp = (double) Math.round(tmp) / presc;

		return ""+tmp;
	}
	
	public static double precision(double value, int dec){
		int pwn = (int) Math.pow(10, dec);
		int nmbr = (int)(value*pwn);
		return nmbr/(pwn*1.0);
	}

	public static String spaces(String value, int sp){
		if (value.length() < sp){
			String temp = "";
			for (int rr = 0; rr < sp-value.length(); rr++) {	
				temp = temp+" ";
			}
			return temp+value;
		} else {
			return value;
		}
	}
	
	public static String precisionAndSpaces(double value, int dec, int sp){
		 return Maths.spaces(""+Maths.precision(value, dec), sp);
	}
}
