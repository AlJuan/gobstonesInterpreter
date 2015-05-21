package ar.fi.uba.tecnicas.parser;

import static ar.fi.uba.tecnicas.parser.EvaluatorUtils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.petitparser.context.ParseError;
import org.petitparser.context.Result;
import org.petitparser.context.Token;
import org.petitparser.parser.Parser;

import ar.fi.uba.tecnicas.commands.AssignmentCommand;
import ar.fi.uba.tecnicas.commands.factory.ColorCreator;
import ar.fi.uba.tecnicas.commands.factory.OrientationCreator;
import ar.fi.uba.tecnicas.exceptions.ProgramTextEvaluationException;
import ar.fi.uba.tecnicas.expressions.AddExpression;
import ar.fi.uba.tecnicas.expressions.AndExpression;
import ar.fi.uba.tecnicas.expressions.DivExpression;
import ar.fi.uba.tecnicas.expressions.EqualsThanExpression;
import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.GreaterOrEqualsThanExpression;
import ar.fi.uba.tecnicas.expressions.GreaterThanExpression;
import ar.fi.uba.tecnicas.expressions.LesserOrEqualsThanExpression;
import ar.fi.uba.tecnicas.expressions.LesserThanExpression;
import ar.fi.uba.tecnicas.expressions.ModExpression;
import ar.fi.uba.tecnicas.expressions.NotEqualsThanExpression;
import ar.fi.uba.tecnicas.expressions.NotExpression;
import ar.fi.uba.tecnicas.expressions.OrExpression;
import ar.fi.uba.tecnicas.expressions.PowerExpression;
import ar.fi.uba.tecnicas.expressions.ProductExpression;
import ar.fi.uba.tecnicas.expressions.SubstractExpression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.expressions.VariableExpression;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.CodeBlock;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.Program;
import ar.fi.uba.tecnicas.range.ValueExpressionRange;

/**
 * GobstonesEvaluator
 * 
 * Responsabilidad: Convertir el arbol generado por el parser en objetos de
 * nuestro modelo
 * 
 * */

public class GobstonesEvaluator extends GobstonesGrammar {

	private ColorCreator colorCreator = new ColorCreator();
	private OrientationCreator orientationCreator = new OrientationCreator();


	public GobstonesEvaluator() {
		super();
	}

	protected Parser color() {
		return super.color().map(
				match -> {
					Token token = (Token) match;
					Color color = this.colorCreator.getColor(token.getValue()
							.toString());
					return new ValueExpression(color);
				});
	}

	protected Parser direction() {
		return super.direction().map(
				match -> {
					Token token = (Token) match;
					Orientation orientation = this.orientationCreator
							.getOrientation(token.getValue().toString());

					return new ValueExpression(orientation);
				});
	}

	@SuppressWarnings("rawtypes")
	protected Parser simpleCmd() {
		return super
				.simpleCmd()
				.map(match -> {

					List cmds = (List) match;

					if (isACommand(cmds
							.get(ASSIGNMENT_COMMAND_VARIABLE_POSITION))) {

						Function command = generateCommandFromMatch(cmds);

						List<Argument> argumentList = command.getArguments();
						argumentList
								.add(extractArgumentFromParsedTree((List) cmds));

						command.setArguments(argumentList);

						return command;

					} else {

						List assignmentSentence = (List) match;

						VariableExpression variable = new VariableExpression(
								assignmentSentence.get(
										ASSIGNMENT_COMMAND_VARIABLE_POSITION)
										.toString());

						Expression valueExpression = extractExpressionFromArgument((List) assignmentSentence
									.get(ASSIGNMENT_COMMAND_ASSIGNED_POSITION));
						Function command = new AssignmentCommand(variable,
								valueExpression);

						return command;
					}

				});
	}

	private boolean isACommand(Object object) {
		if (object instanceof Token) {
			return true;
		}
		return false;
	}

	@Override
	protected Parser ifCmd() {
		return super
				.ifCmd()
				.map(match -> {
					List cmds = (List) match;
					Function command = generateCommandFromMatch(cmds);

					List<Argument> argumentList = command.getArguments();

					Argument complexCommandArgument = extractArgumentFromParsedTree(cmds);
					argumentList.add(complexCommandArgument);

					List ifParsedTree = (List) cmds
							.get(IF_COMMAND_BLOCK_COMMAND_POSITION);

					List<Function> ifCommands = extractCommandsFromParsedTree(ifParsedTree);
					argumentList.add(new Argument(new CodeBlock(ifCommands)));

					List elseParsedTree = (List) cmds
							.get(ELSE_COMMAND_BLOCK_COMMAND_POSITION);

					List<Function> elseCommands;

					if (elseParsedTree == null) {
						elseCommands = new ArrayList<Function>();
					} else {
						elseCommands = extractCommandsFromParsedTree(elseParsedTree);
					}
					argumentList.add(new Argument(new CodeBlock(elseCommands)));

					command.setArguments(argumentList);

					return command;
				});

	}

