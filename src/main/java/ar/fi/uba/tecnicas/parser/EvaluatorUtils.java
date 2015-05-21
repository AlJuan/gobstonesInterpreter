package ar.fi.uba.tecnicas.parser;

import static ar.fi.uba.tecnicas.parser.EvaluatorUtils.COMMAND_POSITION_IN_MATCH;
import static ar.fi.uba.tecnicas.parser.EvaluatorUtils.COMPLEX_COMMAND_EXPRESSION_ARGUMENT_POSITION;
import static ar.fi.uba.tecnicas.parser.EvaluatorUtils.EMPTY_COMMAND_SIZE;
import static ar.fi.uba.tecnicas.parser.EvaluatorUtils.SIMPLE_COMMAND_ARGUMENT_POSITION;
import static ar.fi.uba.tecnicas.parser.EvaluatorUtils.extractExpressionFromArgument;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.petitparser.context.Token;
import org.w3c.dom.ranges.Range;

import ar.fi.uba.tecnicas.commands.factory.CommandCreator;
import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.OneOperandExpression;
import ar.fi.uba.tecnicas.expressions.SubstractExpression;
import ar.fi.uba.tecnicas.expressions.TwoOperandExpression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.range.ValueExpressionRange;

public class EvaluatorUtils {

	public static final int PARSED_TREE_PROGRAM_POSITION = 0;
	public static final int PARSED_TREE_COMMAND_LIST_POSITION = 1;
	public static final int COMMAND_POSITION_IN_MATCH = 0;
	public static final int REPEAT_COMMAND_BLOCK_COMMAND_POSITION = 4;
	public static final int IF_COMMAND_BLOCK_COMMAND_POSITION = 5;
	public static final int ELSE_COMMAND_BLOCK_COMMAND_POSITION = 6;
	public static final int SIMPLE_COMMAND_ARGUMENT_POSITION = 2;
	public static final int COMPLEX_COMMAND_EXPRESSION_ARGUMENT_POSITION = 2;
	public static final int VARIABLE_NAME_OTHER_CHARACTERS_POSITION = 2;
	public static final int VARIABLE_NAME_FIRST_CHARACTER_POSITION = 1;
	public static final int ASSIGNMENT_COMMAND_ASSIGNED_POSITION = 2;
	public static final int ASSIGNMENT_COMMAND_VARIABLE_POSITION = 0;
	public static final int FOREACH_COMMAND_BLOCK_ARGUMENT_POSITION = 4;
	public static final int FOREACH_COMMAND_RANGE_ARGUMENT_POSITION = 3;
	public static final int FOREACH_COMMAND_VARIABLE_NAME_POSITION = 1;


	public static final int EMPTY_COMMAND_SIZE = 3;

	private static final CommandCreator commandCreator = new CommandCreator();


	public static ValueExpressionRange extractRangeFromParsedTree(
			List sequenceParsedTree) {

		ValueExpressionRange range = null;

		List<ValueExpression> rangeOperands = extractOperandsFromRangeSentece(sequenceParsedTree);

		if (isRange(sequenceParsedTree)) {
			if (hasStep(sequenceParsedTree)) {
				/** TODO ver si no se puede evaluar en otro lado */
				GobstonesNumber stepValue = (GobstonesNumber) (rangeOperands
						.get(0)).evaluate(null, null);

				Integer step = stepValue.intValue();
				ValueExpression start = rangeOperands.get(1);
				ValueExpression end = rangeOperands.get(2);

				range = new ValueExpressionRange(step, start, end);
			} else {
				ValueExpression start = rangeOperands.get(0);
				ValueExpression end = rangeOperands.get(1);
				range = new ValueExpressionRange(1, start, end);
			}
		} else {
			if (isEnum(sequenceParsedTree)) {
				range = getRangeFromList(rangeOperands);

			}
		}

		return range;
	}

	public static Boolean isEnum(List sequenceParsedTree) {

		Boolean isEnum = true;

		List otherOperands = (List) sequenceParsedTree.get(1);

		for (Iterator iterator = otherOperands.iterator(); iterator.hasNext();) {
			List operand = (List) iterator.next();

			String separator = operand.get(0).toString();

			if (!separator.equals(",")) {
				isEnum = false;
				break;
			}

		}

		return isEnum;
	}

	public static Boolean hasStep(List sequenceParsedTree) {

		if (sequenceParsedTree.size() > 3) {

			Character posibleSeparator = (Character) sequenceParsedTree.get(1);

			if (posibleSeparator.toString().equals(",")) {
				return true;
			}
		}
		return false;
	}

