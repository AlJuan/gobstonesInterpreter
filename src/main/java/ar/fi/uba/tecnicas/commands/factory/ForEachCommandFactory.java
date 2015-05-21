package ar.fi.uba.tecnicas.commands.factory;

import ar.fi.uba.tecnicas.commands.ForEachCommand;
import ar.fi.uba.tecnicas.functions.Function;

/**
 * ForEachCommandFactory
 * 
 * Responsabilidad: Implementacion del patron Factory para generar los
 * diferentes Command.
 *
 */
public class ForEachCommandFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		return new ForEachCommand();
	}

}
