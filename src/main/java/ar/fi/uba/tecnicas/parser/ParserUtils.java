package ar.fi.uba.tecnicas.parser;

import org.petitparser.parser.Parser;
import org.petitparser.parser.combinators.ChoiceParser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.petitparser.parser.characters.CharacterParser.any;
import static org.petitparser.parser.characters.CharacterParser.is;
import static org.petitparser.parser.primitive.StringParser.of;

public class ParserUtils {

    public static final Parser OPEN_PARENS = is('(').trim();
    public static final Parser CLOSE_PARENS = is(')').trim();
    public static final Parser COMA = is(',').trim();
    public static final Parser SEMI_COLON = is(';').trim();
    public static final Parser OPEN_CURLY_BRACES = is('{').trim();
    public static final Parser CLOSE_CURLY_BRACES = is('}').trim();
    public static final Parser END_OF_LINES = is('\n').trim();
    public static final Parser OPEN_SQUARE_BRACKET = is('[').trim();
    public static final Parser CLOSE_SQUARE_BRACKET = is(']').trim();

    public static Parser tokenWithTrim(Object input, Parser parserToTrim) {
        Parser parser;
        if (input instanceof Parser) {
            parser = (Parser) input;
        } else if (input instanceof String) {
            String inputAsString = (String) input;
            if (inputAsString.length() == 1) {
                return is(inputAsString.charAt(0));
            } else {
                parser = of(inputAsString);
            }
        } else {
            throw new IllegalStateException("Object not parsable: " + input);
        }
        return parser.token().trim(parserToTrim);
    }

    public static Parser newLiterals(Function<String, Parser> literalFactory, String... literalList) {
        if (literalList == null || literalList.length < 2) {
            throw new AssertionError("At least 2 Literals options are required");
        }

        List<Parser> literalParsers = new ArrayList<>();

        for (String literalToken : literalList) {
            literalParsers.add(literalFactory.apply(literalToken));
        }

        return new ChoiceParser(literalParsers.toArray(new Parser[literalParsers.size()]));
    }

    public static Parser singleLineComment(String symbol) {
        return of(symbol).seq(any().starLazy(of("\n"))).seq(of("\n"));
    }

    public static Parser multiLineComment(String startSymbol, String endSymbol) {
        return of(startSymbol)
                .seq(any().starLazy(of(endSymbol)))
                .seq(of(endSymbol));
    }

}
