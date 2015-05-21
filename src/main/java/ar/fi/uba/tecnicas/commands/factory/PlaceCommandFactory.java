package ar.fi.uba.tecnicas.commands.factory;

import ar.fi.uba.tecnicas.commands.PlaceCommand;
import ar.fi.uba.tecnicas.functions.Function;

/**
 * PlaceCommandFactory
 * 
 * Responsabilidad: Implementacion del patron Factory para generar los
 * diferentes Command.
 *
 */
public class PlaceCommandFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		return new PlaceCommand();
	}

}
