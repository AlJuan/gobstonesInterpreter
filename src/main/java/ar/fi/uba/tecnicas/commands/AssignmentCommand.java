package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.expressions.VariableExpression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * AssignmentCommand
 * 
 * Responsabilidad: Representa una asignacion de una variable de gobstones.
 *
 */
public class AssignmentCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 2;

	public AssignmentCommand(Expression variableExpression,
			Expression valueExpression) {
		getArguments().add(new Argument(variableExpression));
		getArguments().add(new Argument(valueExpression));
	}

	public AssignmentCommand() {
		super();
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		VariableExpression variableExpression = castArgument(this
				.getArguments().get(0), VariableExpression.class);
		ValueExpression valueExpression = castArgument(getArguments().get(1),
				ValueExpression.class);

		executeComandLogic(variableExpression, valueExpression, programStack);
	}

	private void executeComandLogic(VariableExpression variableExpression,
			ValueExpression valueExpression, ProgramStack programStack) {
		programStack.addVariable(variableExpression.getVariableName(),
				valueExpression);

	}

	@Override
	protected Integer getArgumentsNumber() {

		return ARGUMENTS_NUMBER;
	}

}
