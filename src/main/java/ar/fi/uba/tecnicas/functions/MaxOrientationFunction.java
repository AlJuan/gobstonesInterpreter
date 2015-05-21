package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * MaxOrientationFunction
 * 
 * Responsabilidad: Representa a la funcion maxDir de Gobstones. Devuelve la
 * mayor direccion en el orden, o sea devuelve Oeste
 *
 */

public class MaxOrientationFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 0;

	public MaxOrientationFunction() {
		super();
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {

		return new ValueExpression(Orientation.WEST);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
