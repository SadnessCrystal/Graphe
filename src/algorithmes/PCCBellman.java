package algorithmes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.CircuitAbsorbantEx;
import exceptions.NoPathEx;
import graphes.IGraph;

public class PCCBellman {
	private PCCBellman() {
		throw new IllegalStateException("Classe utilitaire");
	}

	// Penser à vérifier boucle foreach avec listePredecesseur
	
	/**
	 * @brief
	 * @param g Graphe
	 * @param listePredecesseurs HashMap des prédecesseurs de chaque noeud
	 * @param listeNoeudsTries Liste vide, contiendra la liste des noeuds triés
	 * @param triParNiveau 
	 * @param nbNoeudTriee Nombre de noeuds triés
	 * @return
	 */
	private static int nettoyagePredecesseurs(IGraph g, Map<String, List<String>> listePredecesseurs, List<String> listeNoeudsTries, List<String> triParNiveau, int nbNoeudTriee) {
		for (String i : g.noeuds()) {
			if (listePredecesseurs.containsKey(i) && listePredecesseurs.get(i).isEmpty()) {
				listeNoeudsTries.add(i);
				listePredecesseurs.remove(i);
				nbNoeudTriee++;
				triParNiveau.add(i);
			}
		}
		return nbNoeudTriee;
	}
	
	
	/**
	 * @brief On supprime les noeuds de la liste de la liste de
	 * prédesesseurs de tous les autres noeuds
	 * @param g Graphe
	 * @param listePredecesseurs HashMap des prédecesseurs de chaque noeud
	 * @param listeNoeudsTries Liste des noeuds venant d'être triés
	 */
	private static void suppressionPredecesseurs(IGraph g, Map<String, List<String>> listePredecesseurs, List<String> listeNoeudsTries) {
		for (String i : g.noeuds()) {
			for (String j : listeNoeudsTries) {
				/* Si un noeud venant d'être trié est encore présent en tant que prédécesseur,
				 * alors le supprimer de la liste des prédecesseurs */
				if (listePredecesseurs.containsKey(i) && listePredecesseurs.get(i).contains(j)) {
					listePredecesseurs.get(i).remove(j);
				}
			}
		}
	}
	

	public static boolean estOK(IGraph g) {
		// Liste des noeuds triées par niveau
		List<String> triParNiveau = new ArrayList<>();
		
		// Liste de prédecesseur de chaque noeud
		Map<String, List<String>> listePredecesseurs = listePredecesseurs(g);
		
		int nbNoeudTriee = 0;	// Nombre de noeuds triée
				
		// Tant qu'il existe des noeuds non triés
		while(nbNoeudTriee < g.getNbNoeuds()) { 
			// Liste temporaire de noeuds venant d'être attribué
			ArrayList<String> listeNoeudsTries = new ArrayList<>();
			
			// Detection des noeuds de niveau supérieur
			int tmp = nettoyagePredecesseurs(g, listePredecesseurs, listeNoeudsTries, triParNiveau, nbNoeudTriee);
			
			// Si aucun noeud n'a été trié, c'est la preuve d'un circuit
			if (tmp - nbNoeudTriee == 0)
				return false;
			
			nbNoeudTriee = tmp;// Actualisation du nombre de noeuds
			
			// Suppression de la liste des prédecesseurs des noeuds venant d'être triés
			suppressionPredecesseurs(g, listePredecesseurs, listeNoeudsTries);
		}
		// Si l'algorithme est parvenu jusque là, c'est la preuve de l'abscence de circuit.
		return true;
	}
	
	
	private static Map<String, List<String>> listePredecesseurs(IGraph g){
		/*
		 * Tableau de nbNoeuds dont chaque case comporte la liste de
		 * prédecesseur du noeud correspodnant
		 */
		Map<String, List<String>> predecesseurs = new HashMap<>();
		
		// Insersion des prédécesseurs
		for (String noeudSucc: g.noeuds()) {
			predecesseurs.put(noeudSucc, new ArrayList<>());
			for (String noeudPrec : g.noeuds()) {
				if (g.aArc(noeudPrec, noeudSucc))
					predecesseurs.get(noeudSucc).add(noeudPrec);
			}
		}
		
		return predecesseurs;
	}
	
	
	/**
	 * 
	 * @param g
	 * @param distances Liste de la longueur des différants chemins
	 * @param predecesseurs Prédecesseur de chaque noeud
	 */
	private static void triParNiveau(IGraph g, Map<String, Integer> distances, List<String> triParNiveau, String noeudDepart) {

		// Contient la liste de prédecesseur de chaque noeud
		Map<String, List<String>> listePredecesseurs = listePredecesseurs(g);
		
		boolean truc = true;
		int nbNoeudTriee = 0;	// Nombre de noeuds triée
		
		distances.put(noeudDepart, 0);
		
		ArrayList<String> listeNoeudsSupprimes = new ArrayList<>();
		
		// On supprime les prédécesseurs qui sont déjà triés
		while(nbNoeudTriee < g.getNbNoeuds()) { 
			ArrayList<String> listeNoeudsTries = new ArrayList<>();
			
			nbNoeudTriee = nettoyagePredecesseurs(g, listePredecesseurs, listeNoeudsTries, triParNiveau, nbNoeudTriee);
			
			if (truc) {
				if (listeNoeudsTries.contains(noeudDepart)) {
					for (String i : triParNiveau)
						listeNoeudsSupprimes.add(i);
					triParNiveau.clear();
					truc = false;
					triParNiveau.add(noeudDepart);
					listeNoeudsSupprimes.remove(noeudDepart);
				}
				
				else {
					for (String i : triParNiveau)
						listeNoeudsSupprimes.add(i);
					triParNiveau.clear();
				}
			}
			
			suppressionPredecesseurs(g, listePredecesseurs, listeNoeudsTries);
		}
	}


