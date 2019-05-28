package state;

import java.util.ArrayList;

import basic.BinaryN; 
import basic.OnlyInteger; 
import basic.Variable;
import util.Randomizer;


/**
 * Define the class associated to variables
 * 
 * 
 * @author Jhon Edgar Amaya
 *
 */
public class Variables  implements  Cloneable  { 
	public Variable[] allvar;
	public String type;


	Variables(String type, int nVar){
		this.type = type;
		if (type.equals("PERMUTATIONAL")){ 
			this.allvar = new OnlyInteger[nVar]; 
			for (int i=0; i< nVar; i++){
				allvar[i] = new OnlyInteger(i);
			}
		} else if (type.equals("BINARY")){
			/*this.typeVar="INTEGER";*/
			this.allvar = new BinaryN[nVar]; 
			for (int i=0; i< nVar; i++){
				allvar[i] = new BinaryN( );
			}
		}
	}


	public void init (int[] vc){
		if (type.equals("PERMUTATIONAL")){ 
			/*ArrayList<String> t = new ArrayList<String>();*/

			for (int i=0; i< allvar.length; i++){
				((OnlyInteger) allvar[i]).setValue(vc[i]);
			}  

		} /*else if (type.equals("ALLBINARY")){ 

			for (int i=0; i< allvar.length; i++){
				allvar[i].randomize();
			}
		}*/
	}


	public void initRandomize(){
		if (type.equals("PERMUTATIONAL")){ 
			ArrayList<String> t = new ArrayList<String>();

			for (int i=0; i< allvar.length; i++){
				t.add(""+i);
			} 

			int j=0;
			while(t.size() > 0){
				int y = Randomizer.getInt(t.size());
				((OnlyInteger) allvar[j]).setValue(Integer.parseInt(t.get(y)));
				t.remove(y);
				j++; 
			}

		} else if (type.equals("BINARY")){ 

			for (int i=0; i< allvar.length; i++){
				allvar[i].randomize();
				/*int u = Randomizer.getInt(2)*2 -1;
				System.out.println(u);
				((BinaryN) allvar[i]).setValue(u);*/
			}
		}
	}


	public void initRandomize(String type){
		if (type.equals("PERMUTATIONAL")){ 
			ArrayList<String> t = new ArrayList<String>();

			for (int i=0; i< allvar.length; i++){
				t.add(""+i);
			}  

			int j=0;
			while(t.size() > 0){
				int y = Randomizer.getInt(0, t.size());
				((OnlyInteger) allvar[j]).setValue(Integer.parseInt(t.get(y)));
				t.remove(y);
				j++; 
			}  
		} else if (type.equals("BINARY")){ 

			for (int i=0; i< allvar.length; i++){
				allvar[i].randomize();
			}
		}
	}


	public void print( ) {

		if (type.equals("PERMUTATIONAL")){ 
			for (int i = 0; i < allvar.length; i++) {
				System.out.print ( ((OnlyInteger) allvar[i]).getValue()+ " " );
			}  
		}
		System.out.println();
	}


	// REVISAR
	public Variables clones(){
		return new Variables("F",2);
	}

	//} else if (type.equals("ALLREAL")){
	//} else if (type.equals("ALLINTEGER")

	
	public Variable[] duplicate(int nVar){ 

		if (type.equals("PERMUTATIONAL")){ 
			Variable[] all = new OnlyInteger[nVar]; 
			for (int i=0; i< nVar; i++){
				all[i] = allvar[i].copy();
			}
			return all;
		} else if (type.equals("BINARY")){ 
			Variable[] all = new BinaryN[nVar]; 
			for (int i=0; i< nVar; i++){
				all[i] = allvar[i].copy();
			}
			return all;
		} else {
			return null;
		} 
	}

	
	protected Object clone() {
		Object obj = null;
		try {
			obj = (Variables) super.clone();
			allvar = this.duplicate(allvar.length);
			/*for (int t=0; t< allvar.length; t++){
				allvar[t] = allvar[t].copy();
			}*/
		} catch (CloneNotSupportedException ex) {
			System.out.println("Imposible duplicate object!!!");
		}
		/*((Variables) obj).allvar  =  (Variable[]) ((Variables) obj).allvar.clone();  */
		/*for (int t=0; t< allvar.length; t++){
				allvar[t] = allvar[t].copy();
		 }*/ 

		return obj; 
	}
}
