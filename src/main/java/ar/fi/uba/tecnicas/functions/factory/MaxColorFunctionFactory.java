package ar.fi.uba.tecnicas.functions.factory;

import ar.fi.uba.tecnicas.commands.factory.CommandFactory;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.functions.MaxColorFunction;

public class MaxColorFunctionFactory implements CommandFactory {

	@Override
	public Function createCommand() {

		return new MaxColorFunction();
	}

}
