package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.OpposableValue;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * CanMoveFunction
 * 
 * Responsabilidad: Representa al comando puedeMover de Gobstones. Devuelve si
 * es posible mover el cabezal en la direccion indicada en el argumento.
 *
 */

public class CanMoveFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public CanMoveFunction() {
		super();
	}

	public CanMoveFunction(Expression expression) {
		this.arguments.add(new Argument(expression));
	}

	private Expression executeFunctionLogic(Board board, Orientation orientation) {
		GobstonesBoolean gobstonesBoolean = GobstonesBoolean
				.getGobstonesBooleanByValue(board.canMove(orientation));
		return new ValueExpression(gobstonesBoolean);
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {
		Expression expression = castArgument(this.getArguments().get(0),
				Expression.class);
		Orientation orientation = Expression.castExpressionResult(
				expression.evaluate(board, programStack), Orientation.class);
		return executeFunctionLogic(board, orientation);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
