package ar.fi.uba.tecnicas.functions.factory;

import ar.fi.uba.tecnicas.commands.factory.CommandFactory;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.functions.MaxBoolFunction;

public class MaxBoolFunctionFactory implements CommandFactory {

	@Override
	public Function createCommand() {

		return new MaxBoolFunction();
	}

}
