package ar.fi.uba.tecnicas.functions.factory;

import ar.fi.uba.tecnicas.commands.factory.CommandFactory;
import ar.fi.uba.tecnicas.functions.ContainsFunction;
import ar.fi.uba.tecnicas.functions.Function;

public class ContainsFunctionFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		
		return new ContainsFunction();
	}

}
