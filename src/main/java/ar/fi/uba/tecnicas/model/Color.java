package ar.fi.uba.tecnicas.model;

/**
 * Color
 * 
 * Responsabilidad: Representa el color de una bolita particular del tablero.
 * Contiene los tipos de colores existentes en Gobstones.
 * 
 */
public enum Color implements OrderedValue{
	BLUE(ReservedWords.COLOR_BLUE), BLACK(ReservedWords.COLOR_BLACK), RED(
			ReservedWords.COLOR_RED), GREEN(ReservedWords.COLOR_GREEN);

	private String colorName;

	Color(String color) {
		this.colorName = color;
	}

	public String getColorName() {
		return colorName;
	}

	public OrderedValue getNext() {
		Color[] possibleColors = values();
		return possibleColors[(this.ordinal() + 1) % possibleColors.length];
	}

	public OrderedValue getPrevious() {
		Color[] possibleColors = values();
		Integer previousColorIndex = this.ordinal() - 1;
		if (previousColorIndex < 0)
			previousColorIndex = possibleColors.length - 1;
		return possibleColors[previousColorIndex];

	}

}
