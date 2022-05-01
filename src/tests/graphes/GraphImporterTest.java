package tests.graphes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import graphes.ihm.Arc;
import graphes.ihm.GraphImporter;

class GraphImporterTest {

	@Test
	void test() {
		Arc a = GraphImporter.parse("1 -5 3");
		assertEquals(1, a.getSource());
		assertEquals(3, a.getDestination());
		assertEquals(-5, a.getValuation());
		Assertions.assertThrows( IllegalArgumentException.class, () -> {
	           GraphImporter.parse("a1 -5 3");
	  });

	}
	

}