	/*
	 * 	Vérifie si toutes les conditions suivantes sont réunies :
	 * 		- Il existe un arc entre le noeud "actuel" et le noeud actuellement testé
	 *  	- L'une des conditions suivantes doit être remplie :
	 * 			- Aucun chemin avec le noeud successeur n'a encore été calculé
	 * 			- Le chemin testé est plus optimisé que le chemin actuel
	 */
	private static boolean peutRemplacerDistanceActuelle(IGraph g, Map<String, Integer> distances, String idxNoeudA, String idxNoeudPred, String noeudDepart) {
		System.out.println("Predecesseur : " + idxNoeudPred + "\nSuccesseur : " + idxNoeudA);
		return g.aArc(idxNoeudPred, idxNoeudA) && (!distances.containsKey(idxNoeudA) || 
				distances.get(idxNoeudA) > g.getValeur(idxNoeudPred, idxNoeudA) + 
				distances.get(idxNoeudPred));
	}
	
	
	public static String algorithmeBellman(IGraph g, String numNoeudDepart, String numNoeudArrivee) throws CircuitAbsorbantEx, NoPathEx {
		if (!estOK(g)) { throw new CircuitAbsorbantEx(); }
		
		Map<String, Integer> distances = new HashMap<>();
		Map<String, String> predecesseurs = new HashMap<>();
		List<String> triParNiveau = new ArrayList<>();
		
		triParNiveau(g, distances, triParNiveau, numNoeudDepart);
		if (numNoeudDepart.equals("B")) System.out.println(triParNiveau);
		for (String idxNoeudA : triParNiveau) {
			if (numNoeudDepart.equals("B")) System.out.println(idxNoeudA + " : ");
			int idx = 0;
			for (String idxNoeudPred = triParNiveau.get(idx); !idxNoeudPred.equals(idxNoeudA); idxNoeudPred = triParNiveau.get(++idx)) {
				if (numNoeudDepart.equals("B")) System.out.println("    " + idxNoeudPred);
				if (peutRemplacerDistanceActuelle(g, distances, idxNoeudA, idxNoeudPred, numNoeudDepart)) {
					if (numNoeudDepart.equals("B")) System.out.println(distances);
					if (numNoeudDepart.equals("B")) System.out.println(predecesseurs);
					distances.put(idxNoeudA, g.getValeur(idxNoeudPred, idxNoeudA) + distances.get(idxNoeudPred));
					predecesseurs.put(idxNoeudA, idxNoeudPred);
				}
			}
		}
		
		return affichage(g, predecesseurs, numNoeudDepart, numNoeudArrivee);
	}


	private static String affichage(IGraph g, Map<String, String> predecesseurs, String idxNoeudDepart, String idxNoeudArrivee) {
		StringBuilder sb = new StringBuilder();
		
		String i = idxNoeudArrivee;
		
		while(i != null){
			if (!i.equals(idxNoeudArrivee))
				sb.insert(0,  " - ");
			sb.insert(0, i);
			i = predecesseurs.get(i);
		}
		
		return sb.toString();
	}
}
