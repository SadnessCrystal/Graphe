package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import algorithmes.PCCBellman;
import graphes.GrapheLA;
import graphes.GrapheMA;
import graphes.IGraph;

public class PCCBellmanTest {
	@Test
	public void exo3_1_1() {
		//String[] noeuds = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
		String[] noeuds = {"I", "H", "G", "F", "E", "D", "C", "B", "A"};
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
	public void exo3_2() {
		//String[] noeuds = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

		String[] noeuds = {"J", "I", "H", "G", "F", "E", "D", "C", "B", "A"};
		
		IGraph g = new GrapheMA(noeuds);


        g.ajouterArc("A", "B", 8);      // A -> B (8)
        g.ajouterArc("A", "D", 3);      // A -> D (3)

        g.ajouterArc("B", "C", 4);      // B -> C (4)
        g.ajouterArc("B", "E", 5);      // B -> E (5)

        g.ajouterArc("C", "F", 1);      // C -> F (1)
        g.ajouterArc("C", "I", 5);      // C -> I (5)

        g.ajouterArc("D", "E", 2);      // D -> E (2)
        g.ajouterArc("D", "J", 1);      // D -> J (1)

        g.ajouterArc("E", "G", 3);      // E -> G (3)
        g.ajouterArc("E", "I", 2);      // E -> I (2)

        g.ajouterArc("F", "H", 5);      // F -> H (5)

        g.ajouterArc("G", "H", 4);      // G -> H (4)

        g.ajouterArc("I", "H", 2);      // I -> H (2)

        g.ajouterArc("J", "F", 6);      // J -> F (6)
        g.ajouterArc("J", "G", 6);      // J -> G (6)
		
		assertTrue(PCCBellman.estOK(g));
		
		assertEquals(PCCBellman.algorithmeBellman(g, "A", "B"), "A - B");
        assertEquals(PCCBellman.algorithmeBellman(g, "A", "C"), "A - B - C");
        assertEquals(PCCBellman.algorithmeBellman(g, "A", "D"), "A - D");
        assertEquals(PCCBellman.algorithmeBellman(g, "A", "E"), "A - D - E");
        assertEquals(PCCBellman.algorithmeBellman(g, "A", "F"), "A - D - J - F");
        assertEquals(PCCBellman.algorithmeBellman(g, "A", "G"), "A - D - E - G");
        assertEquals(PCCBellman.algorithmeBellman(g, "A", "H"), "A - D - E - I - H");
        assertEquals(PCCBellman.algorithmeBellman(g, "A", "I"), "A - D - E - I");
        assertEquals(PCCBellman.algorithmeBellman(g, "A", "J"), "A - D - J");
        System.out.print(PCCBellman.algorithmeBellman(g, "B", "F"));
	}
	
	
	@Test
	public void exo3_6_1() {
		String[] noeuds = {"A", "B", "C", "D", "E", "F", "G"};
		IGraph g = new GrapheLA(noeuds);
		
        g.ajouterArc("A", "B", 3);      // A -> B (7)
        g.ajouterArc("A", "C", 1);      // A -> C (1)

        g.ajouterArc("B", "D", 4);      // B -> D (4)
        g.ajouterArc("B", "E", 2);      // B -> E (2)
        g.ajouterArc("B", "F", -3);     // B -> F (-3)

        g.ajouterArc("C", "B", 5);      // C -> B (5)
        g.ajouterArc("C", "E", 2);      // C -> E (2)
        g.ajouterArc("C", "F", 7);      // C -> F (7)

        g.ajouterArc("D", "G", 4);      // D -> G (4)

        g.ajouterArc("E", "G", 10);     // E -> G (10)

        g.ajouterArc("F", "E", 3);      // F -> E (3)
		
		assertTrue(PCCBellman.estOK(g));
	}

	@Test
	public void exoBonus() {
		String[] noeuds = {"0", "1", "2", "3"};
		IGraph g = new GrapheLA(noeuds);
		
		g.ajouterArc("0", "1", 5);	// 0 -> 1 (5)
		g.ajouterArc("0", "2", 4);	// 0 -> 2 (4)
		
		g.ajouterArc("1", "3", 3);	// 1 -> 3 (3)
		
		g.ajouterArc("2", "1", -6);	// 2 -> 1 (-6)
		
		g.ajouterArc("3", "2", 2);	// 3 -> 2 (2)
		
		assertFalse(PCCBellman.estOK(g));
	}
}
