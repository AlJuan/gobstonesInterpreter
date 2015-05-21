package ar.fi.uba.tecnicas.commands.factory;

import java.util.HashMap;
import java.util.Map;

import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.ReservedWords;

/**
 * OrientationCreator
 * 
 * Responsabilidad: Convierte una palabra reservada de Gobstones a un
 * Orientation de nuestro modelo.
 * 
 */

public class OrientationCreator {

	private Map<String, Orientation> expectedValues;

	public OrientationCreator() {
		expectedValues = new HashMap<String, Orientation>();
		expectedValues.put(ReservedWords.ORIENTATION_NORTH, Orientation.NORTH);
		expectedValues.put(ReservedWords.ORIENTATION_SOUTH, Orientation.SOUTH);
		expectedValues.put(ReservedWords.ORIENTATION_EAST, Orientation.EAST);
		expectedValues.put(ReservedWords.ORIENTATION_WEST, Orientation.WEST);
	}

	public Orientation getOrientation(String orientation) {
		if (expectedValues.containsKey(orientation))
			return expectedValues.get(orientation);
		return null;
	}

}
