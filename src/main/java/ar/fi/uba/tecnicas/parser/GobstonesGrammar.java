package ar.fi.uba.tecnicas.parser;

import org.petitparser.parser.Parser;
import org.petitparser.parser.characters.CharacterParser;
import org.petitparser.parser.primitive.FailureParser;
import org.petitparser.tools.CompositeParser;

import static ar.fi.uba.tecnicas.parser.ParserUtils.*;
import static org.petitparser.parser.characters.CharacterParser.*;
import static org.petitparser.parser.primitive.StringParser.of;

public class GobstonesGrammar extends CompositeParser {

    @Override
    protected void initialize() {
        def("start", ref("gobstones"));

        def("gobstones", gobstones().end());

        def("defs", definitions());
        def("def", definition());

        def("programDef", programDefinition());
        def("iProgDef", interactiveProgram());
        def("bProgDef", program());

        def("reservedId", reservedId());

        def("positiveNumber", positiveNumber());
        def("number", number());
        def("comment", comment());
        def("whitespace", whitespace());
        def("boolean", booleans());
        def("color", color());
        def("direction", direction());
        def("literal", literals());
        def("vars", vars());
        def("arrowKey", arrowKeys());
        def("specialKey", specialKeys());
        def("key", key());
        def("keyDef", keyDefinition());
        def("keyAssoc", keyAssociation());
        def("defaultKeyAssoc", defaultKeyAssociation());
        def("keyAssocs", keyAssociations());

        def("gexp", gexp());
        def("skip", skip());
        def("poner", poner());
        def("sacar", sacar());
        def("mover", mover());
        def("irAlBorde", irAlBorde());
        def("vaciarTablero", vaciarTablero());
        def("procCall", procCall());
        def("simpleAssignment", simpleAssignment());
        def("multiAssignment", multiAssignment());
        def("simpleCmd", simpleCmd());
        def("cmd", cmd());
        def("neCmds", neCmds());
        def("cmds", cmds());
        def("blockCmd", blockCmd());
        def("ifCmd", ifCmd());
        def("switchCmd", switchCmd());
        def("repeatCmd", repeatCmd());
        def("whileCmd", whileCmd());
        def("foreachCmd", foreachCmd());
        def("compCmd", compCmd());
        def("sequence", sequence());

        def("bexp", bexp());
        def("bterm", bterm());
        def("bfact", bfact());
        def("batom", batom());
        def("nexp", nexp());
        def("nterm", nterm());
        def("nfactH", nfactH());
        def("nfactL", nfactL());

        def("natom", natom());
        def("rop", rop());
        def("nop", nop());
        def("mop", mop());
        def("funcCall", funcCall());
        def("args", args());

        def("varTuple", varTuple());
        def("varTuple1", varTuple1());
        def("varNames", varNames());
        def("gexpTuple", gexpTuple());
        def("gexpTuple1", gexpTuple1());
        def("gexps", gexps());

        def("varName", lowerId());
        def("funcName", lowerId());
        def("procName", upperId());

        def("predefProc", predefinedProcedures());
        def("predefFunc", predefinedFunctions());

        def("procedure", procedure());
        def("function", function());
    }

    protected Parser gobstones() {
        Parser programDef = ref("programDef");
        Parser defs = ref("defs");
        return programDef.seq(defs).or(defs.seq(programDef));
    }

    protected Parser definitions() {
//        return ref("def").plus(); // TODO: según la sintáxis, es al menos una definición. Pero me parece mucho, así que...
        return ref("def").star();

    }

    protected Parser definition() {
        return ref("procedure").or(ref("function"));
    }

    protected Parser programDefinition() {
        return ref("bProgDef").or(ref("iProgDef"));
    }

    protected Parser program() {
        Parser returnStatement = token("return").seq(ref("varTuple").seq(SEMI_COLON.optional()));
        Parser programBody = OPEN_CURLY_BRACES.seq(ref("whitespace").optional().seq(ref("cmds"))).seq(returnStatement.optional()).seq(CLOSE_CURLY_BRACES); // TODO: ojo con los comentarios
        return token("program").trim().seq(programBody);
    }

