package ar.fi.uba.tecnicas.model;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tecnicas.exceptions.NoSuchColorException;

/**
 * Cell
 * 
 * Responsabilidad: Representa una celda dentro de un tablero de Gobstones.
 * Contiene las bolitas del tablero.
 * 
 */
public class Cell {

	private List<Ball> balls;

	public Cell() {
		this.balls = new ArrayList<Ball>();
	}

	public void place(Ball ball) {
		balls.add(ball);
	}

	public Integer getQuantity() {
		return balls.size();
	}

	public void remove(Color color) {
		for (Ball actualBall : balls) {
			if (actualBall.getColor().equals(color)) {
				balls.remove(actualBall);
				return;
			}
		}
		throw new NoSuchColorException(color.getColorName());
	}

	public List<Ball> getBalls() {
		return balls;
	}

	public Integer getQuantityByColor(Color color) {
		Integer quantityCounter = 0;
		for (Ball ball : balls){
			if (ball.getColor().equals(color))
				quantityCounter++;
		}
		return quantityCounter;
	}
}
