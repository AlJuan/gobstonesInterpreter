package ar.fi.uba.tecnicas.commands.factory;

import java.util.HashMap;
import java.util.Map;

import ar.fi.uba.tecnicas.functions.ContainsFunction;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.functions.factory.BallsQuantityFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.CanMoveFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.ContainsFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.MaxBoolFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.MaxColorFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.MaxOrientationFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.MinBoolFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.MinColorFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.MinOrientationFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.NextFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.OppositeFunctionFactory;
import ar.fi.uba.tecnicas.functions.factory.PreviousFunctionFactory;
import ar.fi.uba.tecnicas.model.ReservedWords;

/**
 * CommandCreator
 * 
 * Responsabilidad: Convierte una palabra reservada de Gobstones a un Command de
 * nuestro modelo.
 *
 */

public class CommandCreator {

	private Map<String, CommandFactory> expectedValues;

	public CommandCreator() {
		expectedValues = new HashMap<String, CommandFactory>();
		expectedValues
				.put(ReservedWords.COMMAND_MOVE, new MoveCommandFactory());
		expectedValues.put(ReservedWords.COMMAND_PLACE,
				new PlaceCommandFactory());
		expectedValues.put(ReservedWords.COMMAND_REPEAT,
				new RepeatCommandFactory());
		expectedValues.put(ReservedWords.COMMAND_REMOVE,
				new RemoveCommandFactory());
		expectedValues.put(ReservedWords.COMMAND_EMPTY_BOARD,
				new EmptyBoardCommandFactory());
		expectedValues.put(ReservedWords.COMMAND_IF,
				new IfCommandFactory());
		expectedValues.put(ReservedWords.COMMAND_WHILE,
				new WhileCommandFactory());
		expectedValues.put(ReservedWords.COMMAND_FOREACH,
				new ForEachCommandFactory());
		expectedValues.put(ReservedWords.COMMAND_GO_TO_EDGE,
				new GoToEdgeCommandFactory());
		expectedValues.put(ReservedWords.FUNCTION_CONTAINS,
				new ContainsFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_BALLS_QUANTITY,
				new BallsQuantityFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_CAN_MOVE,
				new CanMoveFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_MAX_BOOL,
				new MaxBoolFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_MIN_BOOL,
				new MinBoolFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_MAX_COLOR,
				new MaxColorFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_MIN_COLOR,
				new MinColorFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_MAX_ORIENTATION,
				new MaxOrientationFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_MIN_ORIENTATION,
				new MinOrientationFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_NEXT,
				new NextFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_PREVIOUS,
				new PreviousFunctionFactory());
		expectedValues.put(ReservedWords.FUNCTION_OPPOSITE,
				new OppositeFunctionFactory());
	}

	public Function getCommand(String command) {
		if (expectedValues.containsKey(command))
			return expectedValues.get(command).createCommand();
		return null;
	}

}
