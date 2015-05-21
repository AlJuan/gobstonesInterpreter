package ar.fi.uba.tecnicas.model;

import java.util.List;

import ar.fi.uba.tecnicas.functions.Function;

/**
 * CodeBlock
 * 
 * Responsabilidad: Representa un bloque de codigo Gobstones. Al ejecutarlo se
 * ejecutan todos los comandos que contiene.
 * 
 */
public class CodeBlock {

	private List<Function> codeLines;

	public CodeBlock() {
		super();
	}

	public CodeBlock(List<Function> codeLines) {
		this.codeLines = codeLines;
	}

	public void execute(Board board, ProgramStack parentProgramStack) {
		ProgramStack localProgramStack = parentProgramStack.copyProgramStack();
		for (Function command : codeLines) {
			command.execute(board, localProgramStack);
		}
		parentProgramStack.updateProgramStack(localProgramStack);
	}

	public List<Function> getCodeLines() {
		return codeLines;
	}

	public void setCodeLines(List<Function> codeLines) {
		this.codeLines = codeLines;
	}

}
