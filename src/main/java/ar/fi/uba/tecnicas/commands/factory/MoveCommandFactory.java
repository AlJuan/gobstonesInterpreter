package ar.fi.uba.tecnicas.commands.factory;

import ar.fi.uba.tecnicas.commands.MoveCommand;
import ar.fi.uba.tecnicas.functions.Function;

/**
 * MoveCommandFactory
 * 
 * Responsabilidad: Implementacion del patron Factory para generar los
 * diferentes Command.
 *
 */
public class MoveCommandFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		return new MoveCommand();
	}

}
