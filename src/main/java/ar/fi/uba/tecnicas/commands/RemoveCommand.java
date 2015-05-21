package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.VoidExpression;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * RemoveCommand
 * 
 * Responsabilidad: Representa al comando Sacar de Gobstones. Ejecuta la accion
 * de Sacar una bolita del color indicado en el argumento, en el tablero, en la
 * posicion actual del cabezal, si es posible.
 *
 */

public class RemoveCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public RemoveCommand() {
		super();
	}

	public RemoveCommand(Expression color) {
		getArguments().add(new Argument(color));
	}

	private void executeCommandLogic(Board board, Color color) {
		board.remove(color);
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		Expression colorValue = castArgument(this.getArguments().get(0), Expression.class);
		executeCommandLogic(board, (Color)colorValue.evaluate(board, programStack));
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}
}
