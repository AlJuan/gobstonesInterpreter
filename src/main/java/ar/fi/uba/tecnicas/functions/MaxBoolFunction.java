package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * MaxBoolFunction
 * 
 * Responsabilidad: devuelve el mayor boolean en el orden, o sea TRUE 
 *
 */

public class MaxBoolFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 0;

	public MaxBoolFunction() {
		super();
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {
		
		return new ValueExpression(Boolean.TRUE);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
