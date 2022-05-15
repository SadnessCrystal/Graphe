package pcc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.CircuitAbsorbantEx;
import exceptions.NoPathEx;
import graphes.IGraphe;
import graphes.IPCC;

public class Bellman implements IPCC {
	/**
	 * @brief Suppression des noeud avec pas ou plus de predecesseurs
	 * @param g Graphe
	 * @param listePredecesseurs HashMap des prédecesseurs de chaque noeud
	 * @param listeNoeudsTriesTemporaire Liste des noeuds venant d'être triés
	 * @param listeNoeudsParNiveau Liste des noeuds triés par niveau
	 */
	private static void nettoyagePredecesseurs(IGraphe g, Map<Integer, List<Integer>> listePredecesseurs, List<Integer> listeNoeudsTriesTemporaire, List<Integer> listeNoeudsParNiveau) {
		for (Integer i : g) {
			if (listePredecesseurs.containsKey(i) && listePredecesseurs.get(i).isEmpty()) {
				listeNoeudsTriesTemporaire.add(i);
				listePredecesseurs.remove(i);
				listeNoeudsParNiveau.add(i);
			}
		}
	}
	
	
	/**
	 * @brief On supprime les noeuds de la liste de la liste de
	 * prédesesseurs de tous les autres noeuds
	 * @param g Graphe
	 * @param listePredecesseurs HashMap des prédecesseurs de chaque noeud
	 * @param listeNoeudsTriesTemporaire Liste des noeuds venant d'être triés
	 */
	private static void suppressionPredecesseurs(IGraphe g, Map<Integer, List<Integer>> listePredecesseurs, List<Integer> listeNoeudsTriesTemporaire) {
		for (Integer i : g) {
			for (Integer j : listeNoeudsTriesTemporaire) {
				/* Si un noeud venant d'être trié est encore présent en tant que prédécesseur,
				 * alors le supprimer de la liste des prédecesseurs */
				if (listePredecesseurs.containsKey(i) && listePredecesseurs.get(i).contains(j))
					listePredecesseurs.get(i).remove(j);
			}
		}
	}
	
	@Override
	public boolean estOK(IGraphe g) {
		List<Integer> listeNoeudsParNiveau = new ArrayList<>();// Liste des noeuds triées par niveau
		Map<Integer, List<Integer>> listePredecesseurs = listePredecesseurs(g);// Liste de prédecesseur de chaque noeud
				
		// Tant qu'il existe des noeuds non triés
		while(listeNoeudsParNiveau.size() < g.getNbSommets()) { 
			ArrayList<Integer> listeNoeudsTriesTemporaire = new ArrayList<>();// Liste temporaire de noeuds venant d'être attribué
			
			int nbNoeudTriee = listeNoeudsParNiveau.size();
			
			// Detection des noeuds de niveau supérieur
			nettoyagePredecesseurs(g, listePredecesseurs, listeNoeudsTriesTemporaire, listeNoeudsParNiveau);
			
			// Si aucun noeud n'a été trié, c'est la preuve d'un circuit
			if (listeNoeudsParNiveau.size() == nbNoeudTriee)
				return false;
			
			// Suppression de la liste des prédecesseurs des noeuds venant d'être triés
			suppressionPredecesseurs(g, listePredecesseurs, listeNoeudsTriesTemporaire);
		}
		// Si l'algorithme est parvenu jusque là, c'est la preuve de l'abscence de circuit.
		return true;
	}
	
	/**
	 * @param g Graphe
	 * @return Retourne les prédecesseurs de chaque noeud
	 */
	private static Map<Integer, List<Integer>> listePredecesseurs(IGraphe g){
		Map<Integer, List<Integer>> predecesseurs = new HashMap<>();
		// Insersion des prédécesseurs
		for (Integer noeudSucc : g) {
			predecesseurs.put(noeudSucc, new ArrayList<>());
			for (Integer noeudPrec : g)
				if (g.aArc(noeudPrec, noeudSucc))
					predecesseurs.get(noeudSucc).add(noeudPrec);
		}
		return predecesseurs;
	}
	
