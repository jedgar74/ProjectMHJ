package state;
 
import java.util.ArrayList;
import basic.Variable;
import basic.OnlyInteger;
import basic.BinaryN; 

public class Solution  implements  Cloneable  {
	public Variables var ; 
	public double fitness ;
	public int nVar;
	public String typeVar;
	
	
	public Solution(String typeVar, int nVar){
		/*super();*/
		this.typeVar = typeVar;
		this.nVar = nVar;
		if (typeVar.equals("PERMUTATIONAL")){
			//var  = new PERMUTATIONALAL(nVar);
			var  = new Variables("PERMUTATIONAL", nVar);
		} else if (typeVar.equals("BINARY")){
			//var  = new PERMUTATIONALAL(nVar);
			var  = new Variables("BINARY", nVar);
		}
	}
	
	public Solution(String typeVar, int nVar, String init) {
		/*super();*/
		this.typeVar = typeVar;
		this.nVar = nVar;
		if (typeVar.equals("PERMUTATIONAL")){
			//var  = new PERMUTATIONALAL(nVar);
			var  = new Variables("PERMUTATIONAL", nVar);
			if (init.equals("RANDOM"))
				var.initRandomize();
		} else if (typeVar.equals("BINARY")){
			//var  = new PERMUTATIONALAL(nVar);
			/*System.out.println(this.typeVar+" "+ this.nVar +" "+init);*/
			var  = new Variables("BINARY", nVar);
			if (init.equals("RANDOM"))
				var.initRandomize();
		}
	}
	
	
	public Solution(String typeVar, int[] vc) {
		/*super();*/
		this.typeVar = typeVar;
		this.nVar = vc.length;
		if (typeVar.equals("PERMUTATIONAL")){
			//var  = new PERMUTATIONALAL(nVar);
			var = new Variables("PERMUTATIONAL", nVar);
		    var.init(vc);
		}
	}
/*	public Variable getVar(int i) {
		return var.allvar[i] ;
	}*/
	
	public Variables getVar() {
		return var ;
	}
	
	public void setVar(Variables var) {
		this.var  = var;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public int getnVar() {
		return nVar;
	}
	
	public void setnVar(int nVar) {
		this.nVar = nVar;
	}
	
	public String getTypeVar() {
		return typeVar;
	}
	
	public void setTypeVar(String typeVar) {
		this.typeVar = typeVar;
	}
	
	//JKL
	public int getLength() {
		return var.allvar.length;
	}
	
	//JKL
	public Variable getVariable(int i) {
		return var.allvar[i];
	}
	
	
	
	public void print( ) {

		if (var.type.equals("PERMUTATIONAL")){ 
			for (int i = 0; i < var.allvar.length; i++) {
				System.out.print ( ((OnlyInteger) var.allvar[i]).getValue()+ " " );
			}  
		} else if (var.type.equals("BINARY")){ 
			for (int i = 0; i < var.allvar.length; i++) {
				System.out.print ( ((BinaryN) var.allvar[i]).getValue()+ " " );
			}  
		}
		System.out.println(" ::: "+this.fitness);
	}
	
	public void print(String name) {
		 
		if (var.type.equals("PERMUTATIONAL")){ 
			for (int i = 0; i < var.allvar.length; i++) {
				System.out.print ( ((OnlyInteger) var.allvar[i]).getValue()+ " " );
			}  
		}
		System.out.println(" ::: "+this.fitness+" ::: "+name);
	}
	
	public String toString( ) {
		String aux="";
		if (var.type.equals("PERMUTATIONAL")){ 
			for (int i = 0; i < var.allvar.length; i++) {
				aux=aux+ ((OnlyInteger) var.allvar[i]).getValue()+ " ";
			}  
		}
		aux=aux+" ::: "+this.fitness;
		return aux;
	}
	
	
	public boolean equalsTo(Solution s) {
		boolean aux= true;
		if (var.type.equals("PERMUTATIONAL")){ 
			for (int i = 0; i < var.allvar.length; i++) {
				if (((OnlyInteger) var.allvar[i]).getValue() != ((OnlyInteger) s.var.allvar[i]).getValue()){ 
					
					return false;
				} 
			}  
		}
		return aux;
	}
	
	
	public boolean check(){
		boolean control = true;
		ArrayList<String> n = new ArrayList<String>( );
		if (var.type.equals("PERMUTATIONAL")){ 
			for (int i = 0; i < var.allvar.length; i++) {
				n.add(""+i);
			}  
			for (int i = 0; i < var.allvar.length; i++) {
				boolean nofind = true;
				for (int h = 0; h < n.size(); h++) {
					String u = ""+((OnlyInteger) var.allvar[i]).getValue();
					if (u.equals(n.get(h))){
						nofind =false;
						n.remove(h);
						break;
					}
				}  
				if (nofind){
					control =false; 
					break;
				}
			}  
		}
		
		return control;
	}
	 
	
	public Object clone() {
		Object obj = null;
		try {
			obj = (Solution) super.clone();
		    
		} catch (CloneNotSupportedException ex) {
			System.out.println("Imposible duplicate object!!!");
		}
		 ((Solution) obj).var  =  (Variables) ((Solution) obj).var.clone(); 
		/* var  =  (Variables) var.clone();*/
		return obj; 
	}
}
