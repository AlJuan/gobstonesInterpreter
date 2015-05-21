package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * BallsQuantityFunction
 * 
 * Responsabilidad: Representa a la funcion nroBolitas de Gobstones. Duevuelve
 * la cantidad de bolitas que hay en la celda actual del color pasado como
 * argumento
 *
 */

public class BallsQuantityFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public BallsQuantityFunction() {
		super();
	}

	public BallsQuantityFunction(Color color) {
		this.arguments.add(new Argument(color));
	}

	private Expression executeFunctionLogic(Board board, Color color) {
		return new ValueExpression(board.getQuantityByColor(color));
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {
		Color color = castArgument(this.getArguments().get(0), Color.class);
		return executeFunctionLogic(board, color);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
