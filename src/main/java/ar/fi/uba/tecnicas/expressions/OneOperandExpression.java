package ar.fi.uba.tecnicas.expressions;

/**
 * OneOperandExpression
 * 
 * Responsabilidad: Abstraccion de expresion de un solo operando.
 * 
 * */
public abstract class OneOperandExpression extends Expression {
	private Expression expression;

	public OneOperandExpression() {
		super();
	}
	
	public OneOperandExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

}
