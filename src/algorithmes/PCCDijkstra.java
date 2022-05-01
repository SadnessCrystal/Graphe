package algorithmes;

import java.util.HashMap;
import java.util.Map;

import exceptions.ArcNégatifEx;
import exceptions.NoPathEx;
import graphes.IGraphe;

public class PCCDijkstra implements PCC {
	// Nombre indiquant l'absence de calcul de la distance pour le noeud concerné
	private static final int NON_CALCULE = -100;
	
	/**
	 * @brief Indique si l'algorithme de Dijkstra peut ici être appliqué
	 * @param g Le graphe
	 * @return true si applicable, false sinon
	 */
	@Override
	public boolean estOK(IGraphe g) {
		for (Integer i : g)
			for (Integer j : g)
				if (g.aArc(i, j) && g.getValuation(i, j) < 0)
					return false;
		return true;
	}
	
	
	/**
	 * @brief Initialise la HashMap de longueur de chaque noeud
	 * @param g Graphe
	 * @param noeudD Noeud de départ
	 * @return HashMap initialisée de la longueur du chemin actuel entre le noeud
	 * 		   de départ en indéterminée (sauf le noeud de départ, fixée à 0) et
	 * 		   le noeud en clé
	 */
	private static Map<Integer, Integer> initialisationDistances(IGraphe g,
											Integer noeudD){
		HashMap<Integer, Integer> distances = new HashMap<>();
		
		// Rempli le tableau de distance à "l'infini"
		for (Integer i : g)
			if (i != noeudD)
				distances.put(i, NON_CALCULE);
		
		/* La longueur du chemin pour le noeud de départ est mis à 0
		 * pour faciliter l'implémentation de l'algorithme
		 */
		distances.put(noeudD, 0);
		
		return distances;
	}
	
	
	/**
	 * @brief Vérifie si toutes les conditions suivantes sont réunies :
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
	private static boolean peutRemplacerDistanceActuelle(IGraphe g, 
			Map<Integer, Integer> distances, Integer noeudP, Integer noeudS) {
		return 	g.aArc(noeudP, noeudS) && (distances.get(noeudS) ==
				NON_CALCULE || distances.get(noeudS) >
				g.getValuation(noeudP, noeudS) + distances.get(noeudP));
	}
	
	
	/**
	 * @brief Vérifie si toutes les conditions suivantes sont réunies :
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
	private static boolean peutEtreLeProchainNoeud(Map<Integer, Integer> distances,
							Integer noeudP, Integer noeudSucc, Integer noeudSuiv) {
		return distances.get(noeudSucc) != NON_CALCULE &&
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
	 * @param noeudA : ???
	 * @return noeudSuivant, le noeud suivant
	 */
	private static Integer choixNoeudSuivant(IGraphe g, Map<Integer, Integer> distances,
							Map<Integer, Integer> predecesseurs, Integer noeudP, Integer noeudA){
		// Impossible de prendre un noeud déjà terminé
		assert(distances.containsKey(noeudP));
		
		Integer noeudSuivant = null; // Aucun noeud suivant n'est choisi par défaut
		
		// On parcourt tous les noeuds qui n'ont pas été mis de côté
		for (Integer noeudS : distances.keySet()) {
			if (peutRemplacerDistanceActuelle(g, distances, noeudP, noeudS)) {
				distances.put(noeudS, g.getValuation(noeudP, noeudS) + distances.get(noeudP));
				predecesseurs.put(noeudS, noeudP);
			}
			
			if (peutEtreLeProchainNoeud(distances, noeudP, noeudS, noeudSuivant)){
				noeudSuivant = noeudS;
			}
		}
		
		// Mit de côté, il n'est plus utile
		if (!noeudP.equals(noeudA))
			distances.remove(noeudP);
			
		return noeudSuivant;
	}
	
	
	/**
	 * @param predecesseurs HashMap des prédécesseurs des noeuds en clé
	 * @param noeudA Noeud d'arrivée
	 * @return Le chemin du noeud de départ au noeud d'arrivée
	 */
	private static String affichage(Map<Integer, Integer> predecesseurs,
							Integer noeudD, Integer noeudA, Integer distance) {
		StringBuilder sb = new StringBuilder();
		
		Integer i = noeudA;
		
		while(true){
			sb.insert(0,  " ");
			sb.insert(0, i);
			if (!i.equals(noeudD))
				i = predecesseurs.get(i);
			else
				break;
		}
		return "Dijkstra" + System.lineSeparator()
		+ distance + System.lineSeparator() + sb.toString();
	}
	
	
	/**
	 * @brief Algorithme de Dijkstra
	 * @param g Graphe contenant les noeuds
	 * @param noeudD Noeud de départ
	 * @param noeudA Noeud d'arrivée
	 * @return Le chemin le plus court entre noeudD et noeudA
	 * @throws ArcNégatifEx S'il existe au moins un arc négatif dans le graphe
	 * @throws NoPathEx Si aucun chemin entre le noeud de départ et le noeud
	 * 					d'arrivé n'est trouvé
	 */
	@Override
	public String algorithme(IGraphe g, Integer noeudD, Integer noeudA)
											throws ArcNégatifEx, NoPathEx {
		if (!estOK(g)) { throw new ArcNégatifEx(); }
		
		// HashMap des longueurs de chemins pour chaque noeud
		Map<Integer, Integer> distances = initialisationDistances(g, noeudD);
		
		// HashMap de prédécecesseur de chaque noeud
		Map<Integer, Integer> predecesseurs = new HashMap<>();
		
		// Noeud sur lequel sera calculé les longueur des chemins
		Integer noeudActuel = noeudD;
		
		/* Tant que le noeud d'arrivée n'a pas la certitude d'avoir eu le chemin
		   le plus court, poursuivre l'algorithme */
		while(noeudActuel != noeudA) {
			//System.out.println(distances + " " + predecesseurs);
			/* 
			 * Si aucun noeud n'a été choisi comme prochain noeud de calcul
			 * et que le noeud d'arrivée n'a pas encore trouvé de chemin
			 * certifié optimisé, alors il n'y a pas de chemins atteignable
			 * pour le noeud d'arrivée
			 */
			if (noeudActuel == null)
				throw new NoPathEx();
			
			// On actualise en permanence le noeud "actuel"
			noeudActuel = choixNoeudSuivant(g, distances, predecesseurs, noeudActuel, noeudA);
		}
		return affichage(predecesseurs, noeudD, noeudA, distances.get(noeudA));
	}
}
