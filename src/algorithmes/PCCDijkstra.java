package algorithmes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import exceptions.ArcNégatifEx;
import exceptions.NoPathEx;
import graphes.IGraph;

public class PCCDijkstra {
	// Nombre indiquant l'absence de calcul de la distance pour le noeud concerné
	private static final int NON_CALCULE = -100;
	
	
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
	
	
	/**
	 * @brief Vérifie si toutes les conditions suivantes sont réunies :
	 *  	- Le noeud successeur n'a pas été mis de côté par l'algorithme (son
	 * 		  chemin n'est donc pas certain d'être optimal)
	 * 		- Il existe un arc du noeud prédécesseur au noeud successeur
	 *  	- L'une des conditions suivantes doit être remplie :
	 * 			- Aucun chemin entre le noeud de départ et le noeud successeur
	 * 			  n'a encore été calculé/trouvé
	 * 			- Le chemin (en passant par le noeud prédécesseur) est plus
	 * 			  optimisé que le chemin actuel
	 *
	 * @param g Graphe
	 * @param distances HashMap de la longueur du chemin actuel entre le noeud
	 * 					de départ et le noeud en clé
	 * @param noeudP Noeud prédécesseur à noeudS
	 * @param noeudS Noeud successeur à noeudP
	 * @return true si les conditions sont réunies, false sinon
	 */
	private static boolean peutRemplacerLaDistanceActuelle(IGraph g, 
			Map<String, Integer> distances, String noeudP, String noeudS) {
		return 	distances.containsKey(noeudS) && g.aArc(noeudP, noeudS) &&
				(distances.get(noeudS) == NON_CALCULE || distances.get(noeudS) >
					g.getValeur(noeudP, noeudS) + distances.get(noeudP));
	}
	
	
	/**
	 * @brief Vérifie si toutes les conditions suivantes sont réunies :
	 *  	- Le noeud potentiellement successeur ne doit pas avoir été mis de
	 * 		  côté par l'algorithme (on ne refait pas 2 fois les calculs)
	 *  	- La distance doit avoir été déjà calculée (il existe bien un chemin
	 * 		  entre le noeud de départ et le noeud potentiellement successeur)
	 *  	- Le noeud potentiellement successeur ne peut être identique au
	 * 		  noeud "actuel"
	 *  	- L'une des conditions suivantes doit être remplie :
	 *  		- Aucun noeud n'a encore été choisi comme noeud successeur au
	 * 			  noeud "actuel"
	 *  		- Le noeud actuellement testé a une longueur de chemin
	 * 			  inférieure au noeud successeur (et est donc plus intéressant)
	 *
	 * @param distances HashMap de la longueur du chemin actuel entre le noeud
	 * 					de départ et le noeud en clé
	 * @param noeudP Noeud prédécesseur à noeudSucc
	 * @param noeudSucc Noeud successeur à noeudP
	 * @param noeudSuiv Noeud actuellement choisi pour être le suivant
	 * @return true si les conditions sont réunies, false sinon
	 */
	private static boolean peutEtreLeProchainNoeud(Map<String, Integer> distances,
							String noeudP, String noeudSucc, String noeudSuiv) {
		return distances.containsKey(noeudSucc) &&
				distances.get(noeudSucc) != NON_CALCULE &&
				!noeudP.equals(noeudSucc) && (noeudSuiv == null || 
				distances.get(noeudSuiv) > distances.get(noeudSucc));
	}
	
	
	/**
	 * @brief Choisi le noeud sur lequel sera calculé les nouvelles longueur des
	 * 		  chemin des autres noeuds
	 * @param g Le graphe
	 * @param distances HashMap de la longueur du chemin actuel entre le noeud
	 * 					de départ et le noeud en clé
	 * @param predecesseurs HashMap des prédécesseurs des noeuds en clé
	 * @param noeudP Le noeud précédant (le noeud de départ si c'est la première
	 * 				 boucle, le noeudSuivant précédant sinon)
	 * @return noeudSuivant, le noeud suivant
	 */
	private static String choixNoeudSuivant(IGraph g, Map<String, Integer> distances,
							Map<String, String> predecesseurs, String noeudP){
		// Impossible de prendre un noeud déjà terminé
		assert(distances.containsKey(noeudP));
		
		String noeudSuivant = null; // Aucun noeud suivant n'est choisi par défaut
		
		for (String noeudS : g.noeuds()) {
			if (peutRemplacerLaDistanceActuelle(g, distances, noeudP, noeudS)) {
				distances.put(noeudS, g.getValeur(noeudP, noeudS) + distances.get(noeudP));
				predecesseurs.put(noeudS, noeudP);
			}
			
			if (peutEtreLeProchainNoeud(distances, noeudP, noeudS, noeudSuivant)){
				noeudSuivant = noeudS;
			}
		}
		
		distances.remove(noeudP);
			
		return noeudSuivant;
	}
	
	
	/**
	 * @param predecesseurs HashMap des prédécesseurs des noeuds en clé
	 * @param noeudA Noeud d'arrivée
	 * @return Le chemin du noeud de départ au noeud d'arrivée
	 */
	private static String affichage(Map<String, String> predecesseurs,
							String noeudD, String noeudA) {
		StringBuilder sb = new StringBuilder();
		
		String i = noeudA;
		
		while(true){
			if (!i.equals(noeudA))
				sb.insert(0,  " - ");
			sb.insert(0, i);
			if (!i.equals(noeudD))
				i = predecesseurs.get(i);
			else
				break;
		}
		
		return sb.toString();
	}
	
	
	/**
	 * @brief Initialise la HashMap de longueur de chaque noeud
	 * @param g Graphe
	 * @param noeudD Noeud de départ
	 * @return HashMap initialisée de la longueur du chemin actuel entre le noeud
	 * 		   de départ en indéterminée (sauf le noeud de départ, fixée à 0) et
	 * 		   le noeud en clé
	 */
	private static Map<String, Integer> initialisationDistances(IGraph g,
											String noeudD){
		Map<String, Integer> distances = new HashMap<>();
		
		// Rempli le tableau de distance à "l'infini"
		for (String i : g.noeuds()) {
			if (!i.equals(noeudD))
				distances.put(i, NON_CALCULE);
		}
		
		/* La longueur du chemin pour le noeud de départ est mis à 0
		 * pour faciliter l'implémentation de l'algorithme
		 */
		distances.put(noeudD, 0);
		
		return distances;
	}
	
	
	/**
	 * @brief Algorithme de Dijkstra
	 * @param g Graphe contenant les noeuds
	 * @param noeudD Noeud de départ
	 * @param noeudA Noeud d'arrivée
	 * @return Le chemin le plus court entre noeudD et noeudA
	 * @throws ArcNégatifEx, présence d'au moins un arc négatif dans le graphe, impossible de calculer n'importe quel chemin
	 * @throws NoPathEx, aucun chemin entre le noeud de départ et le noeud d'arrivé trouvé
	 */
	public static String algorithmeDijkstra(IGraph g, String noeudD, String noeudA)
											throws ArcNégatifEx, NoPathEx {
		if (!estOK(g)) { throw new ArcNégatifEx(); }
		
		// HashMap des longueurs de chemins pour chaque noeud
		Map<String, Integer> distances = initialisationDistances(g, noeudD);
		
		// HashMap de prédécecesseur de chaque noeud
		Map<String, String> predecesseurs = new HashMap<>();
		
		// Noeud sur lequel sera calculé les longueur des chemins
		String noeudActuel = noeudD;
		
		/* Tant que le noeud d'arrivée n'a pas la certitude d'avoir eu le chemin
		   le plus court, poursuivre l'algorithme */
		while(distances.containsKey(noeudA)) {
			/* Si aucun noeud n'a été choisi comme prochain noeud de calcul
			 * et que le noeud d'arrivée n'a pas encore trouvé de chemin
			 * certifié optimisé, alors il n'y a pas de chemins atteignable
			 * pour le noeud d'arrivé */
			if (noeudActuel == null) { throw new NoPathEx(); }
			
			// On actualise en permanence le noeud "actuel"
			noeudActuel = choixNoeudSuivant(g, distances, predecesseurs, noeudActuel);
		}
		
		return affichage(predecesseurs, noeudD, noeudA);
	}
}
