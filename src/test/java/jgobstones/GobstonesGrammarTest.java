package jgobstones;


import org.junit.Test;
import org.petitparser.context.ParseError;
import org.petitparser.context.Result;
import org.petitparser.parser.Parser;
import org.petitparser.tools.CompositeParser;

import ar.fi.uba.tecnicas.parser.GobstonesGrammar;
import static java.lang.String.format;

public class GobstonesGrammarTest {

    private final CompositeParser gobstones = new GobstonesGrammar();

    private <T> T validate(String source, String production) {
        Parser parser = gobstones.ref(production).end();
        Result result = parser.parse(source);
        try {
            return result.get();
        } catch (ParseError e) {
            int startPosition = e.getFailure().getPosition() - 5 > 0 ? e.getFailure().getPosition() - 5 : 0;
            int endPosition =
                    e.getFailure().getPosition() + 6 < e.getFailure().getBuffer().length() ? e.getFailure()
                            .getPosition() + 6 : e.getFailure().getBuffer().length();
            throw new RuntimeException(format("Error parsing: \n%s\n Error near: %s\n%s", e.getFailure().getBuffer(),
                    e.getFailure().getBuffer().substring(startPosition, endPosition), e.getFailure().getMessage()), e);
        }
    }

    @Test
    public void numberLiteral() {
        validate("10", "number");
    }

    @Test
    public void negativeNumberLiteral() {
        validate("-1", "number");
    }

    @Test
    public void haskellMultiComment() {
        validate("{- hello! -}", "comment");
    }

    @Test
    public void javaMultiComment() {
        validate("/* hello! */", "comment");
    }

    @Test
    public void haskellSingleComment() {
        validate("-- hello!\n", "comment");
    }

    @Test
    public void javaSingleComment() {
        validate("// hello! \n", "comment");
    }

    @Test
    public void pythonSingleComment() {
        validate("# hello! \n", "comment");
    }

    @Test
    public void pythonMultiComment() {
        validate("\"\"\" hello! \"\"\"", "comment");
    }

    @Test
    public void booleanLiterals() {
        validate("True", "boolean");
        validate("False", "boolean");
    }

    @Test
    public void colorLiterals() {
        validate("Verde", "color");
        validate("Rojo", "color");
        validate("Azul", "color");
        validate("Negro", "color");
    }

    @Test
    public void directionLiterals() {
        validate("Norte", "direction");
        validate("Este", "direction");
        validate("Sur", "direction");
        validate("Oeste", "direction");
    }

    @Test
    public void predefinedProcedures() {
        validate("Poner", "predefProc");
        validate("Sacar", "predefProc");
        validate("Mover", "predefProc");
        validate("IrAlBorde", "predefProc");
        validate("VaciarTablero", "predefProc");
    }

    @Test
    public void arrowKeys() {
        validate("ARROW_LEFT", "arrowKey");
        validate("ARROW_RIGHT", "arrowKey");
        validate("ARROW_UP", "arrowKey");
        validate("ARROW_DOWN", "arrowKey");
    }

    @Test
    public void specialKeys() {
        validate("SPACE", "specialKey");
        validate("ENTER", "specialKey");
        validate("TAB", "specialKey");
        validate("BACKSPACE", "specialKey");
        validate("DELETE", "specialKey");
        validate("ESCAPE", "specialKey");
        validate("CTRL_D", "specialKey");
    }

    @Test
    public void key() {
        validate("SPACE", "key"); // estos pinchan si no se agrega el end() a UpperChar en la regla para "key".
        validate("ARROW_LEFT", "key");
        validate("A", "key");
        validate("1", "key");
    }

    @Test
    public void keyDefinition() {
        validate("K_SPACE", "keyDef");
        validate("K_ARROW_LEFT", "keyDef");
        validate("K_A", "keyDef");
        validate("K_K", "keyDef");
        validate("K_1", "keyDef");
        validate("K_CTRL_D", "keyDef");
    }

    @Test
    public void keyAssociation() {
        validate("K_SPACE -> {}", "keyAssoc");
        validate("K_TAB->{}", "keyAssoc");
        validate("K_0 -> { Poner(Rojo) }", "keyAssoc");
        validate("K_ARROW_DOWN -> { Poner(Rojo) }", "keyAssoc");
        validate("K_A -> { }", "keyAssoc");
    }

    @Test
    public void defaultKeyAssociation() {
        validate("_->{}", "defaultKeyAssoc");
        validate("_ -> { Poner(Verde) }", "defaultKeyAssoc");
    }

