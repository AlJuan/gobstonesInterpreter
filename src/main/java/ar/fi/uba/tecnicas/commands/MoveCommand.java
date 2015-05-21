package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * MoveCommand
 * 
 * Responsabilidad: Representa al comando Mover de Gobstones. Ejecuta la accion
 * de Mover el cabezal en la direccion indicada en el argumento, en el tablero,
 * si es posible.
 *
 */

public class MoveCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public MoveCommand() {
		super();
	}

	public MoveCommand(Expression value) {
		getArguments().add(new Argument(value));
	}

	private void executeCommandLogic(Board board, Orientation orientation) {
		board.move(orientation);
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		Expression orientationValue = castArgument(this.getArguments()
				.get(0), Expression.class);
		executeCommandLogic(board,
				(Orientation) orientationValue.evaluate(board, programStack));
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
