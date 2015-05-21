package ar.fi.uba.tecnicas.model;

/**
 * GobstonesBoolean
 * 
 * Responsabilidad: Representa un valor Booleano de Gobstones, el cual esta
 * ordenado.
 * 
 */
@SuppressWarnings("rawtypes")
public enum GobstonesBoolean implements OrderedValue {

	FALSE(java.lang.Boolean.FALSE), TRUE(java.lang.Boolean.TRUE);

	private java.lang.Boolean value;

	GobstonesBoolean(java.lang.Boolean value) {
		this.value = value;
	}

	public GobstonesBoolean getNext() {
		GobstonesBoolean[] possibleBooleans = values();
		return possibleBooleans[(this.ordinal() + 1) % possibleBooleans.length];
	}

	public GobstonesBoolean getPrevious() {
		GobstonesBoolean[] possibleBooleans = values();
		Integer previousBooleanIndex = this.ordinal() - 1;
		if (previousBooleanIndex < 0)
			previousBooleanIndex = possibleBooleans.length - 1;
		return possibleBooleans[previousBooleanIndex];

	}

	public static GobstonesBoolean getGobstonesBooleanByValue(Boolean bool) {
		if (bool)
			return GobstonesBoolean.TRUE;
		return GobstonesBoolean.FALSE;
	}

	public Boolean getValue() {
		return value;
	}

}
