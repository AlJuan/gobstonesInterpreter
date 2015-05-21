package ar.fi.uba.tecnicas.commands.factory;

import ar.fi.uba.tecnicas.commands.IfCommand;
import ar.fi.uba.tecnicas.functions.Function;

/**
 * IfCommandFactory
 * 
 * Responsabilidad: Implementacion del patron Factory para generar los
 * diferentes Command.
 *
 */
public class IfCommandFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		return new IfCommand();
	}

}
