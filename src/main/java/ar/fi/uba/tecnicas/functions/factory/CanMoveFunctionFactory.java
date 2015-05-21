package ar.fi.uba.tecnicas.functions.factory;

import ar.fi.uba.tecnicas.commands.factory.CommandFactory;
import ar.fi.uba.tecnicas.functions.CanMoveFunction;
import ar.fi.uba.tecnicas.functions.Function;

public class CanMoveFunctionFactory implements CommandFactory {

	@Override
	public Function createCommand() {

		return new CanMoveFunction();
	}

}
