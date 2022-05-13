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
	 * @brief
	 * @param g Graphe
	 * @param listePredecesseurs HashMap des prédecesseurs de chaque noeud
	 * @param listeNoeudsTriesTemporaire Liste vide, contiendra la liste des noeuds triés
	 * @param listeNoeudsParNiveau 
	 * @param nbNoeudTriee Nombre de noeuds triés
	 * @return
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
			for (int j : listeNoeudsTriesTemporaire) {
				/* Si un noeud venant d'être trié est encore présent en tant que prédécesseur,
				 * alors le supprimer de la liste des prédecesseurs */
				//System.out.println(listePredecesseurs.containsKey(10) && listePredecesseurs.get(10).contains(8));
				if (listePredecesseurs.containsKey(i) && listePredecesseurs.get(i).contains(j))
					listePredecesseurs.get(i).remove(Integer.valueOf(j));
			}
		}
	}
	
	public boolean estOK(IGraphe g) {
		List<Integer> listeNoeudsParNiveau = new ArrayList<>();// Liste des noeuds triées par niveau
		Map<Integer, List<Integer>> listePredecesseurs = listePredecesseurs(g);// Liste de prédecesseur de chaque noeud
				
		// Tant qu'il existe des noeuds non triés
		while(listeNoeudsParNiveau.size() < g.getNbSommets()) { 
			ArrayList<Integer> listeNoeudsTriesTemporaire = new ArrayList<>();// Liste temporaire de noeuds venant d'être attribué
			
			int nbNoeudTriee = listeNoeudsParNiveau.size();
			
			//System.out.println("Avant : " + listePredecesseurs + " " + listeNoeudsParNiveau);
			
			// Detection des noeuds de niveau supérieur
			nettoyagePredecesseurs(g, listePredecesseurs, listeNoeudsTriesTemporaire, listeNoeudsParNiveau);
			
			// Si aucun noeud n'a été trié, c'est la preuve d'un circuit
			if (listeNoeudsParNiveau.size() == nbNoeudTriee)
				return false;
			
			//System.out.println("Après : " + listePredecesseurs + " " + listeNoeudsParNiveau + " (" + listeNoeudsTriesTemporaire + ")");
			
			// Suppression de la liste des prédecesseurs des noeuds venant d'être triés
			suppressionPredecesseurs(g, listePredecesseurs, listeNoeudsTriesTemporaire);
		}
		// Si l'algorithme est parvenu jusque là, c'est la preuve de l'abscence de circuit.
		return true;
	}
	
	
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
	 * 
	 * @param g
	 * @param distances
	 * @param listeNoeudsParNiveau
	 * @param noeudD
	 * @throws NoPathEx
	 */
	private static void triParNiveau(IGraphe g, Map<Integer, Integer> distances, List<Integer> listeNoeudsParNiveau, Integer noeudD, Integer noeudA) throws NoPathEx{
		// Contient la liste de prédecesseur de chaque noeud
		Map<Integer, List<Integer>> listePredecesseurs = listePredecesseurs(g);
		distances.put(noeudD, 0);
		
		listePredecesseurs.remove(noeudD);
		
		// Tant qu'il reste des ArrayList vides : 
		while(listePredecesseurs.containsValue(new ArrayList<>())) {
			for (Integer i : g) {
				if (listePredecesseurs.containsKey(i) && listePredecesseurs.get(i).isEmpty()) {
					if (i.equals(noeudA))
						throw new NoPathEx();
					suppressionRecursive(g, listePredecesseurs, i, noeudA);
				}
			}
		}
		
		listePredecesseurs.put(noeudD, new ArrayList<>());
		
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
	
	
	private static void suppressionRecursive(IGraphe g, Map<Integer, List<Integer>> listePredecesseurs, Integer noeudASupprimer, Integer noeudA) {
		assert(listePredecesseurs.get(noeudASupprimer).isEmpty());
		
		listePredecesseurs.remove(noeudASupprimer);
		
		// Parcourir la liste de prédecesseurs de noeuds.
		for (Integer i : g) {
			// Si le noeud à supprimer existe en tant que prédecesseur, le supprimer
			if (listePredecesseurs.containsKey(i) && listePredecesseurs.get(i).contains(noeudASupprimer))
				listePredecesseurs.get(i).remove(noeudASupprimer);
			
			if (listePredecesseurs.containsKey(i) && listePredecesseurs.get(i).contains(noeudASupprimer)) {
				if (i.equals(noeudA))
					throw new NoPathEx();
				listePredecesseurs.remove(i); // Supprimer le noeud de la HashMap
				suppressionRecursive(g, listePredecesseurs, i, noeudA);
			}
		}
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
