package ar.fi.uba.tecnicas.expressions;

import ar.fi.uba.tecnicas.exceptions.IllegalExpressionResultException;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * Expression
 * 
 * Representa una expresion de Gobstones.
 *
 */

public abstract class Expression {

	public abstract Object evaluate(Board board, ProgramStack stack);

	public static <T> T castExpressionResult(Object expressionResult,
			Class<T> resultType) {
		try {
			return resultType.cast(expressionResult);
		} catch (ClassCastException e) {
			throw new IllegalExpressionResultException(resultType.getName(),
					expressionResult.getClass().getName());
		}
	}
}
