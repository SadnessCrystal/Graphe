package graphes;

import java.util.List;

import exceptions.NoPathEx;

public interface IPCC {
	/**
	 * @param g Graphe à vérifier
	 * @return true si le graphe est compatible avec l'algorithme, false sinon
	 */
	boolean estOK(IGraphe g);
	
	/**
	 * @param g Graphe 
	 * @param noeudDepart Noeud de départ de l'algorithme
	 * @param noeudArrivee Noeud d'arrivée de l'algorithme
	 * @param chemin[inout] Chemin utilisé par l'algorithme
	 * @return Distance la plus courte entre le noeud de départ et d'arrivée
	 * @throws NoPathEx Aucun chemin entre noeudDepart et noeudArrivee
	 */
	int pc(IGraphe g, Integer noeudDepart, Integer noeudArrivee, List<Integer> chemin) throws NoPathEx;
}
