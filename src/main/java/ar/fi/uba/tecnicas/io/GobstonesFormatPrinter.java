package ar.fi.uba.tecnicas.io;

import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Position;

/**
 * GobstonesFormatPrinter
 * 
 * Responsabilidad: Implementacion de la interfaz Printer que permite imprimir
 * el tablero a traves de la consola
 * 
 */

public class GobstonesFormatPrinter extends AbstractConsolePrinter {

	protected void printHeader(Board board) {
		System.out.println("GBB/1.0");
		System.out.println("size " + board.getColumnsQuantity() + " "
				+ board.getRowsQuantity());
	}

	protected void printPosition(int posX, int posY) {
		System.out.print("cell " + posX + " " + posY + " ");
	}

	protected void printBalls(String color, int quantityOfBallsOfSameColor) {
		System.out.print(color + " " + quantityOfBallsOfSameColor + " ");
	}
	
	protected void printFooter(Board board) {
		Position actualPosition = board.getActualPosition();
		System.out.println("head " + actualPosition.getX() + " "
				+ actualPosition.getY());
		System.out.println("%%");
	}
}
