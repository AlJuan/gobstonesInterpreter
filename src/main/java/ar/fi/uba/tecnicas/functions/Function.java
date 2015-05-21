package ar.fi.uba.tecnicas.functions;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicas.exceptions.IllegalArgumentsNumberException;
import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

public abstract class Function {

	List<Argument> arguments = new ArrayList<Argument>();

	protected abstract Expression executeFunction(Board board,
			ProgramStack programStack);

	protected abstract Integer getArgumentsNumber();

	public Expression execute(Board board, ProgramStack programStack) {
		validateArgumentsNumber();
		return executeFunction(board, programStack);
	}

	private void validateArgumentsNumber() {
		if (!getArgumentsNumber().equals(arguments.size()))
			throw new IllegalArgumentsNumberException(getArgumentsNumber(),
					arguments.size());
	}

	protected <T> T castObject(Object obj, Class<T> clazz) {
		try {
			T castedObj = clazz.cast(obj);
			return castedObj;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException();
		}
	}

	public <T> T castArgument(Argument argument, Class<T> clazz) {
		return castObject(argument.getValue(), clazz);
	}

	public <T> List<T> castArgumentToList(Argument arg, Class<T> contentClass) {
		List<T> parametrizedList = List.class.cast(arg.getValue());
		for (Object content : parametrizedList) {
			if (!contentClass.isInstance(content)) {
				throw new IllegalArgumentException();
			}
		}
		return parametrizedList;
	}

	public List<Argument> getArguments() {
		return arguments;
	}

	public void setArguments(List<Argument> argumentList) {
		this.arguments = argumentList;
	}

}
