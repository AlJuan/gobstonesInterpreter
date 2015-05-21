package ar.fi.uba.tecnicas.expressions;

import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * ValueExpression
 * 
 * Representa un valor cualquiera.
 *
 */
public class ValueExpression extends NoOperandExpression {

	private Object value;

	public ValueExpression(Object value) {
		this.value = value;
	}

	@Override
	public Object evaluate(Board board, ProgramStack stack) {
		return value;
	}

}
