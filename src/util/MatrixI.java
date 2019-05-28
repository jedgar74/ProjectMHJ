package util;

import java.util.ArrayList;

/**  
 * This class implements all methods to matrix with integer values
 * 
 * @author Jhon Edgar Amaya
 */  
public class MatrixI {

	private int col; // number o columns
	private int row; // number of rows

	private int[][] matrix;

	/**
	 * Constructor with row and column
	 * 
	 * @param _row
	 * @param _column
	 */
	public MatrixI(int _row, int _column) { 
		this.col = _column;
		this.row = _row;
		this.matrix = new int[_row][_column];
	}

	/**
	 * Set a specific element in the matrix
	 * 
	 * @param _row
	 * @param _column
	 * @param _value
	 */
	public void setElement (int _row, int _column, int _value) {
		this.matrix[_row][_column] = _value;
	}

	/**
	 * Get a specific element in the matrix
	 * 
	 * @param _row
	 * @param _column
	 * @return value of matrix[row][column]
	 */
	public int getElement (int _row, int _column) {
		return this.matrix[_row][_column];
	}

	/**
	 * Copy a matrix
	 * 
	 * @param x
	 */
	public void copy(MatrixI _matrix) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				this.matrix[i][j] =  _matrix.getElement (i, j);
			}
		}
	}

	/**
	 * Print the matrix
	 * 
	 */
	public void printlnAll() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(" " + this.matrix[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * 
	 */
	public void printlnAllInt() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				int k = (int) this.matrix[i][j];
				System.out.print(" " + k);
			}
			System.out.println();
		}
	}

	/**
	 * @param v
	 */
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
	public int[][] getMatrix() {
		return matrix ;
	}

	/**
	 * 
	 * @param matrix
	 */
	public void setMatrix(int[][] matrix) {
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

	/**
	 * @param aux
	 * @param b
	 * @return
	 */
	public int[][] inverted(int[][] aux, int b) {
		int[][] oth = new int[2 * b][b];

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

	/**
	 * @return
	 */
	public int[][] basic() {
		int[][] oth = { { 1, 1 }, { 1, 0 }, { 0, 1 }, { 0, 0 } };

		return oth;
	}

	public int[][] fullfill(int io, int jo, int[][] aux, int[][] info,
			int k) {
		for (int i = 0; i < 2 * k; i++) {
			for (int j = 0; j < k; j++) {
				aux[io + i][jo + j] = info[i][j];
			}
		}

		return aux;
	}

	/**
	 * @param i
	 * @return
	 */
	public int[][] generated(int i) {
		int[][] oth = new int[2 * i][i];
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
			int[][] oth2 = new int[i][i / 2];
			oth2 = generated(i / 2);

			oth = fullfill(0, 0, oth, oth2, (i / 2));
			oth = fullfill(0, (i / 2), oth, oth2, (i / 2));
			oth = fullfill(i, 0, oth, oth2, (i / 2));
			oth = fullfill(i, (i / 2), oth, inverted(oth2, (i / 2)), (i / 2));
			return oth;
		}

	}

	/**
	 * @param i
	 */
	public void filled(int i) {
		this.matrix = this.generated(i);
	}

	/**
	 * @param a
	 * @param b
	 */
	public void sum (MatrixI a, MatrixI b) {
		try{
			if (a.row==b.row && a.col == b.col){
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						this.matrix[i][j] = a.getElement (i, j) + b.getElement (i, j);
					}
				}
			} 
		} catch (Exception e){
			System.out.println("Dimension error");
		}
	}

	/**
	 * @param g
	 * @param a
	 */
	public void product (int g, MatrixI a) { 

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				this.matrix[i][j] = g * a.getElement (i, j) ;
			}
		}  
	}

	/**
	 * @param a
	 * @param b
	 */
	public void product (MatrixI a, MatrixI b) {
		try{
			if ( a.col == b.row){
				this.matrix = new int[a.row][b.col];
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						this.matrix[i][j] = a.getElement (i, j) * b.getElement (j, i);
					}
				}
			} 
		} catch (Exception e){
			System.out.println("Dimension error");
		}
	}

	/**
	 * @param c
	 * @return
	 */
	public int[][] getIdentity(int c) {
		this.matrix = new int[c][c];
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