	/**
	 * @brief Trie par niveau les noeuds
	 * @param g Graphe
	 * @param distances La distance du noeud de départ pour chaque noeud
	 * @param listeNoeudsParNiveau Liste des noeuds triée par niveau
	 * @param noeudD Noeud de départ
	 * @param noeudA Noeud d'arrivée
	 * @throws NoPathEx
	 */
	private static void triParNiveau(IGraphe g, Map<Integer, Integer> distances, List<Integer> listeNoeudsParNiveau, Integer noeudD, Integer noeudA) throws NoPathEx{
		// Contient la liste de prédecesseur de chaque noeud
		Map<Integer, List<Integer>> listePredecesseurs = listePredecesseurs(g);
		distances.put(noeudD, 0);
		
		// Suppression les prédecesseurs inutiles
		preSuppresionPredecesseurs(g, noeudD, noeudA, listePredecesseurs);
		
		int nbNoeudTrieeMax = listePredecesseurs.size();
		
		// Tant qu'il existe des noeuds non triés
		while(listeNoeudsParNiveau.size() < nbNoeudTrieeMax) { 
			ArrayList<Integer> listeNoeudsTriesTemporaire = new ArrayList<>(); // Liste temporaire de noeuds venant d'être attribué
			
			// Detection des noeuds de niveau supérieur
			nettoyagePredecesseurs(g, listePredecesseurs, listeNoeudsTriesTemporaire, listeNoeudsParNiveau);
			
			// Suppression de la liste des prédecesseurs des noeuds venant d'être triés
			suppressionPredecesseurs(g, listePredecesseurs, listeNoeudsTriesTemporaire);
		}
	}

	/**
	 * @brief Supprime tous les prédecesseurs, jusqu'au noeud de départ
	 * @param g Graphe
	 * @param noeudD Noeud de départ
	 * @param noeudA Noeud d'arrivée
	 * @param listePredecesseurs Liste des prédecesseurs de chaque noeud
	 */
	private static void preSuppresionPredecesseurs(IGraphe g, Integer noeudD, Integer noeudA,
			Map<Integer, List<Integer>> listePredecesseurs) throws NoPathEx {
		/* On supprimer temporairement le noeud de départ pour que
		 * ses successeurs ne soient pas supprimés*/
		listePredecesseurs.remove(noeudD);
		
		// Tant qu'il reste des noeud sans prédécesseurs : 
		while(listePredecesseurs.containsValue(new ArrayList<>())) {
			for (Integer noeudASupprimer : g) {
				// Si le noeud n'a pas ou plus de prédecesseurs
				if (listePredecesseurs.containsKey(noeudASupprimer) && listePredecesseurs.get(noeudASupprimer).isEmpty()) {
					/* Si le noeud en question est le noeud de départ,
					 * cela signifie qu'il n'existe aucun chemin entre
					 * lui et le noeud de départ*/
					if (noeudASupprimer.equals(noeudA))
						throw new NoPathEx();
					
					listePredecesseurs.remove(noeudASupprimer);
					
					// Parcourir la liste de prédecesseurs de noeuds.
					for (Integer i : g)
						// Si le noeud à supprimer existe en tant que prédecesseur, le supprimer
						if (listePredecesseurs.containsKey(i) && listePredecesseurs.get(i).contains(noeudASupprimer))
							listePredecesseurs.get(i).remove(noeudASupprimer);
				}
			}
		}
		
		// Reinsérer le noeud de départ
		listePredecesseurs.put(noeudD, new ArrayList<>());
	}
	

	/*
	 * 	Vérifie si toutes les conditions suivantes sont réunies :
	 * 		- Il existe un arc entre le noeud "actuel" et le noeud actuellement testé
	 *  	- L'une des conditions suivantes doit être remplie :
	 * 			- Aucun chemin avec le noeud successeur n'a encore été calculé
	 * 			- Le chemin testé est plus optimisé que le chemin actuel
	 */
	private static boolean peutRemplacerDistanceActuelle(IGraphe g, Map<Integer, Integer> distances, Integer noeudS, Integer noeudP) {
		return g.aArc(noeudP, noeudS) && (!distances.containsKey(noeudS) || 
				distances.get(noeudS) > g.getValuation(noeudP, noeudS) + 
				distances.get(noeudP));
	}
	
	@Override
	public int pc(IGraphe g, Integer noeudD, Integer noeudA, List<Integer> chemin) throws CircuitAbsorbantEx, NoPathEx {
		if (!estOK(g))
			throw new CircuitAbsorbantEx();
		
		Map<Integer, Integer> distances = new HashMap<>();
		Map<Integer, Integer> predecesseurs = new HashMap<>();
		List<Integer> listeNoeudsParNiveau = new ArrayList<>();
		
		triParNiveau(g, distances, listeNoeudsParNiveau, noeudD, noeudA);
		
		for (Integer noeudS : listeNoeudsParNiveau) {
			for (int idx = 0, noeudP = listeNoeudsParNiveau.get(idx); noeudP != noeudS; noeudP = listeNoeudsParNiveau.get(++idx)) {
				if (peutRemplacerDistanceActuelle(g, distances, noeudS, noeudP)) {
					distances.put(noeudS, g.getValuation(noeudP, noeudS) + distances.get(noeudP));
					predecesseurs.put(noeudS, noeudP);
				}	
			}
		}
		
		Integer noeud = noeudA;
		
		while(noeud != null) {
			chemin.add(0, noeud);
			noeud = predecesseurs.get(noeud);
		}
		
		return distances.get(noeudA);
	}
}
