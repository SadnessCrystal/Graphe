package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import graphes.GrapheMA;

class GrapheMATest {
	private static final int NB_NOEUDS = 6;
	@Test
	void test() {
		GrapheMA g = new GrapheMA(NB_NOEUDS);
		assertEquals(NB_NOEUDS, g.getNbNoeuds());
		
		g.ajouterArc(1,2,1);
		g.ajouterArc(1,3,1);
		g.ajouterArc(1,4,1);
		g.ajouterArc(1,5,1);
		g.ajouterArc(2,5,1);
		g.ajouterArc(4,4,1);
		g.ajouterArc(5,1,1);
		
		assertTrue(g.aArc(1,5));
		assertTrue(g.aArc(4,4));
		assertTrue(g.aArc(5,1));
		assertFalse(g.aArc(4,1));
		assertFalse(g.aArc(6,6));
		
		assertEquals(4,g.dOut(1)); // degr� sortant
		assertEquals(1,g.dOut(2));
		assertEquals(0,g.dOut(3));
		assertEquals(1,g.dOut(5));
		assertEquals(0,g.dOut(6));
		
		assertEquals(1, g.dIn(1)); // degr� entrant
		assertEquals(2, g.dIn(4));
		assertEquals(2, g.dIn(5));
		assertEquals(0, g.dIn(6));
		
		assertTrue(g.toString().contentEquals(
			"0 1 1 1 1 0 \n"+
			"0 0 0 0 1 0 \n"+
			"0 0 0 0 0 0 \n"+
			"0 0 0 1 0 0 \n"+
			"1 0 0 0 0 0 \n"+
			"0 0 0 0 0 0 \n")
		);
	}
}