	public static Boolean isRange(List sequenceParsedTree) {
		Object posibleSeparator = sequenceParsedTree.get(1);

		if (posibleSeparator instanceof Token) {
			String separator = ((Token) posibleSeparator).getValue().toString();
			if (separator.equals("..")) {
				return true;
			}
		} else {
			if (sequenceParsedTree.size() > 2) {
				posibleSeparator = sequenceParsedTree.get(3);
				String separator = ((Token) posibleSeparator).getValue()
						.toString();
				if (separator.equals("..")) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<ValueExpression> extractOperandsFromRangeSentece(
			List sequenceParsedTree) {

		List<ValueExpression> rangeOperands = new ArrayList<ValueExpression>();

		for (Iterator iterator = sequenceParsedTree.iterator(); iterator
				.hasNext();) {
			Object object = (Object) iterator.next();

			if (object instanceof List) {
				rangeOperands
						.addAll(extractOperandsFromRangeSentece((List) object));
			}

			if (object instanceof ValueExpression) {
				rangeOperands.add((ValueExpression) object);
			}

		}
		return rangeOperands;
	}

	public static ValueExpressionRange getRangeFromList(List lista) {

		List<ValueExpression> rangeList = new ArrayList<ValueExpression>();

		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			ValueExpression object = (ValueExpression) iterator.next();

			rangeList.add(object);
		}

		return new ValueExpressionRange(rangeList);

	}


	public static Expression extractExpressionFromArgument(
			List expressionArgument) {

		Stack<Expression> expressionsStack = new Stack<Expression>();

		pushExpressionsIntoStack(expressionArgument, expressionsStack);

		return expressionsStack.size() > 0 ? expressionsStack.pop() : null;

	}

	public static void pushExpressionsIntoStack(List expressionArgument,
			Stack<Expression> expressionsStack) {
		for (Iterator iterator = expressionArgument.iterator(); iterator
				.hasNext();) {
			Object object = (Object) iterator.next();

			if (object != null) {
				if (object instanceof List) {

					List innerExpressionList = (List) object;
					if (!innerExpressionList.isEmpty()) {

						pushExpressionsIntoStack(innerExpressionList,
								expressionsStack);
					}

				}
				if (object instanceof Expression) {
					expressionsStack.push((Expression) object);
				}

			}
		}

		if (expressionsStack.size() > 1) {

			Expression rightExpression = expressionsStack.pop();
			Expression operationExpression = expressionsStack.pop();

			if (operationExpression instanceof SubstractExpression) {
				SubstractExpression expression = (SubstractExpression) operationExpression;
				expression.setRightExpression(rightExpression);

				if (expressionsStack.size() == 1) {
					Expression leftExpression = expressionsStack.pop();
					expression.setLeftExpression(leftExpression);
				}

				expressionsStack.push(expression);
			} else {

				if (expressionsStack.size() == 0) {

					if (operationExpression instanceof OneOperandExpression) {
						OneOperandExpression expression = (OneOperandExpression) operationExpression;
						expression.setExpression(rightExpression);

						expressionsStack.push(expression);
					}

				}

				if (expressionsStack.size() == 1) {

					if (operationExpression instanceof TwoOperandExpression) {
						Expression leftExpression = expressionsStack.pop();
						TwoOperandExpression expression = (TwoOperandExpression) operationExpression;
						expression.setRightExpression(rightExpression);
						expression.setLeftExpression(leftExpression);

						expressionsStack.push(expression);
					}
				}
			}
		}

	}
	
	public static Function generateCommandFromMatch(List cmds) {
		Token token = (Token) cmds.get(COMMAND_POSITION_IN_MATCH);
		Function command = commandCreator.getCommand(token.getValue()
				.toString());
		return command;
	}

	public static void setCommandArgumentsFromMatch(List cmds, Function command) {

		List<Argument> argumentList = command.getArguments();
		if (commandHasArguments(cmds)) {
			Object argument = cmds.get(SIMPLE_COMMAND_ARGUMENT_POSITION);
			argumentList.add(new Argument(argument));
		}

		command.setArguments(argumentList);

	}

	public static boolean commandHasArguments(List cmds) {
		return cmds.size() > EMPTY_COMMAND_SIZE;
	}

	public static List<Function> extractCommandsFromParsedTree(List parsedTree) {

		List<Function> commandList = new ArrayList<Function>();

		if (parsedTree != null) {

			for (Iterator iterator = parsedTree.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				if (object instanceof List) {
					commandList
							.addAll(extractCommandsFromParsedTree((List) object));
				}
				if (object instanceof Function) {
					commandList.add((Function) object);
				}
			}
		}

		return commandList;
	}

	public static Argument extractArgumentFromParsedTree(List cmds) {
		Object expressionArgument = cmds
				.get(COMPLEX_COMMAND_EXPRESSION_ARGUMENT_POSITION);

		Expression expression = null;

		if (expressionArgument instanceof List) {
			expression = extractExpressionFromArgument((List) expressionArgument);
		} else {
			expression = (Expression) expressionArgument;
		}

		return new Argument(expression);
	}


}
