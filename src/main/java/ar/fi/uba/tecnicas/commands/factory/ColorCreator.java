package ar.fi.uba.tecnicas.commands.factory;

import java.util.HashMap;
import java.util.Map;

import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.ReservedWords;

/**
 * ColorCreator
 * 
 * Responsabilidad: Convierte una palabra reservada de Gobstones a un Color de
 * nuestro modelo.
 *
 */

public class ColorCreator {

	private Map<String, Color> expectedValues;

	public ColorCreator() {
		expectedValues = new HashMap<String, Color>();
		expectedValues.put(ReservedWords.COLOR_BLACK, Color.BLACK);
		expectedValues.put(ReservedWords.COLOR_GREEN, Color.GREEN);
		expectedValues.put(ReservedWords.COLOR_BLUE, Color.BLUE);
		expectedValues.put(ReservedWords.COLOR_RED, Color.RED);
	}

	public Color getColor(String color) {
		if (expectedValues.containsKey(color))
			return expectedValues.get(color);
		return null;
	}

}