	@Override
	protected Parser whileCmd() {
		return super
				.whileCmd()
				.map(match -> {
					List cmds = (List) match;
					Function command = generateCommandFromMatch(cmds);

					List<Argument> argumentList = command.getArguments();

					Argument complexCommandArgument = extractArgumentFromParsedTree(cmds);
					argumentList.add(complexCommandArgument);

					List parsedTree = (List) cmds
							.get(REPEAT_COMMAND_BLOCK_COMMAND_POSITION);

					List<Function> commands = extractCommandsFromParsedTree(parsedTree);
					argumentList.add(new Argument(new CodeBlock(commands)));

					command.setArguments(argumentList);

					return command;
				});
	}

	@SuppressWarnings("rawtypes")
	protected Parser repeatCmd() {
		return super
				.repeatCmd()
				.map(match -> {
					List cmds = (List) match;
					Function command = generateCommandFromMatch(cmds);

					List<Argument> argumentList = command.getArguments();

					Argument complexCommandArgument = extractArgumentFromParsedTree(cmds);
					argumentList.add(complexCommandArgument);

					List parsedTree = (List) cmds
							.get(REPEAT_COMMAND_BLOCK_COMMAND_POSITION);

					List<Function> commands = extractCommandsFromParsedTree(parsedTree);
					argumentList.add(new Argument(new CodeBlock(commands)));

					command.setArguments(argumentList);

					return command;
				});
	}

	@Override
	protected Parser functionWithOneArg(String funcName) {

		return super.functionWithOneArg(funcName).map(match -> {
			Function function = generateCommandFromMatch((List) match);
			List<Argument> argumentList = function.getArguments();
			argumentList.add(extractArgumentFromParsedTree((List) match));

			function.setArguments(argumentList);

			return new ValueExpression(function);
		});
	}

	@Override
	protected Parser functionWithNoArg(String funcName) {

		return super.functionWithNoArg(funcName).map(match -> {
			Function function = generateCommandFromMatch((List) match);

			return new ValueExpression(function);
		});
	}

	@Override
	protected Parser sequence() {
		return super
				.sequence()
				.map(match -> {

					List sequenceParsedTree = (List) match;

					ValueExpressionRange range = extractRangeFromParsedTree(sequenceParsedTree);

					return range;
				});
	}

	@Override
	protected Parser foreachCmd() {
		return super
				.foreachCmd()
				.map(match -> {
					List cmds = (List) match;
					Function command = generateCommandFromMatch(cmds);

					List<Argument> argumentList = command.getArguments();

					String variableName = (String) cmds
							.get(FOREACH_COMMAND_VARIABLE_NAME_POSITION);
					argumentList.add(new Argument(variableName));

					ValueExpressionRange rangeArgument = (ValueExpressionRange) cmds
							.get(FOREACH_COMMAND_RANGE_ARGUMENT_POSITION);
					argumentList.add(new Argument(rangeArgument));

					List parsedTree = (List) cmds
							.get(FOREACH_COMMAND_BLOCK_ARGUMENT_POSITION);

					List<Function> commands = extractCommandsFromParsedTree(parsedTree);
					argumentList.add(new Argument(new CodeBlock(commands)));

					command.setArguments(argumentList);

					return command;

				});
	}

	@Override
	protected Parser lowerId() {

		return super
				.lowerId()
				.map(match -> {

					List varNameList = (List) match;
					StringBuilder sb = new StringBuilder();

					sb.append((Character) varNameList
							.get(VARIABLE_NAME_FIRST_CHARACTER_POSITION));

					List otherCharactersList = (List) varNameList
							.get(VARIABLE_NAME_OTHER_CHARACTERS_POSITION);

					for (Iterator iterator = otherCharactersList.iterator(); iterator
							.hasNext();) {
						Character character = (Character) iterator.next();

						sb.append(character);
					}

					return sb.toString();
				});
	}

	@Override
	public Parser notOperator() {

		return super.notOperator().map(match -> {
			NotExpression notExpression = new NotExpression();

			return notExpression;
		});
	}

	@Override
	protected Parser lessOperator() {
		return super
				.lessOperator()
				.map(match -> {
					LesserThanExpression lesserThanExpression = new LesserThanExpression();

					return lesserThanExpression;
				});
	}

