package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.expressions.VoidExpression;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * PlaceCommand
 * 
 * Responsabilidad: Representa al comando Poner de Gobstones. Ejecuta la accion
 * de Poner una bolita del color indicado en el argumento, en el tablero, en la
 * posicion actual del cabezal.
 *
 */

public class PlaceCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public PlaceCommand() {
		super();
	}

	public PlaceCommand(Expression value) {
		getArguments().add(new Argument(value));
	}

	private void executeCommandLogic(Board board, Color color) {
		board.place(color);
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		Expression colorValue = castArgument(this.getArguments().get(0),
				Expression.class);
		executeCommandLogic(
				board,
				Expression.castExpressionResult(
						colorValue.evaluate(board, programStack), Color.class));
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
