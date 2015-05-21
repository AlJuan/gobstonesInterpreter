package ar.fi.uba.tecnicas.model;

import java.util.ArrayList;
import java.util.List;
import java.lang.Boolean;

import ar.fi.uba.tecnicas.exceptions.OutOfBoundsException;

/**
 * Board
 * 
 * Responsabilidad: Representa el tablero de Gobstones. Contiene la lista de las
 * diferentes celdas del tablero, asi como la posicion del cabezal en el
 * tablero.
 */

public class Board {

	public Integer getColumnsQuantity() {
		return columnsQuantity;
	}

	public void setColumnsQuantity(Integer columnsQuantity) {
		this.columnsQuantity = columnsQuantity;
	}

	public Integer getRowsQuantity() {
		return rowsQuantity;
	}

	public void setRowsQuantity(Integer rowsQuantity) {
		this.rowsQuantity = rowsQuantity;
	}

	private Integer columnsQuantity;
	private Integer rowsQuantity;
	private List<List<Cell>> cells;
	private Position actualPosition;

	public Board(Integer columns, Integer rows) {
		this.columnsQuantity = columns;
		this.rowsQuantity = rows;
		createEmptyBoard(columns, rows);
		this.actualPosition = new Position(0, 0);
	}

	private void createEmptyBoard(Integer columns, Integer rows) {
		this.cells = new ArrayList<List<Cell>>();
		for (int x = 0; x < columns; x++) {
			cells.add(createColumn(rows));
		}
	}

	public void empty() {
		this.createEmptyBoard(this.columnsQuantity, this.rowsQuantity);
	}

	public void move(Orientation orientation) {
		validateResultingPosition(orientation.getPosition());
		actualPosition = actualPosition.add(orientation.getPosition());
	}

	public Position getActualPosition() {
		return this.actualPosition;
	}

	public void remove(Color color) {
		Cell actualCell = this.getActualCell();
		actualCell.remove(color);
	}

	// Places a new ball in the actual position
	public void place(Color color) {
		Cell actualCell = this.getActualCell();
		actualCell.place(new Ball(color));
	}

	// Returns the amount of balls placed on the actual position
	public Integer getQuantity() {
		Cell actualCell = this.getActualCell();
		return actualCell.getQuantity();
	}

	public Cell getActualCell() {
		return getCell(this.actualPosition);
	}

	public Cell getCell(Position position) {
		List<Cell> column = this.cells.get(position.getX());
		return column.get(position.getY());
	}

	public void goToEdge(Orientation orientation) {
		Position nextPosition = this.actualPosition.add(orientation.getPosition());
		while (!isOutOfBounds(nextPosition)) {
			this.move(orientation);
			nextPosition = this.actualPosition.add(orientation.getPosition());
		}
	}

	private List<Cell> createColumn(Integer rows) {
		List<Cell> row = new ArrayList<Cell>();
		for (int y = 0; y < rows; y++) {
			row.add(new Cell());
		}
		return row;
	}

	private void validateResultingPosition(Position pos) {
		Position resultingPosition = this.actualPosition.add(pos);
		if (isOutOfBounds(resultingPosition))
			throw new OutOfBoundsException(resultingPosition.getX(),
					resultingPosition.getY());
	}

	public Boolean canMove(Orientation orientation) {
		Position moveResultPosition = this.actualPosition.add(orientation.getPosition()); 
		return !isOutOfBounds(moveResultPosition);
	}

	private Boolean isOutOfBounds(Position pos) {
		return pos.getX() < 0 || pos.getY() < 0
				|| pos.getX() >= this.columnsQuantity
				|| pos.getY() >= this.rowsQuantity;
	}

	public Integer getQuantityByColor(Color color) {
		return this.getActualCell().getQuantityByColor(color);
	}

}