    @Test
    public void keyAssociations() {
        validate("_ -> {}", "keyAssocs");
        validate("K_SPACE -> {}", "keyAssocs");
        validate("K_0 -> {Poner(Rojo)} K_1 -> {Poner(Verde) }", "keyAssocs");
        validate("K_TAB -> {} _ -> {}", "keyAssocs");
    }

    @Test
    public void interactiveProgram() {
        validate("interactive program { _ -> {} } ", "iProgDef");
        validate("interactive program { K_R -> {Poner(Rojo)} K_V -> {Poner(Verde)} } ", "iProgDef");
        validate("interactive program { K_R -> {Poner(Rojo)} K_V -> {Poner(Verde)} _ -> {Salir()} } ", "iProgDef");
    }

    @Test
    public void program() {
        validate("program {/* comment*/}", "bProgDef");
        validate("program {a:=23;}", "bProgDef");
        validate("program {a:=23;}", "bProgDef");
        validate("program {a:=23; return (a)}", "bProgDef");
        validate("program {a:=23; return (a);}", "bProgDef");
        validate("program {a:=23;\n (varName1 ,varName2) := f(1,2);}", "bProgDef");
        validate("program {a:=23;\n (varName1 ,varName2) := f(1,2); \n Sacar ( Verde );}", "bProgDef");
        validate("program {a:=23;\n (varName1 ,varName2) := f(1,2); \n Sacar ( Verde );}", "bProgDef");
        validate("program /* comment*/ {if (1 < 2 || True /= False ) {a:=23;}\n (varName1 ,varName2) := f(1,2); \n Sacar ( Verde );}", "bProgDef");
        validate("program {/*Hola Gente */ \n /*como estan */ }", "bProgDef");
        validate("program { //Un comentario de linea \n //Otro comentario de linea \n }", "bProgDef");
    }

    @Test
    public void programDefinition() {
        validate("program {}", "programDef");
        validate("interactive program {}", "programDef");
    }

    @Test
    public void definition() {
        validate("procedure SimpleProcedure(varName1, varName2) {Poner(Verde);Sacar(Rojo)}", "def");
        validate("function simpleFunction() {Poner(Rojo) return (1);}", "def");
    }

    @Test
    public void definitions() {
        validate("procedure SimpleProcedure(varName1, varName2) {Poner(Verde);Sacar(Rojo)}", "defs");
        validate("procedure SimpleProcedure(varName1, varName2) {Poner(Verde);Sacar(Rojo)} function simpleFunction() {Poner(Rojo) return (1);}", "defs");
    }

    @Test
    public void gobstones() {
        validate("procedure Proc() {} program {} ", "gobstones");
        validate("program {} procedure Proc() {} ", "gobstones");
        validate("procedure Proc() {} function func() {return (0)} interactive program { _ -> {}} ", "gobstones");
        validate("interactive program { _ -> {}} ", "gobstones");
        validate("program { } ", "gobstones");
    }

    @Test
    public void predefinedFunctions() {
        validate("nroBolitas", "predefFunc");
        validate("hayBolitas", "predefFunc");
        validate("puedeMover", "predefFunc");
        validate("siguiente", "predefFunc");
        validate("previo", "predefFunc");
        validate("opuesto", "predefFunc");
        validate("minBool", "predefFunc");
        validate("maxBool", "predefFunc");
        validate("minDir", "predefFunc");
        validate("maxDir", "predefFunc");
        validate("minColor", "predefFunc");
        validate("maxColor", "predefFunc");
    }

    @Test
    public void literals() {
        validate("True", "literal");
        validate("Verde", "literal");
        validate("Oeste", "literal");
        validate("10", "literal");
        validate("-10", "literal");
    }

    @Test
    public void funcName() {
        validate("funcionName", "funcName");
        validate("anotherfunctionname", "funcName");
    }

    @Test
    public void varTuple() {
        validate("()", "varTuple");
        validate("(varName)", "varTuple");
        validate("(varName1, varName2)", "varTuple");
        validate("(varName1,varName2)", "varTuple");
        validate("(varName1 ,varName2)", "varTuple");
        validate("( varName1 ,varName2 ,lal )", "varTuple");
    }

