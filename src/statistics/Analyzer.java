package statistics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import util.Maths;
import util.Matrix;


/**
 * Analyzer
 * 
 * 
 * @author Jhon E. Amaya
 *
 */
public class Analyzer {
	ArrayList<BasicStats> nameParameters = new ArrayList<BasicStats>();
	ArrayList<String> instances = new ArrayList<String>();
    String info ="";
	
    public BasicStats getInfo(int i){
	
		return nameParameters.get(i);
	}
    
	public void stats (String typeProblem){
		FriedmanImanHolm f = new FriedmanImanHolm(3); 
		f.setLabels(getLabels());
		f.setValues(getResults());
		f.setGraph(true);
		if (typeProblem.equals("MIN")){	
			f.minProblem();
			info = f.getInfo();
			/*System.out.println(info);*/
		} else if (typeProblem.equals("MAX")){	
			f.maxProblem();
			info = f.getInfo();
			/*System.out.println(info);*/
		}
	}
	
	public void addInfo(String inf) {
		info = info + inf;
	}
	
	public int getSize () {
		return this.nameParameters.size();
	}
	
	public Matrix getResults(){
		int i=1;
		ArrayList<String> ranges = new ArrayList<String>();
		String ins = this.instances.get(0);

		ranges.add(""+0);
		while (i < this.instances.size()){
			if (! ins.equals(this.instances.get(i))){
				ranges.add(""+i);
				ins = this.instances.get(i);
			}
			i++;
		}
		
		
		Matrix values = new Matrix(ranges.size(), nameParameters.size()/ranges.size());
		for (int rd = 0; rd < ranges.size(); rd++) { 
			int ini = Integer.parseInt(ranges.get(rd));
			int fin =0;
			if (rd < ranges.size()-1){
				fin =Integer.parseInt(ranges.get(rd+1)) ;
			} else 
				fin = nameParameters.size() ; 
			
			for (int rr = ini; rr < fin; rr++) {
				 values.setElement(rd, rr-ini, nameParameters.get(rr).average()); 
			}
			 
		}
		
		return values;
	}
	
	
	public Vector<String> getLabels(){
		Vector<String> labels = new Vector<String>();
		
		for (int rr = 0; rr < nameParameters.size(); rr++) {
			String s = nameParameters.get(rr).getLabel();
			String r = s.substring(0, s.indexOf("["));
			 if (! labels.contains(r)){
				 labels.add(r);
			 }
		}
		
		return labels;
	}
	
	
	public void add (BasicStats bs, String ins){
		this.nameParameters.add(bs);
		this.instances.add(ins);
	}

	
	public void print(){
		/*System.out.println(":: Instance :: " );
		System.out.println(":: N. Experiment.:: "+nameParameters.get(0).nSolutions());
		System.out.println("-----------------------------------");
		for (int rr = 0; rr < nameParameters.size(); rr++) {
			System.out.print (this.spaces(nameParameters.get(rr).getLabel(), 24)  );

			System.out.print (
					this.spaces(""+precision(nameParameters.get(rr).getBetter(), 2), 9) );
			System.out.print (
					this.spaces(""+precision(nameParameters.get(rr).getNBetter(), 2), 9) );
			double ave = nameParameters.get(rr).average();
			System.out.print (
					this.spaces(""+precision(ave, 2), 9) );
			System.out.println (
					this.spaces(""+precision(nameParameters.get(rr).stDeviat(ave), 2), 9) );

		}*/
		int i=1;
		ArrayList<String> ranges = new ArrayList<String>();
		String ins = this.instances.get(0);

		ranges.add(""+0);
		while (i < this.instances.size()){
			if (! ins.equals(this.instances.get(i))){
				ranges.add(""+i);
				ins = this.instances.get(i);
			}
			i++;
		}

		/*System.out.println (ranges.size());*/
		for (int rd = 0; rd < ranges.size(); rd++) {
			int ini = Integer.parseInt(ranges.get(rd));
			int fin =0;
			if (rd < ranges.size()-1){
				fin =Integer.parseInt(ranges.get(rd+1)) ;
			} else 
				fin = nameParameters.size() ;
			
			/*System.out.println (ini+".."+fin);*/
			System.out.println("\n:: Instance :: " + this.instances.get(ini));
			System.out.println(":: N. Experiment.:: "+nameParameters.get(ini).nSolutions());
			System.out.println("-----------------------------------");
			System.out.print (Maths.spaces("", 24)  );
			System.out.print (Maths.spaces("Better", 9)  );
			System.out.print (Maths.spaces("N.Bet.", 9)  );
			System.out.print (Maths.spaces("Mean", 9)  );
			System.out.print (Maths.spaces("S.D.", 9)  );
			System.out.println("-----------------------------------");
			for (int rr = ini; rr < fin; rr++) {
				System.out.print (Maths.spaces(nameParameters.get(rr).getLabel(), 24)  );

				System.out.print (
						Maths.spaces(""+Maths.precision(nameParameters.get(rr).getBetter(), 2), 9) );
				System.out.print (
						Maths.spaces(""+Maths.precision(nameParameters.get(rr).getNBetter(), 2), 9) );
				double ave = nameParameters.get(rr).average();
				System.out.print (
						Maths.spaces(""+Maths.precision(ave, 2), 9) );
				System.out.println (
						Maths.spaces(""+Maths.precision(nameParameters.get(rr).stDeviat(ave), 2), 9) );

			}
		}
	}
 
	
	public String toString(){
		String r ="";
		/*
		r = r+":: Instance :: " + "\n";
		r = r+":: N. Experiment.:: "+nameParameters.get(0).nSolutions()+"\n";
		r = r+"-----------------------------------"+"\n";
		for (int rr = 0; rr < nameParameters.size(); rr++) {
			r = r+this.spaces(nameParameters.get(rr).getLabel(), 24) ;

			r = r+ this.spaces(""+precision(nameParameters.get(rr).getBetter(), 2), 9) ;
		    r = r+ this.spaces(""+precision(nameParameters.get(rr).getNBetter(), 2), 9) ;
			double ave = nameParameters.get(rr).average();
			r = r+ this.spaces(""+precision(ave, 2), 9) ;
		    r = r+ this.spaces(""+precision(nameParameters.get(rr).stDeviat(ave), 2), 9)+"\n";

		}
		r = r+"-----------------------------------"+"\n";
		for (int rr = 0; rr < nameParameters.size(); rr++) {
			r = r+"\n"+this.spaces(nameParameters.get(rr).getLabel(), 24) +"\n";
			r = r+"-----------------------------------"+"\n";
			for (int e = 0; e < nameParameters.get(rr).nSolutions(); e++) { 
				r = r+ nameParameters.get(rr).getSolutionComplete(e).toString()+"\n";
			} 
		}*/

		int i=1;
		ArrayList<String> ranges = new ArrayList<String>();
		String ins = this.instances.get(0);

		ranges.add(""+0);
		while (i < this.instances.size()){
			if (! ins.equals(this.instances.get(i))){
				ranges.add(""+i);
				ins = this.instances.get(i);
			}
			i++;
		} 

		for (int rd = 0; rd < ranges.size(); rd++) { 
			int ini = Integer.parseInt(ranges.get(rd));
			int fin =0;
			if (rd < ranges.size()-1){
				fin =Integer.parseInt(ranges.get(rd+1)) ;
			} else 
				fin = nameParameters.size() ;
			r = r+"\n:: Instance :: " + this.instances.get(ini)+ "\n";
			r = r+":: N. Experiment.:: "+nameParameters.get(ini).nSolutions()+"\n";
			r = r+"-----------------------------------"+"\n";
			r = r+Maths.spaces("", 24)  ;
			r = r+Maths.spaces("Better", 9)   ;
			r = r+Maths.spaces("N.Bet.", 9)   ;
			r = r+Maths.spaces("Mean", 9)  ;
			r = r+Maths.spaces("S.D.", 9)  +"\n";
			r = r+"-----------------------------------"+"\n";
			for (int rr = ini; rr < fin; rr++) {
				r = r+Maths.spaces(nameParameters.get(rr).getLabel(), 24) ;

				r = r+ Maths.spaces(""+Maths.precision(nameParameters.get(rr).getBetter(), 2), 9) ;
				r = r+ Maths.spaces(""+Maths.precision(nameParameters.get(rr).getNBetter(), 2), 9) ;
				double ave = nameParameters.get(rr).average();
				r = r+ Maths.spaces(""+Maths.precision(ave, 2), 9) ;
				r = r+ Maths.spaces(""+Maths.precision(nameParameters.get(rr).stDeviat(ave), 2), 9)+"\n";

			}
			r = r+"-----------------------------------"+"\n";
			for (int rr = ini; rr < fin; rr++) {
				r = r+"\n"+Maths.spaces(nameParameters.get(rr).getLabel(), 24) +"\n";
				r = r+"-----------------------------------"+"\n";
				for (int e = 0; e < nameParameters.get(rr).nSolutions(); e++) { 
					r = r+ nameParameters.get(rr).getSolutionComplete(e).toString()+"\n";
					//r = r+ nameParameters.get(rr).getSolutionComplete(e).getFitness()+"\n";
				} 
			}
		}
		return r;
	}


