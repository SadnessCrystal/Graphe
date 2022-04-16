package algorithmes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import exceptions.ArcNégatifEx;
import exceptions.NoPathEx;
import graphes.IGraph;

// Notes : les HashMap peuvent être optimisées en n'insérant les éléments au fur et à mesure au lieu de le faire tous en même temps ???

public class PCCDijkstra {
	private static final int FIN = -1;			// Nombre indiquant la fin du calcul de la distance pour le noeud concerné
	private static final int NON_CALCULE = -100;// Nombre indiquant l'absence de calcul de la distance pour le noeud conserné
	
	
	private PCCDijkstra() {
		throw new IllegalStateException("Classe utilitaire");
	}
	
	/**
	 * @brief Indique si l'algorithme de Dijkstra peut ici être appliqué
	 * @param g Le graphe
	 * @return true si applicable, false sinon
	 */
	public static boolean estOK(IGraph g) {
		for (String i : g.noeuds()) {
			for (String j : g.noeuds()) {
				if (g.aArc(i, j) && g.getValeur(i, j) < 0)
					return false;
			}
		}
		return true;
	}
	
	/*
	 * 	Vérifie si toutes les conditions suivantes sont réunies :
	 * 		- Le noeud actuellement testé n'a pas été mis de côté par l'algorithme (son chemin n'est donc pas certain d'être optimal)
	 * 		- Il existe un arc entre le noeud "actuel" et le noeud actuellement testé
	 *  	- L'une des conditions suivantes doit être remplie :
	 * 			- Aucun chemin entre le noeud de départ et le noeud testé n'a encore été calculé
	 * 			- Le chemin (en passant par le noeud "actuel") est plus optimisé que le chemin actuel
	 */
	private static boolean peutRemplacerLaDistanceActuelle(IGraph g, Map<String, Integer> distances, String noeudA, String noeudS) {
		return 	distances.get(noeudS) != FIN && g.aArc(noeudA, noeudS) && (distances.get(noeudS) == NON_CALCULE ||
					distances.get(noeudS) > g.getValeur(noeudA, noeudS) + distances.get(noeudA));
	}
	
	
	/*
	 * 	Vérifie si toutes les conditions suivantes sont réunies :
	 *  	- L'une des conditions suivantes doit être remplie :
	 *  		- Aucun noeud n'a encore été choisi comme noeud succésseur au noeud "actuel"
	 *  		- Le noeud actuellement testé a une longueur de chemin inférieure au noeud succésseur (et est donc plus intéressant)
	 *  	- Le noeud potentiellement successeur ne doit pas avoir été mis de côté par l'algorithme (on ne refait pas 2 fois les calculs)
	 *  	- La distance doit avoir été déjà calculée (il existe bien un chemin entre le noeud de départ et le noeud potentiellement successeur)
	 *  	- Le noeud potentiellement successeur ne peut être identique au noeud "actuel"
	 */
	private static boolean peutEtreLeProchainNoeud(Map<String, Integer> distances, Map<String, String> predecesseurs, String idxSucc, String idxNoeudSuivant, String idxNoeudActuel) {
		return (idxNoeudSuivant == null || distances.get(idxNoeudSuivant) > distances.get(idxSucc)) &&
				distances.get(idxSucc) != FIN && distances.get(idxSucc) != NON_CALCULE &&
					!idxNoeudActuel.equals(idxSucc);
	}
	
	
	private static String choixNoeudSuivant(IGraph g, Map<String, Integer> distances, Map<String, String> predecesseurs, String noeudActuel){
		// Impossible de prendre un noeud déjà terminé
		assert(distances.get(noeudActuel) != FIN);
		
		String noeudSuivant = null;
		
		for (String noeudSucc : g.noeuds()) {
			if (peutRemplacerLaDistanceActuelle(g, distances, noeudActuel, noeudSucc)) {
				distances.put(noeudSucc, g.getValeur(noeudActuel, noeudSucc) + distances.get(noeudActuel));
				predecesseurs.put(noeudSucc, noeudActuel);
			}
			
			System.out.println("Noeud " + noeudSucc + " : " + distances.get(noeudSucc) + "[" + predecesseurs.get(noeudSucc) + "]");
		
			if (peutEtreLeProchainNoeud(distances, predecesseurs, noeudSucc, noeudSuivant, noeudActuel)){
				noeudSuivant = noeudSucc;
			}
		}
		
		distances.put(noeudActuel, FIN);
		
		System.out.println("   -> Choix : " + noeudSuivant + "\n");
			
		return noeudSuivant;
	}
	
	
	private static String affichage(Map<String, String> predecesseurs, String noeudD, String noeudA) {
		LinkedList<String> liste = new LinkedList<>();
		liste.addLast(noeudA);
		
		while(!liste.getLast().equals(noeudD)) {
			liste.addLast(predecesseurs.get(liste.getLast()));
		}
		
		
		StringBuilder sb = new StringBuilder();
		
		while(!liste.isEmpty()) {
			sb.append(liste.removeLast());
			
			if (!liste.isEmpty())
				 sb.append(" - ");
		}
		
		return sb.toString();
	}
	
	
	private static Map<String, Integer> initialisationDistances(IGraph g, String noeudD){
		Map<String, Integer> distances = new HashMap<>();
		
		// Rempli le tableau de distance à l'infini
		for (String i : g.noeuds()) {
			if (!i.equals(noeudD))
				distances.put(i, NON_CALCULE);
			else
				distances.put(noeudD, 0);				
		}
		
		return distances;
	}
	
	private static Map<String, String> initialisationPredecesseurs(IGraph g, String noeudD){
		Map<String, String> predecesseurs = new HashMap<>();
		
		// Indique le début du graphe : absance définitive de prédécesseur
		for (String i : g.noeuds()) {
			if (!i.equals(noeudD))
				predecesseurs.put(i, noeudD);
		}
		
		return predecesseurs;
	}
	
	
	public static String algorithmeDijkstra(IGraph g, String noeudD, String noeudA) throws ArcNégatifEx, NoPathEx {
		if (!estOK(g)) { throw new ArcNégatifEx(); }
		
		Map<String, Integer> distances = initialisationDistances(g, noeudD);
		Map<String, String> predecesseurs = initialisationPredecesseurs(g, noeudD);
		
		String noeudActuel = noeudD;
		
		while(distances.get(noeudA) != FIN) {
			if (noeudActuel == null)
				throw new NoPathEx();
			
			noeudActuel = choixNoeudSuivant(g, distances, predecesseurs, noeudActuel);
		}
		
		return affichage(predecesseurs, noeudD, noeudA);
	}
}
