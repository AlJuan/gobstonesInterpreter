package ar.fi.uba.tecnicas.functions.factory;

import ar.fi.uba.tecnicas.commands.factory.CommandFactory;
import ar.fi.uba.tecnicas.functions.BallsQuantityFunction;
import ar.fi.uba.tecnicas.functions.Function;

public class BallsQuantityFunctionFactory implements CommandFactory {

	@Override
	public Function createCommand() {

		return new BallsQuantityFunction();
	}

}
