package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import graphes.GrapheLA;
import algorithmes.PCCDijkstra;

public class PCCDijkstraTest {
	@Test
	public void exo3_1_1() {
		GrapheLA g1 = new GrapheLA(9);
		
		g1.ajouterArc(1, 3, 2);		// A -> C (2)
		g1.ajouterArc(1, 4, 1);  	// A -> D (1)
		
		g1.ajouterArc(2, 7, 3);		// B -> G (3)
		
		g1.ajouterArc(3, 8, 2);		// C -> H (2)
		
		g1.ajouterArc(4, 2, 3);		// D -> B (3)
		g1.ajouterArc(4, 3, 5);		// D -> C (5)
		g1.ajouterArc(4, 5, 3);		// D -> E (3)
		
		g1.ajouterArc(5, 3, 1);		// E -> C (1)
		g1.ajouterArc(5, 7, 3);		// E -> G (3)
		g1.ajouterArc(5, 8, 7);		// E -> H (7)
		
		g1.ajouterArc(7, 2, 2);		// G -> B (2)
		g1.ajouterArc(7, 6, 1);		// G -> F (1)
		
		g1.ajouterArc(8, 6, 4);		// H -> F (4)
		g1.ajouterArc(8, 7, 2);		// H -> G (2)
		
		g1.ajouterArc(9, 8, 10);	// I -> H (10)
		
		assertEquals(PCCDijkstra.algoritmeDijkstra(g1, 1, 2), "A - D - B");
		assertEquals(PCCDijkstra.algoritmeDijkstra(g1, 1, 3), "A - C");
		assertEquals(PCCDijkstra.algoritmeDijkstra(g1, 1, 4), "A - D");
		assertEquals(PCCDijkstra.algoritmeDijkstra(g1, 1, 5), "A - D - E");
		//assertEquals(PCCDijkstra.algoritmeDijkstra(g1, 1, 6), "A - C - H - G - F");
		//assertEquals(PCCDijkstra.algoritmeDijkstra(g1, 1, 7), "A - C - H - G");
		assertEquals(PCCDijkstra.algoritmeDijkstra(g1, 1, 8), "A - C - H");
	}
	
	
	@Test
	public void exo3_2() {
		
	}
}