    protected Parser interactiveProgram() {
        Parser interactiveProgramBody = OPEN_CURLY_BRACES.seq(ref("keyAssocs")).seq(CLOSE_CURLY_BRACES);
        return token("interactive program").seq(interactiveProgramBody);
    }

    protected Parser keyAssociations() {
        Parser defaultKeyAssocOptional = ref("defaultKeyAssoc").optional();
        Parser oneOrMoreKeyAssoc = ref("keyAssoc").plus();
        return oneOrMoreKeyAssoc.seq(defaultKeyAssocOptional).or(defaultKeyAssocOptional);
    }

    protected Parser defaultKeyAssociation() {
        return is('_').seq(of("->").trim()).seq(ref("blockCmd"));
    }

    protected Parser keyAssociation() {
        return ref("keyDef").seq(of("->").trim()).seq(ref("blockCmd"));
    }

    protected Parser keyDefinition() {
        return of("K_").seq(ref("key"));
    }

    protected Parser key() {
        return digit().or(ref("specialKey")).or(ref("arrowKey")).or(upperCase());
    }

    protected Parser specialKeys() {
        return newLiterals("SPACE", "ENTER", "TAB", "BACKSPACE", "DELETE", "ESCAPE", "CTRL_D");
    }

    protected Parser arrowKeys() {
        return newLiterals("ARROW_LEFT", "ARROW_RIGHT", "ARROW_UP", "ARROW_DOWN");
    }

    protected Parser function() {
        return token("function").seq(ref("funcName")).seq(ref("varTuple")).seq(funcBody());
    }

    protected Parser funcBody() {
        return OPEN_CURLY_BRACES
                .seq(ref("cmds"))
                .seq(token("return"))
                .seq(ref("gexpTuple1"))
                .seq(SEMI_COLON.optional())
                .seq(CLOSE_CURLY_BRACES);
    }

    protected Parser procedure() {
        return token("procedure").seq(ref("procName")).seq(ref("varTuple")).seq(procBody());
    }

    protected Parser procBody() {
        return OPEN_CURLY_BRACES
                .seq(ref("cmds"))
                .seq((token("return").seq(ref("varTuple").seq(SEMI_COLON.optional()))).optional())
                .seq(CLOSE_CURLY_BRACES);
    }

    protected Parser compCmd() {
        return ref("ifCmd").or(ref("switchCmd")).or(ref("repeatCmd")).or(ref("whileCmd")).or(ref("foreachCmd"))
                .or(ref("blockCmd"));
    }

    /**
     * Creates a parser for the if statement.
     * <p/>
     * <pre>
     * In Gobstones the if statement is as follows:
     *
     * if (<gexp>) [then] <blockcmd> [else <blockcmd>]
     * </pre>
     *
     * @return the the created parser. It is never null.
     */
    protected Parser ifCmd() {
        Parser ifClause = token("if");
        Parser thenClause = token("then");
        Parser elseClause = token("else").seq(ref("blockCmd"));
        return ifClause
                .seq(OPEN_PARENS)
                .seq(ref("gexp"))
                .seq(CLOSE_PARENS)
                .seq(thenClause.optional())
                .seq(ref("blockCmd"))
                .seq(elseClause.optional());
    }

    /**
     * Creates a parser for the repeat statement.
     * <p/>
     * <pre>
     * In Gobstones the repeat statement is as follows:
     *
     * repeat (<gexp>) <blockcmd>
     * </pre>
     *
     * @return the the created parser. It is never null.
     */
    protected Parser repeatCmd() {
        return token("repeat").seq(OPEN_PARENS).seq(ref("gexp")).seq(CLOSE_PARENS).seq(ref("blockCmd"));
    }

    /**
     * Creates a parser for the while statement.
     * <p/>
     * <pre>
     * In Gobstones the while statement is as follows:
     *
     * while (<gexp>) <blockcmd>
     * </pre>
     *
     * @return the the created parser. It is never null.
     */
    protected Parser whileCmd() {
        return token("while").seq(OPEN_PARENS).seq(ref("gexp")).seq(CLOSE_PARENS).seq(ref("blockCmd"));
    }

