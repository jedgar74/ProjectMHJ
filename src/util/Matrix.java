package util;
 

import java.util.ArrayList;

/**  
 * This class implements all methods to matrix with integer values
 * 
 * @author Jhon Edgar Amaya
 */
public class Matrix {

	private int col; // number o columns
	private int row; // number of rows

	private double[][] matrix;

	/**
	 * Constructor with row and column
	 * 
	 * @param _row
	 * @param _column
	 */
	public Matrix(int _row, int _column) {
		this.col = _column;
		this.row = _row;
		this.matrix = new double[_row][_column];
	}

	/**
	 * 
	 * @param _row
	 * @param _column
	 * @param _dist
	 */
	public void setElement(int _row, int _column, double _dist) {
		this.matrix[_row][_column] = _dist;
	}

	/**
	 * 
	 * @param _row
	 * @param _column
	 * @return value of element [row][column]
	 */
	public double getElement(int _row, int _column) {
		return this.matrix[_row][_column];
	}

	/**
	 * 
	 * @param x
	 */
	public void copy(Matrix x) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				this.matrix[i][j] = x.getElement(i, j);
			}
		}
	}

	public void printlnAll(int b) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				String r=" " + this.matrix[i][j];
				if (r.length()>  b){
					r=r.substring(0, b);
				}
				System.out.print(r);
			}
			System.out.println();
		}
	}
	
	public void printlnAll() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(" " + this.matrix[i][j]);
			}
			System.out.println();
		}
	}

	public void printlnAllInt() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				int k = (int) this.matrix[i][j];
				System.out.print(" " + k);
			}
			System.out.println();
		}
	}

	public void printSeqs(ArrayList<String> v) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				int tmp = Integer.parseInt(v.get(j));
				int k = (int) this.matrix[i][tmp];
				System.out.print(" " + k);
			}
			System.out.println();
		}

	}

	/**
	 * 
	 *
	 */
	public void println() {
		System.out.println("::: " + this.row + " - " + this.col);
	}

	/**
	 * 
	 * @return number of rows
	 */
	public int getRow() {
		return row;
	}

	/**
	 * 
	 * @param m
	 */
	public void setRow(int m) {
		this.row = m;
	}

	/**
	 * 
	 * @return matrix
	 */
	public double[][] getMatrix() {
		return matrix;
	}

	/**
	 * 
	 * @param matrix
	 */
	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * 
	 * @return number columns
	 */
	public int getCol() {
		return col;
	}

	/**
	 * 
	 * @param n
	 */
	public void setCol(int n) {
		this.col = n;
	}

	public double[][] inverted(double[][] aux, int b) {
		double[][] oth = new double[2 * b][b];

		for (int i = 0; i < (2 * b); i++) {
			for (int j = 0; j < b; j++) {
				if (aux[i][j] == 0)
					oth[i][j] = 1;
				else
					oth[i][j] = 0;
			}
		}

		return oth;
	}

	public double[][] basic() {
		double[][] oth = { { 1, 1 }, { 1, 0 }, { 0, 1 }, { 0, 0 } };

		return oth;
	}

	public double[][] fullfill(int io, int jo, double[][] aux, double[][] info,
			int k) {
		for (int i = 0; i < 2 * k; i++) {
			for (int j = 0; j < k; j++) {
				aux[io + i][jo + j] = info[i][j];
			}
		}

		return aux;
	}

	public double[][] generated(int i) {
		double[][] oth = new double[2 * i][i];
		// System.out.println("@@@@@ "+i);
		if (i == 4) {
			// System.out.println("#1 ");
			oth = fullfill(0, 0, oth, basic(), 2);
			// System.out.println("#2 ");
			oth = fullfill(0, 2, oth, basic(), 2);
			// System.out.println("#3 ");
			oth = fullfill(4, 0, oth, basic(), 2);
			// System.out.println("#4 ");
			oth = fullfill(4, 2, oth, inverted(basic(), 2), 2);
			return oth;
		} else {
			double[][] oth2 = new double[i][i / 2];
			oth2 = generated(i / 2);

			oth = fullfill(0, 0, oth, oth2, (i / 2));
			oth = fullfill(0, (i / 2), oth, oth2, (i / 2));
			oth = fullfill(i, 0, oth, oth2, (i / 2));
			oth = fullfill(i, (i / 2), oth, inverted(oth2, (i / 2)), (i / 2));
			return oth;
		}

	}

	public void filled(int i) {
		this.matrix = this.generated(i);
	}
	
	public void sum (Matrix a, Matrix b) {
		try{
			if (a.row==b.row && a.col == b.col){
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						this.matrix[i][j] = a.getElement(i, j) + b.getElement(i, j);
					}
				}
			} 
		} catch (Exception e){
			System.out.println("Dimension error");
		}
	}

	public void product (int g, Matrix a) { 

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				this.matrix[i][j] = g * a.getElement(i, j) ;
			}
		}  
	}
	
	public void product (Matrix a, Matrix b) {
		try{
			if ( a.col == b.row){
				this.matrix = new double[a.row][b.col];
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						this.matrix[i][j] = a.getElement(i, j) * b.getElement(j, i);
					}
				}
			} 
		} catch (Exception e){
			System.out.println("Dimension error");
		}
	}
	
	public double[][] getIdentity(int c) {
		this.matrix = new double[c][c];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (i==j)
					this.matrix[i][j] = 1;
				else
					this.matrix[i][j] = 0;
			}
		}
		return matrix ;
	}
	
	public String toString(){
		String s="";
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				s = s+this.matrix[i][j] + " ";
			}	
			s = s+"\n";
		}
		return s;
	}
}
