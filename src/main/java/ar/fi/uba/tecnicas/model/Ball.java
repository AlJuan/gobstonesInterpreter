package ar.fi.uba.tecnicas.model;

/**
 * Ball
 * 
 * Responsabilidad: Representa una pelota del modelo de Gobstones con su
 * respectivo color.
 * 
 * */
public class Ball {

	private Color color;

	public Ball(Color c) {
		this.color = c;
	}

	public Color getColor() {
		return this.color;
	}
}
