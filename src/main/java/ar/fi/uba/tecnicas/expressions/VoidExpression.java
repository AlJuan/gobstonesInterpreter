package ar.fi.uba.tecnicas.expressions;

import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * VoidExpression
 * 
 * Expresion que retorna siempre nulo.
 *
 */
public class VoidExpression extends NoOperandExpression {

	@Override
	public Object evaluate(Board board, ProgramStack stack) {
		return null;
	}

}