    /**
     * Creates a parser for the switch statement.
     * <p/>
     * <pre>
     * In Gobstones the switch statement is as follows:
     *
     * switch(<gexp>) to <branches>
     * < branches > -> _-> <blockcmd> | < lits > -> < blockcmd >[;] < branches > | <literal> | <literal>, <lits>
     * </pre>
     *
     * @return the the created parser. It is never null.
     */
    protected Parser switchCmd() {
        return token("switch").seq(OPEN_PARENS).seq(ref("gexp")).seq(CLOSE_PARENS).seq(token("to")).seq(branches());
    }

    protected Parser branches() {
        Parser literalBranch = literals().seq(token("->")).seq(ref("blockCmd"));
        Parser defaultBranch = token("_").seq(token("->")).seq(ref("blockCmd"));
        return literalBranch.star().seq(defaultBranch);
    }

    /**
     * Creates a parser for the foreach statement.
     * <p/>
     * <pre>
     * In Gobstones the foreach statement is as follows:
     *
     * foreach <varName> in <sequence> <blockcmd>
     * </pre>
     *
     * @return the the created parser. It is never null.
     */
    protected Parser foreachCmd() {
        return token("foreach").seq(ref("varName")).seq(token("in")).seq(sequence()).seq(ref("blockCmd"));
    }

    protected Parser sequence() {
        return OPEN_SQUARE_BRACKET.seq(seqdef()).seq(CLOSE_SQUARE_BRACKET).pick(1);
    }

    protected Parser seqdef() {
        return range().or(enums());
    }

    protected Parser range() {
        Parser simpleRange = ref("gexp").seq(token("..")).seq(ref("gexp"));
        Parser complexRange = ref("gexp").seq(COMA).seq(simpleRange);
        return simpleRange.or(complexRange);
    }

    protected Parser enums() {
        return ref("gexp").seq(COMA.seq(ref("gexp")).star());
    }

    protected Parser blockCmd() {
        return OPEN_CURLY_BRACES.seq(END_OF_LINES.star()).seq(ref("cmds")).seq(END_OF_LINES.star())
                .seq(CLOSE_CURLY_BRACES).pick(2).or(ref("whitespace"));
    }

    protected Parser cmds() {
        Parser neCmd = ref("neCmds").seq(SEMI_COLON.optional());
        return neCmd.seq(neCmd.star()).optional();
    }

    protected Parser neCmds() {
        Parser singleCmd = ref("cmd").seq(SEMI_COLON.optional());
        return singleCmd.seq(singleCmd.star());
    }

    protected Parser cmd() {
        return ref("compCmd").or(ref("simpleCmd"));
    }

    protected Parser simpleCmd() {
        return ref("skip").or(ref("poner")).or(ref("sacar")).or(ref("mover")).or(ref("irAlBorde"))
                .or(ref("vaciarTablero")).or(ref("procCall")).or(ref("simpleAssignment")).or(ref("funcCall"))
                .or(ref("multiAssignment"));
    }

    protected Parser skip() {
        return token("Skip");
    }

    protected Parser poner() {
        return token("Poner").seq(OPEN_PARENS).seq(ref("gexp")).seq(CLOSE_PARENS);
    }

    protected Parser sacar() {
        return token("Sacar").seq(OPEN_PARENS).seq(ref("gexp")).seq(CLOSE_PARENS);
    }

    protected Parser mover() {
        return token("Mover").seq(OPEN_PARENS).seq(ref("gexp")).seq(CLOSE_PARENS);
    }

    protected Parser irAlBorde() {
        return token("IrAlBorde").seq(OPEN_PARENS).seq(ref("gexp")).seq(CLOSE_PARENS);
    }

    protected Parser vaciarTablero() {
        return token("VaciarTablero").seq(OPEN_PARENS).seq(CLOSE_PARENS);
    }

    protected Parser procCall() {
        return ref("procName").seq(ref("args"));
    }

    protected Parser simpleAssignment() {
        return lowerId().seq(token(":=")).seq(ref("gexp"));
    }

    protected Parser multiAssignment() {
        return ref("varTuple1").seq(token(":=")).seq(ref("funcCall"));
    }

    protected Parser gexp() {
        return bexp();
    }

    protected Parser args() {
        return ref("gexpTuple");
    }

    protected Parser funcCall() {
        return ref("funcName").seq(ref("args"));
    }

