package tests.anciens_tests;
//
//import static org.junit.Assert.*;
//
//import org.junit.jupiter.api.Test;
//
//import algorithmes.PCCBellman;
//import algorithmes.PCCDijkstra;
//import exceptions.ArcNégatifEx;
//import exceptions.CircuitAbsorbantEx;
//import exceptions.NoPathEx;
//import graphes.IGraphe;
//import graphes.types.GrapheLA;
//import graphes.types.GrapheMA;
//
//public class PCCTestDouble {
//	private void comparateurAlgorithmiqueResultat(IGraphe g) {
//		for (String i : g) {
//			for (String j : g) {
//        		if (!i.equals(j)) {
//        			try {
//	        			String r1 = PCCBellman.algorithmeBellman(g, i, j);
//	        			String r2 = PCCDijkstra.algorithmeDijkstra(g, i, j);
//	        			if (!r1.equals(r2)) {
//	        				System.out.println(i + " -> " + j + " : " + r1 + " contre " + r2);
//	        			}
//        				assertEquals(r1, r2);
//        			}
//        			catch(NoPathEx e) {
//        		        assertThrows(NoPathEx.class, () -> PCCBellman.algorithmeBellman(g, i, j));
//        		        assertThrows(NoPathEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, i, j));
//        			}
//        		}
//        	}
//        }
//	}
//	
//	private void comparateurAlgorithmiqueThrows(IGraphe g) {
//		for (String i : g) {
//			for (String j : g) {
//        		if (!i.equals(j)) {
//        			assertThrows(ArcNégatifEx.class, () -> PCCDijkstra.algorithmeDijkstra(g, i, j));
//        			assertThrows(CircuitAbsorbantEx.class, () -> PCCBellman.algorithmeBellman(g, i, j));
//        		}
//        	}
//        }
//	}
//	
//	
//	public void comparateurAlgorithmique(IGraphe g) {
//		assertEquals("Ce graphe ne peut pas être comparé via ces 2 algorithmes à la fois", PCCBellman.estOK(g), PCCDijkstra.estOK(g));
//		
//		if (PCCDijkstra.estOK(g))
//			comparateurAlgorithmiqueResultat(g);
//		else
//			comparateurAlgorithmiqueThrows(g);
//	}
//	
//	
//	@Test
//	public void exo3_2() {
//		IGraphe g = new GrapheMA(10);
//
//        g.ajouterArc(1, 8, 2);      // A -> B (8)
//        g.ajouterArc("A", "D", 3);      // A -> D (3)
//
//        g.ajouterArc("B", "C", 4);      // B -> C (4)
//        g.ajouterArc("B", "E", 5);      // B -> E (5)
//
//        g.ajouterArc("C", "F", 1);      // C -> F (1)
//        g.ajouterArc("C", "I", 5);      // C -> I (5)
//
//        g.ajouterArc("D", "E", 2);      // D -> E (2)
//        g.ajouterArc("D", "J", 1);      // D -> J (1)
//
//        g.ajouterArc("E", "G", 3);      // E -> G (3)
//        g.ajouterArc("E", "I", 2);      // E -> I (2)
//
//        g.ajouterArc("F", "H", 5);      // F -> H (5)
//
//        g.ajouterArc("G", "H", 4);      // G -> H (4)
//
//        g.ajouterArc("I", "H", 2);      // I -> H (2)
//
//        g.ajouterArc("J", "F", 6);      // J -> F (6)
//        g.ajouterArc("J", "G", 6);      // J -> G (6)
//        
//        comparateurAlgorithmique(g);
//	}
//	
//	
//	@Test
//	public void exoFaitMain() {
//		IGraphe g = new GrapheLA(7);
//		
//		g.ajouterArc(0, 1, 1);		// A -> B (1)
//		g.ajouterArc("A", "F", 10);
//		
//		g.ajouterArc("B", "C", 1);
//		
//		g.ajouterArc("C", "D", 1);
//		
//		g.ajouterArc("D", "E", 1);
//
//		g.ajouterArc("E", "F", 1);
//		
//		g.ajouterArc("F", "G", 10);
//        
//        comparateurAlgorithmique(g);
//	}
//	
//	@Test
//	public void exoBonus() {
//		IGraphe g = new GrapheLA(4);
//		
//		System.out.print(g);
//		
//		g.ajouterArc("0", "1", 5);	// 0 -> 1 (5)
//		g.ajouterArc("0", "2", 4);	// 0 -> 2 (4)
//		
//		g.ajouterArc("1", "3", 3);	// 1 -> 3 (3)
//		
//		g.ajouterArc("2", "1", -6);	// 2 -> 1 (-6)
//		
//		g.ajouterArc("3", "2", 2);	// 3 -> 2 (2)
//		
//		System.out.print(g);
//		
//        comparateurAlgorithmique(g);
//	}
//}
