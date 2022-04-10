package algorithmes;

import java.util.ArrayList;
import java.util.LinkedList;

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
		

		
//		for (int i=0; i<g.getNbNoeuds(); ++i) {
//			System.out.print("\n"+String.valueOf((char)(i+1+64)) + " : ");
//			for (int j=0; j<listePredecesseurs[i].size(); ++j)
//				System.out.print(String.valueOf((char)(listePredecesseurs[i].get(j)+1+64)) + " ");
//		}
		
		int[][] listeNoeudsDistancesPredecesseurs = new int[g.getNbNoeuds()][3];
		int idxNoeudTriee = 0;
		
		for (int i=0; i<g.getNbNoeuds(); ++i) {
			listeNoeudsDistancesPredecesseurs[i][0] = -100;
			
			if (!listePredecesseurs[i].isEmpty()) {
				listeNoeudsDistancesPredecesseurs[i][1] = -100;
			}
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
		
		
//		for (int i=0; i<g.getNbNoeuds(); ++i) {
//			System.out.print(String.valueOf((char)(listeNoeudsDistancesPredecesseurs[i][0]+1+64)) + " ");
//		}
		
		return listeNoeudsDistancesPredecesseurs;
	}
	
	
	public static String algorithmeBellman(Igraph g, int numNoeudDepart, int numNoeudArrivee) throws CircuitAbsorbantEx {
		if (!estOK(g)) {
			throw new CircuitAbsorbantEx();
		}
		
		int[][] listeNoeudsDistancesPredecesseurs = triParNiveau(g);
		
		for (int idxNoeudActuel=0; idxNoeudActuel<g.getNbNoeuds(); ++idxNoeudActuel) {
			for (int idxNoeudPred=0; idxNoeudPred<idxNoeudActuel; ++idxNoeudPred) {
				int vraiIdxActuel = listeNoeudsDistancesPredecesseurs[idxNoeudActuel][0];
				int vraiIdxPred = listeNoeudsDistancesPredecesseurs[idxNoeudPred][0];
				
				if (g.aArc(vraiIdxPred+1, vraiIdxActuel+1) && (listeNoeudsDistancesPredecesseurs[idxNoeudActuel][1] == -100 || 
						listeNoeudsDistancesPredecesseurs[idxNoeudActuel][1] > g.valeurArc(vraiIdxPred+1, vraiIdxActuel+1) + 
							listeNoeudsDistancesPredecesseurs[idxNoeudPred][1])) {
					listeNoeudsDistancesPredecesseurs[idxNoeudActuel][1] = g.valeurArc(vraiIdxPred+1, vraiIdxActuel+1) + 
							listeNoeudsDistancesPredecesseurs[idxNoeudPred][1];
					listeNoeudsDistancesPredecesseurs[idxNoeudActuel][2] = idxNoeudPred;
				}
			}
		}
		
		return affichage(listeNoeudsDistancesPredecesseurs, numNoeudDepart-1, numNoeudArrivee-1);
	}


	private static String affichage(int[][] listeNoeudsDistancesPredecesseurs, int idxNoeudDepart, int idxNoeudArrivee) {
		StringBuilder sb = new StringBuilder();
		System.out.println("\n");
		for (int i=0; i<listeNoeudsDistancesPredecesseurs.length; ++i) {
			System.out.println(String.valueOf((char)(listeNoeudsDistancesPredecesseurs[i][0]+1+64)) + " : " +
					listeNoeudsDistancesPredecesseurs[i][1] + "[" + String.valueOf((char)(listeNoeudsDistancesPredecesseurs[i][2]+1+64)) + "]");
		}
		
		for (int i=0; i<listeNoeudsDistancesPredecesseurs.length; ++i) {
			if (listeNoeudsDistancesPredecesseurs[i][0] == idxNoeudArrivee) {
				do{
					if (listeNoeudsDistancesPredecesseurs[i][0] != idxNoeudArrivee)
						sb.insert(0,  " - ");
					sb.insert(0, String.valueOf((char)(listeNoeudsDistancesPredecesseurs[i][0]+1 + 64)));
					i = listeNoeudsDistancesPredecesseurs[i][2];
				}while(listeNoeudsDistancesPredecesseurs[i][0] != -1);
				
				break;
			}
		}
		
		return sb.toString();
	}
}
