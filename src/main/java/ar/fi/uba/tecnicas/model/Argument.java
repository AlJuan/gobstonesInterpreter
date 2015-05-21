package ar.fi.uba.tecnicas.model;

/**
 * Argument
 * 
 * Responsabilidad: Representa un argumento de un comando de Gobstones
 * 
 */
public class Argument {

	private Object value;

	public Argument(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
