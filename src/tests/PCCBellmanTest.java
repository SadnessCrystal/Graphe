package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import algorithmes.PCCBellman;
import graphes.GrapheLA;

public class PCCBellmanTest {
	@Test
	public void exo3_1_1() {
		GrapheLA g = new GrapheLA(9);
		
		g.ajouterArc(1, 3, 2);	// A -> C (2)
		g.ajouterArc(1, 4, 1);  // A -> D (1)
		
		g.ajouterArc(2, 7, 3);	// B -> G (3)
		
		g.ajouterArc(3, 8, 2);	// C -> H (2)
		
		g.ajouterArc(4, 2, 3);	// D -> B (3)
		g.ajouterArc(4, 3, 5);	// D -> C (5)
		g.ajouterArc(4, 5, 3);	// D -> E (3)
		
		g.ajouterArc(5, 3, 1);	// E -> C (1)
		g.ajouterArc(5, 7, 3);	// E -> G (3)
		g.ajouterArc(5, 8, 7);	// E -> H (7)
		
		g.ajouterArc(7, 2, 2);	// G -> B (2)
		g.ajouterArc(7, 6, 1);	// G -> F (1)
		
		g.ajouterArc(8, 6, 4);	// H -> F (4)
		g.ajouterArc(8, 7, 2);	// H -> G (2)
		
		g.ajouterArc(9, 8, 10);	// I -> H (10)
		
		assertFalse(PCCBellman.estOK(g));
	}
	
	@Test
	public void exo3_2() {
		GrapheLA g = new GrapheLA(10);
		
		g.ajouterArc(1, 2, 8);	// A -> B (8)
		g.ajouterArc(1, 4, 3);	// A -> D (3)
		
		g.ajouterArc(2, 3, 4);	// B -> C (4)
		g.ajouterArc(2, 5, 5);	// B -> E (5)
		
		g.ajouterArc(3, 6, 1);	// C -> F (1)
		g.ajouterArc(3, 9, 5);	// C -> I (5)
		
		g.ajouterArc(4, 5, 2);	// D -> E (2)
		g.ajouterArc(4, 10, 1);	// D -> J (1)
		
		g.ajouterArc(5, 7, 3);	// E -> G (3)
		g.ajouterArc(5, 9, 2);	// E -> I (2)
		
		g.ajouterArc(6, 8, 5);	// F -> H (5)
		
		g.ajouterArc(7, 8, 4);	// G -> H (4)
		
		g.ajouterArc(9, 8, 2);	// I -> H (2)
		
		g.ajouterArc(10, 6, 6);	// J -> F (6)
		g.ajouterArc(10, 7, 6);	// J -> G (6)
		
		assertTrue(PCCBellman.estOK(g));
		
		assertEquals(PCCBellman.algorithmeBellman(g, 1, 2), "A - B");
		assertEquals(PCCBellman.algorithmeBellman(g, 1, 3), "A - B - C");
		assertEquals(PCCBellman.algorithmeBellman(g, 1, 4), "A - D");
		assertEquals(PCCBellman.algorithmeBellman(g, 1, 5), "A - D - E");
		assertEquals(PCCBellman.algorithmeBellman(g, 1, 6), "A - D - J - F");
		assertEquals(PCCBellman.algorithmeBellman(g, 1, 7), "A - D - E - G");
		assertEquals(PCCBellman.algorithmeBellman(g, 1, 8), "A - D - E - I - H");
		assertEquals(PCCBellman.algorithmeBellman(g, 1, 9), "A - D - E - I");
		assertEquals(PCCBellman.algorithmeBellman(g, 1, 10), "A - D - J");
	}
	
	@Test
	public void exo3_6_1() {
		GrapheLA g = new GrapheLA(7);
		
		g.ajouterArc(1, 2, 3);	// A -> B (7)
		g.ajouterArc(1, 3, 1);	// A -> C (1)
		
		g.ajouterArc(2, 4, 4);	// B -> D (4)
		g.ajouterArc(2, 5, 2);	// B -> E (2)
		g.ajouterArc(2, 6, -3);	// B -> F (-3)
		
		g.ajouterArc(3, 2, 5);	// C -> B (5)
		g.ajouterArc(3, 5, 2);	// C -> E (2)
		g.ajouterArc(3, 6, 7);	// C -> F (7)
		
		g.ajouterArc(4, 7, 4);	// D -> G (4)
		
		g.ajouterArc(5, 7, 10);	// E -> G (10)
		
		g.ajouterArc(6, 5, 3);	// F -> E (3)
		
		assertTrue(PCCBellman.estOK(g));
	}

	@Test
	public void exoBonus() {
		GrapheLA g = new GrapheLA(4);
		
		g.ajouterArc(1, 2, 5);	// 0 -> 1 (5)
		g.ajouterArc(1, 3, 4);	// 0 -> 2 (4)
		
		g.ajouterArc(2, 4, 3);	// 1 -> 3 (3)
		
		g.ajouterArc(3, 2, -6);	// 2 -> 1 (-6)
		
		g.ajouterArc(4, 3, 2);	// 3 -> 2 (2)
		
		assertFalse(PCCBellman.estOK(g));
	}
}
