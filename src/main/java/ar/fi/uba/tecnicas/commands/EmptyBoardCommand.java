package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * EmptyBoardCommand
 * 
 * Responsabilidad: Representa el comando de Gobstones vaciarTablero
 * 
 * */
public class EmptyBoardCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 0;

	public EmptyBoardCommand() {
		super();
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		board.empty();
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
