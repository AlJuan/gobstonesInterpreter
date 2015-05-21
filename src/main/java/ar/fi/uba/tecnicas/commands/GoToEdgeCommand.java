package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.VoidExpression;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * GoToEdgeCommand
 * 
 * Responsabilidad: Representa al comando IrAlBorde de Gobstones. Mueve el
 * cabezal al borde del tablero en la direccion indicada en el argumento.
 *
 */

public class GoToEdgeCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public GoToEdgeCommand() {
		super();
	}

	public GoToEdgeCommand(Orientation orientation) {
		getArguments().add(new Argument(orientation));
	}

	private void executeCommandLogic(Board board, Orientation orientation) {
		board.goToEdge(orientation);
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		Expression expression = castArgument(this.getArguments().get(0),
				Expression.class);
		Orientation orientation = Expression.castExpressionResult(
				expression.evaluate(board, programStack), Orientation.class);
		executeCommandLogic(board, orientation);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