	public void printFile(String info, String stat){
		try { 
			Calendar dateAndTime = new GregorianCalendar();
			File auxFile = new File("./output", info+" "+dateAndTime.get(Calendar.DAY_OF_MONTH)+"_"
					+(dateAndTime.get(Calendar.MONTH)+1)+"_"
					+dateAndTime.get(Calendar.YEAR)+" "
					+dateAndTime.get(Calendar.HOUR_OF_DAY)+"_"
					+dateAndTime.get(Calendar.MINUTE)+".txt");
			FileWriter output = new FileWriter(auxFile);

			output.write( toString()); 
			if (stat.equals("STATS"))
				output.write(this.info); 
			output.close(); 
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("....");
		} 
	}

	

	/*public void printFile(){
		try { 
			Calendar dateAndTime = new GregorianCalendar();
			File auxFile = new File("./output/Execute"+dateAndTime.get(Calendar.DAY_OF_MONTH)+"_"
					+(dateAndTime.get(Calendar.MONTH)+1)+"_"
					+dateAndTime.get(Calendar.YEAR)+" "
					+dateAndTime.get(Calendar.HOUR_OF_DAY)+"_"
					+dateAndTime.get(Calendar.MINUTE)+".txt");
			FileWriter output = new FileWriter(auxFile);

			output.write(toString()); 
			output.close(); 
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("....");
		} 
	}

    public void print(){
    	System.out.println(":: Instance :: " );
		System.out.println(":: N. Experiment.:: "+nameParameters.get(0).nSolutions());
		System.out.println("-----------------------------------");
		for (int rr = 0; rr < nameParameters.size(); rr++) {
			System.out.print (this.spaces(nameParameters.get(rr).getLabel(), 24)  );

			System.out.print (
					this.spaces(""+precision(nameParameters.get(rr).getBetter(), 2), 9) );
			System.out.print (
					this.spaces(""+precision(nameParameters.get(rr).getNBetter(), 2), 9) );
			double ave = nameParameters.get(rr).average();
			System.out.print (
					this.spaces(""+precision(ave, 2), 9) );
			System.out.println (
					this.spaces(""+precision(nameParameters.get(rr).stDeviat(ave), 2), 9) );

		}
    }*/
}