    protected Parser mop() {
        return divOperator().or(modOperator());
    }

    protected Parser nop() {
        return additionOperator().or(subtractionOperator());
    }

    protected Parser rop() {
        return equalOperator().or(notEqualOperator()).or(lessOrEqualOperator()).or(lessOperator()).or(greaterThanOrEqualOperator()).or(greaterThanOperator());
    }

    protected Parser natom() {
        Parser nroBolitasFunc = functionWithOneArg("nroBolitas");
        Parser hayBolitasFunc = functionWithOneArg("hayBolitas");
        Parser puedeMoverFunc = functionWithOneArg("puedeMover");
        Parser siguienteFunc = functionWithOneArg("siguiente");
        Parser previoFunc = functionWithOneArg("previo");
        Parser opuestoFunc = functionWithOneArg("opuesto");
        Parser minBoolFunc = functionWithNoArg("minBool");
        Parser maxBoolFunc = functionWithNoArg("maxBool");
        Parser minDirFunc = functionWithNoArg("minDir");
        Parser maxDirFunc = functionWithNoArg("maxDir");
        Parser minColorFunc = functionWithNoArg("minColor");
        Parser maxColorFunc = functionWithNoArg("maxColor");

        return ref("funcCall")
                .or(ref("varName"))
                .or(ref("literal"))
                .or(subtractionOperator().seq(ref("natom")))
                .or(nroBolitasFunc).or(hayBolitasFunc).or(puedeMoverFunc).or(siguienteFunc).or(previoFunc).or(opuestoFunc)
                .or(minBoolFunc).or(maxBoolFunc).or(minDirFunc).or(maxDirFunc).or(minColorFunc).or(maxColorFunc)
                .or(OPEN_PARENS.seq(ref("gexp")).seq(CLOSE_PARENS).pick(1))
                ;
    }

    protected Parser nfactL() {
        return ref("natom").seq((powerOperator().seq(ref("natom"))).star());
    }

    protected Parser nfactH() {
        return ref("nfactL").seq((ref("mop").seq(ref("nfactL"))).star());
    }

    protected Parser nterm() {
        return ref("nfactH").seq((productOperator().seq(ref("nfactH"))).star());
    }

    protected Parser nexp() {
        return ref("nterm").seq((ref("nop").seq(ref("nterm"))).star());
    }

    protected Parser batom() {
        return ref("nexp").seq((ref("rop").seq(ref("nexp"))).star());
    }

    protected Parser bfact() {
        return notOperator().trim().seq(ref("batom")).or(ref("batom"));
    }

    protected Parser bterm() {
        return ref("bfact").seq((andOperator().seq(ref("bfact"))).star());
    }

    protected Parser bexp() {
        return ref("bterm").seq((orOperator().seq(ref("bterm"))).star());
    }

    protected Parser gexps() {
        return ref("gexp").seq((COMA.seq(ref("gexp"))).star());
    }

    protected Parser gexpTuple1() {
        return OPEN_PARENS.seq(ref("gexps")).seq(CLOSE_PARENS);
    }

    protected Parser varTuple() {
        return ref("varTuple1").or(OPEN_PARENS.seq(CLOSE_PARENS));
    }

    protected Parser varTuple1() {
        return OPEN_PARENS.seq(ref("varNames")).seq(CLOSE_PARENS);
    }

    protected Parser varNames() {
        return ref("varName").seq((COMA.seq(ref("varName"))).star());
    }

    protected Parser gexpTuple() {
        return ref("gexpTuple1").or(OPEN_PARENS.seq(CLOSE_PARENS));
    }

    protected Parser functionWithOneArg(String funcName) {
        return token(funcName).seq(OPEN_PARENS).seq(ref("gexp")).seq(CLOSE_PARENS);
    }

    protected Parser functionWithNoArg(String funcName) {
        return token(funcName).seq(OPEN_PARENS).seq(CLOSE_PARENS);
    }

    protected Parser divOperator() {
        return of("div").trim();
    }

    protected Parser modOperator() {
        return of("mod").trim();
    }

    protected Parser subtractionOperator() {
        return is('-').trim();
    }

    protected Parser additionOperator() {
        return is('+').trim();
    }

