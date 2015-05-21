package ar.fi.uba.tecnicas.expressions;

import ar.fi.uba.tecnicas.exceptions.IllegalVariableNameException;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * VariableExpression
 * 
 * Representa una variable. Evalua el valor de la misma.
 *
 */
public class VariableExpression extends NoOperandExpression {

	private String variableName;

	public VariableExpression(String variableName) {
		this.variableName = variableName;
	}

	@Override
	public Object evaluate(Board board, ProgramStack stack) {
		if (!stack.containsVariable(variableName))
			throw new IllegalVariableNameException(variableName);
		return stack.getExpression(variableName).evaluate(board, stack);
	}

	public String getVariableName() {
		return variableName;
	}

}
