package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * MinOrientationFunction
 * 
 * Responsabilidad: Representa a la funcion minDir de Gobstones. Devuelve la
 * menor direccion en el orden, o sea devuelve Norte
 *
 */

public class MinOrientationFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 0;

	public MinOrientationFunction() {
		super();
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {

		return new ValueExpression(Orientation.NORTH);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