    protected Parser productOperator() {
        return is('*').trim();
    }

    protected Parser powerOperator() {
        return is('^').trim();
    }

    protected Parser greaterThanOperator() {
        return is('>').trim();
    }

    protected Parser greaterThanOrEqualOperator() {
        return of(">=").trim();
    }

    protected Parser lessOperator() {
        return is('<').trim();
    }

    protected Parser lessOrEqualOperator() {
        return of("<=").trim();
    }

    protected Parser notEqualOperator() {
        return of("/=").trim();
    }

    protected Parser equalOperator() {
        return of("==").trim();
    }

    protected Parser andOperator() {
        return of("&&").trim();
    }

    protected Parser orOperator() {
        return of("||").trim();
    }

    protected Parser notOperator() {
        return token("not");
    }

    protected Parser reservedId() {
        return token("if").or(token("then")).or(token("else")).or(token("not")).or(token("True")).or(token("False"))
                .or(token("Verde")).or(token("Rojo")).or(token("Azul")).or(token("Negro")).or(token("Norte"))
                .or(token("Sur")).or(token("Este")).or(token("Oeste")).or(token("switch")).or(token("to"))
                .or(token("while")).or(token("repeat")).or(token("Skip")).or(token("foreach")).or(token("in"))
                .or(token("interactive")).or(token("program")).or(token("procedure")).or(token("function"))
                .or(token("return")).or(token("Mover")).or(token("Poner")).or(token("Sacar"))
                .or(token("IrAlBorde")).or(token("VaciarTablero")).or(token("div")).or(token("mod"))
                .or(token("siguiente")).or(token("previo")).or(token("opuesto")).or(token("hayBolitas"))
                .or(token("nroBolitas")).or(token("puedeMover")).or(token("minBool")).or(token("maxBool"))
                .or(token("minDir")).or(token("maxDir")).or(token("minColor")).or(token("maxColor"));
    }

    private Parser nonReservedId(Parser firstCharacterParser) {
        Parser notReservedId = ref("reservedId").not().seq(firstCharacterParser).seq(word().star()).trim();
        Parser biggerReservedId = ref("reservedId").seq(firstCharacterParser).seq(word().plus()).trim();
        return notReservedId.or(biggerReservedId);
    }

    protected Parser lowerId() {
        return nonReservedId(lowerCase());
    }

    protected Parser upperId() {
        return nonReservedId(upperCase());
    }

    protected Parser literals() {
        return ref("number").or(ref("color").or(ref("direction").or(ref("boolean")
                .or(FailureParser.withMessage("Literal expected.")))));
    }

    protected Parser vars() {
        return lowerId();
    }

    protected Parser comment() {
        return multiLineComment("{-", "-}")
                .or(multiLineComment("/*", "*/"))
                .or(multiLineComment("\"\"\"", "\"\"\""))
                .or(singleLineComment("--"))
                .or(singleLineComment("#"))
                .or(singleLineComment("//"));
    }

    protected Parser whitespace() {
        return CharacterParser.whitespace().or(ref("comment"));
    }

    protected Parser positiveNumber() {
        return digit().plus();
    }

    protected Parser number() {
        return of("-").optional().seq(ref("positiveNumber")).flatten();
    }

    protected Parser color() {
        return newLiterals("Rojo", "Verde", "Azul", "Negro");
    }

    protected Parser direction() {
        return newLiterals("Norte", "Sur", "Este", "Oeste");
    }

    protected Parser booleans() {
        return newLiterals("True", "False");
    }

    protected Parser predefinedProcedures() {
        return newLiterals(
                "Poner",
                "Sacar",
                "Mover",
                "IrAlBorde",
                "VaciarTablero"
        );
    }

    protected Parser predefinedFunctions() {
        return newLiterals(
                "nroBolitas",
                "hayBolitas",
                "puedeMover",
                "siguiente",
                "previo",
                "opuesto",
                "minBool",
                "maxBool",
                "minDir",
                "maxDir",
                "minColor",
                "maxColor"
        );
    }

    private Parser token(Object input) {
        return tokenWithTrim(input, ref("whitespace"));
    }

    private Parser newLiterals(String... tokens) {
        return ParserUtils.newLiterals(this::token, tokens);
    }

}
