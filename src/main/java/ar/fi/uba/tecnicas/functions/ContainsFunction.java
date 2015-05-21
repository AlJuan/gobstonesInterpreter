package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * ContainsFunction
 * 
 * Responsabilidad: Representa a la funcion hayBolitas de Gobstones. Devuelve si
 * hay bolitas en la celda actual del color pasado como argumento.
 * 
 */

public class ContainsFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public ContainsFunction() {
		super();
	}

	public ContainsFunction(Expression expression) {
		this.arguments.add(new Argument(expression));
	}

	private Expression executeFunctionLogic(Board board, Color color) {
		GobstonesBoolean gobstonesBoolean = GobstonesBoolean
				.getGobstonesBooleanByValue(board.getQuantityByColor(color) > 0);
		return new ValueExpression(gobstonesBoolean);
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {
		Expression expression = castArgument(this.getArguments().get(0), Expression.class);
		Color color = Expression.castExpressionResult(
				expression.evaluate(board, programStack), Color.class);
		return executeFunctionLogic(board, color);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
