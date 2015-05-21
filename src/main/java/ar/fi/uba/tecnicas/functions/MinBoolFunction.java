package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * MinBoolFunction
 * 
 * Responsabilidad: devuelve el menor boolean en el orden, o sea FALSE 
 *
 */

public class MinBoolFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 0;

	public MinBoolFunction() {
		super();
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {
		
		return new ValueExpression(Boolean.FALSE);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
