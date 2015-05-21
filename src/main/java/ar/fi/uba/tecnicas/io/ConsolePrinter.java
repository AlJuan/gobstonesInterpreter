package ar.fi.uba.tecnicas.io;

import ar.fi.uba.tecnicas.model.Board;


/**
 * ConsolePrinter
 * 
 * Responsabilidad: Implementacion de la interfaz Printer que permite imprimir
 * el tablero a traves de la consola
 * 
 */

public class ConsolePrinter extends AbstractConsolePrinter {

	protected void printHeader(Board board){}
	
	protected void printFooter(Board board){}
	
	protected void printPosition(int posX, int posY) {
		System.out.print("Posicion: (" + posX + "," + posY + ")");
	}

	protected void printBalls(String color, int quantityOfBallsOfSameColor) {
		System.out.print(" Nro de pelotas de color " + color + ": "
				+ quantityOfBallsOfSameColor);
	}
}
