package ar.fi.uba.tecnicas.model;

import java.util.HashMap;

import ar.fi.uba.tecnicas.expressions.Expression;

/**
 * Program
 * 
 * Responsabilidad: Representa el stack de variables de un programa Gobstones.
 * 
 */
public class ProgramStack {

	private HashMap<String, Expression> programVariables;
					//varName, Expression
	public ProgramStack() {
		this.programVariables = new HashMap<String, Expression>();
	}

	private ProgramStack(HashMap<String, Expression> programVariables) {
		this.programVariables = programVariables;
	}

	public ProgramStack copyProgramStack() {
		return new ProgramStack(new HashMap<String, Expression>(
				programVariables));
	}

	// Updates each (key,value) pair on this programStack with the ones in the
	// updatedProgramStack
	public void updateProgramStack(ProgramStack updatedProgramStack) {
		for (String variableName : this.programVariables.keySet()) {
			this.programVariables.put(variableName,
					updatedProgramStack.getExpression(variableName));
		}
	}

	public boolean containsVariable(String variableName) {
		return programVariables.containsKey(variableName);
	}

	public Expression getExpression(String variableName) {
		return programVariables.get(variableName);
	}

	public void addVariable(String variableName, Expression expression) {
		programVariables.put(variableName, expression);
	}

}