	@Override
	protected Parser equalOperator() {

		return super
				.equalOperator()
				.map(match -> {
					EqualsThanExpression equalsThanExpression = new EqualsThanExpression();

					return equalsThanExpression;

				});
	}

	@Override
	protected Parser greaterThanOperator() {

		return super
				.greaterThanOperator()
				.map(match -> {
					GreaterThanExpression greaterThanExpression = new GreaterThanExpression();

					return greaterThanExpression;
				});
	}

	@Override
	protected Parser vars() {

		return super.vars().map(match -> {
			return match.toString();
		});
	}

	@Override
	protected Parser number() {
		return super.number().map(
				match -> {

					GobstonesNumber gobstonesInteger = new GobstonesNumber(
							new Integer(match.toString()));

					ValueExpression numberExpression = new ValueExpression(
							gobstonesInteger);

					return numberExpression;
				});
	}

	@Override
	protected Parser booleans() {
		return super.booleans().map(
				match -> {
					Token booleanToken = (Token) match;
					String booleanString = booleanToken.getValue().toString();
					Boolean boolValue = Boolean.parseBoolean(booleanString);

					ValueExpression booleanExpression;

					if (boolValue == Boolean.TRUE) {
						booleanExpression = new ValueExpression(
								GobstonesBoolean.TRUE);
					} else {
						booleanExpression = new ValueExpression(
								GobstonesBoolean.FALSE);
					}

					return booleanExpression;
				});
	}

	@Override
	protected Parser additionOperator() {

		return super.additionOperator().map(match -> {
			AddExpression addExpression = new AddExpression();

			return addExpression;
		});
	}

	@Override
	protected Parser subtractionOperator() {

		return super
				.subtractionOperator()
				.map(match -> {
					SubstractExpression substractExpression = new SubstractExpression();

					return substractExpression;
				});
	}

	@Override
	protected Parser orOperator() {

		return super.orOperator().map(match -> {
			OrExpression orExpression = new OrExpression();

			return orExpression;
		});
	}

	@Override
	protected Parser andOperator() {

		return super.andOperator().map(match -> {
			AndExpression andExpression = new AndExpression();

			return andExpression;
		});
	}

	@Override
	protected Parser divOperator() {

		return super.divOperator().map(match -> {
			DivExpression divExpression = new DivExpression();

			return divExpression;
		});
	}

	@Override
	protected Parser greaterThanOrEqualOperator() {

		return super
				.greaterThanOrEqualOperator()
				.map(match -> {
					GreaterOrEqualsThanExpression greaterOrEqualsThanExpression = new GreaterOrEqualsThanExpression();

					return greaterOrEqualsThanExpression;
				});
	}

	@Override
	protected Parser lessOrEqualOperator() {

		return super
				.lessOrEqualOperator()
				.map(match -> {
					LesserOrEqualsThanExpression lesserOrEqualsThanExpression = new LesserOrEqualsThanExpression();

					return lesserOrEqualsThanExpression;
				});
	}

	@Override
	protected Parser notEqualOperator() {

		return super
				.notEqualOperator()
				.map(match -> {
					NotEqualsThanExpression notEqualsThanExpression = new NotEqualsThanExpression();

					return notEqualsThanExpression;
				});
	}

	@Override
	protected Parser powerOperator() {
		return super.powerOperator().map(match -> {
			PowerExpression powerExpression = new PowerExpression();

			return powerExpression;
		});
	}

	@Override
	protected Parser productOperator() {

		return super.productOperator().map(match -> {
			ProductExpression productExpression = new ProductExpression();

			return productExpression;
		});
	}

	@Override
	protected Parser modOperator() {

		return super.modOperator().map(match -> {
			ModExpression modExpression = new ModExpression();

			return modExpression;
		});
	}

	@SuppressWarnings("rawtypes")
	protected Parser program() {
		return super
				.program()
				.map(match -> {
					List cmds = (List) match;
					List<Function> commands = extractCommandsFromParsedTree((List) cmds
							.get(PARSED_TREE_COMMAND_LIST_POSITION));
					Program program = new Program();
					program.setCodeBlock(new CodeBlock(commands));

					return program;
				});
	}

	public Program evaluate(String programText) {

		Program program = null;

		try {
			Result result = this.parse(programText);
			List resultList = result.get();
			program = (Program) resultList.get(PARSED_TREE_PROGRAM_POSITION);
		} catch (ParseError e) {
			throw new ProgramTextEvaluationException();

		}
		return program;
	}

}