    @Test
    public void gexpComponents() {
        validate("variable", "natom");
        validate("otra", "natom");
        validate("-variable", "natom");
        validate("- variable", "natom");
        validate("minColor()", "natom");
        validate("Rojo", "natom");
        validate("Norte", "natom");
        validate("2", "natom");

        validate("variable", "nfactL");
        validate("variable^2", "nfactL");
        validate("variable^2^3", "nfactL");
        validate("variable^funcion()", "nfactL");

        validate("variable div 2", "nfactH");
        validate("5 mod 3", "nfactH");

        validate("3*5", "nterm");
        validate("3 * 5", "nterm");
        validate("variable*5", "nterm");
        validate("variable*5^2", "nterm");
        validate("variable*(5^2)", "nterm");
        validate("(variable*5)^2", "nterm");

        validate("2 + variable", "nexp");
        validate("variable - 3", "nexp");
        validate("variable - ( (3*2) ^ 2 )", "nexp");

        validate("var == Rojo", "batom");
        validate("var + 2^2 == 6", "batom");
        validate("var /= Este", "batom");
        validate("var < var2", "batom");
        validate("var <= var2", "batom");
        validate("var > var2", "batom");
        validate("var >= var2", "batom");
        validate("var1 + var2 >= var3", "batom");

        validate("var", "bfact");
        validate("not var", "bfact");
        validate("not (var /= Este)", "bfact");
        validate("not (not var == Rojo)", "bfact");
        validate("var2 == (not var == Rojo)", "bfact");

        validate("var", "bterm");
        validate("not var", "bterm");
        validate("not var && var2", "bterm");
        validate("var == Rojo && var2 /= Azul", "bterm");
        validate("1 <= var && var2 /= Este && func() == 1", "bterm");

        validate("var", "bexp");
        validate("not var", "bexp");
        validate("not var || var2", "bexp");
        validate("var == Rojo || var2 /= Azul && var3 || var4", "bexp");
        validate("var == Rojo || var2 /= Azul && funcion(Rojo) || cantidadDeBolitas()", "bexp");
    }

    @Test
    public void funcCall() {
        validate("funcion()", "funcCall");
        validate("funcion(Rojo)", "funcCall");
    }

    @Test
    public void gexp() {
        validate("(True)", "gexp");
        validate("True", "gexp");
        validate("(False)", "gexp");
        validate("False", "gexp");
        validate("1 div 2", "gexp");
        validate("1", "gexp");
        validate("1+2", "gexp");
        validate("1/=2", "gexp");
        validate("1 < 2", "gexp");
        validate("(5 + 3) - 3", "gexp");
        validate("not True", "gexp");
        validate("f()", "gexp");
        validate("fa( )", "gexp");
        validate("f( 1 )", "gexp");
        validate("f(xa(y(1)))", "gexp");
        validate("f( 1 ,3)", "gexp");
        validate("f( 1 ,3+3)", "gexp");
        validate("f( 1 ,f2(3+3))", "gexp");
        validate("f(1+3)", "gexp");
        validate("fd(Norte,Este,Oeste,12+67+True||   func(3))", "gexp");
        validate("fd(1,2+3,Oeste)", "gexp");
        validate("(3/=2) && 1<2 || (34>=221) && 12 || ((5 + -3) > 3)", "gexp");
        validate("(Oeste/=Este) && Sur<21 ^3|| (-234>=Oeste) && Norte || ((5 + 3) > Sur)", "gexp");
        validate("(Oeste/=Este) && f() && fd(1,2+3,Oeste)|| (Este>=Oeste) && True || ((5 + 3) > 3 + aFunction(1+3))",
                "gexp");
    }

    @Test
    public void simpleCmd() {
        validate("Skip", "simpleCmd");
        validate("a:=23", "simpleCmd");
        validate("(varName1 ,varName2) := f(1,2)", "simpleCmd");
        validate("AlgoBlablaLargo(Verde)", "simpleCmd");
        validate("AlgoBlablaLargoBlabLa(Verde)", "simpleCmd");
        validate("Sacar ( Verde ) ", "simpleCmd");
        validate("Poner(Verde)", "simpleCmd");
        validate("Mover(Oeste)", "simpleCmd");
        validate("IrAlBorde(Oeste)", "simpleCmd");
        validate("VaciarTablero()", "simpleCmd");
        validate("AProc()", "simpleCmd");
        validate("AnotherProcWithParams(Verde, 1+3)", "simpleCmd");
    }

    @Test
    public void neCmds() {
        validate("Poner(Negro)", "neCmds");
        validate("Poner(Negro);", "neCmds");
        validate("Poner(Negro);Poner(Negro)", "neCmds");
        validate(" Poner(Negro) ; Poner(Negro) ", "neCmds");
        validate("Poner(Negro)Poner(Negro)", "neCmds");
        validate(" Poner(Negro) Poner(Negro) ", "neCmds");
    }

    @Test
    public void cmds() {
        validate("", "cmds");
        validate("Poner(Negro)", "cmds");
        validate("Poner(Negro);Poner(Negro)Poner(Negro)Poner(Negro)", "cmds");
        validate(" Poner(Negro) ; Poner(Negro) Poner(Negro)Poner(Negro) ", "cmds");
    }

