package ar.fi.uba.tecnicas.commands.factory;

import ar.fi.uba.tecnicas.commands.WhileCommand;
import ar.fi.uba.tecnicas.functions.Function;

/**
 * WhileCommandFactory
 * 
 * Responsabilidad: Implementacion del patron Factory para generar los
 * diferentes Command.
 *
 */
public class WhileCommandFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		return new WhileCommand();
	}

}
