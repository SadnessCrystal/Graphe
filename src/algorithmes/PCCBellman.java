package algorithmes;

import java.util.ArrayList;

import exceptions.CircuitAbsorbantEx;
import graphes.Igraph;

public class PCCBellman {
	
	private PCCBellman() {
		throw new IllegalStateException("Classe utilitaire");
	}
	

	public static boolean estOK(Igraph g) {

		ArrayList<Integer>[] listePredecesseurs = listePredecesseurs(g);
		ArrayList<Integer> listeNoeudsTries = new ArrayList<>();
		
		int[][] listeNoeudsDistancesPredecesseurs = new int[g.getNbNoeuds()][3];
		int idxNoeudTriee = 0;
		boolean aucunNoeudEnleve = true;
		
		for (int i=0; i<g.getNbNoeuds(); ++i) {
			listeNoeudsDistancesPredecesseurs[i][0] = -1;
		}
		
		// On supprime les prédécesseurs qui sont déjà triés
		while(idxNoeudTriee < g.getNbNoeuds()) { 

			aucunNoeudEnleve = true;
			
			for (int i=0; i<g.getNbNoeuds(); ++i) {
				if (listePredecesseurs[i].isEmpty()) {
					listeNoeudsTries.add(i);
					listePredecesseurs[i].add(-1);
					listeNoeudsDistancesPredecesseurs[idxNoeudTriee++][0] = i;
					aucunNoeudEnleve = false;
				}
			}
			
			for (int i=0; i<g.getNbNoeuds(); ++i) {
				for (int j : listeNoeudsTries) {
					if (listePredecesseurs[i].contains(j)) {
						listePredecesseurs[i].remove(Integer.valueOf(j));
					}
				}
			}
			
			if (aucunNoeudEnleve)
				return false;
			
			listeNoeudsTries.clear();
		}
		
		return true;
	}
	
	
	private static ArrayList<Integer>[] listePredecesseurs(Igraph g){
		ArrayList<Integer>[] listePredecesseurs = new ArrayList[g.getNbNoeuds()];
		
		for (int i=0; i<g.getNbNoeuds(); ++i) {
			listePredecesseurs[i] = new ArrayList<>();
		}
		
		// Initialisation des prédécesseurs
		for (int noeudPrec=0; noeudPrec<g.getNbNoeuds(); ++noeudPrec) {
			for (int noeudSucc=0; noeudSucc<g.getNbNoeuds(); ++noeudSucc) {
				if (g.aArc(noeudPrec+1, noeudSucc+1)) {
					listePredecesseurs[noeudSucc].add(noeudPrec);
				}
			}
		}
		
		return listePredecesseurs;
	}
	
	
	private static int[][] triParNiveau(Igraph g) {

		ArrayList<Integer>[] listePredecesseurs = listePredecesseurs(g);
		ArrayList<Integer> listeNoeudsTries = new ArrayList<>();
		
		int[][] listeNoeudsDistancesPredecesseurs = new int[g.getNbNoeuds()][3];
		int idxNoeudTriee = 0;
		
		for (int i=0; i<g.getNbNoeuds(); ++i) {
			listeNoeudsDistancesPredecesseurs[i][0] = -1;
		}
		
		// On supprime les prédécesseurs qui sont déjà triés
		while(idxNoeudTriee < g.getNbNoeuds()) { 
			
			for (int i=0; i<g.getNbNoeuds(); ++i) {
				if (listePredecesseurs[i].isEmpty()) {
					listeNoeudsTries.add(i);
					listePredecesseurs[i].add(-1);
					listeNoeudsDistancesPredecesseurs[idxNoeudTriee++][0] = i;
				}
			}
			
			for (int i=0; i<g.getNbNoeuds(); ++i) {
				for (int j : listeNoeudsTries) {
					if (listePredecesseurs[i].contains(j)) {
						listePredecesseurs[i].remove(Integer.valueOf(j));
					}
				}
			}
			
			listeNoeudsTries.clear();
		}
		
		return listeNoeudsDistancesPredecesseurs;
	}
	
	
	public static String algorithmeBellman(Igraph g, int numNoeudDepart, int numNoeudArrivee) throws CircuitAbsorbantEx {
		if (!estOK(g)) {
			throw new CircuitAbsorbantEx();
		}
		
		int[][] listeNoeudsDistancesPredecesseurs = triParNiveau(g);
		
		
		return "";
	}
}
