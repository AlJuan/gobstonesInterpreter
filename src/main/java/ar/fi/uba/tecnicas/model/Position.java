package ar.fi.uba.tecnicas.model;

/**
 * Position
 * 
 * Responsabilidad: Representa una posicion particular del tablero.
 */

public class Position {

	private Integer x, y;

	public Position(Integer x, Integer y) {
		if (x == null || y == null)
			throw new NullPointerException();
		this.setX(x);
		this.setY(y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (!x.equals(other.x) || !y.equals(other.y))
			return false;
		return true;
	}

	public Position add(Position position) {
		return new Position(this.x + position.getX(), this.y + position.getY());
	}
	
	public Position multiply(Integer times) {
		return new Position(this.x * times, this.y * times);
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

}
