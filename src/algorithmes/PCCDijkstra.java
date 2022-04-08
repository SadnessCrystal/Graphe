package algorithmes;

import java.util.LinkedList;

import exceptions.ArcNégatifEx;
import graphes.Igraph;

public class PCCDijkstra {
	
	private PCCDijkstra() {
		throw new IllegalStateException("Classe utilitaire");
	}
	
	/**
	 * @brief Indique si l'algorithme de Dijkstra peut ici être appliqué
	 * @param g Le graphe
	 * @return true si applicable, false sinon
	 */
	private static boolean estOK(Igraph g) {
		for (int i = 0; i<g.getNbNoeuds(); ++i) {
			for (int j=0; j < g.getNbNoeuds(); ++j) {
				if (g.valeurArc(i+1, j+1) < 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	// Si le noeud testé n'est pas terminé ET que l'arc existe ET (que sa distance n'est pas calculée OU qu'elle est plus optimisée)
	private static boolean peutRemplacerLaDistanceActuelle(Igraph g, int[][] listeDistances, int idxNoeudActuel, int idxSucc) {
		return listeDistances[idxSucc][0] != -1   &&   g.aArc(idxNoeudActuel+1, idxSucc+1)   &&   (listeDistances[idxSucc][0] == -100 ||
				listeDistances[idxSucc][0] > g.valeurArc(idxNoeudActuel+1, idxSucc+1) + listeDistances[idxNoeudActuel][0]);
	}
	
	
	
	private static int choixNoeudSuivant(Igraph g, int[][] listeDistances, int idxNoeudActuel) {
		// Impossible de prendre un noeud déjà terminé
		assert(listeDistances[idxNoeudActuel][0] != -1);
		
		int idxNoeudSuivant = -100;
		
		for (int idxSucc=0; idxSucc<g.getNbNoeuds(); ++idxSucc) {
			if (peutRemplacerLaDistanceActuelle(g, listeDistances, idxNoeudActuel, idxSucc)) {
				listeDistances[idxSucc] = new int[] {g.valeurArc(idxNoeudActuel+1, idxSucc+1) + listeDistances[idxNoeudActuel][0], idxNoeudActuel};
			}
			
			//Debug : System.out.println("   Noeud " + idxSucc + " : " + listeDistances[idxSucc][0] + "[" + listeDistances[idxSucc][1] + "]");
			
			// Pas le début
			if (listeDistances[idxSucc][1] != -1 &&
					// Valeur non prise OU optimsable
					(idxNoeudSuivant == -100 || listeDistances[idxNoeudSuivant][0] > listeDistances[idxSucc][0]) &&
							// Pas déjà terminé
							listeDistances[idxSucc][0] != -1 && 
								// La distance doit avoir été déjà calculée
								listeDistances[idxSucc][0] != -100 &&
									// Le prochain ne peut être identique à l'actuel
									idxNoeudActuel != idxSucc){
				idxNoeudSuivant = idxSucc;
			}
		}
		
		listeDistances[idxNoeudActuel][0] = -1;
		
		//Debug : System.out.println("\nChoix : " + idxNoeudSuivant);
		
		return idxNoeudSuivant;
	}
	
	
	private static String affichage(int[][] listeDistances, int idxNoeudsDepart, int idxNoeudsArrivee) {
		LinkedList<Integer> liste = new LinkedList<>();
		liste.addLast(idxNoeudsArrivee);
		
		while(liste.getLast() != idxNoeudsDepart) {
			liste.addLast(listeDistances[liste.getLast()][1]);
		}
		
		
		StringBuilder sb = new StringBuilder();
		
		while(!liste.isEmpty()) {
			sb.append(String.valueOf((char)(liste.removeLast()+1 + 64)));
			
			if (liste.size() != 0)
				 sb.append(" - ");
		}
		
		return sb.toString();
	}
	
	
	public static String algoritmeDijkstra(Igraph g, int numNoeudsDepart, int numNoeudsArrivee) throws ArcNégatifEx {
		if (!estOK(g)) { throw new ArcNégatifEx(); }
		
		int[][] listeDistances = new int[g.getNbNoeuds()][2];
		
		
		// Rempli le tableau de distance à l'infini
		for (int i=0; i<g.getNbNoeuds(); ++i) {
			if (i != numNoeudsDepart - 1) {
				listeDistances[i][0] = -100;
			}
		}
		
		// Indique le début du graphe : absance définitive de prédécesseur
		listeDistances[numNoeudsDepart - 1][1] = -1;
		
		int idxNoeudActuel = numNoeudsDepart - 1;
		
		while(listeDistances[numNoeudsArrivee-1][0] == -100) {
			idxNoeudActuel = choixNoeudSuivant(g, listeDistances, idxNoeudActuel);
		}
		
		return affichage(listeDistances, numNoeudsDepart-1, numNoeudsArrivee-1);
	}
}
