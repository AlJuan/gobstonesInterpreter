package ar.fi.uba.tecnicas.model;

/**
 * Orientation
 * 
 * Responsabilidad: Representa una direccion en la que se puede mover el cabezal
 *
 */

public enum Orientation implements OrderedValue, OpposableValue {
	NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0);

	private Position position;

	Orientation(Integer posX, Integer posY) {
		this.position = new Position(posX, posY);
	}

	public Position getPosition() {
		return this.position;
	}

	public OrderedValue getNext() {
		Orientation[] possibleOrientations = values();
		return possibleOrientations[(this.ordinal() + 1)
				% possibleOrientations.length];
	}

	public OrderedValue getPrevious() {
		Orientation[] possibleOrientations = values();
		Integer previousOrientationIndex = this.ordinal() - 1;
		if (previousOrientationIndex < 0)
			previousOrientationIndex = possibleOrientations.length - 1;
		return possibleOrientations[previousOrientationIndex];

	}

	private Orientation getOrientationByValue(Position position) {
		for (Orientation orientation : values()) {
			if (orientation.getPosition().equals(position))
				return orientation;
		}
		return null;
	}

	@Override
	public OpposableValue getOpposite() {
		return getOrientationByValue(this.getPosition().multiply(-1));
	}
}
