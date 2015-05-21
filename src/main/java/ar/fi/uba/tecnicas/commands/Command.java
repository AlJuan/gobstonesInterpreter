package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.VoidExpression;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * Command
 * 
 * Responsabilidad: Representa una funcion o comando de Gobstones que no tiene
 * valor de retorno
 * 
 * */

public abstract class Command extends Function {

	protected abstract void executeCommand(Board board,
			ProgramStack programStack);

	protected Expression executeFunction(Board board, ProgramStack programStack) {
		executeCommand(board, programStack);
		return new VoidExpression();
	}

}
