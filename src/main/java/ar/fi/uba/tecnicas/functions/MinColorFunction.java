package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * MinColorFunction
 * 
 * Responsabilidad: devuelve el menor color en el orden, o sea el color azul 
 *
 */

public class MinColorFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 0;

	public MinColorFunction() {
		super();
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {
		
		return new ValueExpression(Color.BLUE);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
