package ar.fi.uba.tecnicas.io;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicas.jgobstones.Printer;
import ar.fi.uba.tecnicas.model.Ball;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Cell;
import ar.fi.uba.tecnicas.model.Position;

/**
 * AbstractConsolePrinter
 * 
 */

public abstract class AbstractConsolePrinter implements Printer {

	protected abstract void printPosition(int posX, int posY);

	protected abstract void printBalls(String color,
			int quantityOfBallsOfSameColor);

	protected abstract void printHeader(Board board);
	
	protected abstract void printFooter(Board board);

	public void print(Board board) {
		this.printHeader(board);
		for (int posX = 0; posX < board.getColumnsQuantity(); posX++) {
			for (int posY = 0; posY < board.getRowsQuantity(); posY++) {
				Position position = new Position(posX, posY);
				Cell cell = board.getCell(position);
				int quantityOfBallsInCell = cell.getQuantity();
				if (quantityOfBallsInCell != 0) {
					printPosition(posX, posY);
					printBallsPerCell(cell);
				}
			}
		}
		this.printFooter(board);
	}

	private void printBallsPerCell(Cell cell) {
		List<Ball> listOfBalls = cell.getBalls();
		List<String> listOfBallColorsInCell = getColorsInCell(listOfBalls);
		for (String color : listOfBallColorsInCell) {
			int quantityOfBallsOfSameColor = getQuantityOfBallsByColor(
					listOfBalls, color);
			printBalls(color, quantityOfBallsOfSameColor);
		}
		System.out.println();
	}

	private Integer getQuantityOfBallsByColor(List<Ball> balls, String color) {
		int quantity = 0;
		for (Ball actualBall : balls) {
			if (color == actualBall.getColor().getColorName()) {
				quantity++;
			}
		}
		return quantity;
	}

	public List<String> getColorsInCell(List<Ball> balls) {
		int pos = 0;
		List<String> listOfColors = new ArrayList<String>();
		String actualColor = "";
		for (Ball actualBall : balls) {
			if (pos == 0) {
				actualColor = actualBall.getColor().getColorName();
				listOfColors.add(actualColor);
				pos++;
			} else {
				if (actualColor != actualBall.getColor().getColorName()
						&& !contains(listOfColors, actualBall.getColor()
								.getColorName())) {
					actualColor = actualBall.getColor().getColorName();
					listOfColors.add(actualColor);
					pos++;
				}
			}
		}
		return listOfColors;
	}

	public boolean contains(List<String> listOfColors, String color) {
		boolean hasColor = false;
		for (String colorInList : listOfColors) {
			if (colorInList == color) {
				hasColor = true;
			}
		}
		return hasColor;
	}
}
