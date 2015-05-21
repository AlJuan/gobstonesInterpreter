package ar.fi.uba.tecnicas.commands.factory;

import ar.fi.uba.tecnicas.commands.GoToEdgeCommand;
import ar.fi.uba.tecnicas.functions.Function;

/**
 * EmptyBoardCommandFactory
 * 
 * Responsabilidad: Implementacion del patron Factory para generar los
 * diferentes Command.
 *
 */
public class GoToEdgeCommandFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		return new GoToEdgeCommand();
	}

}
