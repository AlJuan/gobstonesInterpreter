package ar.fi.uba.tecnicas.model;

/**
 * Program
 * 
 * Responsabilidad: Representa un programa de Gobstones que ejecuta comandos
 * sobre un tablero
 * 
 */

public class Program {

	private Board board;
	
	private ProgramStack programStack;

	private CodeBlock codeBlock;

	public Program() {
		this.programStack = new ProgramStack();
	}

	public CodeBlock getCodeBlock() {
		return codeBlock;
	}

	public void setCodeBlock(CodeBlock codeBlock) {
		this.codeBlock = codeBlock;
	}

	public void execute() {
		this.codeBlock.execute(board, programStack);
	}

	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public ProgramStack getProgramStack() {
		return programStack;
	}

}
