package ar.fi.uba.tecnicas.functions.factory;

import ar.fi.uba.tecnicas.commands.factory.CommandFactory;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.functions.NextFunction;

public class NextFunctionFactory implements CommandFactory {

	@Override
	public Function createCommand() {
		
		return new NextFunction();
	}

}
