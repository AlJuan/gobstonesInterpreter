package ar.fi.uba.tecnicas.commands.factory;

import ar.fi.uba.tecnicas.commands.RepeatCommand;
import ar.fi.uba.tecnicas.functions.Function;

/**
 * RepeatCommandFactory
 * 
 * Responsabilidad: Implementacion del patron Factory para generar los
 * diferentes Command.
 *
 */
public class RepeatCommandFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		return new RepeatCommand();
	}

}
