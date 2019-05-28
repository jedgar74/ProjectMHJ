package algorithm;

import java.io.File;
import java.io.FileReader; 
import java.util.StringTokenizer;
import java.util.ArrayList;

import problem.Internal;
import problem.Problem;
import state.Operators; 

/**
 * Basic elements about an heuristic
 * 
 * @author Jhon Edgar Amaya
 */
public class Heuristic {
	/*Solution stateFinal;
	Solution stateInitial;*/
	public String shortTerm ;
	public Internal status = new Internal();
	public Problem objProblem = new Problem(); 
	
	public Operators op = new Operators();
	
	String version; // version of algorithm  
	boolean changeOn = false; // controlled to changes

	ArrayList<String> nameParameters = new ArrayList<String>();
	ArrayList<String> valueParameters = new ArrayList<String>();

	/*public Solution getStateFinal() {
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
	}*/

	public boolean isStopCriteria(Problem problem) {
		// System.out.println(""+problem.evaluationCounter+" - "+
		// problem.timeLimit);
		if (problem.counter.getCount() <= problem.counter.getLimit())
			return true;
		else
			return false;
	}

	
	public boolean isStopCriteria(long _cycles, Problem problem) {
		// System.out.println(""+problem.evaluationCounter+" - "+
		// problem.timeLimit);
		if (problem.counter.getCount() <= _cycles)
			return true;
		else
			return false;
	}
	/*public boolean timeExecEvaluation(long _cycles, Problem problem) {
		// System.out.println(""+_cycles+" - "+ this.nUpdates);
		// System.out.println(""+_cycles+" - "+ problem.evaluationCounter);
		if (problem.evaluationCounter <= _cycles)
			return true;
		else
			return false;
	}*/

	public ArrayList<String> getMethods(String _info) {
		ArrayList<String> _listMethods = new ArrayList<String>();

		String[] elements = _info.split(",");
		for (int r = 0; r < elements.length; r++)
			_listMethods.add(elements[r]);
		return _listMethods;
	}
	

	public void readParameters(String nameFile, String metaH) {

		try {
			File readFile = new File("./config/"+metaH, nameFile + ".cfg");
			FileReader input = new FileReader(readFile);
			char buffer[] = new char[1];

			String tmp = "";

			for (int charsRead = input.read(buffer); charsRead != -1; charsRead = input
					.read(buffer)) {

				char caracter = buffer[0];

				if (caracter == '\n') {
					processingParameter(tmp);
					tmp = "";
				} else
					tmp = new StringBuffer(String.valueOf(tmp))
							.append(caracter).toString();
			}

			// We must verifying if local search exist!!!!

			input.close();
		} catch (Exception e) {
			System.out.println("Error to read configuration file :-( ");
			e.printStackTrace();
		}
	}

	
	public void readParameters(String nameFile, String _directory, String metaH) { 
		try { 
			File readFile = new File("_directory", nameFile + ".cfg");
			FileReader input = new FileReader(readFile);
			char buffer[] = new char[1];

			String tmp = "";

			for (int charsRead = input.read(buffer); charsRead != -1; charsRead = input
					.read(buffer)) {

				char caracter = buffer[0];

				if (caracter == '\n') {
					processingParameter(tmp);
					tmp = "";
				} else
					tmp = new StringBuffer(String.valueOf(tmp))
							.append(caracter).toString();
			}

			// We must verifying if local search exist!!!!

			input.close();
		} catch (Exception e) {
			System.out.println("Error to read configuration file :-( ");
			e.printStackTrace();
		}
	}
	
	public void processingParameter(String _lineParameter) {
		String paramet = "";
		String value = "";

		try {
			StringTokenizer tokens = new StringTokenizer(_lineParameter, "=");
			paramet = tokens.nextToken();
			value = tokens.nextToken();
		} catch (Exception e) {
			paramet = "NULL";
		}

		if (paramet.trim().equals("LOCALSEARCH")) {

		} else {
			this.nameParameters.add(paramet.trim());
			this.valueParameters.add(value.trim());
		}

	}

	public void readParameters2(String nameFile) {

		try {
			File readFile = new File("./config/"+objProblem.nameShort, nameFile + ".cfg");
			FileReader input = new FileReader(readFile);
			char buffer[] = new char[1];

			String tmp = "";

			for (int charsRead = input.read(buffer); charsRead != -1; charsRead = input .read(buffer)) {

				char caracter = buffer[0];

				if (caracter == '\n') {
					processingParameter2(tmp);
					tmp = "";
				} else
					tmp = new StringBuffer(String.valueOf(tmp)) .append(caracter).toString();
			}

			input.close();
		} catch (Exception e) {
			System.out.println("Error to read configuration file :-( ");
			e.printStackTrace();
		}
	}

	public void processingParameter2(String _lineParameter) {
		String paramet = "";
		String value = "";

		try {
			StringTokenizer tokens = new StringTokenizer(_lineParameter, "=");
			paramet = tokens.nextToken();
			value = tokens.nextToken();
		} catch (Exception e) {
			paramet = "NULL";
		}

		// Este tipo de inicializacion debe ser por heuristica.
		this.nameParameters.add(paramet.trim());
		this.valueParameters.add(value.trim());

	}

	public void printsParameters() {

	}

}
