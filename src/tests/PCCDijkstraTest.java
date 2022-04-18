package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import graphes.GrapheLA;
import graphes.GrapheMA;
import graphes.IGraph;
import algorithmes.PCCDijkstra;
import exceptions.NoPathEx;

public class PCCDijkstraTest {
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

        assertTrue(PCCDijkstra.estOK(g));

        //!!!
//        System.out.print(PCCDijkstra.algorithmeDijkstra(g, "A", "A"));
        
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "B"), "A - D - B");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "C"), "A - C");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "D"), "A - D");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "E"), "A - D - E");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "F"), "A - C - H - G - F");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "G"), "A - C - H - G");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "H"), "A - C - H");
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "A", "I"));


        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "B", "A"));
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "B", "C"));
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "B", "D"));
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "B", "E"));
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "B", "F"), "B - G - F");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "B", "G"), "B - G");
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "B", "H"));
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "B", "I"));
        
        
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "C", "A"));
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "C", "B"), "C - H - G - B");
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "C", "D"));
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "C", "E"));
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "C", "F"), "C - H - G - F");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "C", "G"), "C - H - G");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "C", "H"), "C - H");
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "C", "I"));
        
        
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "D", "A"));
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "D", "B"), "D - B");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "D", "C"), "D - E - C");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "D", "E"), "D - E");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "D", "F"), "D - B - G - F");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "D", "G"), "D - B - G");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "D", "H"), "D - E - C - H");
        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, "D", "I"));
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
		
		assertTrue(PCCDijkstra.estOK(g));

        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "B"), "A - B");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "C"), "A - B - C");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "D"), "A - D");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "E"), "A - D - E");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "F"), "A - D - J - F");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "G"), "A - D - E - G");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "H"), "A - D - E - I - H");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "I"), "A - D - E - I");
        assertEquals(PCCDijkstra.algorithmeDijkstra(g, "A", "J"), "A - D - J");
        System.out.print(PCCDijkstra.algorithmeDijkstra(g, "B", "F"));
	}
	
	@Test
	public void exo3_6_1() {
		//String[] noeuds = {"A", "B", "C", "D", "E", "F", "G"};

		String[] noeuds = {"G", "F", "E", "D", "C", "B", "A"};
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
		
		assertFalse(PCCDijkstra.estOK(g));
	}
}