    @Test
    public void blockCmd() {
        validate("{}", "blockCmd");
        validate("{Poner(Negro)}", "blockCmd");
        validate("{Poner(Negro);Poner(Negro)Poner(Negro)Poner(Negro)}", "blockCmd");
        validate("{ Poner(Negro) ; Poner(Negro) Poner(Negro)Poner(Negro) }", "blockCmd");
        validate("{\n\nPoner(Negro) ; Poner(Negro) Poner(Negro)Poner(Negro)\n\n}", "blockCmd");
    }

    @Test
    public void compCmd() {
        validate("{}", "compCmd");
        validate("{Poner(Negro)}", "compCmd");
        validate("{Poner(Negro);Poner(Negro)Poner(Negro)Poner(Negro)}", "compCmd");
        validate("if (True) {}", "compCmd");
        validate("if (True) then {Poner(Negro);Poner(Rojo);Mover(Este)}", "compCmd");
        validate("repeat (300) {Poner(Rojo)}", "compCmd");
        validate("repeat (300)\n\n{\n\nPoner(Rojo)\n\n}", "compCmd");
    }

    @Test
    public void ifCmd() {
        validate("if (1<1  ) {}", "compCmd");
        validate("if (  True || False < True ) then {}", "compCmd");
        validate("if (1 < 2 ) then {}", "compCmd");
        validate("if (True) {}", "compCmd");
        validate("if (True) then {Poner(Negro);Poner(Rojo);Mover(Este)}", "compCmd");
        validate("if (True) then {Poner(Negro)} else {Poner(Rojo)}", "compCmd");
    }

    @Test
    public void repeatCmd() {
        validate("repeat (300) {Poner(Rojo)}", "compCmd");
        validate("repeat (300)\n\n{\n\nPoner(Rojo)\n\n}", "compCmd");
        validate("repeat(300){Poner(Rojo)}", "compCmd");
    }

    @Test
    public void whileCmd() {
        validate("while (True) {Poner(Rojo)}", "compCmd");
        validate("while ( False )\n{\n\nPoner(Verde)\n\n}", "compCmd");
        validate("while (1 < 2) {Poner(Negro)}", "compCmd");
    }

    @Test
    public void cmd() {
        validate("Skip", "cmd");
        validate("a:=23", "cmd");
        validate("(varName1 ,varName2) := f(1,2)", "cmd");
        validate("PonerAlgoBlablaLargo(Verde)", "cmd");
        validate("PonedAlgoBlablaLargo(Verde)", "cmd");
        validate("Sacar ( Verde ) ", "cmd");
        validate("if (1 < 2 ) {}", "cmd");
        validate("if ((5 + 3) - 3) {}", "cmd");
        validate("if (True) then {}", "cmd");
        validate("if (True) {}", "cmd");
        validate("if (Rojo && Verde) {}", "cmd");
    }

    @Test
    public void foreachCmd() {
        validate("foreach varName in [1,2,3] {Poner(Rojo)}", "compCmd");
        validate("foreach varName in [True,False] {Poner(Verde)}", "compCmd");
        validate("foreach varName in [1..10] {Poner(Negro)}", "compCmd");
        validate("foreach varName in [1,2..10] {Sacar(Rojo)}", "compCmd");
    }

    @Test
    public void switchCmd() {
        validate("switch (varName) to _->{Poner(Rojo)}", "compCmd");
        validate("switch (True) to _->{Poner(Rojo)}", "compCmd");
        validate("switch (1) /* comment*/ to _->{Poner(Verde)}", "compCmd");
        validate("switch (1) to\n1->{Poner(Verde)}\n_->{Poner(Rojo)\n}", "compCmd");
        validate("switch (1) to\n1->{Poner(Verde)}\n2->{Poner(Negro)}\n_->{Poner(Rojo)\n}", "compCmd");
    }

    @Test
    public void procedure() {
        validate("procedure SimpleProcedure() {Poner(Verde)}", "procedure");
        validate("procedure SimpleProcedure() {}", "procedure");
        validate("procedure SimpleProcedure(varName1, varName2) {Poner(Verde)}", "procedure");
        validate("procedure SimpleProcedure(varName1, varName2) {Poner(Verde);Sacar(Rojo)}", "procedure");
    }

    @Test
    public void function() {
        validate("function simpleFunction() {Poner(Rojo) return (1);}", "function");
        validate("function simpleFunction2() {Poner(Rojo) return (1)}", "function");
        validate("function simpleFunction3() {return (True);}", "function");
        validate("function anotherFunction() {return (False)}", "function");
        validate("function proceduraaaa() {return (False);}", "function");
        validate("function ifprobando() {return (False);}", "function");
        validate("function saasprobando() {return (False, True);}", "function");
    }
}
