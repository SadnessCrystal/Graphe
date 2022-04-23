package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import algorithmes.PCCBellman;
import exceptions.NoPathEx;
import graphes.GrapheLA;
import graphes.GrapheMA;
import graphes.IGraph;

public class PCCBellmanTest {
	@Test
	public void exo3_1_1() {
		String[] noeuds = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
		IGraph g = new GrapheMA(noeuds);

        g.ajouterArc("A", "C", 2);      // A -> C (2)
        g.ajouterArc("A", "D", 1);      // A -> D (1)

        g.ajouterArc("B", "G", 3);      // B -> G (3)

        g.ajouterArc("C", "H", 2);      // C -> H (2)

        g.ajouterArc("D", "B", 3);      // D -> B (3)
        g.ajouterArc("D", "C", 5);      // D -> C (5)
        g.ajouterArc("D", "E", 3);      // D -> E (3)

        g.ajouterArc("E", "C", 1);      // E -> C (1)
        g.ajouterArc("E", "G", 3);      // E -> G (3)
        g.ajouterArc("E", "H", 7);      // E -> H (7)

        g.ajouterArc("G", "B", 2);      // G -> B (2)
        g.ajouterArc("G", "F", 1);      // G -> F (1)

        g.ajouterArc("H", "F", 4);      // H -> F (4)
        g.ajouterArc("H", "G", 2);      // H -> G (2)

        g.ajouterArc("I", "H", 10);		// I -> H (10)
		
		assertFalse(PCCBellman.estOK(g));
	}
	
	@Test
	public void exo3_6_1() {
		String[] noeuds = {"A", "B", "C", "D", "E", "F", "G"};
		IGraph g = new GrapheLA(noeuds);
		
        g.ajouterArc("A", "B", 7);      // A -> B (7)
        g.ajouterArc("A", "C", 1);      // A -> C (1)

        g.ajouterArc("B", "D", 4);      // B -> D (4)
        g.ajouterArc("B", "E", 2);      // B -> E (2)
        g.ajouterArc("B", "F", -3);     // B -> F (-3)

        g.ajouterArc("C", "B", 5);      // C -> B (5)
        g.ajouterArc("C", "E", 2);      // C -> E (2)
        g.ajouterArc("C", "F", 7);      // C -> F (7)

        g.ajouterArc("D", "G", 4);      // D -> G (4)

        g.ajouterArc("E", "G", 10);     // E -> G (10)

        /// !!! modifier dans l'autre !!!
        g.ajouterArc("F", "D", 5);		// F -> D (5)
        g.ajouterArc("F", "E", 3);      // F -> E (3)
		
		assertTrue(PCCBellman.estOK(g));
		
		assertEquals(PCCBellman.algorithmeBellman(g, "A", "B"), "A - C - B");
		assertEquals(PCCBellman.algorithmeBellman(g, "A", "C"), "A - C");
		assertEquals(PCCBellman.algorithmeBellman(g, "A", "D"), "A - C - B - F - D");
		assertEquals(PCCBellman.algorithmeBellman(g, "A", "E"), "A - C - E");
		assertEquals(PCCBellman.algorithmeBellman(g, "A", "F"), "A - C - B - F");
		assertEquals(PCCBellman.algorithmeBellman(g, "A", "G"), "A - C - B - F - D - G");
		
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "B", "A"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "B", "C"));
		assertEquals(PCCBellman.algorithmeBellman(g, "B", "D"), "B - F - D");
		assertEquals(PCCBellman.algorithmeBellman(g, "B", "E"), "B - F - E");
		assertEquals(PCCBellman.algorithmeBellman(g, "B", "F"), "B - F");
		// G?
		
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "C", "A"));
		assertEquals(PCCBellman.algorithmeBellman(g, "C", "B"), "C - B");
		assertEquals(PCCBellman.algorithmeBellman(g, "C", "D"), "C - B - F - D");
		assertEquals(PCCBellman.algorithmeBellman(g, "C", "E"), "C - E");
		assertEquals(PCCBellman.algorithmeBellman(g, "C", "F"), "C - B - F");
		assertEquals(PCCBellman.algorithmeBellman(g, "C", "G"), "C - B - F - D - G");
		
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "D", "A"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "D", "B"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "D", "C"));
		assertEquals(PCCBellman.algorithmeBellman(g, "D", "G"), "D - G");
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "D", "E"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "D", "F"));
		// G?
		
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "E", "A"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "E", "B"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "E", "C"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "E", "D"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "E", "F"));
		assertEquals(PCCBellman.algorithmeBellman(g, "E", "G"), "E - G");
		
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "F", "A"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "F", "B"));
		assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, "F", "C"));
		assertEquals(PCCBellman.algorithmeBellman(g, "F", "D"), "F - D");
		assertEquals(PCCBellman.algorithmeBellman(g, "F", "E"), "F - E");
		assertEquals(PCCBellman.algorithmeBellman(g, "F", "G"), "F - D - G");
	
		////// GGGGGG ?
	}
}
