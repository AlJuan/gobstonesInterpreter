package ar.fi.uba.tecnicas.model;


/**
 * GobstonesBoolean
 * 
 * Responsabilidad: Representa un valor numerico de Gobstones, el cual esta
 * ordenado, se puede comparar y negar.
 * 
 */
public class GobstonesNumber implements OrderedValue, OpposableValue, Comparable {

	private Double value;

	public GobstonesNumber(Integer value) {
		this.value = 0.0 + value;
	}
	
	public GobstonesNumber(Double value) {
		this.value = value;
	}

	@Override
	public OrderedValue getNext() {
		return new GobstonesNumber(value + 1);
	}

	@Override
	public OrderedValue getPrevious() {
		return new GobstonesNumber(value - 1);
	}

	@Override
	public OpposableValue getOpposite() {
		Double opposite = new Double(value);
		return new GobstonesNumber(opposite *= -1);
	}
	
	public Double getValue() {
		return value;
	}

	public Integer intValue() {
		return value.intValue();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GobstonesNumber other = (GobstonesNumber) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object other) {
		GobstonesNumber otherNumber = (GobstonesNumber) other;
		return this.value.compareTo(otherNumber.getValue());
	}

}
