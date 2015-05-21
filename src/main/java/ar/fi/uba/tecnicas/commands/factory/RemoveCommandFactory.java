package ar.fi.uba.tecnicas.commands.factory;

import ar.fi.uba.tecnicas.commands.RemoveCommand;
import ar.fi.uba.tecnicas.functions.Function;

/**
 * RemoveCommandFactory
 * 
 * Responsabilidad: Implementacion del patron Factory para generar los
 * diferentes Command.
 *
 */
public class RemoveCommandFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		return new RemoveCommand();
	}

}
