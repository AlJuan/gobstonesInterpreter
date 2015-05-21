package ar.fi.uba.tecnicas.model;

/**
 * OrderedConstant
 * 
 * Responsabilidad: Representa una palabra reservada (constante) ordenada de
 * gobstones
 * 
 */
public interface OrderedValue {
	public OrderedValue getNext();

	public OrderedValue getPrevious();
}
